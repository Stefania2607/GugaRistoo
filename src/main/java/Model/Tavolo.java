package Model;

public class Tavolo {

    private final int id;
    private final int numeroPosti;
    private boolean occupato;

    public Tavolo(int id, int numeroPosti) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id tavolo deve essere positivo.");
        }
        if (numeroPosti <= 0) {
            throw new IllegalArgumentException("Numero posti deve essere positivo.");
        }
        this.id = id;
        this.numeroPosti = numeroPosti;
        this.occupato = false;
    }

    public int getId() {
        return id;
    }

    public int getNumeroPosti() {
        return numeroPosti;
    }

    public boolean isOccupato() {
        return occupato;
    }

    public void occupa() {
        this.occupato = true;
    }

    public void libera() {
        this.occupato = false;
    }

    @Override
    public String toString() {
        return "Tavolo{" +
                "id=" + id +
                ", numeroPosti=" + numeroPosti +
                ", occupato=" + occupato +
                '}';
    }
}

