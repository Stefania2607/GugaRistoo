package DAO;

import controller.Bean.Piatto;
import controller.Exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PiattoDAO {

    /**
     * Ottiene una connessione al DB usando la tua ConnectionFactory.
     * Tutto passa da ConnectionFactory (no JNDI / DataSource Tomcat).
     */
    private Connection getConnection() throws SQLException {
        return ConnectionFactory.getConnection();
    }

    // ------------------------------------------------------------------------
    //  METODI DI LETTURA
    // ------------------------------------------------------------------------

    public List<Piatto> findVini(String sotto) throws DAOException {
        List<Piatto> lista = new ArrayList<>();

        String baseSql =
                "SELECT id, nome, descrizione, categoria, tipo_bevanda, prezzo, immagine_url " +
                        "FROM piatto WHERE categoria = 'bevanda' ";

        if (sotto == null) sotto = "tutti";

        String sql = switch (sotto) {
            case "rossi" -> baseSql + "AND tipo_bevanda = 'vini_rossi'";
            case "bianchi" -> baseSql + "AND tipo_bevanda = 'vini_bianchi'";
            case "rose" -> baseSql + "AND tipo_bevanda = 'vini_rose'";
            case "bollicine" -> baseSql + "AND tipo_bevanda = 'vini_bollicine'";
            default -> baseSql + "AND tipo_bevanda LIKE 'vini_%'";
        };

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }
            return lista;

        } catch (SQLException e) {
            throw new DAOException("Errore DB in PiattoDAO.findVini(sotto=" + sotto + ")", e);
        }
    }
    public List<Piatto> findByCategoria(String categoria) throws DAOException {
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
                    lista.add(mapRow(rs));
                }
            }
            return lista;

        } catch (SQLException e) {
            throw new DAOException("Errore DB in PiattoDAO.findByCategoria(categoria=" + categoria + ")", e);
        }
    }

    /** Comodità per ottenere tutte le bevande (categoria = 'bevanda'). */
    public List<Piatto> findBevande() throws DAOException {
        return findByCategoria("bevanda");
    }
    public List<Piatto> findBevandeByTipo(String tipoBevanda) throws DAOException {
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
                    lista.add(mapRow(rs));
                }
            }
            return lista;

        } catch (SQLException e) {
            throw new DAOException("Errore DB in PiattoDAO.findBevandeByTipo(tipoBevanda=" + tipoBevanda + ")", e);
        }
    }

    /** Restituisce tutti i piatti della tabella, senza filtri. */
    public List<Piatto> findAll() throws DAOException {
        List<Piatto> lista = new ArrayList<>();

        String sql =
                "SELECT id, nome, descrizione, categoria, tipo_bevanda, prezzo, immagine_url " +
                        "FROM piatto";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }
            return lista;

        } catch (SQLException e) {
            throw new DAOException("Errore DB in PiattoDAO.findAll()", e);
        }
    }
    public void insert(Piatto p) throws DAOException {

        String sql =
                "INSERT INTO piatto (nome, descrizione, categoria, tipo_bevanda, prezzo, immagine_url) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNome());
            ps.setString(2, p.getDescrizione());
            ps.setString(3, p.getCategoria());
            ps.setString(4, p.getTipoBevanda()); // può essere NULL
            ps.setBigDecimal(5, p.getPrezzo());
            ps.setString(6, p.getImmagineUrl());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Errore DB in PiattoDAO.insert(nome=" + p.getNome() + ")", e);
        }
    }
    public Piatto findById(int id) throws DAOException {
        String sql =
                "SELECT id, nome, descrizione, categoria, tipo_bevanda, prezzo, immagine_url " +
                        "FROM piatto WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                } else {
                    return null; // nessun piatto con quell'id
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Errore DB in PiattoDAO.findById(id=" + id + ")", e);
        }
    }

    private Piatto mapRow(ResultSet rs) throws SQLException {
        Piatto p = new Piatto();
        p.setId(rs.getInt("id"));
        p.setNome(rs.getString("nome"));
        p.setDescrizione(rs.getString("descrizione"));
        p.setCategoria(rs.getString("categoria"));
        p.setTipoBevanda(rs.getString("tipo_bevanda"));
        p.setPrezzo(rs.getBigDecimal("prezzo"));
        p.setImmagineUrl(rs.getString("immagine_url"));
        return p;
    }
}
