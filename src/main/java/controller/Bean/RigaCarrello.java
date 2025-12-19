package controller.Bean;

import java.math.BigDecimal;

public class RigaCarrello {

    private Piatto piatto;
    private int quantita;

    public RigaCarrello(Piatto piatto, int quantita) {
        this.piatto = piatto;
        this.quantita = quantita;
    }

    public Piatto getPiatto() {
        return piatto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void incrementa() {
        this.quantita++;
    }

    public void decrementa() {
        this.quantita--;
    }

    public BigDecimal getPrezzoUnitario() {
        return piatto.getPrezzo();
    }

    public BigDecimal getTotaleRiga() {
        return getPrezzoUnitario().multiply(BigDecimal.valueOf(quantita));
    }

    public String getNome() {
        return piatto.getNome();
    }

    public String getImmagineUrl() {
        return piatto.getImmagineUrl();
    }
}
