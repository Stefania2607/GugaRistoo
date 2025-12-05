package Controller.Grafico;

import Controller.Applicativo.MenuApplicativo;
import Controller.Bean.Piatto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/menu")
public class MenuController extends HttpServlet {

    private MenuApplicativo menuApplicativo;

    @Override
    public void init() throws ServletException {
        super.init();
        menuApplicativo = new MenuApplicativo();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // es. parametro ?categoria=primi
        String categoria = request.getParameter("categoria");

        List<Piatto> lista;

        if (categoria == null || categoria.isBlank()) {
            lista = menuApplicativo.getMenuCompleto();
        } else {
            lista = menuApplicativo.getMenuPerCategoria(categoria);
        }

        request.setAttribute("listaPiatti", lista);

        // parte grafica: decido quale JSP mostrare
        request.getRequestDispatcher("menu.jsp").forward(request, response);
    }
}

