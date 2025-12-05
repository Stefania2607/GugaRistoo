package DAO;

import Controller.Bean.Piatto;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PiattoDAO {

    /**
     * Ottiene una connessione al DB usando la tua ConnectionFactory.
     * Qui NON usiamo JNDI / DataSource di Tomcat: tutto passa da ConnectionFactory.
     */
    private Connection getConnection() throws SQLException {
        return ConnectionFactory.getConnection();
    }
    public List<Piatto> findVini(String sotto) {
        List<Piatto> lista = new ArrayList<>();

        String baseSql =
                "SELECT id, nome, descrizione, categoria, tipo_bevanda, prezzo, immagine_url " +
                        "FROM piatto WHERE categoria = 'bevanda' ";

        String sql;

        if (sotto == null) {
            sotto = "tutti";
        }

        switch (sotto) {
            case "rossi":
                sql = baseSql + "AND tipo_bevanda = 'vini_rossi'";
                break;
            case "bianchi":
                sql = baseSql + "AND tipo_bevanda = 'vini_bianchi'";
                break;
            case "rose":
                sql = baseSql + "AND tipo_bevanda = 'vini_rose'";
                break;
            case "bollicine":
                sql = baseSql + "AND tipo_bevanda = 'vini_bollicine'";
                break;
            case "tutti":
            default:
                sql = baseSql + "AND tipo_bevanda LIKE 'vini_%'";
                break;
        }

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Piatto p = new Piatto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescrizione(rs.getString("descrizione"));
                p.setCategoria(rs.getString("categoria"));
                p.setTipoBevanda(rs.getString("tipo_bevanda"));
                p.setPrezzo(rs.getBigDecimal("prezzo"));
                p.setImmagineUrl(rs.getString("immagine_url"));
                lista.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Errore in findVini(" + sotto + ")", e);
        }

        return lista;
    }
    // ------------------------------------------------------------------------
    //  METODI DI LETTURA
    // ------------------------------------------------------------------------

    /**
     * Restituisce tutti i piatti di una certa categoria
     * (es. "antipasto", "primo", "secondo", "dolce", "bevanda"...).
     */
    public List<Piatto> findByCategoria(String categoria) {
        List<Piatto> lista = new ArrayList<>();

        String sql =
                "SELECT id, nome, descrizione, categoria, tipo_bevanda, prezzo, immagine_url " +
                        "FROM piatto " +
                        "WHERE categoria = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, categoria);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Piatto p = new Piatto();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setDescrizione(rs.getString("descrizione"));
                    p.setCategoria(rs.getString("categoria"));
                    p.setTipoBevanda(rs.getString("tipo_bevanda"));   // per piatti non-bevanda sarà NULL
                    p.setPrezzo(rs.getBigDecimal("prezzo"));
                    p.setImmagineUrl(rs.getString("immagine_url"));
                    lista.add(p);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Errore in findByCategoria(" + categoria + ")", e);
        }

        return lista;
    }

    /**
     * Comodità per ottenere tutte le bevande (categoria = 'bevanda').
     */
    public List<Piatto> findBevande() {
        return findByCategoria("bevanda");
    }

    /**
     * Restituisce solo le bevande di un certo tipo specifico
     * (es. tipoBevanda = "vini", "birre", "alcolici", "analcolici", "acqua", "altre").
     */
    public List<Piatto> findBevandeByTipo(String tipoBevanda) {
        List<Piatto> lista = new ArrayList<>();

        String sql =
                "SELECT id, nome, descrizione, categoria, tipo_bevanda, prezzo, immagine_url " +
                        "FROM piatto " +
                        "WHERE categoria = 'bevanda' AND tipo_bevanda = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tipoBevanda);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Piatto p = new Piatto();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setDescrizione(rs.getString("descrizione"));
                    p.setCategoria(rs.getString("categoria"));
                    p.setTipoBevanda(rs.getString("tipo_bevanda"));
                    p.setPrezzo(rs.getBigDecimal("prezzo"));
                    p.setImmagineUrl(rs.getString("immagine_url"));
                    lista.add(p);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Errore in findBevandeByTipo(" + tipoBevanda + ")", e);
        }

        return lista;
    }

    /**
     * Restituisce tutti i piatti della tabella, senza filtri.
     */
    public List<Piatto> findAll() {
        List<Piatto> lista = new ArrayList<>();

        String sql =
                "SELECT id, nome, descrizione, categoria, tipo_bevanda, prezzo, immagine_url " +
                        "FROM piatto";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Piatto p = new Piatto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescrizione(rs.getString("descrizione"));
                p.setCategoria(rs.getString("categoria"));
                p.setTipoBevanda(rs.getString("tipo_bevanda"));
                // Usiamo SEMPRE BigDecimal per i soldi
                p.setPrezzo(rs.getBigDecimal("prezzo"));
                p.setImmagineUrl(rs.getString("immagine_url"));
                lista.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Errore nella findAll()", e);
        }

        return lista;
    }

    // ------------------------------------------------------------------------
    //  INSERT
    // ------------------------------------------------------------------------

    /**
     * Inserisce un nuovo piatto nel DB.
     *
     * NOTA:
     * - tipo_bevanda può essere NULL per piatti normali (primi, secondi, dolci, ecc.)
     * - per le bevande, setta categoria = "bevanda" e tipoBevanda = es. "vini", "birre", ecc.
     */
    public void insert(Piatto p) {

        String sql =
                "INSERT INTO piatto " +
                        "  (nome, descrizione, categoria, tipo_bevanda, prezzo, immagine_url) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNome());
            ps.setString(2, p.getDescrizione());
            ps.setString(3, p.getCategoria());
            ps.setString(4, p.getTipoBevanda());        // può essere NULL
            ps.setBigDecimal(5, p.getPrezzo());
            ps.setString(6, p.getImmagineUrl());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Errore nella insert()", e);
        }
    }
}
