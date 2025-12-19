package controller.Bean;

public class Prenotazione {

    // --- Campi base ---
    private int id;
    private int utenteId;
    private String dataPrenotazione;   // es. "2025-12-25"
    private String oraPrenotazione;    // es. "20:30"
    private int numPersone;
    private String note;
    private String tipo;               // SOLO_TAVOLO, SMART, PER_ORDINE, ...
    private String stato;              // opzionale: IN_ATTESA, CONFERMATA, CANCELLATA, ...

    // --- Info tavolo (possono essere null se non assegnato) ---
    private Integer tavoloId;          // FK su tavolo.id
    private Integer tavoloNumero;      // numero “umano” del tavolo in sala

    // ======================= COSTRUTTORI =======================

    public Prenotazione() {
    }

    public Prenotazione(int id,
                        int utenteId,
                        String dataPrenotazione,
                        String oraPrenotazione,
                        int numPersone,
                        String note,
                        String tipo,
                        String stato,
                        Integer tavoloId,
                        Integer tavoloNumero) {
        this.id = id;
        this.utenteId = utenteId;
        this.dataPrenotazione = dataPrenotazione;
        this.oraPrenotazione = oraPrenotazione;
        this.numPersone = numPersone;
        this.note = note;
        this.tipo = tipo;
        this.stato = stato;
        this.tavoloId = tavoloId;
        this.tavoloNumero = tavoloNumero;
    }

    // ======================= GETTER / SETTER =======================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUtenteId() {
        return utenteId;
    }

    public void setUtenteId(int utenteId) {
        this.utenteId = utenteId;
    }

    public String getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setDataPrenotazione(String dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    public String getOraPrenotazione() {
        return oraPrenotazione;
    }

    public void setOraPrenotazione(String oraPrenotazione) {
        this.oraPrenotazione = oraPrenotazione;
    }

    public int getNumPersone() {
        return numPersone;
    }

    public void setNumPersone(int numPersone) {
        this.numPersone = numPersone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public Integer getTavoloId() {
        return tavoloId;
    }

    public void setTavoloId(Integer tavoloId) {
        this.tavoloId = tavoloId;
    }

    public Integer getTavoloNumero() {
        return tavoloNumero;
    }

    public void setTavoloNumero(Integer tavoloNumero) {
        this.tavoloNumero = tavoloNumero;
    }

    // ======================= toString (utile per debug) =======================

    @Override
    public String toString() {
        return "Prenotazione{" +
                "id=" + id +
                ", utenteId=" + utenteId +
                ", dataPrenotazione='" + dataPrenotazione + '\'' +
                ", oraPrenotazione='" + oraPrenotazione + '\'' +
                ", numPersone=" + numPersone +
                ", note='" + note + '\'' +
                ", tipo='" + tipo + '\'' +
                ", stato='" + stato + '\'' +
                ", tavoloId=" + tavoloId +
                ", tavoloNumero=" + tavoloNumero +
                '}';
    }
}
