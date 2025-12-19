package controller.grafico;

import controller.applicativo.PrenotazioneApplicativo;
import controller.bean.Utente;
import controller.bean.Tavolo;
import controller.exception.PrenotazioneException;
import dao.TavoloDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ordinaSmart")
public class OrdinaSmartController extends HttpServlet {

    private PrenotazioneApplicativo prenotazioneApplicativo;
    private TavoloDAO tavoloDAO;

    @Override
    public void init() throws ServletException {
        this.prenotazioneApplicativo = new PrenotazioneApplicativo();
        this.tavoloDAO = new TavoloDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session;
        Utente u;

        try {
            session = request.getSession(false);
            u = (session != null) ? (Utente) session.getAttribute("utenteLoggato") : null;
        } catch (RuntimeException e) {
            // estremamente raro, ma Sonar "high" ti vuole pronto
            log("Errore runtime nel recupero sessione/utente in OrdinaSmartController.doGet", e);
            safeSendError(response);
            return;
        }

        if (u == null) {
            safeRedirect(request, response, request.getContextPath() + "/login",
                    "Redirect verso /login fallito in OrdinaSmartController.doGet (utente null)");
            return;
        }

        // Carico tavoli liberi
        try {
            List<Tavolo> tavoliLiberi = tavoloDAO.findLiberi();
            request.setAttribute("tavoliLiberi", tavoliLiberi);
        } catch (SQLException e) {
            log("Errore SQL in tavoloDAO.findLiberi() in OrdinaSmartController.doGet", e);
            request.setAttribute("errore",
                    "Errore nel caricamento dei tavoli disponibili. Riprova più tardi.");
        } catch (RuntimeException e) {
            log("Errore runtime in tavoloDAO.findLiberi() in OrdinaSmartController.doGet", e);
            request.setAttribute("errore",
                    "Errore nel caricamento dei tavoli disponibili. Riprova più tardi.");
        }

        // Forward protetto
        try {
            RequestDispatcher rd = request.getRequestDispatcher("/ordinaSmart.jsp");
            rd.forward(request, response);
        } catch (ServletException e) {
            log("Errore ServletException nel forward a /ordinaSmart.jsp", e);
            safeSendError(response);
        } catch (IOException e) {
            log("Errore IOException nel forward a /ordinaSmart.jsp", e);
            safeSendError(response);
        } catch (RuntimeException e) {
            log("Errore runtime nel forward a /ordinaSmart.jsp", e);
            safeSendError(response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session;
        Utente u;

        try {
            session = request.getSession(false);
            u = (session != null) ? (Utente) session.getAttribute("utenteLoggato") : null;
        } catch (RuntimeException e) {
            log("Errore runtime nel recupero sessione/utente in OrdinaSmartController.doPost", e);
            safeSendError(response);
            return;
        }

        if (u == null) {
            safeRedirect(request, response, request.getContextPath() + "/login",
                    "Redirect verso /login fallito in OrdinaSmartController.doPost (utente null)");
            return;
        }

        // Lettura parametri
        String tavoloStr  = request.getParameter("tavoloId");
        String data       = request.getParameter("data");
        String ora        = request.getParameter("ora");
        String personeStr = request.getParameter("persone");
        String note       = request.getParameter("note");

        int tavoloId;
        int persone;

        try {
            tavoloId = Integer.parseInt(tavoloStr);
            persone  = Integer.parseInt(personeStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errore", "Dati non validi. Controlla tavolo e numero di persone.");
            doGet(request, response); // doGet ha già gestione "safe"
            return;
        } catch (RuntimeException e) {
            log("Errore runtime nel parsing parametri in OrdinaSmartController.doPost", e);
            request.setAttribute("errore", "Errore nei dati inseriti. Riprova.");
            doGet(request, response);
            return;
        }

        // Validazioni minime
        if (data == null || data.isBlank() || ora == null || ora.isBlank() || persone <= 0) {
            request.setAttribute("errore", "Compila correttamente tutti i campi richiesti.");
            doGet(request, response);
            return;
        }

        // Logica applicativa
        try {
            int prenId = prenotazioneApplicativo
                    .creaPrenotazioneConTavoloPerOrdine(u, tavoloId, data, ora, persone, note);

            // Salvo contesto in sessione
            try {
                session.setAttribute("prenotazioneCorrenteId", prenId);
                session.setAttribute("tavoloCorrenteId", tavoloId);
                session.setAttribute("modalitaServizio", "RISTORANTE");
            } catch (RuntimeException e) {
                log("Errore runtime nel setAttribute sessione in OrdinaSmartController.doPost", e);
                safeSendError(response);
                return;
            }

            // Redirect protetto
            safeRedirect(request, response, request.getContextPath() + "/menu",
                    "Redirect verso /menu fallito in OrdinaSmartController.doPost");

        } catch (PrenotazioneException e) {
            // Errore applicativo: lo gestisci tu (senza printStackTrace)
            log("PrenotazioneException in creaPrenotazioneConTavoloPerOrdine (userId=" + u.getId()
                    + ", tavoloId=" + tavoloId + ")", e);

            request.setAttribute("errore", e.getMessage());
            doGet(request, response);

        } catch (RuntimeException e) {
            log("Errore runtime in creaPrenotazioneConTavoloPerOrdine", e);
            request.setAttribute("errore", "Errore durante la prenotazione. Riprova.");
            doGet(request, response);
        }
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
     * sendError può lanciare IOException: la gestiamo qui dentro per evitare ricorsione infinita di try/catch.
     */
    private void safeSendError(HttpServletResponse response) {
        if (response.isCommitted()) return;
        try {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            // Ultimo livello: log e stop (niente altre chiamate pericolose)
            log("Impossibile inviare errore HTTP " + HttpServletResponse.SC_INTERNAL_SERVER_ERROR + " (IOException)", e);
        } catch (RuntimeException e) {
            log("Impossibile inviare errore HTTP " + HttpServletResponse.SC_INTERNAL_SERVER_ERROR + " (RuntimeException)", e);
        }
    }
}
