package controller.grafico;

import controller.bean.Carrello;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/svuotaCarrello")
public class SvuotaCarrelloController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            Carrello carrello = (Carrello) session.getAttribute("carrello");
            if (carrello != null) {
                carrello.svuota();
            }
        }

        response.sendRedirect(request.getContextPath() + "/carrello");
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        // se qualcuno lo chiama in GET (tipo dalla barra del browser)
        doPost(request, response);
    }
}
