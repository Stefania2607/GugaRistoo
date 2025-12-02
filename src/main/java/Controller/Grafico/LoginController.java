package Controller.Grafico;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String codice = request.getParameter("codice");

        System.out.println("Nome inserito: " + nome);
        System.out.println("Codice inserito: " + codice);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/home.jsp");
        dispatcher.forward(request, response);
    }
}

