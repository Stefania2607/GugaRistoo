package Model;

public class RigaOrdine {

    private final int id;
    private final Piatto piatto;
    private int quantita;
    private double prezzoUnitario; // fotografato al momento
    private String note;

    public RigaOrdine(int id, Piatto piatto, int quantita, String note) {
        if (id <= 0) throw new IllegalArgumentException("Id riga invalido.");
        if (piatto == null) throw new IllegalArgumentException("Piatto obbligatorio.");
        if (quantita <= 0) throw new IllegalArgumentException("Quantità invalida.");

        this.id = id;
        this.piatto = piatto;
        this.quantita = quantita;
        this.prezzoUnitario = piatto.getPrezzo();
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public Piatto getPiatto() {
        return piatto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int q) {
        if (q <= 0) throw new IllegalArgumentException("Quantità invalida.");
        quantita = q;
    }

    public double getPrezzoUnitario() {
        return prezzoUnitario;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
