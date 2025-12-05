package Controller.Bean;

public class Prenotazione {
    private int id;
    private int utenteId;
    private String dataPrenotazione;  // yyyy-MM-dd (per semplicità String)
    private String oraPrenotazione;   // HH:mm
    private int numPersone;
    private String note;
    private String tipo;   // SOLO_TAVOLO, TAVOLO_E_ORDINE...
    private String stato;  // ATTIVA, CANCELLATA, ecc.

    // getter e setter

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUtenteId() { return utenteId; }
    public void setUtenteId(int utenteId) { this.utenteId = utenteId; }

    public String getDataPrenotazione() { return dataPrenotazione; }
    public void setDataPrenotazione(String dataPrenotazione) { this.dataPrenotazione = dataPrenotazione; }

    public String getOraPrenotazione() { return oraPrenotazione; }
    public void setOraPrenotazione(String oraPrenotazione) { this.oraPrenotazione = oraPrenotazione; }

    public int getNumPersone() { return numPersone; }
    public void setNumPersone(int numPersone) { this.numPersone = numPersone; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }
}
