package model;

public class TipStanaProdaja {
    private int idTipStana;
    private int brojSoba;
    private int kvadratura;
    private String balkon;
    private int stanIdStan;

    // Konstruktor
    public TipStanaProdaja(int idTipStana, int brojSoba, int kvadratura, String balkon, int stanIdStan) {
        this.idTipStana = idTipStana;
        this.brojSoba = brojSoba;
        this.kvadratura = kvadratura;
        this.balkon = balkon;
        this.stanIdStan = stanIdStan;
    }

    // Getteri i setteri
    public int getIdTipStana() {
        return idTipStana;
    }

    public void setIdTipStana(int idTipStana) {
        this.idTipStana = idTipStana;
    }

    public int getBrojSoba() {
        return brojSoba;
    }

    public void setBrojSoba(int brojSoba) {
        this.brojSoba = brojSoba;
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

    public int getStanIdStan() {
        return stanIdStan;
    }

    public void setStanIdStan(int stanIdStan) {
        this.stanIdStan = stanIdStan;
    }
}
