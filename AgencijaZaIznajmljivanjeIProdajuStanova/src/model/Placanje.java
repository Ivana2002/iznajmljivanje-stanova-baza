package model;

import java.sql.Date;

public class Placanje {
    private int idPlacanje;
    private Date datumPlacanja;
    private double iznos;
    private int idUgovor;

  
    public Placanje(int idPlacanje, Date datumPlacanja, double iznos, int idUgovor) {
        this.idPlacanje = idPlacanje;
        this.datumPlacanja = datumPlacanja;
        this.iznos = iznos;
        this.idUgovor = idUgovor;
    }

   
    public int getIdPlacanje() {
        return idPlacanje;
    }

    public void setIdPlacanje(int idPlacanje) {
        this.idPlacanje = idPlacanje;
    }

    public Date getDatumPlacanja() {
        return datumPlacanja;
    }

    public void setDatumPlacanja(Date datumPlacanja) {
        this.datumPlacanja = datumPlacanja;
    }

    public double getIznos() {
        return iznos;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
    }

    public int getIdUgovor() {
        return idUgovor;
    }

    public void setIdUgovor(int idUgovor) {
        this.idUgovor = idUgovor;
    }
}
