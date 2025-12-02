package Model;

public class Utente {

    public enum Ruolo {
        ADMIN,
        CAMERIERE,
        CLIENTE
    }

    private final String username;
    private String password;
    private String nome;
    private String cognome;
    private Ruolo ruolo;

    public Utente(String username, String password, String nome, String cognome, Ruolo ruolo) {

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username non può essere vuoto.");
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password non può essere vuota.");
        }

        this.username = username;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.ruolo = ruolo;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password non valida.");
        }
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "username='" + username + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", ruolo=" + ruolo +
                '}';
    }
}
