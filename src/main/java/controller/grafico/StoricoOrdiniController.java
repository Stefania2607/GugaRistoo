package controller.grafico;

import controller.applicativo.OrdineApplicativo;
import controller.bean.Utente;
import controller.exception.DAOException;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/storicoOrdini")
public class StoricoOrdiniController extends HttpServlet {

    private OrdineApplicativo ordineApplicativo;

    @Override
    public void init() throws ServletException {
        super.init();
        ordineApplicativo = new OrdineApplicativo();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Utente u = (session != null) ? (Utente) session.getAttribute("utenteLoggato") : null;
        String ruolo = (session != null) ? (String) session.getAttribute("ruolo") : null;

        if (u == null || ruolo == null || !"CLIENTE".equalsIgnoreCase(ruolo)) {
            response.sendRedirect("login");
            return;
        }

        try {
            List<controller.bean.Ordine> lista = ordineApplicativo.trovaPerUtente(u.getId());
            request.setAttribute("listaOrdini", lista);
            request.getRequestDispatcher("storicoOrdini.jsp").forward(request, response);
        } catch (DAOException e) {
            throw new ServletException("Errore nel recupero dello storico ordini", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Utente u = (session != null) ? (Utente) session.getAttribute("utenteLoggato") : null;
        String ruolo = (session != null) ? (String) session.getAttribute("ruolo") : null;

        if (u == null || ruolo == null || !"CLIENTE".equalsIgnoreCase(ruolo)) {
            response.sendRedirect("login");
            return;
        }

        String idOrdineStr = request.getParameter("idOrdine");
        if (idOrdineStr != null) {
            try {
                int idOrdine = Integer.parseInt(idOrdineStr);

                ordineApplicativo.annullaOrdineEprenotazione(idOrdine, u.getId());

                session.setAttribute("notificaOrdine",
                        "Ordine annullato e prenotazione associata cancellata (se presente).");

            } catch (NumberFormatException ignore) {
                session.setAttribute("notificaOrdine",
                        "ID ordine non valido.");
            } catch (DAOException e) {
                throw new ServletException("Errore durante l'annullamento dell'ordine", e);
            }
        }
        response.sendRedirect("storicoOrdini");
    }

}