package controller.Grafico;

import controller.Exception.DAOException;
import DAO.PiattoDAO;
import controller.Bean.Piatto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/bevande")
public class BevandeController extends HttpServlet {
    private final PiattoDAO piattoDAO = new PiattoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tipo = request.getParameter("tipo");   // es: "birre", "analcolici", "vini_rossi"...
        String sotto = request.getParameter("sotto"); // es: "rossi", "bianchi", "tutti" (solo per vini)

        List<Piatto> bevande;

        try {
            // Se stai filtrando i VINI con sotto-categorie
            if ("vini".equalsIgnoreCase(tipo)) {
                bevande = piattoDAO.findVini(sotto);   // sotto può essere null → nel DAO diventa "tutti"
            }
            // Se stai filtrando per tipo_bevanda specifico (birre, analcolici, acqua, ecc.)
            else if (tipo != null && !tipo.isBlank()) {
                bevande = piattoDAO.findBevandeByTipo(tipo);
            }
            // Default: tutte le bevande
            else {
                bevande = piattoDAO.findBevande();
            }

        } catch (DAOException e) {
            // gestione centralizzata: qui decidi cosa fare (500 / pagina errore / messaggio)
            throw new ServletException("Errore nel caricamento bevande", e);
        }

        request.setAttribute("bevande", bevande);
        request.getRequestDispatcher("bevande.jsp").forward(request, response);
    }
}