package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Factory centralizzata per la gestione delle connessioni al DB del ristorante.
 * Struttura identica a quella che mi hai mandato, ma adattata al tuo progetto.
 */
public class ConnectionFactory {

    private static String connectionUrl;
    private static String currentUser;
    private static String currentPass;

    static {
        try (InputStream input = ConnectionFactory.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new RuntimeException("Impossibile trovare il file db.properties nel classpath!");
            }

            // Carico il driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            Properties properties = new Properties();
            properties.load(input);

            // Configurazione di default (es. utente generico dell'app)
            connectionUrl = properties.getProperty("CONNECTION_URL");
            currentUser   = properties.getProperty("LOGIN_USER");
            currentPass   = properties.getProperty("LOGIN_PASS");

            System.out.println("[ConnectionFactory] Configurazione iniziale caricata. USER = " + currentUser);

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(
                    "Errore durante il caricamento delle impostazioni DB: " + e.getMessage(),
                    e
            );
        }
    }

    /**
     * 🔹 Restituisce una nuova connessione viva ogni volta.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionUrl, currentUser, currentPass);
    }

    /**
     * 🔹 Cambia ruolo (utente DB) aggiornando le credenziali per le connessioni future.
     *     Non chiude connessioni già aperte in altri thread.
     *
     *  Usa chiavi del tipo:
     *   - ADMIN_USER / ADMIN_PASS
     *   - CAMERIERE_USER / CAMERIERE_PASS
     *   - CLIENTE_USER / CLIENTE_PASS
     *   lette da db.properties.
     */
    public static void Cambio_Di_Ruolo(Ruolo ruolo) throws SQLException {
        try (InputStream input = ConnectionFactory.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new RuntimeException("Impossibile trovare il file db.properties nel classpath!");
            }

            Properties properties = new Properties();
            properties.load(input);

            // Esempio: se ruolo = ADMIN → ADMIN_USER / ADMIN_PASS
            String prefix = ruolo.name(); // ADMIN, CAMERIERE, CLIENTE...

            String newUser = properties.getProperty(prefix + "_USER");
            String newPass = properties.getProperty(prefix + "_PASS");

            if (newUser == null || newPass == null) {
                throw new SQLException("Credenziali non trovate per il ruolo: " + ruolo +
                        " (mancano " + prefix + "_USER o " + prefix + "_PASS in db.properties)");
            }

            currentUser = newUser;
            currentPass = newPass;

            // Log diagnostico
            System.out.println("[ConnectionFactory] Cambio ruolo a " + ruolo + " (user: " + currentUser + ")");

        } catch (IOException e) {
            throw new SQLException("Errore durante il cambio di ruolo: " + e.getMessage(), e);
        }
    }
}
