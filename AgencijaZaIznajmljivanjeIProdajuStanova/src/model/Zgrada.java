package model;

public class Zgrada {
    private int idZgrade;
    private int brojSpratova;
    private int godinaIzgradnje;
    private boolean imaLift;
    private int adresaId;
    private String zgradaInfo; // Dodajemo polje za prikaz informacija o zgradi

    // Konstruktor
    public Zgrada(int idZgrade, int brojSpratova, int godinaIzgradnje, boolean imaLift, int adresaId) {
        this.idZgrade = idZgrade;
        this.brojSpratova = brojSpratova;
        this.godinaIzgradnje = godinaIzgradnje;
        this.imaLift = imaLift;
        this.adresaId = adresaId;
    }

    // Getteri i setteri
    public int getIdZgrade() {
        return idZgrade;
    }

    public void setIdZgrade(int idZgrade) {
        this.idZgrade = idZgrade;
    }

    public int getBrojSpratova() {
        return brojSpratova;
    }

    public void setBrojSpratova(int brojSpratova) {
        this.brojSpratova = brojSpratova;
    }

    public int getGodinaIzgradnje() {
        return godinaIzgradnje;
    }

    public void setGodinaIzgradnje(int godinaIzgradnje) {
        this.godinaIzgradnje = godinaIzgradnje;
    }

    public boolean isImaLift() {
        return imaLift;
    }

    public void setImaLift(boolean imaLift) {
        this.imaLift = imaLift;
    }

    public int getAdresaId() {
        return adresaId;
    }

    public void setAdresaId(int adresaId) {
        this.adresaId = adresaId;
    }

    public void setZgradaInfo(String zgradaInfo) {
        this.zgradaInfo = zgradaInfo;
    }

    @Override
    public String toString() {
        return zgradaInfo != null ? zgradaInfo : "Zgrada " + idZgrade;
    }
}