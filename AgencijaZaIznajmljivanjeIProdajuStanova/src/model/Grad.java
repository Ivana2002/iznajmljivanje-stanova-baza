package model;

public class Grad {
    private int idGrad;
    private String naziv;
    private int drzavaId;

    
    public Grad(int idGrad, String naziv, int drzavaId) {
        this.idGrad = idGrad;
        this.naziv = naziv;
        this.drzavaId = drzavaId;
    }

    
    public int getIdGrad() {
        return idGrad;
    }

    public void setIdGrad(int idGrad) {
        this.idGrad = idGrad;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getDrzavaId() {
        return drzavaId;
    }

    public void setDrzavaId(int drzavaId) {
        this.drzavaId = drzavaId;
    }
    
    @Override
    public String toString() {
        return naziv;
    }
}
