package controller.Bean;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class Carrello {

    // idPiatto → RigaCarrello
    private Map<Integer, RigaCarrello> righe = new LinkedHashMap<>();

    public Map<Integer, RigaCarrello> getRighe() {
        return righe;
    }

    public void aggiungiPiatto(Piatto p) {
        int id = p.getId();

        RigaCarrello r = righe.get(id);
        if (r == null) {
            r = new RigaCarrello(p, 1);
            righe.put(id, r);
        } else {
            r.incrementa();
        }
    }

    public void rimuoviPiatto(Piatto p) {
        int id = p.getId();
        RigaCarrello r = righe.get(id);
        if (r != null) {
            r.decrementa();
            if (r.getQuantita() <= 0) righe.remove(id);
        }
    }

    public void svuota() {
        righe.clear();
    }

    public BigDecimal getTotale() {
        BigDecimal totale = BigDecimal.ZERO;
        for (RigaCarrello r : righe.values()) {
            totale = totale.add(r.getTotaleRiga());
        }
        return totale;
    }

    public int getNumeroArticoli() {
        int totale = 0;
        for (RigaCarrello r : righe.values()) {
            totale += r.getQuantita();
        }
        return totale;
    }
    public void incrementaPiatto(int idPiatto) {
        RigaCarrello r = righe.get(idPiatto);
        if (r != null) {
            r.incrementa();
        }
    }

    public void decrementaPiatto(int idPiatto) {
        RigaCarrello r = righe.get(idPiatto);
        if (r != null) {
            r.decrementa();
            if (r.getQuantita() <= 0) {
                righe.remove(idPiatto);  // se arriva a 0, elimino la riga dal carrello
            }
        }
    }

    public boolean isVuoto() {
        return righe == null || righe.isEmpty();
    }

}
