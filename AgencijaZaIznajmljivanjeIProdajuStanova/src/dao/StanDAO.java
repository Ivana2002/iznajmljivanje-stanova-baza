package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import konekcija.Konekcija;
import model.Stan;

public class StanDAO {

    private Connection konekcija;

    public StanDAO() {
        try {
            konekcija = Konekcija.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Dodavanje novog stana
    public void createStan(Stan stan) throws SQLException {
        String query = "INSERT INTO stan (Povrsina, Cijena, VLASNIK_idVlasnik, ZGRADA_idZgrade, sprat, brojStana, " +
                       "vrstaUsluge_prodaja, vrstaUsluge_iznajmljivanje, cijenaProdaje, pretragaRezervacijaId, " +
                       "TIP_STANA_PRODAJA_idTipStana, TIP_STANA_IZNAJMLJIVANJE_idTip_Stana) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, stan.getPovrsina());
            stmt.setDouble(2, stan.getCijena());
            stmt.setInt(3, stan.getVlasnikId());
            stmt.setInt(4, stan.getZgradaId());
            stmt.setInt(5, stan.getSprat());
            stmt.setInt(6, stan.getBrojStana());
            stmt.setObject(7, null); // vrstaUsluge_prodaja - postavljamo na NULL jer radimo samo sa iznajmljivanjem
            stmt.setString(8, stan.getVrstaUslugeIznajmljivanje());
            stmt.setDouble(9, 0.0); // cijenaProdaje - postavljamo na 0 jer radimo samo sa iznajmljivanjem
            stmt.setObject(10, stan.getPretragaRezervacijaId());
            stmt.setObject(11, null); // TIP_STANA_PRODAJA_idTipStana - postavljamo na NULL jer radimo samo sa iznajmljivanjem
            stmt.setInt(12, stan.getTipStanaIznajmljivanjeId()); // TIP_STANA_IZNAJMLJIVANJE_idTip_Stana - ovo je obavezno za iznajmljivanje

            stmt.executeUpdate();
        }
    }

    // Pretraga stana prema ID-u
    public Stan getStanById(int id) throws SQLException {
        String query = "SELECT * FROM stan WHERE idStan = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapirajStan(rs);
            }
        }
        return null;
    }

    // Pretraga svih stanova za iznajmljivanje
    public List<Stan> getAllStani() throws SQLException {
        List<Stan> stani = new ArrayList<>();
        String query = "SELECT s.* FROM stan s " +
                      "WHERE s.TIP_STANA_IZNAJMLJIVANJE_idTip_Stana IS NOT NULL";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                stani.add(mapirajStan(rs));
            }
        }
        return stani;
    }

   
 // Ažuriranje podataka o stanu
    public void updateStan(Stan stan) throws SQLException {
        String query = "UPDATE stan SET Povrsina=?, Cijena=?, VLASNIK_idVlasnik=?, ZGRADA_idZgrade=?, sprat=?, brojStana=?, " +
                       "vrstaUsluge_iznajmljivanje=?, pretragaRezervacijaId=?, TIP_STANA_IZNAJMLJIVANJE_idTip_Stana=? " +
                       "WHERE idStan=?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, stan.getPovrsina());
            stmt.setDouble(2, stan.getCijena());
            stmt.setInt(3, stan.getVlasnikId());
            stmt.setInt(4, stan.getZgradaId());
            stmt.setInt(5, stan.getSprat());
            stmt.setInt(6, stan.getBrojStana());
            stmt.setString(7, stan.getVrstaUslugeIznajmljivanje());
            stmt.setObject(8, stan.getPretragaRezervacijaId());
            stmt.setObject(9, stan.getTipStanaIznajmljivanjeId());
            stmt.setInt(10, stan.getIdStan());

            stmt.executeUpdate();
        }
    }

    // Brisanje stana prema ID-u
    public void deleteStan(int id) throws SQLException {
        String query = "DELETE FROM stan WHERE idStan = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Pretraga stanova prema vlasniku
    public List<Stan> getStaniByVlasnik(int vlasnikId) throws SQLException {
        List<Stan> stani = new ArrayList<>();
        String query = "SELECT * FROM stan WHERE VLASNIK_idVlasnik = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, vlasnikId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                stani.add(mapirajStan(rs));
            }
        }
        return stani;
    }

    // Pretraga stanova prema tipu iznajmljivanja
    public List<Stan> getStaniByTipIznajmljivanje(String tipIznajmljivanja) throws SQLException {
        List<Stan> stani = new ArrayList<>();
        String query = "SELECT * FROM stan"; // Uklonili smo filter za tip iznajmljivanja
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                stani.add(mapirajStan(rs));
            }
        }
        return stani;
    }

    // Pomoćna metoda za mapiranje ResultSet-a u Stan objekat
    private Stan mapirajStan(ResultSet rs) throws SQLException {
        int idStan = rs.getInt("idStan");
        int povrsina = rs.getInt("Povrsina");
        double cijena = rs.getDouble("Cijena");
        int vlasnikId = rs.getInt("VLASNIK_idVlasnik");
        int zgradaId = rs.getInt("ZGRADA_idZgrade");
        int sprat = rs.getInt("sprat");
        int brojStana = rs.getInt("brojStana");
        String vrstaUslugeProdaja = rs.getString("vrstaUsluge_prodaja");
        String vrstaUslugeIznajmljivanje = rs.getString("vrstaUsluge_iznajmljivanje");
        double cijenaProdaje = rs.getDouble("cijenaProdaje");
        Integer pretragaRezervacijaId = rs.getObject("pretragaRezervacijaId", Integer.class); 
        int tipStanaProdajaId = rs.getInt("TIP_STANA_PRODAJA_idTipStana");
        int tipStanaIznajmljivanjeId = rs.getInt("TIP_STANA_IZNAJMLJIVANJE_idTip_Stana");

        return new Stan(idStan, povrsina, cijena, vlasnikId, zgradaId, sprat, brojStana,
                       vrstaUslugeProdaja, vrstaUslugeIznajmljivanje, cijenaProdaje,
                       pretragaRezervacijaId, tipStanaProdajaId, tipStanaIznajmljivanjeId);
    }
}