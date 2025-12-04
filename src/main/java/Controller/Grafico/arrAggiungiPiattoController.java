package Controller.Grafico;

import Controller.Bean.Piatto;
import DAO.PiattoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/aggiungiPiatto")
public class arrAggiungiPiattoController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String prezzo = request.getParameter("prezzo");

        Piatto p = new Piatto();
        p.setNome(nome);
        String prezzoString = request.getParameter("prezzo");
        p.setPrezzo(new BigDecimal(prezzoString));


        new PiattoDAO().insert(p);

        response.sendRedirect(request.getContextPath() + "/menu");
    }
}
