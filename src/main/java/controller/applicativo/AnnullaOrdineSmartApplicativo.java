package controller.applicativo;

import controller.exception.DAOException;

public class AnnullaOrdineSmartApplicativo {

    private final OrdineApplicativo ordineApplicativo;
    private final PrenotazioneApplicativo prenotazioneApplicativo;

    public AnnullaOrdineSmartApplicativo() {
        this.ordineApplicativo = new OrdineApplicativo();
        this.prenotazioneApplicativo = new PrenotazioneApplicativo();
    }

    /**
     * Annulla ordine + prenotazione collegata e libera il tavolo.
     * Propaga DAOException (checked) al chiamante.
     */
    public void runAnnullaOrdineEprenotazione(int ordineId, int userId) throws DAOException {
        ordineApplicativo.annullaOrdineEprenotazione(ordineId, userId);
    }

    /**
     * Annulla solo la prenotazione e libera il tavolo.
     * Propaga DAOException (checked) al chiamante.
     */
    public void runAnnullaPrenotazione(int prenotazioneId, int userId) throws DAOException {
        prenotazioneApplicativo.annullaPrenotazione(prenotazioneId, userId);
    }

    /**
     * Metodo “facciata” in stile AreaRiservata: decide cosa annullare.
     * - se ordineId != null -> annulla ordine + prenotazione
     * - else se prenId != null -> annulla prenotazione
     * - else -> nessuna azione (ritorna un messaggio)
     */
    public String run(Integer ordineId, Integer prenId, int userId) throws DAOException {

        if (ordineId != null) {
            ordineApplicativo.annullaOrdineEprenotazione(ordineId, userId);
            return "Ordine annullato, prenotazione cancellata e tavolo liberato.";
        }

        if (prenId != null) {
            prenotazioneApplicativo.annullaPrenotazione(prenId, userId);
            return "Prenotazione annullata e tavolo liberato.";
        }

        return "Nessun ordine/prenotazione attiva da annullare.";
    }
}
