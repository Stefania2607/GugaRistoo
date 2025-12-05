package Controller.Grafico;

import Controller.Applicativo.LoginApplicativo;
import Controller.Bean.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private LoginApplicativo loginApplicativo;

    @Override
    public void init() throws ServletException {
        super.init();
        loginApplicativo = new LoginApplicativo();  // usa applicativo
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Delego al controller applicativo
        Utente u = loginApplicativo.autentica(username, password);

        if (u == null) {
            request.setAttribute("erroreLogin", "Username o password non validi");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // Parte grafica: gestione sessione, navigazione
        HttpSession session = request.getSession();
        session.setAttribute("utenteLoggato", u);
        session.setAttribute("ruolo", u.getRuolo());

        switch (u.getRuolo()) {
            case "ADMIN":
                response.sendRedirect("adminHome.jsp");
                break;
            case "CAMERIERE":
                response.sendRedirect("cameriereHome.jsp");
                break;
            case "CLIENTE":
            default:
                response.sendRedirect("clienteHome.jsp");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Utente u = (session != null) ? (Utente) session.getAttribute("utenteLoggato") : null;

        // Se l'utente è già autenticato, NON ha senso far vedere di nuovo il form di login
        if (u != null) {
            String ruolo = u.getRuolo();

            switch (ruolo) {
                case "ADMIN":
                    response.sendRedirect("adminHome.jsp");
                    return;
                case "CAMERIERE":
                    response.sendRedirect("cameriereHome.jsp");
                    return;
                case "CLIENTE":
                default:
                    response.sendRedirect("clienteHome.jsp");
                    return;
            }
        }

        // Utente non autenticato → mostro la pagina di login
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

}
