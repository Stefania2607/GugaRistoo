package controller.applicativo;

import controller.bean.Utente;
import controller.exception.DAOException;

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
        ordineApplicativo = new OrdineApplicativo();
        prenotazioneApplicativo = new PrenotazioneApplicativo();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final HttpSession session = request.getSession(false);
        final Utente u = (session != null) ? getUtenteLoggato(session) : null;

        if (u == null) {
            try {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            } catch (IOException e) {
                throw new ServletException("Redirect verso /login fallito", e);
            }
        }



        // attributi sessione (potrebbero non esserci)
        final Integer prenId = getIntegerAttr(session, "prenotazioneCorrenteId");
        final Integer ordineId = getIntegerAttr(session, "ordineCorrenteId");

        try {
            if (ordineId != null) {
                ordineApplicativo.annullaOrdineEprenotazione(ordineId, u.getId());
                session.setAttribute("notificaPrenotazione",
                        "Ordine annullato, prenotazione cancellata e tavolo liberato.");

            } else if (prenId != null) {
                prenotazioneApplicativo.annullaPrenotazione(prenId, u.getId());
                session.setAttribute("notificaPrenotazione",
                        "Prenotazione annullata e tavolo liberato.");

            } else {
                session.setAttribute("notificaPrenotazione",
                        "Nessun ordine/prenotazione attiva da annullare.");
            }

        } catch (DAOException e) {
            log("Errore DAO durante annullaOrdineSmart. ordineId=" + ordineId
                    + ", prenId=" + prenId + ", userId=" + u.getId(), e);

            session.setAttribute("notificaPrenotazione",
                    "Errore durante l'annullamento. Riprova.");
        } finally {
            // Pulizia contesto dalla sessione
            pulisciContestoOrdinaSmart(session);
        }

        response.sendRedirect(request.getContextPath() + "/ordinaSmart");
    }

    private Utente getUtenteLoggato(HttpSession session) {
        Object attr = session.getAttribute("utenteLoggato");
        return (attr instanceof Utente) ? (Utente) attr : null;
    }

    private Integer getIntegerAttr(HttpSession session, String name) {
        Object attr = session.getAttribute(name);
        return (attr instanceof Integer) ? (Integer) attr : null;
    }

    private void pulisciContestoOrdinaSmart(HttpSession session) {
        session.removeAttribute("prenotazioneCorrenteId");
        session.removeAttribute("tavoloCorrenteId");
        session.removeAttribute("modalitaServizio");
        session.removeAttribute("ordineCorrenteId");
    }
}
