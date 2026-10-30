package model;

import java.sql.Date;

public class UgovorZaIznajmljivanje {
    private int idUgovor;
    private Date datumPocetka;
    private Date datumKraja;
    private double cijenaIznajmljivanja;
    private int zakupacId;
    private int stanId;
    private int statusZakupaId;
    private int vlasnikId;
    private int zaposleniId;

    // Konstruktor
    public UgovorZaIznajmljivanje(int idUgovor, Date datumPocetka, Date datumKraja, double cijenaIznajmljivanja,
                                  int zakupacId, int stanId, int statusZakupaId, int vlasnikId, int zaposleniId) {
        this.idUgovor = idUgovor;
        this.datumPocetka = datumPocetka;
        this.datumKraja = datumKraja;
        this.cijenaIznajmljivanja = cijenaIznajmljivanja;
        this.zakupacId = zakupacId;
        this.stanId = stanId;
        this.statusZakupaId = statusZakupaId;
        this.vlasnikId = vlasnikId;
        this.zaposleniId = zaposleniId;
    }

    // Getters and Setters
    public int getIdUgovor() {
        return idUgovor;
    }

    public void setIdUgovor(int idUgovor) {
        this.idUgovor = idUgovor;
    }

    public Date getDatumPocetka() {
        return datumPocetka;
    }

    public void setDatumPocetka(Date datumPocetka) {
        this.datumPocetka = datumPocetka;
    }

    public Date getDatumKraja() {
        return datumKraja;
    }

    public void setDatumKraja(Date datumKraja) {
        this.datumKraja = datumKraja;
    }

    public double getCijenaIznajmljivanja() {
        return cijenaIznajmljivanja;
    }

    public void setCijenaIznajmljivanja(double cijenaIznajmljivanja) {
        this.cijenaIznajmljivanja = cijenaIznajmljivanja;
    }

    public int getZakupacId() {
        return zakupacId;
    }

    public void setZakupacId(int zakupacId) {
        this.zakupacId = zakupacId;
    }

    public int getStanId() {
        return stanId;
    }

    public void setStanId(int stanId) {
        this.stanId = stanId;
    }

    public int getStatusZakupaId() {
        return statusZakupaId;
    }

    public void setStatusZakupaId(int statusZakupaId) {
        this.statusZakupaId = statusZakupaId;
    }

    public int getVlasnikId() {
        return vlasnikId;
    }

    public void setVlasnikId(int vlasnikId) {
        this.vlasnikId = vlasnikId;
    }

    public int getZaposleniId() {
        return zaposleniId;
    }

    public void setZaposleniId(int zaposleniId) {
        this.zaposleniId = zaposleniId;
    }
}

