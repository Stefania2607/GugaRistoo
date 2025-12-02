package Model;
public class Tavolo {

    private final int id;
    private final int numeroPosti;
    private StatoTavolo stato;
    private Utente cameriereAssegnato; // solo CAMERIERE

    public Tavolo(int id, int numeroPosti) {
        if (id <= 0) throw new IllegalArgumentException("Id tavolo deve essere positivo.");
        if (numeroPosti <= 0) throw new IllegalArgumentException("Numero posti deve essere positivo.");

        this.id = id;
        this.numeroPosti = numeroPosti;
        this.stato = StatoTavolo.LIBERO;
    }

    public int getId() {
        return id;
    }

    public int getNumeroPosti() {
        return numeroPosti;
    }

    public StatoTavolo getStato() {
        return stato;
    }

    public void setStato(StatoTavolo stato) {
        this.stato = stato;
    }

    public Utente getCameriereAssegnato() {
        return cameriereAssegnato;
    }

    public void setCameriereAssegnato(Utente cameriere) {
        this.cameriereAssegnato = cameriere;
    }

    @Override
    public String toString() {
        return "Tavolo{" +
                "id=" + id +
                ", numeroPosti=" + numeroPosti +
                ", stato=" + stato +
                ", cameriere=" + (cameriereAssegnato != null ? cameriereAssegnato.getUsername() : "Nessuno") +
                '}';
    }
}
