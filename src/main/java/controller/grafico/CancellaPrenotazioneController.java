package controller.grafico;

import controller.applicativo.PrenotazioneApplicativo;
import controller.bean.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/cancellaPrenotazione")
public class CancellaPrenotazioneController extends HttpServlet {

    private PrenotazioneApplicativo prenotazioneApplicativo;

    @Override
    public void init() throws ServletException {
        super.init();
        prenotazioneApplicativo = new PrenotazioneApplicativo();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Utente u = (session != null)
                ? (Utente) session.getAttribute("utenteLoggato")
                : null;

        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String idStr = request.getParameter("idPrenotazione");

        if (idStr != null) {
            try {
                int prenId = Integer.parseInt(idStr);

                boolean ok = prenotazioneApplicativo
                        .annullaPrenotazione(prenId, u.getId());

                if (ok) {
                    session.setAttribute("notificaPrenotazione",
                            "Prenotazione cancellata (ed eventuale tavolo liberato).");
                } else {
                    session.setAttribute("notificaPrenotazione",
                            "Impossibile cancellare la prenotazione selezionata.");
                }

            } catch (NumberFormatException e) {
                session.setAttribute("notificaPrenotazione",
                        "ID prenotazione non valido.");
            }
        } else {
            session.setAttribute("notificaPrenotazione",
                    "Nessuna prenotazione selezionata.");
        }

        response.sendRedirect(request.getContextPath() + "/prenotazioni");
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/prenotazioni");
    }
}
