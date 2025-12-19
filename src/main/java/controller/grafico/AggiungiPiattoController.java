package controller.grafico;

import controller.applicativo.PiattoApplicativo;
import controller.exception.DAOException;
import controller.exception.DatiNonValidiException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/aggiungiPiatto")
public class AggiungiPiattoController extends HttpServlet {

    private transient PiattoApplicativo piattoApplicativo;

    @Override
    public void init() throws ServletException {
        this.piattoApplicativo = new PiattoApplicativo();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        final String nome = request.getParameter("nome");
        final String prezzoString = request.getParameter("prezzo");

        try {
            // delega tutta la logica all'applicativo
            piattoApplicativo.aggiungiPiatto(nome, prezzoString);

            // se va tutto bene → torna al menu
            safeRedirect(request, response, request.getContextPath() + "/menu"
            );

        } catch (DatiNonValidiException e) {
            // errore lato utente → torno alla JSP del form con messaggio
            try {
                request.setAttribute("errore", e.getMessage());
                request.setAttribute("nome", nome);
                request.setAttribute("prezzo", prezzoString);
            } catch (RuntimeException ex) {
                log("Errore runtime nel setAttribute in AggiungiPiattoController (DatiNonValidiException)", ex);
                safeSendError(response);
                return;
            }

            safeForward(request, response
            );

        } catch (RuntimeException e) {
            // errore imprevisto (es. NullPointer, IllegalState, ecc.) → log + 500 controllato
            log("Errore runtime durante aggiunta piatto (nome=" + nome + ", prezzo=" + prezzoString + ")", e);
            safeSendError(response);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Redirect "safe" per Sonar: gestisce IOException localmente.
     */
    private void safeRedirect(HttpServletRequest request, HttpServletResponse response,
                              String targetUrl) {
        try {
            response.sendRedirect(response.encodeRedirectURL(targetUrl));
        } catch (IOException e) {
            log("Redirect verso /menu fallito in AggiungiPiattoController" + " | target=" + targetUrl + " | remoteAddr=" + request.getRemoteAddr(), e);
            safeSendError(response);
        } catch (RuntimeException e) {
            log("Errore runtime durante redirect: " + targetUrl, e);
            safeSendError(response);
        }
    }

    /**
     * Forward "safe": gestisce ServletException/IOException localmente.
     */
    private void safeForward(HttpServletRequest request, HttpServletResponse response) {
        try {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/aggiungiPiatto.jsp");
            rd.forward(request, response);
        } catch (ServletException e) {
            log("Forward a /WEB-INF/views/aggiungiPiatto.jsp fallito in AggiungiPiattoController" + " (ServletException) | jsp=" + "/WEB-INF/views/aggiungiPiatto.jsp", e);
            safeSendError(response);
        } catch (IOException e) {
            log("Forward a /WEB-INF/views/aggiungiPiatto.jsp fallito in AggiungiPiattoController" + " (IOException) | jsp=" + "/WEB-INF/views/aggiungiPiatto.jsp", e);
            safeSendError(response);
        } catch (RuntimeException e) {
            log("Forward a /WEB-INF/views/aggiungiPiatto.jsp fallito in AggiungiPiattoController" + " (RuntimeException) | jsp=" + "/WEB-INF/views/aggiungiPiatto.jsp", e);
            safeSendError(response);
        }
    }

    /**
     * sendError può lanciare IOException: gestito qui per evitare catene infinite di try/catch.
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
