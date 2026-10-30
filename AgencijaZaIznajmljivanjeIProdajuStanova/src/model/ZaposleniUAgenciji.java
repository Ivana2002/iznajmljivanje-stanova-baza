package model;

public class ZaposleniUAgenciji {
    private int idZaposleni;
    private String ime;
    private String prezime;
    private String telefon;
    private String email;

    // Konstruktor
    public ZaposleniUAgenciji(int idZaposleni, String ime, String prezime, String telefon, String email) {
        this.idZaposleni = idZaposleni;
        this.ime = ime;
        this.prezime = prezime;
        this.telefon = telefon;
        this.email = email;
    }

    // Getteri i setteri
    public int getIdZaposleni() {
        return idZaposleni;
    }

    public void setIdZaposleni(int idZaposleni) {
        this.idZaposleni = idZaposleni;
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
    @Override
    public String toString() {
        return ime + " " + prezime; // Prikazuj ime i prezime
    }
}

