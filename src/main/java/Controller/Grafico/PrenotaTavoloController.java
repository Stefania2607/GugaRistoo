package Controller.Grafico;

import Controller.Applicativo.PrenotazioneApplicativo;
import Controller.Bean.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/prenotaTavolo")
public class PrenotaTavoloController extends HttpServlet {

    private PrenotazioneApplicativo prenotazioneApplicativo;

    @Override
    public void init() throws ServletException {
        super.init();
        prenotazioneApplicativo = new PrenotazioneApplicativo();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Utente u = (session != null) ? (Utente) session.getAttribute("utenteLoggato") : null;

        if (u == null) {
            // Non loggato → vai al login
            response.sendRedirect("login");
            return;
        }

        // Mostro form di prenotazione tavolo
        request.getRequestDispatcher("prenotaTavolo.jsp").forward(request, response);
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

        String data = request.getParameter("data");          // es: 2025-12-25
        String ora = request.getParameter("ora");            // es: 20:30
        String personeStr = request.getParameter("persone");
        String note = request.getParameter("note");
        request.getRequestDispatcher("confermaPrenotazione.jsp").forward(request, response);

        int persone = 0;
        try {
            persone = Integer.parseInt(personeStr);
        } catch (NumberFormatException e) {
            persone = 0;
        }

        boolean ok = prenotazioneApplicativo.creaPrenotazioneSoloTavolo(
                u, data, ora, persone, note
        );

        if (!ok) {
            request.setAttribute("errore", "Errore durante la prenotazione. Controlla i dati inseriti.");
            request.getRequestDispatcher("prenotaTavolo.jsp").forward(request, response);
            return;
        }

        // Prenotazione riuscita → pagina di conferma o area cliente
        request.setAttribute("messaggio",
                "Prenotazione registrata con successo per il " + data + " alle " + ora + ".");
        request.getRequestDispatcher("confermaPrenotazione.jsp").forward(request, response);
    }
}
