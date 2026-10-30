package model;

public class TipStanaIznajmljivanje {
    private int idTipStana;
    private String opis;

    // Konstruktor
    public TipStanaIznajmljivanje(int idTipStana, String opis) {
        this.idTipStana = idTipStana;
        this.opis = opis;
    }

    // Getteri i setteri
    public int getIdTipStana() {
        return idTipStana;
    }

    public void setIdTipStana(int idTipStana) {
        this.idTipStana = idTipStana;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    // Dodajemo toString() metodu za ispravan prikaz u JComboBox
    @Override
    public String toString() {
        return opis; // Vraća opis tipa stana (npr. "Na dan", "Na mesec")
    }
}