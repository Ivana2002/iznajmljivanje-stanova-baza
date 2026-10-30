package model;

import java.sql.Date;

public class RecenzijeStanovaZaIznajmljivanje {
    private int idRecenzija;
    private double ocjena;
    private int brojStana;
    private String komentar;
    private Date datumRecenzije;
    private String klijent;
    private int idStan;

    
    public RecenzijeStanovaZaIznajmljivanje(int idRecenzija, double ocjena, int brojStana, String komentar, Date datumRecenzije, String klijent, int idStan) {
        this.idRecenzija = idRecenzija;
        this.ocjena = ocjena;
        this.brojStana = brojStana;
        this.komentar = komentar;
        this.datumRecenzije = datumRecenzije;
        this.klijent = klijent;
        this.idStan = idStan;
    }

 
    public int getIdRecenzija() {
        return idRecenzija;
    }

    public void setIdRecenzija(int idRecenzija) {
        this.idRecenzija = idRecenzija;
    }

    public double getOcjena() {
        return ocjena;
    }

    public void setOcjena(double ocjena) {
        this.ocjena = ocjena;
    }

    public int getBrojStana() {
        return brojStana;
    }

    public void setBrojStana(int brojStana) {
        this.brojStana = brojStana;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public Date getDatumRecenzije() {
        return datumRecenzije;
    }

    public void setDatumRecenzije(Date datumRecenzije) {
        this.datumRecenzije = datumRecenzije;
    }

    public String getKlijent() {
        return klijent;
    }

    public void setKlijent(String klijent) {
        this.klijent = klijent;
    }

    public int getIdStan() {
        return idStan;
    }

    public void setIdStan(int idStan) {
        this.idStan = idStan;
    }
}
