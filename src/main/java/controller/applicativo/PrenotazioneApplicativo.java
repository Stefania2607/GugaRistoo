package controller.applicativo;

import controller.Bean.Utente;
import controller.Bean.Prenotazione;
import controller.Exception.PrenotazioneException;
import DAO.PrenotazioneDAO;
import DAO.TavoloDAO;

import java.sql.SQLException;
import java.util.List;

public class PrenotazioneApplicativo {

    private final PrenotazioneDAO prenotazioneDAO;
    private final TavoloDAO tavoloDAO;

    public PrenotazioneApplicativo() {
        this.prenotazioneDAO = new PrenotazioneDAO();
        this.tavoloDAO = new TavoloDAO();
    }

    // =========================================================
    // 1) SOLO PRENOTAZIONE TAVOLO (senza tavolo_id) - RETURN ID
    // =========================================================
    public int creaPrenotazioneSoloTavolo(Utente u,
                                          String data,
                                          String ora,
                                          int persone,
                                          String note) {

        if (u == null) return -1;

        try {
            return prenotazioneDAO.inserisciPrenotazioneReturnId(
                    u.getId(),
                    data,
                    ora,
                    persone,
                    note,
                    "SOLO_TAVOLO"
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // =========================================================
    // 2) PRENOTAZIONE + TAVOLO PER ORDINA SMART
    // =========================================================
    public int creaPrenotazioneConTavoloPerOrdine(Utente u,
                                                  int tavoloId,
                                                  String data,
                                                  String ora,
                                                  int persone,
                                                  String note)
            throws PrenotazioneException {

        if (u == null) {
            throw new PrenotazioneException("Utente non autenticato.");
        }

        try {
            boolean occupato = tavoloDAO.occupaSeLibero(tavoloId);
            if (!occupato) {
                throw new PrenotazioneException("Il tavolo selezionato non è più disponibile.");
            }

            int prenId = prenotazioneDAO.inserisciPrenotazioneConTavolo(
                    u.getId(),
                    tavoloId,
                    data,
                    ora,
                    persone,
                    note,
                    "SMART"
            );

            if (prenId <= 0) {
                tavoloDAO.libera(tavoloId);
                throw new PrenotazioneException("Errore durante la creazione della prenotazione.");
            }

            return prenId;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                tavoloDAO.libera(tavoloId);
            } catch (SQLException ignored) {}
            throw new PrenotazioneException("Errore di database durante la prenotazione.", e);
        }
    }

    // =========================================================
    // 3) ANNULLA PRENOTAZIONE + LIBERA EVENTUALE TAVOLO
    //    (usa deleteById, NON controlla utente_id)
    // =========================================================
    public boolean annullaPrenotazione(int prenotazioneId, int utenteId) {

        Integer tavoloId = null;

        // 1) Provo a recuperare il tavolo associato
        try {
            tavoloId = tavoloDAO.findTavoloIdByPrenotazione(prenotazioneId);
            System.out.println("[PrenotazioneApplicativo] Tavolo associato a prenotazione "
                    + prenotazioneId + " = " + tavoloId);
        } catch (SQLException e) {
            System.out.println("[PrenotazioneApplicativo] Errore nel findTavoloIdByPrenotazione");
            e.printStackTrace();
        }

        // 2) Cancello la prenotazione SOLO per id
        try {
            int rows = prenotazioneDAO.deleteById(prenotazioneId);
            System.out.println("[PrenotazioneApplicativo] deleteById(" + prenotazioneId
                    + ") -> rows=" + rows);

            if (rows == 0) {
                // nessuna prenotazione con quell'id
                return false;
            }

            // 3) libero il tavolo se lo conosco
            if (tavoloId != null) {
                try {
                    tavoloDAO.libera(tavoloId);
                    System.out.println("[PrenotazioneApplicativo] Tavolo " + tavoloId + " liberato.");
                } catch (SQLException e) {
                    System.out.println("[PrenotazioneApplicativo] Prenotazione cancellata, " +
                            "ma errore nel liberare tavolo " + tavoloId);
                    e.printStackTrace();
                }
            }

            return true;

        } catch (SQLException e) {
            System.out.println("[PrenotazioneApplicativo] Errore durante la DELETE prenotazione");
            e.printStackTrace();
            return false;
        }
    }

    // =========================================================
    // 4) Prenotazione "base" per ordine (senza tavolo)
    // =========================================================
    public int creaPrenotazionePerOrdine(Utente u,
                                         String data,
                                         String ora,
                                         int persone,
                                         String note) {

        if (u == null) return -1;

        try {
            return prenotazioneDAO.inserisciPrenotazioneReturnId(
                    u.getId(),
                    data,
                    ora,
                    persone,
                    note,
                    "ORDINE"
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // =========================================================
    // 5) GET PRENOTAZIONI UTENTE
    // =========================================================
    public List<Prenotazione> getPrenotazioniUtente(int utenteId) {
        try {
            return prenotazioneDAO.findByUtente(utenteId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore nel recupero prenotazioni utente", e);
        }
    }
}
