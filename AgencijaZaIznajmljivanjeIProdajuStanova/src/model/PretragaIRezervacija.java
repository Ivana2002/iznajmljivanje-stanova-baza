package model;

import java.sql.Date;

public class PretragaIRezervacija {
    private int idPretragaIRezervacija;
    private int zakupacId; 
    private String lokacija;
    private String tipZaIznajmljivanje;
    private Date rezervacijaOD;
    private Date rezervacijaDO;
    private boolean dostupnost;

    
    public PretragaIRezervacija(int idPretragaIRezervacija, int zakupacId, String lokacija, String tipZaIznajmljivanje, Date rezervacijaOD, Date rezervacijaDO, boolean dostupnost) {
        this.idPretragaIRezervacija = idPretragaIRezervacija;
        this.zakupacId = zakupacId;
        this.lokacija = lokacija;
        this.tipZaIznajmljivanje = tipZaIznajmljivanje;
        this.rezervacijaOD = rezervacijaOD;
        this.rezervacijaDO = rezervacijaDO;
        this.dostupnost = dostupnost;
    }

  
    public PretragaIRezervacija() {}

   
    public int getIdPretragaIRezervacija() {
        return idPretragaIRezervacija;
    }

    public void setIdPretragaIRezervacija(int idPretragaIRezervacija) {
        this.idPretragaIRezervacija = idPretragaIRezervacija;
    }

    public int getZakupacId() {
        return zakupacId;
    }

    public void setZakupacId(int zakupacId) {
        this.zakupacId = zakupacId;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    public String getTipZaIznajmljivanje() {
        return tipZaIznajmljivanje;
    }

    public void setTipZaIznajmljivanje(String tipZaIznajmljivanje) {
        this.tipZaIznajmljivanje = tipZaIznajmljivanje;
    }

    public Date getRezervacijaOD() {
        return rezervacijaOD;
    }

    public void setRezervacijaOD(Date rezervacijaOD) {
        this.rezervacijaOD = rezervacijaOD;
    }

    public Date getRezervacijaDO() {
        return rezervacijaDO;
    }

    public void setRezervacijaDO(Date rezervacijaDO) {
        this.rezervacijaDO = rezervacijaDO;
    }

    public boolean getDostupnost() {
        return dostupnost;
    }

    public void setDostupnost(boolean dostupnost) {
        this.dostupnost = dostupnost;
    }

    
    @Override
    public String toString() {
        return "Pretraga/Rezervacija: " + lokacija + " (" + rezervacijaOD + " - " + rezervacijaDO + ")";
    }
}