package controller.Grafico;

import controller.applicativo.PrenotazioneApplicativo;
import controller.Bean.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/prenotaETOrdina")
public class PrenotaETOrdinaController extends HttpServlet {

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
            response.sendRedirect("login");
            return;
        }

        request.getRequestDispatcher("prenotaETOrdina.jsp").forward(request, response);
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

        String data       = request.getParameter("data");
        String ora        = request.getParameter("ora");
        String personeStr = request.getParameter("persone");
        String note       = request.getParameter("note");

        int persone = 0;
        try {
            persone = Integer.parseInt(personeStr);
        } catch (NumberFormatException e) {
            persone = 0;
        }

        int prenotazioneId = prenotazioneApplicativo.creaPrenotazionePerOrdine(
                u, data, ora, persone, note
        );

        if (prenotazioneId <= 0) {
            request.setAttribute("errore", "Errore durante la prenotazione. Controlla i dati.");
            request.getRequestDispatcher("prenotaETOrdina.jsp").forward(request, response);
            return;
        }

        session.setAttribute("idPrenotazione", prenotazioneId);
        session.setAttribute("dataPrenotazione", data);
        session.setAttribute("orarioPrenotazione", ora);
        session.setAttribute("numeroPersonePrenotazione", persone);


        response.sendRedirect("menu");
    }
}
