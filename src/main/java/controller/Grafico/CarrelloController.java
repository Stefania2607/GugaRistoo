package controller.Grafico;

import controller.Bean.Carrello;
import controller.Bean.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/carrello")
public class CarrelloController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Utente u = (session != null) ? (Utente) session.getAttribute("utenteLoggato") : null;

        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Carrello carrello = (session != null)
                ? (Carrello) session.getAttribute("carrello")
                : null;

        // se non esiste ancora, lo tratto come vuoto
        request.setAttribute("carrello", carrello);

        request.getRequestDispatcher("carrello.jsp").forward(request, response);
    }

}
