package Controller.Applicativo;

import Controller.Bean.Piatto;
import DAO.PiattoDAO;

import java.util.List;

public class MenuApplicativo {

    private PiattoDAO piattoDAO;

    public MenuApplicativo() {
        this.piattoDAO = new PiattoDAO();
    }

    /**
     * Restituisce l'intero menu (tutti i piatti).
     * Logica applicativa: potresti in futuro filtrare piatti non disponibili,
     * ordinare per categoria, prezzo, ecc.
     */
    public List<Piatto> getMenuCompleto() {
        return piattoDAO.findAll();
    }

    /**
     * Restituisce i piatti filtrati per categoria logica (es. "primi", "secondi", "dolci"...).
     * Se categoria è nulla o vuota, torna il menu completo.
     */
    public List<Piatto> getMenuPerCategoria(String categoria) {

        if (categoria == null || categoria.isBlank()) {
            return piattoDAO.findAll();
        }

        // qui dai un significato applicativo alla categoria
        // ad esempio: normalizzi in minuscolo
        String cat = categoria.trim().toLowerCase();

        return piattoDAO.findByCategoria(cat);
    }
}
