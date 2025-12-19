package controller.applicativo;

import controller.bean.Ordine;
import controller.exception.DAOException;
import dao.OrdineDAO;

import java.util.List;

public class OrdineApplicativo {

    private final OrdineDAO ordineDAO;
    private final PrenotazioneApplicativo prenotazioneApplicativo;

    public OrdineApplicativo() {
        this.ordineDAO = new OrdineDAO();
        this.prenotazioneApplicativo = new PrenotazioneApplicativo();
    }

    public List<Ordine> trovaPerUtente(int idUtente) throws DAOException {
        return ordineDAO.trovaPerUtente(idUtente);
    }

    public void annullaOrdineEprenotazione(int idOrdine, int idUtente) throws DAOException {
        Integer idPrenotazione = ordineDAO.findPrenotazioneIdByOrdine(idOrdine); // deve lanciare DAOException, non SQLException
        ordineDAO.annullaOrdine(idOrdine, idUtente);

        if (idPrenotazione != null) {
            prenotazioneApplicativo.annullaPrenotazione(idPrenotazione, idUtente);
        }
    }
}
