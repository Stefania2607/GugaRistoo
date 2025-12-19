package controller.grafico;

import controller.applicativo.PrenotazioneApplicativo;
import controller.bean.Utente;
import controller.bean.Tavolo;
import controller.exception.PrenotazioneException;
import dao.TavoloDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ordinaSmart")
public class OrdinaSmartController extends HttpServlet {

    private PrenotazioneApplicativo prenotazioneApplicativo;
    private TavoloDAO tavoloDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.prenotazioneApplicativo = new PrenotazioneApplicativo();
        this.tavoloDAO = new TavoloDAO();
    }

    // =======================
    // GET: mostra schermata scelta tavolo + dati prenotazione
    // =======================
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Utente u = (session != null)
                ? (Utente) session.getAttribute("utenteLoggato")
                : null;

        if (u == null) {
            response.sendRedirect("login");
            return;
        }

        try {
            List<Tavolo> tavoliLiberi = tavoloDAO.findLiberi();
            request.setAttribute("tavoliLiberi", tavoliLiberi);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errore",
                    "Errore nel caricamento dei tavoli disponibili. Riprova più tardi.");
        }

        RequestDispatcher rd = request.getRequestDispatcher("ordinaSmart.jsp");
        rd.forward(request, response);
    }

    // =======================
    // POST: prenota tavolo + crea prenotazione per l'ordine
    // =======================
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Utente u = (session != null)
                ? (Utente) session.getAttribute("utenteLoggato")
                : null;

        if (u == null) {
            response.sendRedirect("login");
            return;
        }

        // Lettura parametri
        String tavoloStr  = request.getParameter("tavoloId");
        String data       = request.getParameter("data");
        String ora        = request.getParameter("ora");
        String personeStr = request.getParameter("persone");
        String note       = request.getParameter("note");

        int tavoloId;
        int persone;

        try {
            tavoloId = Integer.parseInt(tavoloStr);
            persone  = Integer.parseInt(personeStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errore", "Dati non validi. Controlla tavolo e numero di persone.");
            doGet(request, response);
            return;
        }

        // Validazioni minime (puoi raffinare quanto vuoi)
        if (data == null || data.isBlank() ||
                ora == null || ora.isBlank() ||
                persone <= 0) {

            request.setAttribute("errore", "Compila correttamente tutti i campi richiesti.");
            doGet(request, response);
            return;
        }

        try {
            // Logica applicativa: occupa tavolo + crea prenotazione
            int prenId = prenotazioneApplicativo
                    .creaPrenotazioneConTavoloPerOrdine(u, tavoloId, data, ora, persone, note);

            // Salvo in sessione il contesto dell'ordine
            session.setAttribute("prenotazioneCorrenteId", prenId);
            session.setAttribute("tavoloCorrenteId", tavoloId);
            session.setAttribute("modalitaServizio", "RISTORANTE");

            // Passo al menu per scegliere piatti e bevande
            response.sendRedirect("menu");

        } catch (PrenotazioneException e) {
            // Errore applicativo (tavolo occupato, problemi DB, ecc.)
            e.printStackTrace();
            request.setAttribute("errore", e.getMessage());
            doGet(request, response);
        }
    }
}
