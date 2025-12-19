package DAO;

import controller.Bean.Carrello;
import controller.Bean.RigaCarrello;
import controller.Bean.Utente;
import controller.Bean.Ordine;
import controller.Exception.DAOException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrdineDAO {

    private Connection getConnection() throws SQLException {
        return ConnectionFactory.getConnection();
    }

    // =========================================================
    // CREA ORDINE DA CARRELLO (con eventuale id_prenotazione)
    // =========================================================
    public int creaOrdineDaCarrello(Carrello carrello,
                                    Utente utente,
                                    String nomeCliente,
                                    String email,
                                    Integer idTavolo,
                                    String orarioPrenotazione,
                                    Integer idPrenotazione) throws DAOException {

        if (carrello == null) throw new DAOException("Carrello nullo");
        if (nomeCliente == null) throw new DAOException("Nome cliente nullo");
        if (email == null) throw new DAOException("Email nulla");

        final String sqlOrdine =
                "INSERT INTO ordine " +
                        " (id_utente, nome_cliente, email, totale, stato, id_tavolo, orario_prenotazione, id_prenotazione) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        final String sqlRiga =
                "INSERT INTO ordine_riga " +
                        " (id_ordine, id_piatto, nome_piatto, quantita, prezzo_unit, subtotale) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = getConnection()) {
            con.setAutoCommit(false);

            try {
                int idOrdine = insertTestaOrdine(con, sqlOrdine, carrello, utente, nomeCliente, email, idTavolo, orarioPrenotazione, idPrenotazione);
                insertRigheOrdine(con, sqlRiga, idOrdine, carrello);

                con.commit();
                return idOrdine;

            } catch (SQLException e) {
                try { con.rollback(); } catch (SQLException ignored) {}
                throw new DAOException("Errore durante il salvataggio dell'ordine", e);

            } finally {
                try { con.setAutoCommit(true); } catch (SQLException ignored) {}
            }

        } catch (SQLException e) {
            throw new DAOException("Errore di connessione durante il salvataggio dell'ordine", e);
        }
    }

    private int insertTestaOrdine(Connection con,
                                  String sqlOrdine,
                                  Carrello carrello,
                                  Utente utente,
                                  String nomeCliente,
                                  String email,
                                  Integer idTavolo,
                                  String orarioPrenotazione,
                                  Integer idPrenotazione) throws SQLException, DAOException {

        BigDecimal totale = carrello.getTotale();
        if (totale == null) totale = BigDecimal.ZERO;

        try (PreparedStatement psOrd = con.prepareStatement(sqlOrdine, Statement.RETURN_GENERATED_KEYS)) {

            // 1) id_utente
            if (utente != null) {
                psOrd.setInt(1, utente.getId());
            } else {
                psOrd.setNull(1, Types.INTEGER);
            }

            // 2) nome_cliente
            psOrd.setString(2, nomeCliente);

            // 3) email
            psOrd.setString(3, email);

            // 4) totale
            psOrd.setBigDecimal(4, totale);

            // 5) stato
            psOrd.setString(5, "CONFERMATO");

            // 6) id_tavolo
            if (idTavolo != null) {
                psOrd.setInt(6, idTavolo);
            } else {
                psOrd.setNull(6, Types.INTEGER);
            }

            // 7) orario_prenotazione
            psOrd.setString(7, orarioPrenotazione);

            // 8) id_prenotazione
            if (idPrenotazione != null) {
                psOrd.setInt(8, idPrenotazione);
            } else {
                psOrd.setNull(8, Types.INTEGER);
            }

            psOrd.executeUpdate();

            try (ResultSet rs = psOrd.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }

            throw new DAOException("Impossibile recuperare l'id dell'ordine appena creato");
        }
    }

    private void insertRigheOrdine(Connection con,
                                   String sqlRiga,
                                   int idOrdine,
                                   Carrello carrello) throws SQLException {

        try (PreparedStatement psRiga = con.prepareStatement(sqlRiga)) {

            for (Map.Entry<Integer, RigaCarrello> entry : carrello.getRighe().entrySet()) {
                Integer idPiatto = entry.getKey();
                RigaCarrello r = entry.getValue();

                BigDecimal prezzoUnit = r.getPrezzoUnitario();
                if (prezzoUnit == null) prezzoUnit = BigDecimal.ZERO;

                BigDecimal subtotale = prezzoUnit.multiply(BigDecimal.valueOf(r.getQuantita()));

                psRiga.setInt(1, idOrdine);
                psRiga.setInt(2, idPiatto);
                psRiga.setString(3, r.getNome());
                psRiga.setInt(4, r.getQuantita());
                psRiga.setBigDecimal(5, prezzoUnit);
                psRiga.setBigDecimal(6, subtotale);

                psRiga.addBatch();
            }

            psRiga.executeBatch();
        }
    }

    // =========================================================
    // STORICO ORDINI PER UTENTE (Bean)
    // =========================================================
    public List<Ordine> trovaPerUtente(int idUtente) throws DAOException {

        final String sql =
                "SELECT " +
                        "  o.id, " +
                        "  o.id_utente, " +
                        "  o.nome_cliente, " +
                        "  o.email, " +
                        "  o.totale, " +
                        "  o.stato, " +
                        "  o.data_ora, " +
                        "  o.id_tavolo, " +
                        "  o.orario_prenotazione, " +
                        "  o.id_prenotazione, " +
                        "  CASE " +
                        "    WHEN o.stato = 'ANNULLATO' THEN 'ANNULLATO' " +
                        "    WHEN TIMESTAMPDIFF(MINUTE, o.data_ora, NOW()) >= 45 THEN 'CONSEGNATO' " +
                        "    WHEN TIMESTAMPDIFF(MINUTE, o.data_ora, NOW()) >= 15 THEN 'IN_CONSEGNA' " +
                        "    ELSE 'IN_PREPARAZIONE' " +
                        "  END AS stato_logico, " +
                        "  GROUP_CONCAT(CONCAT(r.quantita, 'x ', r.nome_piatto) " +
                        "               ORDER BY r.nome_piatto SEPARATOR ', ') AS riepilogo " +
                        "FROM ordine o " +
                        "LEFT JOIN ordine_riga r ON o.id = r.id_ordine " +
                        "WHERE o.id_utente = ? " +
                        "GROUP BY " +
                        "  o.id, o.id_utente, o.nome_cliente, o.email, " +
                        "  o.totale, o.stato, o.data_ora, " +
                        "  o.id_tavolo, o.orario_prenotazione, o.id_prenotazione " +
                        "ORDER BY o.data_ora DESC";

        List<Ordine> lista = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUtente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapRowToOrdineBean(rs));
                }
            }

            return lista;

        } catch (SQLException e) {
            throw new DAOException("Errore nel recupero dello storico ordini", e);
        }
    }

    private Ordine mapRowToOrdineBean(ResultSet rs) throws SQLException {
        Ordine o = new Ordine();
        o.setId(rs.getInt("id"));
        o.setIdUtente(rs.getInt("id_utente"));
        o.setNomeCliente(rs.getString("nome_cliente"));
        o.setEmail(rs.getString("email"));
        o.setTotale(rs.getBigDecimal("totale"));
        o.setStato(rs.getString("stato"));
        o.setDataOra(rs.getTimestamp("data_ora"));
        o.setIdTavolo((Integer) rs.getObject("id_tavolo"));
        o.setOrarioPrenotazione(rs.getString("orario_prenotazione"));
        o.setIdPrenotazione((Integer) rs.getObject("id_prenotazione"));
        o.setStatoLogico(rs.getString("stato_logico"));
        o.setRiepilogoPiatti(rs.getString("riepilogo"));
        return o;
    }

    // =========================================================
    // ANNULLA ORDINE (cambia lo stato)
    // =========================================================
    public void annullaOrdine(int idOrdine, int idUtente) throws DAOException {

        final String sql =
                "UPDATE ordine " +
                        "SET stato = 'ANNULLATO' " +
                        "WHERE id = ? " +
                        "  AND id_utente = ? " +
                        "  AND stato = 'CONFERMATO' " +
                        "  AND TIMESTAMPDIFF(MINUTE, data_ora, NOW()) < 15";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idOrdine);
            ps.setInt(2, idUtente);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Errore durante l'annullamento dell'ordine", e);
        }
    }

    // =========================================================
    // RECUPERA LA PRENOTAZIONE ASSOCIATA A UN ORDINE
    // =========================================================
    public Integer findPrenotazioneIdByOrdine(int ordineId) throws DAOException {

        final String sql = "SELECT id_prenotazione FROM ordine WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ordineId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Object val = rs.getObject("id_prenotazione");
                    return (val != null) ? (Integer) val : null;
                }
            }

            return null;

        } catch (SQLException e) {
            throw new DAOException("Errore nel recupero della prenotazione associata all'ordine", e);
        }
    }

    // =========================================================
    // DELETE FISICO (se mai servirà)
    // =========================================================
    public void deleteOrdine(int ordineId) throws DAOException {

        final String sql = "DELETE FROM ordine WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ordineId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Errore durante la cancellazione fisica dell'ordine", e);
        }
    }
}
