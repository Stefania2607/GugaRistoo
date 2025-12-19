package controller.grafico;

import controller.applicativo.PrenotazioneApplicativo;
import controller.bean.Utente;

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
            response.sendRedirect("login");
            return;
        }

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

        // 1) Lettura parametri form
        String data         = request.getParameter("data");          // es: 2025-12-25
        String ora          = request.getParameter("ora");           // es: 20:30
        String personeStr   = request.getParameter("numeroPersone");
        String note         = request.getParameter("note");
        String nomePrenotante = request.getParameter("nomePrenotante");

        // Riporto i valori in request per ripopolare i campi in caso di errore
        request.setAttribute("data", data);
        request.setAttribute("ora", ora);
        request.setAttribute("numeroPersone", personeStr);
        request.setAttribute("note", note);
        request.setAttribute("nomePrenotante", nomePrenotante);

        boolean hasError = false;

        if (data == null || data.isBlank()) {
            request.setAttribute("erroreData", "Compila questo campo.");
            hasError = true;
        }

        if (ora == null || ora.isBlank()) {
            request.setAttribute("erroreOra", "Compila questo campo.");
            hasError = true;
        }

        if (personeStr == null || personeStr.isBlank()) {
            request.setAttribute("errorePersone", "Compila questo campo.");
            hasError = true;
        }

        int persone = 0;
        if (!hasError) {
            try {
                persone = Integer.parseInt(personeStr);
                if (persone <= 0) {
                    request.setAttribute("errorePersone", "Inserisci un numero di persone valido.");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorePersone", "Inserisci un numero di persone valido.");
                hasError = true;
            }
        }

        if (hasError) {
            request.setAttribute("errorePrenotazione", "Controlla i campi evidenziati.");
            request.getRequestDispatcher("prenotaTavolo.jsp").forward(request, response);
            return;
        }

        System.out.println("[PrenotaTavoloController] utenteId=" + u.getId() +
                ", data=" + data + ", ora=" + ora +
                ", persone=" + persone + ", note=" + note);

        // 2) Logica applicativa
        // ⚠️ Modifica PrenotazioneApplicativo.creaPrenotazioneSoloTavolo
        // in modo che ritorni l'id della prenotazione (int), non più boolean.
        int idPrenotazione = prenotazioneApplicativo.creaPrenotazioneSoloTavolo(
                u, data, ora, persone, note
        );

        if (idPrenotazione <= 0) {
            request.setAttribute("errorePrenotazione",
                    "Errore durante la prenotazione. Controlla i dati inseriti o riprova più tardi.");
            request.getRequestDispatcher("prenotaTavolo.jsp").forward(request, response);
            return;
        }

        // 3) Salvo in sessione i dati necessari per collegare ordine ↔ prenotazione
        session.setAttribute("idPrenotazione", idPrenotazione);
        session.setAttribute("dataPrenotazione", data);
        session.setAttribute("orarioPrenotazione", ora);
        session.setAttribute("numeroPersonePrenotazione", persone);
        // l'id_tavolo lo recupererai poi in checkout tramite JOIN su tabella prenotazione

        // 4) Messaggio di conferma alla JSP
        request.setAttribute("data", data);
        request.setAttribute("ora", ora);
        request.setAttribute("persone", persone);
        request.setAttribute("note", note);

        request.setAttribute(
                "messaggioPrenotazione",
                "Prenotazione registrata con successo per il " + data + " alle " + ora + "."
        );

        request.getRequestDispatcher("prenotaTavolo.jsp").forward(request, response);
    }
}
