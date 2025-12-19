package controller.grafico;

import controller.bean.Carrello;
import controller.bean.Piatto;
import controller.exception.DAOException;
import dao.PiattoDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/aggiungiAlCarrello")
public class AggiungiAlCarrelloController extends HttpServlet {

    private final PiattoDAO piattoDAO = new PiattoDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        final String redirectMenu = request.getContextPath() + "/menuOrdinaDaCasa";

        // 1) leggo parametro
        final String idParam = request.getParameter("idPiatto");
        if (idParam == null || idParam.isBlank()) {
            redirect(request, response, redirectMenu, "idPiatto mancante");
            return;
        }

        final int idPiatto;
        try {
            idPiatto = Integer.parseInt(idParam.trim());
        } catch (NumberFormatException e) {
            log("Parametro idPiatto non numerico: '" + idParam + "'", e);
            redirect(request, response, redirectMenu, "idPiatto non numerico");
            return;
        } catch (RuntimeException e) {
            log("Errore runtime nel parsing idPiatto: '" + idParam + "'", e);
            safeSendError(response);
            return;
        }

        final Piatto piatto;
        try {
            // 2) recupero piatto dal DB
            piatto = piattoDAO.findById(idPiatto);
        } catch (DAOException e) {
            // gestione tua: log + risposta controllata, niente ServletException rilanciata
            log("Errore DB in findById(" + idPiatto + ") dentro AggiungiAlCarrelloController", e);
            safeSendError(response);
            return;
        } catch (RuntimeException e) {
            log("Errore runtime in findById(" + idPiatto + ")", e);
            safeSendError(response);
            return;
        }

        if (piatto == null) {
            redirect(request, response, redirectMenu, "piatto non trovato id=" + idPiatto);
            return;
        }

        // 3) sessione + carrello
        final HttpSession session;
        try {
            session = request.getSession(true);
        } catch (RuntimeException e) {
            log("Errore runtime nel getSession(true) in AggiungiAlCarrelloController", e);
            safeSendError(response);
            return;
        }

        final Carrello carrello = getOrCreateCarrello(session);

        try {
            carrello.aggiungiPiatto(piatto);
        } catch (RuntimeException e) {
            log("Errore runtime durante aggiungiPiatto(id=" + idPiatto + ")", e);
            safeSendError(response);
            return;
        }

        log("[CARRELLO] Aggiunto " + piatto.getNome()
                + " | articoli totali: " + carrello.getNumeroArticoli());

        redirect(request, response, redirectMenu, "fine aggiunta al carrello");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        redirect(request, response, request.getContextPath() + "/menuOrdinaDaCasa", "GET non supportato");
    }

    private Carrello getOrCreateCarrello(HttpSession session) {
        try {
            Object attr = session.getAttribute("carrello");
            if (attr instanceof Carrello) return (Carrello) attr;

            if (attr != null) {
                log("Attributo 'carrello' in sessione di tipo inatteso: " + attr.getClass().getName()
                        + " -> resetto carrello.");
            }

            Carrello c = new Carrello();
            session.setAttribute("carrello", c);
            return c;

        } catch (RuntimeException e) {
            log("Errore runtime nel recupero/creazione carrello in sessione", e);
            // fallback: carrello “volatile” per non crashare
            return new Carrello();
        }
    }

    /**
     * Redirect NO-THROWS: così Sonar non ti chiede di gestire ServletException qui sopra.
     */
    private void redirect(HttpServletRequest request, HttpServletResponse response,
                          String location, String reasonForLog) {
        try {
            response.sendRedirect(response.encodeRedirectURL(location));
        } catch (IOException e) {
            log("Redirect fallito (" + reasonForLog + ") verso: " + location
                    + " | remoteAddr=" + request.getRemoteAddr(), e);
            safeSendError(response);
        } catch (RuntimeException e) {
            log("Errore runtime durante redirect verso: " + location, e);
            safeSendError(response);
        }
    }

    /**
     * Anche sendError può lanciare IOException: NO-THROWS per non creare loop Sonar.
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
