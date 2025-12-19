package controller.Grafico;

import controller.applicativo.MenuApplicativo;
import controller.Bean.Carrello;
import controller.Bean.Utente;
import controller.Bean.Piatto;
import controller.Exception.DAOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/menuOrdinaDaCasa")
public class MenuOrdinaDaCasaController extends HttpServlet {

    private MenuApplicativo menuApplicativo;

    @Override
    public void init() throws ServletException {
        super.init();
        menuApplicativo = new MenuApplicativo();
    }

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

        try {
            List<Piatto> lista = menuApplicativo.getMenuPerCategoria(null);
            request.setAttribute("listaPiatti", lista);
        } catch (DAOException e) {
            throw new ServletException("Errore nel caricamento del menu", e);
        }


        // QUI: recupero carrello dalla SESSIONE e lo passo alla JSP
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        request.setAttribute("carrello", carrello);

        request.getRequestDispatcher("menuOrdinaDaCasa.jsp")
                .forward(request, response);
    }
}
