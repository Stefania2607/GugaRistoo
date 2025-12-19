package controller.grafico;

import controller.bean.Carrello;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/aggiornaCarrello")
public class AggiornaCarrelloController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/menuOrdinaDaCasa");
            return;
        }

        Carrello carrello = (Carrello) session.getAttribute("carrello");

        if (carrello == null) {
            response.sendRedirect(request.getContextPath() + "/menuOrdinaDaCasa");
            return;
        }

        try {
            int idPiatto = Integer.parseInt(request.getParameter("idPiatto"));
            String azione = request.getParameter("azione");

            if ("plus".equals(azione)) {
                carrello.incrementaPiatto(idPiatto);
            } else if ("minus".equals(azione)) {
                carrello.decrementaPiatto(idPiatto);
            }

            // torno alla pagina del carrello
            response.sendRedirect(request.getContextPath() + "/carrello");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/carrello");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
