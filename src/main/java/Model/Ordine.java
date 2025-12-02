package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Ordine {

    private final int id;
    private final Utente cameriere;
    private final LocalDateTime dataOra;
    private StatoOrdine stato;
    private final Tavolo tavolo;
    private final List<RigaOrdine> righe;

    public Ordine(int id, Utente cameriere, Tavolo tavolo) {
        if (id <= 0) throw new IllegalArgumentException("Id ordine invalido.");
        if (cameriere == null) throw new IllegalArgumentException("Cameriere obbligatorio.");
        if (tavolo == null) throw new IllegalArgumentException("Tavolo obbligatorio.");

        this.id = id;
        this.cameriere = cameriere;
        this.tavolo = tavolo;
        this.dataOra = LocalDateTime.now();
        this.stato = StatoOrdine.APERTO;
        this.righe = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public Utente getCameriere() {
        return cameriere;
    }

    public LocalDateTime getDataOra() {
        return dataOra;
    }

    public StatoOrdine getStato() {
        return stato;
    }

    public void setStato(StatoOrdine stato) {
        this.stato = stato;
    }

    public Tavolo getTavolo() {
        return tavolo;
    }

    public List<RigaOrdine> getRighe() {
        return righe;
    }

    public void aggiungiRiga(RigaOrdine riga) {
        righe.add(riga);
    }

    public double calcolaTotale() {
        return righe.stream()
                .mapToDouble(r -> r.getPrezzoUnitario() * r.getQuantita())
                .sum();
    }

    @Override
    public String toString() {
        return "Ordine{" +
                "id=" + id +
                ", cameriere=" + cameriere.getUsername() +
                ", tavolo=" + tavolo.getId() +
                ", stato=" + stato +
                ", totale=" + calcolaTotale() +
                '}';
    }
}
