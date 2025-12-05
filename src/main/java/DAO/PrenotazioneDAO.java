package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Controller.Bean.Prenotazione;

public class PrenotazioneDAO {

    private Connection getConnection() throws SQLException {
        return ConnectionFactory.getConnection();
    }

    // ========= METODO USATO DA creaPrenotazioneSoloTavolo =========
    public void inserisciPrenotazione(int utenteId,
                                      String data,
                                      String ora,
                                      int persone,
                                      String note,
                                      String tipo) throws SQLException {

        String sql = "INSERT INTO prenotazione " +
                "(utente_id, data_prenotazione, ora_prenotazione, num_persone, note, tipo) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, utenteId);
            ps.setString(2, data);
            ps.setString(3, ora);
            ps.setInt(4, persone);
            ps.setString(5, note);
            ps.setString(6, tipo);

            ps.executeUpdate();
        }
    }

    // ========= VARIANTE CHE RESTITUISCE L'ID =========
    public int inserisciPrenotazioneReturnId(int utenteId,
                                             String data,
                                             String ora,
                                             int persone,
                                             String note,
                                             String tipo) throws SQLException {

        String sql = "INSERT INTO prenotazione " +
                "(utente_id, data_prenotazione, ora_prenotazione, num_persone, note, tipo) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, utenteId);
            ps.setString(2, data);
            ps.setString(3, ora);
            ps.setInt(4, persone);
            ps.setString(5, note);
            ps.setString(6, tipo);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return -1;
    }

    // ========= LETTURA PER UTENTE =========
    public List<Prenotazione> findByUtente(int utenteId) throws SQLException {
        List<Prenotazione> lista = new ArrayList<>();

        String sql = "SELECT id, utente_id, data_prenotazione, ora_prenotazione, " +
                "num_persone, note, tipo, stato " +
                "FROM prenotazione " +
                "WHERE utente_id = ? " +
                "ORDER BY data_prenotazione, ora_prenotazione";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, utenteId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Prenotazione p = new Prenotazione();
                    p.setId(rs.getInt("id"));
                    p.setUtenteId(rs.getInt("utente_id"));
                    p.setDataPrenotazione(rs.getString("data_prenotazione"));
                    p.setOraPrenotazione(rs.getString("ora_prenotazione"));
                    p.setNumPersone(rs.getInt("num_persone"));
                    p.setNote(rs.getString("note"));
                    p.setTipo(rs.getString("tipo"));
                    p.setStato(rs.getString("stato"));
                    lista.add(p);
                }
            }
        }

        return lista;
    }

    // ========= DELETE =========
    public void deleteByIdAndUtente(int idPrenotazione, int utenteId) throws SQLException {
        String sql = "DELETE FROM prenotazione WHERE id = ? AND utente_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPrenotazione);
            ps.setInt(2, utenteId);
            ps.executeUpdate();
        }
    }
}

