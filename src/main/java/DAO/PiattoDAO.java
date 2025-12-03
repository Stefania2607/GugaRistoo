package DAO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Controller.Bean.Piatto;

public class PiattoDAO {

    private DataSource ds;

    public PiattoDAO() {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            ds = (DataSource) envCtx.lookup("jdbc/ristorante");
        } catch (Exception e) {
            throw new RuntimeException("Errore JNDI nel costruttore di PiattoDAO", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    // ----------------- LETTURA -----------------
    public List<Piatto> findAll() {
        List<Piatto> lista = new ArrayList<>();

        String sql = "SELECT id, nome, descrizione, prezzo, categoria FROM piatto";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Piatto p = new Piatto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescrizione(rs.getString("descrizione"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setCategoria(rs.getString("categoria"));

                lista.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Errore nella findAll()", e);
        }

        return lista;
    }

    // ----------------- INSERT -----------------
    public void insert(Piatto p) {

        String sql = "INSERT INTO piatto (nome, descrizione, prezzo, categoria) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNome());
            ps.setString(2, p.getDescrizione());
            ps.setDouble(3, p.getPrezzo());
            ps.setString(4, p.getCategoria());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Errore nella insert()", e);
        }
    }
}
