package Controller.Grafico;

import Controller.Bean.Utente;

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
            response.sendRedirect("login");
            return;
        }

        request.getRequestDispatcher("ordinaDaCasa.jsp").forward(request, response);
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

        String modalita = request.getParameter("modalita"); // DOMICILIO o ASPORTO
        String indirizzo = request.getParameter("indirizzo");
        String orario = request.getParameter("orario");

        // Controlli minimi
        if (modalita == null ||
                (!"DOMICILIO".equalsIgnoreCase(modalita)
                        && !"ASPORTO".equalsIgnoreCase(modalita))) {

            request.setAttribute("errore", "Seleziona una modalità valida.");
            request.getRequestDispatcher("ordinaDaCasa.jsp").forward(request, response);
            return;
        }

        if ("DOMICILIO".equalsIgnoreCase(modalita)) {
            if (indirizzo == null || indirizzo.isBlank()) {
                request.setAttribute("errore", "Per la consegna a domicilio devi indicare l'indirizzo.");
                request.getRequestDispatcher("ordinaDaCasa.jsp").forward(request, response);
                return;
            }
        }

        // Salviamo in sessione le info della modalità di servizio
        session.setAttribute("modalitaServizio", modalita.toUpperCase());
        session.setAttribute("indirizzoConsegna", indirizzo);
        session.setAttribute("orarioDesiderato", orario);

        // Ora vai al menu per scegliere i piatti
        response.sendRedirect("menu");
    }
}

