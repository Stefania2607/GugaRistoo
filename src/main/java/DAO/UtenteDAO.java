package DAO;

import Controller.Bean.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteDAO {

    // NIENTE DataSource, niente costruttore JNDI

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
}

