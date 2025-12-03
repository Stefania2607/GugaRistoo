package Controller.Grafico;

import DAO.PiattoDAO;
import Controller.Bean.Piatto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/menu")
public class MenuController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // 1) Prendo i piatti dal DAO
        PiattoDAO dao = new PiattoDAO();
        List<Piatto> piatti = dao.findAll();

        // 2) Li metto nella request
        request.setAttribute("listaPiatti", piatti);

        // 3) Giro la palla alla JSP
        RequestDispatcher rd = request.getRequestDispatcher("menu.jsp");
        rd.forward(request, response);
    }
}
