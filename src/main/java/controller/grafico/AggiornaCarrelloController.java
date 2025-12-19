package controller.grafico;

import controller.bean.Carrello;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/aggiornaCarrello")
public class AggiornaCarrelloController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        final HttpSession session;
        try {
            session = request.getSession(false);
        } catch (RuntimeException e) {
            log("Errore runtime nel recupero sessione in AggiornaCarrelloController.doPost", e);
            safeSendError(response);
            return;
        }

        if (session == null) {
            safeRedirect(request, response, request.getContextPath() + "/menuOrdinaDaCasa",
                    "Redirect verso /menuOrdinaDaCasa fallito (sessione null) in AggiornaCarrelloController.doPost");
            return;
        }

        final Carrello carrello;
        try {
            Object attr = session.getAttribute("carrello");
            carrello = (attr instanceof Carrello) ? (Carrello) attr : null;
        } catch (RuntimeException e) {
            log("Errore runtime nel getAttribute('carrello') in AggiornaCarrelloController.doPost", e);
            safeSendError(response);
            return;
        }

        if (carrello == null) {
            safeRedirect(request, response, request.getContextPath() + "/menuOrdinaDaCasa",
                    "Redirect verso /menuOrdinaDaCasa fallito (carrello null) in AggiornaCarrelloController.doPost");
            return;
        }

        final String idParam = request.getParameter("idPiatto");
        final String azione = request.getParameter("azione");

        final int idPiatto;
        try {
            idPiatto = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            log("Parametro idPiatto non numerico in AggiornaCarrelloController.doPost: '" + idParam + "'", e);
            safeRedirect(request, response, request.getContextPath() + "/carrello",
                    "Redirect verso /carrello fallito dopo NumberFormatException in AggiornaCarrelloController.doPost");
            return;
        } catch (RuntimeException e) {
            log("Errore runtime nel parsing idPiatto in AggiornaCarrelloController.doPost: '" + idParam + "'", e);
            safeSendError(response);
            return;
        }

        try {
            if ("plus".equals(azione)) {
                carrello.incrementaPiatto(idPiatto);
            } else if ("minus".equals(azione)) {
                carrello.decrementaPiatto(idPiatto);
            } else {
                // azione non valida: log e continuo con redirect al carrello
                log("Azione non valida in AggiornaCarrelloController.doPost: '" + azione + "'");
            }
        } catch (RuntimeException e) {
            // in caso i metodi del carrello facciano esplodere qualcosa
            log("Errore runtime durante aggiornamento carrello (azione=" + azione + ", idPiatto=" + idPiatto + ")", e);
            safeSendError(response);
            return;
        }

        // Torno alla pagina del carrello
        safeRedirect(request, response, request.getContextPath() + "/carrello",
                "Redirect verso /carrello fallito in AggiornaCarrelloController.doPost");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        // In GET non aggiorno il carrello (side-effect). Rimando al carrello.
        safeRedirect(request, response, request.getContextPath() + "/carrello",
                "Redirect verso /carrello fallito in AggiornaCarrelloController.doGet");
    }

    /**
     * Redirect "safe" per Sonar: gestisce IOException localmente.
     */
    private void safeRedirect(HttpServletRequest request, HttpServletResponse response,
                              String targetUrl, String logMessage) {
        try {
            response.sendRedirect(response.encodeRedirectURL(targetUrl));
        } catch (IOException e) {
            log(logMessage + " | target=" + targetUrl + " | remoteAddr=" + request.getRemoteAddr(), e);
            safeSendError(response);
        } catch (RuntimeException e) {
            log("Errore runtime durante redirect: " + targetUrl, e);
            safeSendError(response);
        }
    }

    /**
     * sendError può lanciare IOException: gestito qui per evitare try/catch annidati ovunque.
     */
    private void safeSendError(HttpServletResponse response) {
        if (response.isCommitted()) return;
        try {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            log("Impossibile inviare errore HTTP " + HttpServletResponse.SC_INTERNAL_SERVER_ERROR + " (IOException)", e);
        } catch (RuntimeException e) {
            log("Impossibile inviare errore HTTP " + HttpServletResponse.SC_INTERNAL_SERVER_ERROR + " (RuntimeException)", e);
        }
    }
}
