package model;

import java.util.Date;

public class UgovorZaProdaju {
    private int idUgovor;
    private Date datumProdaje;
    private String imeKupca;
    private int brojStana;
    private int stanIdStan;
    private String tipStana;
    private int kvadratura;
   
    private String balkon;
    private String imeProdavca;
    private double cijenaStana;
    private int zaposleniIdZaposleni;
    private int zakupacIdZakupac;
    private int vlasnikIdVlasnik;

    // Konstruktor
    public UgovorZaProdaju(int idUgovor, Date datumProdaje, String imeKupca, int brojStana, int stanIdStan,
                           String tipStana, int kvadratura,  String balkon,
                           String imeProdavca, double cijenaStana, int zaposleniIdZaposleni, int zakupacIdZakupac,
                           int vlasnikIdVlasnik) {
        this.idUgovor = idUgovor;
        this.datumProdaje = datumProdaje;
        this.imeKupca = imeKupca;
        this.brojStana = brojStana;
        this.stanIdStan = stanIdStan;
        this.tipStana = tipStana;
        this.kvadratura = kvadratura;
      
        this.balkon = balkon;
        this.imeProdavca = imeProdavca;
        this.cijenaStana = cijenaStana;
        this.zaposleniIdZaposleni = zaposleniIdZaposleni;
        this.zakupacIdZakupac = zakupacIdZakupac;
        this.vlasnikIdVlasnik = vlasnikIdVlasnik;
    }

    // Getteri i setteri
    public int getIdUgovor() {
        return idUgovor;
    }

    public void setIdUgovor(int idUgovor) {
        this.idUgovor = idUgovor;
    }

    public Date getDatumProdaje() {
        return datumProdaje;
    }

    public void setDatumProdaje(Date datumProdaje) {
        this.datumProdaje = datumProdaje;
    }

    public String getImeKupca() {
        return imeKupca;
    }

    public void setImeKupca(String imeKupca) {
        this.imeKupca = imeKupca;
    }

    public int getBrojStana() {
        return brojStana;
    }

    public void setBrojStana(int brojStana) {
        this.brojStana = brojStana;
    }

    public int getStanIdStan() {
        return stanIdStan;
    }

    public void setStanIdStan(int stanIdStan) {
        this.stanIdStan = stanIdStan;
    }

    public String getTipStana() {
        return tipStana;
    }

    public void setTipStana(String tipStana) {
        this.tipStana = tipStana;
    }

    public int getKvadratura() {
        return kvadratura;
    }

    public void setKvadratura(int kvadratura) {
        this.kvadratura = kvadratura;
    }

   

    public String getBalkon() {
        return balkon;
    }

    public void setBalkon(String balkon) {
        this.balkon = balkon;
    }

    public String getImeProdavca() {
        return imeProdavca;
    }

    public void setImeProdavca(String imeProdavca) {
        this.imeProdavca = imeProdavca;
    }

    public double getCijenaStana() {
        return cijenaStana;
    }

    public void setCijenaStana(double cijenaStana) {
        this.cijenaStana = cijenaStana;
    }

    public int getZaposleniIdZaposleni() {
        return zaposleniIdZaposleni;
    }

    public void setZaposleniIdZaposleni(int zaposleniIdZaposleni) {
        this.zaposleniIdZaposleni = zaposleniIdZaposleni;
    }

    public int getZakupacIdZakupac() {
        return zakupacIdZakupac;
    }

    public void setZakupacIdZakupac(int zakupacIdZakupac) {
        this.zakupacIdZakupac = zakupacIdZakupac;
    }

    public int getVlasnikIdVlasnik() {
        return vlasnikIdVlasnik;
    }

    public void setVlasnikIdVlasnik(int vlasnikIdVlasnik) {
        this.vlasnikIdVlasnik = vlasnikIdVlasnik;
    }
}
