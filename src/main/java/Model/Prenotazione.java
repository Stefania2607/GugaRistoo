package Model;

import java.time.LocalDateTime;

public class Prenotazione {

    private final int id;
    private final Utente cliente;     // Ruolo CLIENTE
    private final LocalDateTime dataOra;
    private final int numeroPersone;
    private Tavolo tavolo;            // può essere assegnato subito o dopo
    private final TipoOrdinePrenotazione tipoOrdine;
    private boolean pagatoOnline;

    public Prenotazione(int id, Utente cliente, LocalDateTime dataOra,
                        int numeroPersone, TipoOrdinePrenotazione tipoOrdine) {

        if (id <= 0) throw new IllegalArgumentException("Id prenotazione invalido.");
        if (cliente == null) throw new IllegalArgumentException("Cliente obbligatorio.");
        if (dataOra == null) throw new IllegalArgumentException("Data e ora obbligatorie.");
        if (numeroPersone <= 0) throw new IllegalArgumentException("Numero persone invalido.");

        this.id = id;
        this.cliente = cliente;
        this.dataOra = dataOra;
        this.numeroPersone = numeroPersone;
        this.tipoOrdine = tipoOrdine;
        this.pagatoOnline = false;
    }

    public int getId() {
        return id;
    }

    public Utente getCliente() {
        return cliente;
    }

    public LocalDateTime getDataOra() {
        return dataOra;
    }

    public int getNumeroPersone() {
        return numeroPersone;
    }

    public Tavolo getTavolo() {
        return tavolo;
    }

    public void setTavolo(Tavolo tavolo) {
        this.tavolo = tavolo;
    }

    public TipoOrdinePrenotazione getTipoOrdine() {
        return tipoOrdine;
    }

    public boolean isPagatoOnline() {
        return pagatoOnline;
    }

    public void setPagatoOnline(boolean pagatoOnline) {
        this.pagatoOnline = pagatoOnline;
    }

    @Override
    public String toString() {
        return "Prenotazione{" +
                "id=" + id +
                ", cliente=" + cliente.getUsername() +
                ", dataOra=" + dataOra +
                ", numeroPersone=" + numeroPersone +
                ", tavolo=" + (tavolo != null ? tavolo.getId() : "Non assegnato") +
                ", tipoOrdine=" + tipoOrdine +
                ", pagatoOnline=" + pagatoOnline +
                '}';
    }
}
