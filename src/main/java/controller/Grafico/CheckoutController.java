package controller.Grafico;

import controller.Bean.Carrello;
import controller.Bean.Utente;
import controller.Exception.DAOException;
import DAO.OrdineDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect("login");
            return;
        }

        Utente utente = (Utente) session.getAttribute("utenteLoggato");
        String ruolo = (String) session.getAttribute("ruolo");
        Carrello carrello = (Carrello) session.getAttribute("carrello");

        if (utente == null || ruolo == null || !"CLIENTE".equalsIgnoreCase(ruolo)) {
            response.sendRedirect("login");
            return;
        }

        if (carrello == null || carrello.getRighe().isEmpty()) {
            response.sendRedirect("ordinaDaCasa");
            return;
        }

        // Mostro la pagina di checkout
        request.getRequestDispatcher("checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect("login");
            return;
        }

        Utente utente = (Utente) session.getAttribute("utenteLoggato");
        String ruolo = (String) session.getAttribute("ruolo");
        Carrello carrello = (Carrello) session.getAttribute("carrello");

        if (utente == null || ruolo == null || !"CLIENTE".equalsIgnoreCase(ruolo)) {
            response.sendRedirect("login");
            return;
        }

        if (carrello == null || carrello.getRighe().isEmpty()) {
            response.sendRedirect("ordinaDaCasa");
            return;
        }

        // Dati dal form
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");

        if (nome == null || nome.isBlank()) {
            nome = (utente.getNome() != null) ? utente.getNome() : utente.getUsername();
        }

        String idTavoloParam = request.getParameter("idTavolo");
        Integer idTavolo = null;
        if (idTavoloParam != null && !idTavoloParam.isBlank()) {
            try {
                idTavolo = Integer.valueOf(idTavoloParam);
            } catch (NumberFormatException ignored) {}
        }

        String orarioPrenotazione = request.getParameter("orario"); // <input name="orario">

        Integer idPrenotazione = (session != null)
                ? (Integer) session.getAttribute("prenotazioneCorrenteId")
                : null;

        int idOrdine;
        try {
            OrdineDAO ordineDAO = new OrdineDAO();
            idOrdine = ordineDAO.creaOrdineDaCarrello(
                    carrello,
                    utente,
                    nome,
                    email,
                    idTavolo,
                    orarioPrenotazione,
                    idPrenotazione              // 👈 SETTIMO PARAMETRO
            );
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServletException("Errore durante il salvataggio dell'ordine", e);
        }

        session.setAttribute("ultimoOrdineId", idOrdine);
        session.setAttribute("carrello", null);

        response.sendRedirect("storicoOrdini");
    }
}
