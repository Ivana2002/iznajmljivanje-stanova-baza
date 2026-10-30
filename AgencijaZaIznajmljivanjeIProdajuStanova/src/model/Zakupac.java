package model;

public class Zakupac {
    private int idZakupac;
    private String ime;
    private String prezime;
    private String telefon;
    private String email;

    // Konstruktor
    public Zakupac(int idZakupac, String ime, String prezime, String telefon, String email) {
        this.idZakupac = idZakupac;
        this.ime = ime;
        this.prezime = prezime;
        this.telefon = telefon;
        this.email = email;
    }

    // Getteri i setteri
    public int getIdZakupac() {
        return idZakupac;
    }

    public void setIdZakupac(int idZakupac) {
        this.idZakupac = idZakupac;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Dodajemo toString() metodu za prikaz u JComboBox-u
    @Override
    public String toString() {
        return ime + " " + prezime;
    }
}