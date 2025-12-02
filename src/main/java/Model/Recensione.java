package Model;

import java.time.LocalDateTime;

public class Recensione {

    private final int id;
    private final Utente cliente;
    private final int voto;  // 1–5
    private final String commento;
    private final LocalDateTime data;

    public Recensione(int id, Utente cliente, int voto, String commento) {
        if (id <= 0) throw new IllegalArgumentException("Id review invalido.");
        if (cliente == null) throw new IllegalArgumentException("Cliente obbligatorio.");
        if (voto < 1 || voto > 5) throw new IllegalArgumentException("Voto deve essere tra 1 e 5.");

        this.id = id;
        this.cliente = cliente;
        this.voto = voto;
        this.commento = commento;
        this.data = LocalDateTime.now();
    }

    public int getId() { return id; }
    public Utente getCliente() { return cliente; }
    public int getVoto() { return voto; }
    public String getCommento() { return commento; }
    public LocalDateTime getData() { return data; }
}
