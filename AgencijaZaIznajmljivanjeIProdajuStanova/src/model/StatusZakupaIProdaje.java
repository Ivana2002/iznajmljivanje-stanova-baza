package model;

public class StatusZakupaIProdaje {
    private int idStatusZakupa;
    private String opis;

    // Konstruktor
    public StatusZakupaIProdaje(int idStatusZakupa, String opis) {
        this.idStatusZakupa = idStatusZakupa;
        this.opis = opis;
    }

    // Getteri i setteri
    public int getIdStatusZakupa() {
        return idStatusZakupa;
    }

    public void setIdStatusZakupa(int idStatusZakupa) {
        this.idStatusZakupa = idStatusZakupa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
