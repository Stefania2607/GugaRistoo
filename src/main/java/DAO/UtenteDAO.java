package DAO;

import controller.Bean.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteDAO {


    private Connection getConnection() throws SQLException {
        return ConnectionFactory.getConnection();
    }

    public Utente login(String username, String password) {

        String sql = "SELECT id, username, password, nome, cognome, ruolo " +
                "FROM utente WHERE username = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Utente u = new Utente();
                    u.setId(rs.getInt("id"));
                    u.setUsername(rs.getString("username"));
                    u.setPassword(rs.getString("password"));
                    u.setNome(rs.getString("nome"));
                    u.setCognome(rs.getString("cognome"));
                    u.setRuolo(rs.getString("ruolo"));
                    return u;
                } else {
                    return null; // credenziali sbagliate
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il login", e);
        }
    }
    public boolean existsByUsername(String username) throws SQLException {
        String sql = "SELECT 1 FROM utente WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true se esiste almeno una riga
            }
        }
    }

    public void insert(Utente u) throws SQLException {
        String sql = "INSERT INTO utente (username, password, nome, cognome, ruolo) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getNome());
            ps.setString(4, u.getCognome());
            ps.setString(5, u.getRuolo());

            ps.executeUpdate();
        }
    }
}

