package controller.Grafico;

import controller.Bean.Carrello;
import controller.Bean.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/ordinaDaCasa")
public class OrdinaDaCasaController extends HttpServlet {

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

        request.getRequestDispatcher("ordinaDaCasa.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utente u = (Utente) session.getAttribute("utenteLoggato");
        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String modalita = request.getParameter("modalita");  // domicilio/ritiro
        String indirizzo = request.getParameter("indirizzo");
        String orario = request.getParameter("orario");

        session.setAttribute("modalitaServizio", modalita);
        session.setAttribute("indirizzoConsegna", indirizzo);
        session.setAttribute("orarioDesiderato", orario);

        Carrello carrello = (Carrello) session.getAttribute("carrello");
        if (carrello == null) {
            carrello = new Carrello();
            session.setAttribute("carrello", carrello);
        }

        response.sendRedirect(request.getContextPath() + "/menuOrdinaDaCasa");
    }
}
