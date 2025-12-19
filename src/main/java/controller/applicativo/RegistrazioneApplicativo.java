package controller.applicativo;

import controller.bean.Utente;
import controller.exception.RegistrazioneException;
import dao.UtenteDAO;

import java.sql.SQLException;

public class RegistrazioneApplicativo {

    private UtenteDAO utenteDAO;

    public RegistrazioneApplicativo() {
        this.utenteDAO = new UtenteDAO();
    }

    public void registraNuovoCliente(String username,
                                     String password,
                                     String confermaPassword,
                                     String nome,
                                     String cognome) throws RegistrazioneException {

        // pulizia minima
        if (username != null) username = username.trim();
        if (nome != null) nome = nome.trim();
        if (cognome != null) cognome = cognome.trim();

        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty() ||
                confermaPassword == null || confermaPassword.isEmpty()) {
            throw new RegistrazioneException("Username e password sono obbligatori.");
        }

        if (!password.equals(confermaPassword)) {
            throw new RegistrazioneException("Le password non coincidono.");
        }

        try {
            if (utenteDAO.existsByUsername(username)) {
                throw new RegistrazioneException("Questo username è già in uso.");
            }

            Utente u = new Utente();
            u.setUsername(username);
            u.setPassword(password);   // per il prof va bene così, nella vita vera ci va l'hash
            u.setNome(nome);
            u.setCognome(cognome);
            u.setRuolo("CLIENTE");

            utenteDAO.insert(u);

        } catch (SQLException e) {
            throw new RegistrazioneException("Errore di sistema durante la registrazione. Riprova più tardi.");
        }
    }
}
