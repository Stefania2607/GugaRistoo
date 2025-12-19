package controller.Grafico;

import controller.Bean.Carrello;
import controller.Bean.Piatto;
import controller.Exception.DAOException;
import DAO.PiattoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/aggiungiAlCarrello")
public class AggiungiAlCarrelloController extends HttpServlet {

    private PiattoDAO piattoDAO = new PiattoDAO();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1) leggo il parametro dal form
            String idParam = request.getParameter("idPiatto");
            if (idParam == null) {
                // niente id -> torno al menu
                response.sendRedirect(request.getContextPath() + "/menuOrdinaDaCasa");
                return;
            }

            int idPiatto = Integer.parseInt(idParam);

            // 2) recupero il piatto dal DB (questo metodo lancia DAOException)
            Piatto p = piattoDAO.findById(idPiatto);

            if (p == null) {
                // id inesistente -> torno al menu
                response.sendRedirect(request.getContextPath() + "/menuOrdinaDaCasa");
                return;
            }

            // 3) prendo la sessione
            HttpSession session = request.getSession();

            // 4) recupero/creo il carrello in sessione
            Carrello carrello = (Carrello) session.getAttribute("carrello");
            if (carrello == null) {
                carrello = new Carrello();
                session.setAttribute("carrello", carrello);
            }

            // 5) aggiungo il piatto al carrello
            carrello.aggiungiPiatto(p);

            System.out.println("[CARRELLO] Aggiunto " + p.getNome()
                    + " | articoli totali: " + carrello.getNumeroArticoli());

            response.sendRedirect(request.getContextPath() + "/menuOrdinaDaCasa");


        } catch (NumberFormatException e) {
            // parametro non numerico
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/menuOrdinaDaCasa");

        } catch (DAOException e) {
            // errore del DAO: lo wrappo in ServletException
            e.printStackTrace();
            throw new ServletException("Errore nel recupero del piatto dal DB", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        // se qualcuno chiama in GET, gestisco allo stesso modo
        doPost(request, response);
    }
}
