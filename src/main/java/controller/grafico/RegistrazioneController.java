package controller.grafico;

import controller.applicativo.RegistrazioneApplicativo;
import controller.exception.RegistrazioneException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/registrazione")
public class RegistrazioneController extends HttpServlet {

    private RegistrazioneApplicativo registrazioneApplicativo;

    @Override
    public void init() throws ServletException {
        super.init();
        registrazioneApplicativo = new RegistrazioneApplicativo();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("utenteLoggato") != null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        request.getRequestDispatcher("registrazione.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String conferma = request.getParameter("confermaPassword");
        String nome     = request.getParameter("nome");
        String cognome  = request.getParameter("cognome");

        // ripopolo i campi in caso di errore
        request.setAttribute("username", username);
        request.setAttribute("nome", nome);
        request.setAttribute("cognome", cognome);

        try {
            registrazioneApplicativo.registraNuovoCliente(
                    username, password, conferma, nome, cognome
            );

            // registrazione andata a buon fine → messaggio e redirect al login
            HttpSession session = request.getSession();
            session.setAttribute("messaggioLogin",
                    "Registrazione completata. Ora effettua il login.");
            response.sendRedirect(request.getContextPath() + "/login");

        } catch (RegistrazioneException e) {
            request.setAttribute("erroreRegistrazione", e.getMessage());
            request.getRequestDispatcher("registrazione.jsp").forward(request, response);
        }
    }
}
