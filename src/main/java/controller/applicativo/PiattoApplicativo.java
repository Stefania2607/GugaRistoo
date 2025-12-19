// src/main/java/Controller/Applicativo/PiattoApplicativo.java
package controller.applicativo;

import controller.bean.Piatto;
import controller.exception.DAOException;
import controller.exception.DatiNonValidiException;
import dao.PiattoDAO;

import java.math.BigDecimal;

public class PiattoApplicativo {

    private final PiattoDAO piattoDAO = new PiattoDAO();

    public void aggiungiPiatto(String nome, String prezzoString) throws DatiNonValidiException, DAOException {

        // VALIDAZIONE BASE
        if (nome == null || nome.isBlank()) {
            throw new DatiNonValidiException("Il nome del piatto è obbligatorio.");
        }
        if (prezzoString == null || prezzoString.isBlank()) {
            throw new DatiNonValidiException("Il prezzo è obbligatorio.");
        }

        // PARSING PREZZO
        BigDecimal prezzo;
        try {
            // se in form usi la virgola, la normalizziamo
            prezzo = new BigDecimal(prezzoString.replace(",", "."));
        } catch (NumberFormatException e) {
            throw new DatiNonValidiException("Formato del prezzo non valido.");
        }

        if (prezzo.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DatiNonValidiException("Il prezzo deve essere positivo.");
        }

        // CREAZIONE BEAN
        Piatto p = new Piatto();
        p.setNome(nome.trim());
        p.setPrezzo(prezzo);

        // CHIAMATA AL DAO
        piattoDAO.insert(p);
    }
}
