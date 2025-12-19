package controller.applicativo;

import controller.Bean.Utente;
import DAO.UtenteDAO;

public class LoginApplicativo {

    private final UtenteDAO utenteDAO;

    public LoginApplicativo() {
        this.utenteDAO = new UtenteDAO();
    }
    public Utente autentica(String username, String password) {

        // Validazioni applicative eventualmente aggiuntive:
        if (username == null || username.isBlank() ||
                password == null || password.isBlank()) {

            return null;  // credenziali mancanti → login fallito
        }

        // Chiamata al DAO
        return utenteDAO.login(username, password);
    }
}
