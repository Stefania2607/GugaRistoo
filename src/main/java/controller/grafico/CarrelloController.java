package controller.grafico;

import controller.bean.Carrello;
import controller.bean.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/carrello")
public class CarrelloController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final HttpSession session = request.getSession(false);

        // 1) controllo login in modo robusto (niente cast ciechi)
        final Utente u = getUtenteLoggato(session);
        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 2) recupero carrello in modo robusto
        final Carrello carrello = getCarrello(session);

        // 3) se non esiste, lo tratto come vuoto (puoi anche istanziarlo qui se vuoi)
        request.setAttribute("carrello", carrello);

        // 4) forward a JSP (path chiaro)
        request.getRequestDispatcher("/carrello.jsp").forward(request, response);
    }

    private Utente getUtenteLoggato(HttpSession session) {
        if (session == null) return null;
        Object attr = session.getAttribute("utenteLoggato");
        if (attr instanceof Utente) return (Utente) attr;

        // se trovi un tipo errato, non esplodere: tratta come non loggato
        if (attr != null) {
            log("Attributo 'utenteLoggato' in sessione di tipo inatteso: " + attr.getClass().getName());
        }
        return null;
    }

    private Carrello getCarrello(HttpSession session) {
        if (session == null) return null;
        Object attr = session.getAttribute("carrello");
        if (attr instanceof Carrello) return (Carrello) attr;

        if (attr != null) {
            log("Attributo 'carrello' in sessione di tipo inatteso: " + attr.getClass().getName());
        }
        return null;
    }
}
