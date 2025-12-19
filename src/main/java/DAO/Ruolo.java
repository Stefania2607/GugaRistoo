package DAO;
public enum Ruolo {
    CAMERIERE(1),
    ADMIN(2),
    CLIENTE(3);

    private final int id;

    Ruolo(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Ruolo fromId(int id) {
        for (Ruolo ruolo : values()) {
            if (ruolo.getId() == id) {
                return ruolo;
            }
        }
        return null;
    }
}
