package Controller.Bean;

public class Piatto {

    private int id;
    private String nome;
    private String descrizione;
    private double prezzo;
    private String categoria;
    private String ingredienti;
    private String immagineUrl;

    // ====== GETTER & SETTER ======

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(String ingredienti) {
        this.ingredienti = ingredienti;
    }

    public String getImmagineUrl() {
        return immagineUrl;
    }

    public void setImmagineUrl(String immagineUrl) {
        this.immagineUrl = immagineUrl;
    }

}
