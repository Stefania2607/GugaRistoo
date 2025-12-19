package controller.grafico;

import controller.bean.Utente;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public abstract class BaseController extends HttpServlet {

    protected Utente requireLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        HttpSession session = request.getSession(false);
        Object attr = (session != null) ? session.getAttribute("utenteLoggato") : null;

        if (attr instanceof Utente) {
            return (Utente) attr;
        }

        // non loggato -> redirect centralizzato
        redirectSafe(request, response);
        return null;
    }

    protected void redirectSafe(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (IOException e) {
            log("Redirect fallito verso: " + "/login", e);
            throw new ServletException("Redirect fallito verso: " + "/login", e);
        }
    }
}
