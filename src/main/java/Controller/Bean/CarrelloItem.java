package Controller.Bean;

public class CarrelloItem {

    private Piatto piatto;
    private int quantita;

    public CarrelloItem(Piatto piatto) {
        this.piatto = piatto;
        this.quantita = 1;
    }

    public Piatto getPiatto() {
        return piatto;
    }

    public void setPiatto(Piatto piatto) {
        this.piatto = piatto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public void incrementa() {
        this.quantita++;
    }

    public void decrementa() {
        if (this.quantita > 1) {
            this.quantita--;
        }
    }

    public double getSubtotale() {
        return piatto.getPrezzo() * quantita;
    }
}
