package Controller.Grafico;

import DAO.PiattoDAO;
import Controller.Bean.Piatto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/bevande")
public class BevandeController extends HttpServlet {

    private PiattoDAO piattoDAO = new PiattoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Carico TUTTE le bevande (categoria = 'bevanda')
        List<Piatto> bevande = piattoDAO.findBevande();
        request.setAttribute("bevande", bevande);

        // Giro alla pagina dedicata
        request.getRequestDispatcher("bevande.jsp").forward(request, response);
    }
}
