package controller.applicativo;

import controller.Bean.Utente;
import controller.Exception.DAOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/annullaOrdineSmart")
public class AnnullaOrdineSmartController extends HttpServlet {

    private OrdineApplicativo ordineApplicativo;
    private PrenotazioneApplicativo prenotazioneApplicativo;

    @Override
    public void init() throws ServletException {
        super.init();
        ordineApplicativo = new OrdineApplicativo();
        prenotazioneApplicativo = new PrenotazioneApplicativo();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Utente u = (session != null)
                ? (Utente) session.getAttribute("utenteLoggato")
                : null;

        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // ATTENZIONE:
        // devi aver messo questi attributi in sessione quando hai creato ordine/prenotazione
        Integer prenId = (session != null)
                ? (Integer) session.getAttribute("prenotazioneCorrenteId")
                : null;
        Integer ordineId = (session != null)
                ? (Integer) session.getAttribute("ordineCorrenteId")
                : null;

        if (ordineId != null) {
            // Caso "pulito": abbiamo l'idOrdine → annullo ordine + prenotazione collegata
            try {
                ordineApplicativo.annullaOrdineEprenotazione(ordineId, u.getId());

                session.setAttribute("notificaPrenotazione",
                        "Ordine annullato, prenotazione cancellata e tavolo liberato.");

            } catch (DAOException e) {
                e.printStackTrace();
                session.setAttribute("notificaPrenotazione",
                        "Errore durante l'annullamento dell'ordine.");
            }

        } else if (prenId != null) {
            // Fallback: abbiamo solo l'idPrenotazione → annullo solo la prenotazione
            prenotazioneApplicativo.annullaPrenotazione(prenId, u.getId());

            session.setAttribute("notificaPrenotazione",
                    "Prenotazione annullata e tavolo liberato.");

        } else {
            session.setAttribute("notificaPrenotazione",
                    "Nessun ordine/prenotazione attiva da annullare.");
        }

        // Pulizia contesto dalla sessione
        if (session != null) {
            session.removeAttribute("prenotazioneCorrenteId");
            session.removeAttribute("tavoloCorrenteId");
            session.removeAttribute("modalitaServizio");
            session.removeAttribute("ordineCorrenteId"); // se esiste
        }

        response.sendRedirect(request.getContextPath() + "/ordinaSmart");
    }
}
