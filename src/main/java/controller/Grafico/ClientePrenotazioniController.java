package controller.Grafico;

import controller.applicativo.PrenotazioneApplicativo;
import controller.Bean.Utente;
import controller.Bean.Prenotazione;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/clientePrenotazioni")
public class ClientePrenotazioniController extends HttpServlet {

    private PrenotazioneApplicativo prenotazioneApplicativo;

    @Override
    public void init() throws ServletException {
        super.init();
        prenotazioneApplicativo = new PrenotazioneApplicativo();
    }

    private Utente getUtenteLoggato(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null) ? (Utente) session.getAttribute("utenteLoggato") : null;
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        Utente u = getUtenteLoggato(request);
        if (u == null) {
            response.sendRedirect("login");
            return;
        }

        // QUI CARICO LE PRENOTAZIONI E LE METTO COME "prenotazioni"
        List<Prenotazione> lista = prenotazioneApplicativo.getPrenotazioniUtente(u.getId());
        request.setAttribute("prenotazioni", lista);

        RequestDispatcher rd = request.getRequestDispatcher("clientePrenotazioni.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        Utente u = getUtenteLoggato(request);
        if (u == null) {
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            String idStr = request.getParameter("idPrenotazione");
            try {
                int idPrenotazione = Integer.parseInt(idStr);
                prenotazioneApplicativo.annullaPrenotazione(idPrenotazione, u.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // DOPO LA CANCELLAZIONE RICARICO SEMPRE LA LISTA
        List<Prenotazione> listaAggiornata = prenotazioneApplicativo.getPrenotazioniUtente(u.getId());
        request.setAttribute("prenotazioni", listaAggiornata);

        RequestDispatcher rd = request.getRequestDispatcher("clientePrenotazioni.jsp");
        rd.forward(request, response);
    }
}
