package dao;

import controller.bean.Prenotazione;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioneDAO {

    // =========================================================
    // GESTIONE CONNESSIONE
    // =========================================================
    private Connection getConnection() throws SQLException {
        return ConnectionFactory.getConnection();
    }

    // =========================================================
    // 1) INSERIMENTO "SOLO TAVOLO" (senza tavolo_id) - NO RETURN
    //    Usato quando non ti serve l'id generato
    // =========================================================
    public void inserisciPrenotazione(int utenteId,
                                      String data,
                                      String ora,
                                      int persone,
                                      String note,
                                      String tipo) throws SQLException {

        final String sql =
                "INSERT INTO prenotazione " +
                        " (utente_id, data_prenotazione, ora_prenotazione, " +
                        "  num_persone, note, tipo, stato) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, utenteId);
            ps.setString(2, data);
            ps.setString(3, ora);
            ps.setInt(4, persone);
            ps.setString(5, note);
            ps.setString(6, tipo);
            ps.setString(7, "ATTIVA");

            ps.executeUpdate();
        }
    }

    // =========================================================
    // 2) INSERIMENTO (senza tavolo) CON RETURN ID
    //    Usato come "prenotazione base" per ordine, solo tavolo, ecc.
    // =========================================================
    public int inserisciPrenotazioneReturnId(int utenteId,
                                             String data,
                                             String ora,
                                             int persone,
                                             String note,
                                             String tipo) throws SQLException {

        final String sql =
                "INSERT INTO prenotazione " +
                        " (utente_id, data_prenotazione, ora_prenotazione, " +
                        "  num_persone, note, tipo, stato) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, utenteId);
            ps.setString(2, data);
            ps.setString(3, ora);
            ps.setInt(4, persone);
            ps.setString(5, note);
            ps.setString(6, tipo);
            ps.setString(7, "ATTIVA");

            int rows = ps.executeUpdate();
            if (rows == 0) {
                return -1;
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            return -1;
        }
    }

    // =========================================================
    // 3) INSERIMENTO CON TAVOLO (ordinaSmart) + RETURN ID
    // =========================================================
    public int inserisciPrenotazioneConTavolo(int utenteId,
                                              int tavoloId,
                                              String data,
                                              String ora,
                                              int persone,
                                              String note,
                                              String tipo) throws SQLException {

        final String sql =
                "INSERT INTO prenotazione " +
                        " (utente_id, tavolo_id, data_prenotazione, ora_prenotazione, " +
                        "  num_persone, note, tipo, stato) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, utenteId);
            ps.setInt(2, tavoloId);
            ps.setString(3, data);
            ps.setString(4, ora);
            ps.setInt(5, persone);
            ps.setString(6, note);
            ps.setString(7, tipo);
            ps.setString(8, "ATTIVA");

            int rows = ps.executeUpdate();
            if (rows == 0) {
                return -1;
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            return -1;
        }
    }

    // =========================================================
    // 4) LETTURA PRENOTAZIONI PER UTENTE
    //    LEFT JOIN su tavolo per avere anche numero tavolo
    // =========================================================
    public List<Prenotazione> findByUtente(int utenteId) throws SQLException {
        List<Prenotazione> lista = new ArrayList<>();

        final String sql =
                "SELECT p.id, p.utente_id, p.data_prenotazione, p.ora_prenotazione, " +
                        "       p.num_persone, p.note, p.tipo, p.stato, " +
                        "       p.tavolo_id, t.numero AS tavolo_numero " +
                        "FROM prenotazione p " +
                        "LEFT JOIN tavolo t ON p.tavolo_id = t.id " +
                        "WHERE p.utente_id = ? " +
                        "ORDER BY p.data_prenotazione, p.ora_prenotazione, p.id";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, utenteId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Prenotazione p = mapRowToPrenotazione(rs);
                    lista.add(p);
                }
            }
        }

        return lista;
    }

    // =========================================================
    // 5) DELETE CONTROLLATA (id prenotazione + utente)
    //    Ritorna il numero di righe cancellate (0 se niente)
    // =========================================================
    public int deleteByIdAndUtente(int idPrenotazione, int idUtente) throws SQLException {
        final String sql =
                "DELETE FROM prenotazione " +
                        "WHERE id = ? AND utente_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPrenotazione);
            ps.setInt(2, idUtente);

            return ps.executeUpdate();
        }
    }

    // =========================================================
    // 6) (OPZIONALE) DELETE SOLO PER ID - utile per admin
    // =========================================================
    public int deleteById(int idPrenotazione) throws SQLException {
        final String sql = "DELETE FROM prenotazione WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPrenotazione);
            return ps.executeUpdate();
        }
    }


    // =========================================================
    // 7) (OPZIONALE) UPDATE STATO PRENOTAZIONE
    //    Esempio: 'ATTIVA' -> 'ANNULLATA'
    // =========================================================
    public int aggiornaStato(int idPrenotazione, String nuovoStato) throws SQLException {
        final String sql =
                "UPDATE prenotazione " +
                        "SET stato = ? " +
                        "WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuovoStato);
            ps.setInt(2, idPrenotazione);

            return ps.executeUpdate();
        }
    }

    // =========================================================
    // METODO DI SUPPORTO: MAPPING ResultSet -> Prenotazione
    // =========================================================
    private Prenotazione mapRowToPrenotazione(ResultSet rs) throws SQLException {
        Prenotazione p = new Prenotazione();

        p.setId(rs.getInt("id"));
        p.setUtenteId(rs.getInt("utente_id"));
        p.setDataPrenotazione(rs.getString("data_prenotazione"));
        p.setOraPrenotazione(rs.getString("ora_prenotazione"));
        p.setNumPersone(rs.getInt("num_persone"));
        p.setNote(rs.getString("note"));
        p.setTipo(rs.getString("tipo"));
        p.setStato(rs.getString("stato"));

        // tavolo_id e tavolo_numero possono essere NULL
        Object tavoloIdObj = rs.getObject("tavolo_id");
        if (tavoloIdObj != null) {
            p.setTavoloId((Integer) tavoloIdObj);
        } else {
            p.setTavoloId(null);
        }

        Object tavoloNumObj = rs.getObject("tavolo_numero");
        if (tavoloNumObj != null) {
            p.setTavoloNumero((Integer) tavoloNumObj);
        } else {
            p.setTavoloNumero(null);
        }

        return p;
    }
}
