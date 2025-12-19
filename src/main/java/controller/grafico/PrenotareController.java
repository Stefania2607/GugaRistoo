package controller.grafico;

import controller.applicativo.PrenotazioneApplicativo;
import controller.bean.Utente;
import controller.bean.Prenotazione;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/prenotazioni")
public class PrenotareController extends HttpServlet {

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
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Utente u = (Utente) session.getAttribute("utenteLoggato");
        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String notifica = (String) session.getAttribute("notificaPrenotazione");
        if (notifica != null) {
            request.setAttribute("notificaPrenotazione", notifica);
            session.removeAttribute("notificaPrenotazione");
        }


        try {
            List<Prenotazione> lista = prenotazioneApplicativo.getPrenotazioniUtente(u.getId());
            request.setAttribute("listaPrenotazioni", lista);
        } catch (Exception e) {
            throw new ServletException("Errore nel caricamento delle prenotazioni", e);
        }

        request.getRequestDispatcher("clientePrenotazioni.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Utente u = (session != null) ? (Utente) session.getAttribute("utenteLoggato") : null;

        if (u == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            String idStr = request.getParameter("idPrenotazione");

            try {
                int id = Integer.parseInt(idStr);

                System.out.println("[PrenotareController] Richiesta cancellazione idPrenotazione="
                        + id + " da utenteId=" + u.getId());

                boolean ok = prenotazioneApplicativo.annullaPrenotazione(id, u.getId());

                if (ok) {
                    session.setAttribute("notificaPrenotazione",
                            "Prenotazione cancellata con successo.");
                } else {
                    session.setAttribute("notificaPrenotazione",
                            "Impossibile cancellare la prenotazione selezionata.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("notificaPrenotazione",
                        "Errore durante la cancellazione.");
            }
        }

        response.sendRedirect(request.getContextPath() + "/prenotazioni");
    }
}
