package controller.applicativo;

import controller.Bean.Piatto;
import controller.Exception.DAOException;
import DAO.PiattoDAO;

import java.util.List;

public class MenuApplicativo {

    private final PiattoDAO piattoDAO = new PiattoDAO();

    public List<Piatto> getMenuPerCategoria(String categoria) throws DAOException {
        if (categoria == null || categoria.isBlank()) {
            return piattoDAO.findAll();
        }
        return piattoDAO.findByCategoria(categoria);
    }

    public Piatto getPiattoById(int id) throws DAOException {
        return piattoDAO.findById(id);
    }


}
