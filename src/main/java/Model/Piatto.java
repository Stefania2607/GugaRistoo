package Model;

public class Piatto {

    public enum Categoria {
        ANTIPASTO,
        PRIMO,
        SECONDO,
        CONTORNO,
        DOLCE,
        BEVANDA
    }

    private final int id;
    private String nome;
    private double prezzo;
    private Categoria categoria;
    private boolean disponibile;

    public Piatto(int id, String nome, double prezzo, Categoria categoria) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id piatto deve essere positivo.");
        }//ciaoooo
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome piatto non può essere vuoto.");
        }
        if (prezzo < 0) {
            throw new IllegalArgumentException("Prezzo non può essere negativo.");
        }
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria non può essere null.");
        }

        this.id = id;
        this.nome = nome;
        this.prezzo = prezzo;
        this.categoria = categoria;
        this.disponibile = true;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome piatto non valido.");
        }
        this.nome = nome;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        if (prezzo < 0) {
            throw new IllegalArgumentException("Prezzo non può essere negativo.");
        }
        this.prezzo = prezzo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria non può essere null.");
        }
        this.categoria = categoria;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

    @Override
    public String toString() {
        return "Piatto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", prezzo=" + prezzo +
                ", categoria=" + categoria +
                ", disponibile=" + disponibile +
                '}';
    }
}
