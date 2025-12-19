package controller.grafico;

import controller.applicativo.LoginApplicativo;
import controller.bean.Utente;
import controller.exception.DAOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private LoginApplicativo loginApplicativo;

    @Override
    public void init() throws ServletException {
        loginApplicativo = new LoginApplicativo();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Utente u = (session != null) ? (Utente) session.getAttribute("utenteLoggato") : null;

        if (u != null) {
            redirectByRuolo(request, response, u.getRuolo());
            return;
        }

        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username != null) username = username.trim();
        if (password != null) password = password.trim();

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("erroreLogin", "Inserisci username e password");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        Utente u;
        u = loginApplicativo.autentica(username, password);

        if (u == null) {
            request.setAttribute("erroreLogin", "Username o password non validi");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // Sessione: (opzionale ma consigliato) invalida e ricrea
        HttpSession old = request.getSession(false);
        if (old != null) old.invalidate();

        HttpSession session = request.getSession(true);
        session.setAttribute("utenteLoggato", u);
        session.setAttribute("ruolo", u.getRuolo());

        redirectByRuolo(request, response, u.getRuolo());
    }

    private void redirectByRuolo(HttpServletRequest request, HttpServletResponse response, String ruolo)
            throws IOException {

        String ctx = request.getContextPath();

        if ("ADMIN".equals(ruolo)) {
            response.sendRedirect(ctx + "/adminHome.jsp");
        } else if ("CAMERIERE".equals(ruolo)) {
            response.sendRedirect(ctx + "/cameriereHome.jsp");
        } else {
            response.sendRedirect(ctx + "/clienteHome.jsp");
        }
    }
}
