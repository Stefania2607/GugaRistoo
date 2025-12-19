package controller.grafico;

import controller.bean.Carrello;
import controller.bean.Piatto;
import controller.exception.DAOException;
import dao.PiattoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/aggiungiAlCarrello")
public class AggiungiAlCarrelloController extends HttpServlet {
    private final PiattoDAO piattoDAO = new PiattoDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final String redirectMenu = request.getContextPath() + "/menuOrdinaDaCasa";

        // 1) leggo parametro
        final String idParam = request.getParameter("idPiatto");
        if (idParam == null || idParam.isBlank()) {
            response.sendRedirect(redirectMenu);
            return;
        }

        final int idPiatto;
        try {
            idPiatto = Integer.parseInt(idParam.trim());
        } catch (NumberFormatException e) {
            log("Parametro idPiatto non numerico: '" + idParam + "'", e);
            response.sendRedirect(redirectMenu);
            return;
        }

        final Piatto piatto;
        try {
            // 2) recupero piatto dal DB
            piatto = piattoDAO.findById(idPiatto);
        } catch (DAOException e) {
            log("Errore DB in findById(" + idPiatto + ") dentro AggiungiAlCarrelloController", e);
            throw new ServletException("Errore nel recupero del piatto dal DB", e);
        }

        if (piatto == null) {
            response.sendRedirect(redirectMenu);
            return;
        }

        // 3) sessione + carrello
        final HttpSession session = request.getSession(true);
        Object attr = session.getAttribute("carrello");
        final Carrello carrello;
        if (attr instanceof Carrello) {
            carrello = (Carrello) attr;
        } else {
            if (attr != null) {
                log("Attributo 'carrello' in sessione di tipo inatteso: " + attr.getClass().getName()
                        + " -> resetto carrello.");
            }
            carrello = new Carrello();
            session.setAttribute("carrello", carrello);
        }

        carrello.aggiungiPiatto(piatto);

        log("[CARRELLO] Aggiunto " + piatto.getNome()
                + " | articoli totali: " + carrello.getNumeroArticoli());

        response.sendRedirect(redirectMenu);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // In GET non aggiungere al carrello (operazione con side-effect).
        response.sendRedirect(request.getContextPath() + "/menuOrdinaDaCasa");
    }
}
