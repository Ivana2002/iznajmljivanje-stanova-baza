package model;

public class Vlasnik {
	
    private int idVlasnik;
    private String ime;
    private String prezime;
    private String email;
    private String telefon;

    public Vlasnik(int idVlasnik, String ime, String prezime, String email, String telefon) {
        super();
        this.idVlasnik = idVlasnik;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.telefon = telefon;
    }

    // Getters and setters
    public int getIdVlasnik() {
        return idVlasnik;
    }

    public void setIdVlasnik(int idVlasnik) {
        this.idVlasnik = idVlasnik;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    // Dodajemo toString() metodu za ispravan prikaz u JComboBox
    @Override
    public String toString() {
        return ime + " " + prezime;
    }
}