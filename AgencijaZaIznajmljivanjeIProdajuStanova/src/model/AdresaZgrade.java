package model;

public class AdresaZgrade {
    private int idAdresa;
    private String ulica;
    private String broj;
    private int gradId;

    
    public AdresaZgrade(int idAdresa, String ulica, String broj, int gradId) {
        this.idAdresa = idAdresa;
        this.ulica = ulica;
        this.broj = broj;
        this.gradId = gradId;
    }

    
    public int getIdAdresa() {
        return idAdresa;
    }

    public void setIdAdresa(int idAdresa) {
        this.idAdresa = idAdresa;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getBroj() {
        return broj;
    }

    public void setBroj(String broj) {
        this.broj = broj;
    }

    public int getGradId() {
        return gradId;
    }

    public void setGradId(int gradId) {
        this.gradId = gradId;
    }
    @Override
    public String toString() {
        return ulica;
    }
}
