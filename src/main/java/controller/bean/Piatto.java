package controller.bean;

import java.math.BigDecimal;

public class Piatto {

    private int id;
    private String nome;
    private String descrizione;
    private BigDecimal prezzo;
    private String categoria;
    private String ingredienti;
    private String immagineUrl;
    // ====== CAMPI DI VIEW (solo presentazione) ======
    private String macroCategoria;   // vini, birre, cocktail, acqua_soft, amari
    private String sottoCategoria;   // rossi, bianchi, bollicine, ecc.
    private String immagineView;     // URL già pronto
    private String prezzoView;       // "12.50 €"


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

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {
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
    private String tipoBevanda;

    public String getTipoBevanda() {

        return tipoBevanda;
    }

    public void setTipoBevanda(String tipoBevanda) {

        this.tipoBevanda = tipoBevanda;
    }

    public String getMacroCategoria() {
        return macroCategoria;
    }

    public void setMacroCategoria(String macroCategoria) {
        this.macroCategoria = macroCategoria;
    }

    public String getSottoCategoria() {
        return sottoCategoria;
    }

    public void setSottoCategoria(String sottoCategoria) {
        this.sottoCategoria = sottoCategoria;
    }

    public String getImmagineView() {
        return immagineView;
    }

    public void setImmagineView(String immagineView) {
        this.immagineView = immagineView;
    }

    public String getPrezzoView() {
        return prezzoView;
    }

    public void setPrezzoView(String prezzoView) {
        this.prezzoView = prezzoView;
    }

}
