package Controller.Grafico;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/testdb")
public class TestDB extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");

        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource) envCtx.lookup("jdbc/ristoranteDB");

            try (Connection conn = ds.getConnection()) {
                resp.getWriter().println("CONNESSIONE OK → " + conn);
            }

        } catch (Exception e) {
            e.printStackTrace(resp.getWriter());
        }
    }
}

