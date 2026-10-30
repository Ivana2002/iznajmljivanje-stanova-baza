package model;

public class Drzava {
    private int idDrzava;
    private String naziv;

   
    public Drzava(int idDrzava, String naziv) {
        this.idDrzava = idDrzava;
        this.naziv = naziv;
    }

   
    public int getIdDrzava() {
        return idDrzava;
    }

    public void setIdDrzava(int idDrzava) {
        this.idDrzava = idDrzava;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    @Override
    public String toString() {
        return naziv;
    }
}
