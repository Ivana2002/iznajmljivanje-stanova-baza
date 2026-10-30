package model;

public class Stan {
    private int idStan;
    private int povrsina;
    private double cijena;
    private int vlasnikId;
    private int zgradaId;
    private int sprat;
    private int brojStana;
    private String vrstaUslugeProdaja;
    private String vrstaUslugeIznajmljivanje;
    private double cijenaProdaje;
    private Integer pretragaRezervacijaId; 
    private Integer tipStanaProdajaId;    
    private Integer tipStanaIznajmljivanjeId; 

    // Konstruktor
    public Stan(int idStan, int povrsina, double cijena, int vlasnikId, int zgradaId, int sprat, int brojStana,
                String vrstaUslugeProdaja, String vrstaUslugeIznajmljivanje, double cijenaProdaje,
                Integer pretragaRezervacijaId, Integer tipStanaProdajaId, Integer tipStanaIznajmljivanjeId) {
        this.idStan = idStan;
        this.povrsina = povrsina;
        this.cijena = cijena;
        this.vlasnikId = vlasnikId;
        this.zgradaId = zgradaId;
        this.sprat = sprat;
        this.brojStana = brojStana;
        this.vrstaUslugeProdaja = vrstaUslugeProdaja;
        this.vrstaUslugeIznajmljivanje = vrstaUslugeIznajmljivanje;
        this.cijenaProdaje = cijenaProdaje;
        this.pretragaRezervacijaId = pretragaRezervacijaId;
        this.tipStanaProdajaId = tipStanaProdajaId;
        this.tipStanaIznajmljivanjeId = tipStanaIznajmljivanjeId;
    }

    // Prazan konstruktor
    public Stan() {}

    // Getteri i setteri
    public int getIdStan() { return idStan; }
    public void setIdStan(int idStan) { this.idStan = idStan; }
    public int getPovrsina() { return povrsina; }
    public void setPovrsina(int povrsina) { this.povrsina = povrsina; }
    public double getCijena() { return cijena; }
    public void setCijena(double cijena) { this.cijena = cijena; }
    public int getVlasnikId() { return vlasnikId; }
    public void setVlasnikId(int vlasnikId) { this.vlasnikId = vlasnikId; }
    public int getZgradaId() { return zgradaId; }
    public void setZgradaId(int zgradaId) { this.zgradaId = zgradaId; }
    public int getSprat() { return sprat; }
    public void setSprat(int sprat) { this.sprat = sprat; }
    public int getBrojStana() { return brojStana; }
    public void setBrojStana(int brojStana) { this.brojStana = brojStana; }
    public String getVrstaUslugeProdaja() { return vrstaUslugeProdaja; }
    public void setVrstaUslugeProdaja(String vrstaUslugeProdaja) { this.vrstaUslugeProdaja = vrstaUslugeProdaja; }
    public String getVrstaUslugeIznajmljivanje() { return vrstaUslugeIznajmljivanje; }
    public void setVrstaUslugeIznajmljivanje(String vrstaUslugeIznajmljivanje) { this.vrstaUslugeIznajmljivanje = vrstaUslugeIznajmljivanje; }
    public double getCijenaProdaje() { return cijenaProdaje; }
    public void setCijenaProdaje(double cijenaProdaje) { this.cijenaProdaje = cijenaProdaje; }
    public Integer getPretragaRezervacijaId() { return pretragaRezervacijaId; }
    public void setPretragaRezervacijaId(Integer pretragaRezervacijaId) { this.pretragaRezervacijaId = pretragaRezervacijaId; }
    public Integer getTipStanaProdajaId() { return tipStanaProdajaId; } // Promenjeno u Integer
    public void setTipStanaProdajaId(Integer tipStanaProdajaId) { this.tipStanaProdajaId = tipStanaProdajaId; } // Promenjeno u Integer
    public Integer getTipStanaIznajmljivanjeId() { return tipStanaIznajmljivanjeId; } // Promenjeno u Integer
    public void setTipStanaIznajmljivanjeId(Integer tipStanaIznajmljivanjeId) { this.tipStanaIznajmljivanjeId = tipStanaIznajmljivanjeId; } // Promenjeno u Integer

    // toString() metoda za debugging
    @Override
    public String toString() {
        return "Stan [idStan=" + idStan + ", povrsina=" + povrsina + ", cijena=" + cijena + ", vlasnikId=" + vlasnikId +
               ", zgradaId=" + zgradaId + ", sprat=" + sprat + ", brojStana=" + brojStana +
               ", vrstaUslugeProdaja=" + vrstaUslugeProdaja + ", vrstaUslugeIznajmljivanje=" + vrstaUslugeIznajmljivanje +
               ", cijenaProdaje=" + cijenaProdaje + ", pretragaRezervacijaId=" + pretragaRezervacijaId +
               ", tipStanaProdajaId=" + tipStanaProdajaId + ", tipStanaIznajmljivanjeId=" + tipStanaIznajmljivanjeId + "]";
    }
}