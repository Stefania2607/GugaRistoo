package Controller.Applicativo;

import Controller.Bean.Utente;
import DAO.UtenteDAO;

public class LoginApplicativo {

    private UtenteDAO utenteDAO;

    public LoginApplicativo() {
        this.utenteDAO = new UtenteDAO();
    }

    /**
     * Esegue la logica applicativa del login.
     * Non si occupa della sessione e della navigazione.
     *
     * @param username inserito dall'utente
     * @param password inserita dall'utente
     * @return oggetto Utente se credenziali corrette, null altrimenti
     */
    public Utente autentica(String username, String password) {

        // Validazioni applicative eventualmente aggiuntive:
        if (username == null || username.isBlank() ||
                password == null || password.isBlank()) {

            return null;  // credenziali mancanti → login fallito
        }

        // Chiamata al DAO: vero cuore del login applicativo
        Utente u = utenteDAO.login(username, password);

        // Logica applicativa aggiuntiva (se volevi, es. controlli, blocchi, log)
        // Esempio: controllare se l'utente è attivo:
        // if (u != null && !u.isAttivo()) return null;

        return u;
    }
}
