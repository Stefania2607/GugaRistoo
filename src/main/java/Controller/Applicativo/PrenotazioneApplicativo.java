package Controller.Applicativo;

import Controller.Bean.Utente;
import DAO.PrenotazioneDAO;

public class PrenotazioneApplicativo {

    private PrenotazioneDAO prenotazioneDAO;

    public PrenotazioneApplicativo() {
        this.prenotazioneDAO = new PrenotazioneDAO();
    }

    /**
     * Prenotazione solo tavolo, senza ordine.
     */
    public boolean creaPrenotazioneSoloTavolo(Utente u,
                                              String data,
                                              String ora,
                                              int persone,
                                              String note) {

        if (u == null) return false;
        if (data == null || data.isBlank()) return false;
        if (ora == null || ora.isBlank()) return false;
        if (persone <= 0) return false;

        try {
            prenotazioneDAO.inserisciPrenotazione(
                    u.getId(),
                    data,
                    ora,
                    persone,
                    note,
                    "SOLO_TAVOLO"
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Prenotazione tavolo + ordine collegato.
     * Ritorna l'id della prenotazione appena creata (o -1 se fallisce).
     */
    public int creaPrenotazionePerOrdine(Utente u,
                                         String data,
                                         String ora,
                                         int persone,
                                         String note) {

        if (u == null) return -1;
        if (data == null || data.isBlank()) return -1;
        if (ora == null || ora.isBlank()) return -1;
        if (persone <= 0) return -1;

        try {
            return prenotazioneDAO.inserisciPrenotazioneReturnId(
                    u.getId(),
                    data,
                    ora,
                    persone,
                    note,
                    "TAVOLO_E_ORDINE"
            );
        } catch (Exception e) {
            return -1;
        }
    }
}
