package DAO;

import Model.Piatto;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PiattoDAO {

    private DataSource dataSource;

    public PiattoDAO() {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            this.dataSource = (DataSource) envCtx.lookup("jdbc/ristoranteDB");
        } catch (NamingException e) {
            throw new RuntimeException("Errore nella lookup della DataSource", e);
        }
    }

    public List<Piatto> findAll() {
        List<Piatto> lista = new ArrayList<>();

        String sql = "SELECT id, nome, descrizione, prezzo, categoria FROM piatto";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String descrizione = rs.getString("descrizione");
                double prezzo = rs.getDouble("prezzo");
                String categoria = rs.getString("categoria");

                Piatto.Categoria catEnum = Piatto.Categoria.valueOf(categoria);

                Piatto p = new Piatto(id, nome, descrizione, prezzo, catEnum);
                lista.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore in findAll(): " + e.getMessage(), e);
        }

        return lista;
    }
}
