package dao;

import controller.bean.Tavolo;   // crea un bean semplice id/numero/posti/stato
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TavoloDAO {

    private Connection getConnection() throws SQLException {
        return ConnectionFactory.getConnection();
    }

    // Tavoli liberi per quel giorno/ora (qui lo facciamo semplice: guardiamo solo stato)
    public List<Tavolo> findLiberi() throws SQLException {
        String sql = "SELECT id, numero, posti, stato FROM tavolo WHERE stato = 'LIBERO' ORDER BY numero";
        List<Tavolo> lista = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tavolo t = new Tavolo();
                t.setId(rs.getInt("id"));
                t.setNumero(rs.getInt("numero"));
                t.setPosti(rs.getInt("posti"));
                t.setStato(rs.getString("stato"));
                lista.add(t);
            }
        }
        return lista;
    }

    /** Prova ad occupare il tavolo solo se è LIBERO.
     *  Ritorna true se è stato occupato, false se qualcun altro l'ha preso prima.
     */
    public boolean occupaSeLibero(int tavoloId) throws SQLException {
        String sql = "UPDATE tavolo SET stato = 'OCCUPATO' " +
                "WHERE id = ? AND stato = 'LIBERO'";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, tavoloId);
            int rows = ps.executeUpdate();
            return rows == 1;
        }
    }

    public void libera(int tavoloId) throws SQLException {
        String sql = "UPDATE tavolo SET stato = 'LIBERO' WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, tavoloId);
            ps.executeUpdate();
        }
    }

    public Integer findTavoloIdByPrenotazione(int prenotazioneId) throws SQLException {
        String sql = "SELECT tavolo_id FROM prenotazione WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, prenotazioneId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("tavolo_id");
                }
            }
        }
        return null;
    }
    public int inserisciPrenotazioneConTavolo(int utenteId,
                                              int tavoloId,
                                              String data,
                                              String ora,
                                              int persone,
                                              String note,
                                              String tipo) throws SQLException {

        String sql = "INSERT INTO prenotazione " +
                "(utente_id, tavolo_id, data_prenotazione, ora_prenotazione, num_persone, note, tipo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, utenteId);
            ps.setInt(2, tavoloId);
            ps.setString(3, data);
            ps.setString(4, ora);
            ps.setInt(5, persone);
            ps.setString(6, note);
            ps.setString(7, tipo);

            int rows = ps.executeUpdate();
            if (rows == 0) return -1;

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

}
