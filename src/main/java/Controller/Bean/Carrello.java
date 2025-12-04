package Controller.Bean;

import java.util.ArrayList;
import java.util.List;

public class Carrello {

    private List<CarrelloItem> items = new ArrayList<>();

    public List<CarrelloItem> getItems() {
        return items;
    }

    public void addPiatto(Piatto p) {
        // se il piatto già c'è, aumento quantità
        for (CarrelloItem item : items) {
            if (item.getPiatto().getId() == p.getId()) {
                item.incrementa();
                return;
            }
        }
        // altrimenti lo aggiungo come nuovo
        items.add(new CarrelloItem(p));
    }

    public void incrementa(int idPiatto) {
        for (CarrelloItem item : items) {
            if (item.getPiatto().getId() == idPiatto) {
                item.incrementa();
                return;
            }
        }
    }

    public void decrementa(int idPiatto) {
        for (int i = 0; i < items.size(); i++) {
            CarrelloItem item = items.get(i);
            if (item.getPiatto().getId() == idPiatto) {
                item.decrementa();
                if (item.getQuantita() <= 0) {
                    items.remove(i);
                }
                return;
            }
        }
    }

    public void remove(int idPiatto) {
        items.removeIf(item -> item.getPiatto().getId() == idPiatto);
    }

    public double getTotale() {
        double totale = 0.0;
        for (CarrelloItem item : items) {
            totale += item.getSubtotale();
        }
        return totale;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
