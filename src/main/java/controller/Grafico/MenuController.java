package controller.Grafico;

import controller.applicativo.MenuApplicativo;
import controller.Bean.Piatto;
import controller.Exception.DAOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/menu")
public class MenuController extends HttpServlet {

    private MenuApplicativo menuApplicativo;

    @Override
    public void init() throws ServletException {
        menuApplicativo = new MenuApplicativo();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String categoria = request.getParameter("categoria");

        try {
            List<Piatto> lista = (categoria == null || categoria.isBlank())
                    ? menuApplicativo.getMenuPerCategoria(null)
                    : menuApplicativo.getMenuPerCategoria(categoria);

            request.setAttribute("listaPiatti", lista);
            request.getRequestDispatcher("menu.jsp").forward(request, response);

        } catch (DAOException e) {
            throw new ServletException("Errore nel caricamento del menu", e);
        }
    }
}
