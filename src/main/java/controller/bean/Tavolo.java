package controller.bean;

public class Tavolo {

    private int id;
    private int numero;
    private int posti;
    private String stato;   // es. "LIBERO" oppure "OCCUPATO"

    public Tavolo() {
    }

    public Tavolo(int id, int numero, int posti, String stato) {
        this.id = id;
        this.numero = numero;
        this.posti = posti;
        this.stato = stato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getPosti() {
        return posti;
    }

    public void setPosti(int posti) {
        this.posti = posti;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    @Override
    public String toString() {
        return "Tavolo{" +
                "id=" + id +
                ", numero=" + numero +
                ", posti=" + posti +
                ", stato='" + stato + '\'' +
                '}';
    }
}
