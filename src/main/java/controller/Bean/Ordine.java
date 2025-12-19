package controller.Bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Ordine {

    private int id;
    private int idUtente;
    private String nomeCliente;
    private String email;
    private BigDecimal totale;
    private String stato;
    private Timestamp dataOra;
    private Integer idTavolo;
    private String orarioPrenotazione;

    // ➜ NUOVO CAMPO: id_prenotazione
    private Integer idPrenotazione;

    // campo calcolato (logico)
    private String statoLogico;

    // riepilogo piatti
    private String riepilogoPiatti;

    // ============================
    // GETTER & SETTER STANDARD
    // ============================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getTotale() {
        return totale;
    }

    public void setTotale(BigDecimal totale) {
        this.totale = totale;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public Timestamp getDataOra() {
        return dataOra;
    }

    public void setDataOra(Timestamp dataOra) {
        this.dataOra = dataOra;
    }

    public Integer getIdTavolo() {
        return idTavolo;
    }

    public void setIdTavolo(Integer idTavolo) {
        this.idTavolo = idTavolo;
    }

    public String getOrarioPrenotazione() {
        return orarioPrenotazione;
    }

    public void setOrarioPrenotazione(String orarioPrenotazione) {
        this.orarioPrenotazione = orarioPrenotazione;
    }
    public Integer getIdPrenotazione() {
        return idPrenotazione;
    }

    public void setIdPrenotazione(Integer idPrenotazione) {
        this.idPrenotazione = idPrenotazione;
    }
    public String getStatoLogico() {
        return statoLogico;
    }

    public void setStatoLogico(String statoLogico) {
        this.statoLogico = statoLogico;
    }
    public String getRiepilogoPiatti() {
        return riepilogoPiatti;
    }

    public void setRiepilogoPiatti(String riepilogoPiatti) {
        this.riepilogoPiatti = riepilogoPiatti;
    }
}
