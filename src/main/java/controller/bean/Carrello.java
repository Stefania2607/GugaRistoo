package controller.bean;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Carrello implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // idPiatto → RigaCarrello
    private final Map<Integer, RigaCarrello> righe = new LinkedHashMap<>();
    public Map<Integer, RigaCarrello> getRighe() {
        return Collections.unmodifiableMap(righe);
    }

    public void aggiungiPiatto(Piatto p) {
        if (p == null) return; // difesa minima: evita NPE

        int id = p.getId();
        RigaCarrello r = righe.get(id);

        if (r == null) {
            righe.put(id, new RigaCarrello(p, 1));
        } else {
            r.incrementa();
        }
    }

    public void rimuoviPiatto(Piatto p) {
        if (p == null) return;

        int id = p.getId();
        RigaCarrello r = righe.get(id);

        if (r != null) {
            r.decrementa();
            if (r.getQuantita() <= 0) {
                righe.remove(id);
            }
        }
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
                righe.remove(idPiatto);
            }
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

    public boolean isVuoto() {
        // righe non è mai null (è final e inizializzata), quindi basta questo:
        return righe.isEmpty();
    }
}
