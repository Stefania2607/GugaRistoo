// src/main/java/Controller/Grafico/AggiungiPiattoController.java
package controller.grafico;

import controller.applicativo.PiattoApplicativo;
import controller.exception.DatiNonValidiException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/aggiungiPiatto")
public class AggiungiPiattoController extends HttpServlet {

    private transient PiattoApplicativo piattoApplicativo;

    @Override
    public void init() throws ServletException {
        super.init();
        this.piattoApplicativo = new PiattoApplicativo();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String prezzoString = request.getParameter("prezzo");

        try {
            // delega tutta la logica all'applicativo
            piattoApplicativo.aggiungiPiatto(nome, prezzoString);

            // se va tutto bene → torna al menu
            response.sendRedirect(request.getContextPath() + "/menu");

        } catch (DatiNonValidiException e) {
            // errore lato utente → torno alla JSP del form con messaggio
            request.setAttribute("errore", e.getMessage());
            request.setAttribute("nome", nome);
            request.setAttribute("prezzo", prezzoString);

            request.getRequestDispatcher("/WEB-INF/views/aggiungiPiatto.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            // errore imprevisto → 500 gestito
            throw new ServletException("Errore interno durante l'aggiunta del piatto.", e);
        }
    }
}
