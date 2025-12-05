package Controller.Grafico;

import Controller.Bean.Utente;
import Controller.Bean.Prenotazione;
import DAO.PrenotazioneDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/prenotazioni")
public class PrenotareController extends HttpServlet {

    private PrenotazioneDAO prenotazioneDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        prenotazioneDAO = new PrenotazioneDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Utente u = (session != null) ? (Utente) session.getAttribute("utenteLoggato") : null;

        if (u == null) {
            response.sendRedirect("login");
            return;
        }

        try {
            List<Prenotazione> lista = prenotazioneDAO.findByUtente(u.getId());
            request.setAttribute("listaPrenotazioni", lista);
        } catch (SQLException e) {
            throw new ServletException("Errore nel caricamento delle prenotazioni", e);
        }

        request.getRequestDispatcher("clientePrenotazioni.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Utente u = (session != null) ? (Utente) session.getAttribute("utenteLoggato") : null;

        if (u == null) {
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            String idStr = request.getParameter("idPrenotazione");
            try {
                int id = Integer.parseInt(idStr);
                prenotazioneDAO.deleteByIdAndUtente(id, u.getId());
            } catch (Exception e) {
                // potresti loggare, ma non blocchiamo tutto
            }
        }

        // Dopo la cancellazione, ricarico la lista
        response.sendRedirect("prenotazioni");
    }
}
