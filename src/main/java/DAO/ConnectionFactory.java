package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    // Parametri di connessione (adatta a quello che hai su MySQL)
    private static final String URL =
            "jdbc:mysql://localhost:3306/ristorante?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // Blocco statico: carica il driver UNA volta sola
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Impossibile caricare il driver MySQL", e);
        }
    }

    // Metodo unico per ottenere una Connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
