package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import konekcija.Konekcija;
import model.PretragaIRezervacija;

public class PretragaIRezervacijaDAO {
    
    private Connection konekcija;

    
    public PretragaIRezervacijaDAO() {
        try {
            konekcija = Konekcija.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Dodavanje nove pretrage i rezervacije
    public void createPretragaIRezervacija(PretragaIRezervacija pretraga) throws SQLException {
        String query = "INSERT INTO PRETRAGA_I_REZERVACIJA (ZAKUPAC_idZakupac, lokacija, tipZaIznajmljivanje, rezervacijaOD, rezervacijaDO, dostupnost) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = konekcija.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, pretraga.getZakupacId());
            stmt.setString(2, pretraga.getLokacija());
            stmt.setString(3, pretraga.getTipZaIznajmljivanje());
            stmt.setDate(4, pretraga.getRezervacijaOD());
            stmt.setDate(5, pretraga.getRezervacijaDO());
            stmt.setBoolean(6, pretraga.getDostupnost());
            stmt.executeUpdate();

            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                pretraga.setIdPretragaIRezervacija(rs.getInt(1));
            }
        }
    }

    // Dohvatanje pretrage i rezervacije prema ID-u
    public PretragaIRezervacija getPretragaIRezervacijaById(int id) throws SQLException {
        String query = "SELECT * FROM PRETRAGA_I_REZERVACIJA WHERE idPRETRAGA_I_REZERVACIJA = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PretragaIRezervacija(
                    rs.getInt("idPRETRAGA_I_REZERVACIJA"),
                    rs.getInt("ZAKUPAC_idZakupac"),
                    rs.getString("lokacija"),
                    rs.getString("tipZaIznajmljivanje"),
                    rs.getDate("rezervacijaOD"),
                    rs.getDate("rezervacijaDO"),
                    rs.getBoolean("dostupnost")
                );
            }
        }
        return null;
    }

    // Dohvatanje svih pretraga i rezervacija
    public List<PretragaIRezervacija> getAllPretrage() throws SQLException {
        List<PretragaIRezervacija> pretrage = new ArrayList<>();
        String query = "SELECT * FROM PRETRAGA_I_REZERVACIJA";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pretrage.add(new PretragaIRezervacija(
                    rs.getInt("idPRETRAGA_I_REZERVACIJA"),
                    rs.getInt("ZAKUPAC_idZakupac"),
                    rs.getString("lokacija"),
                    rs.getString("tipZaIznajmljivanje"),
                    rs.getDate("rezervacijaOD"),
                    rs.getDate("rezervacijaDO"),
                    rs.getBoolean("dostupnost")
                ));
            }
        }
        return pretrage;
    }

    // Ažuriranje podataka o pretrazi i rezervaciji
    public void updatePretragaIRezervacija(PretragaIRezervacija pretraga) throws SQLException {
        String query = "UPDATE PRETRAGA_I_REZERVACIJA SET ZAKUPAC_idZakupac = ?, lokacija = ?, tipZaIznajmljivanje = ?, rezervacijaOD = ?, rezervacijaDO = ?, dostupnost = ? WHERE idPRETRAGA_I_REZERVACIJA = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, pretraga.getZakupacId());
            stmt.setString(2, pretraga.getLokacija());
            stmt.setString(3, pretraga.getTipZaIznajmljivanje());
            stmt.setDate(4, pretraga.getRezervacijaOD());
            stmt.setDate(5, pretraga.getRezervacijaDO());
            stmt.setBoolean(6, pretraga.getDostupnost());
            stmt.setInt(7, pretraga.getIdPretragaIRezervacija());
            stmt.executeUpdate();
        }
    }

    // Brisanje pretrage i rezervacije prema ID-u

    public void deletePretragaIRezervacija(int id) throws SQLException {
        // Provera da li postoje stanovi koji referenciraju ovu pretragu/rezervaciju
        String checkQuery = "SELECT COUNT(*) FROM STAN WHERE pretragaRezervacijaId = ?";
        try (PreparedStatement checkStmt = konekcija.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                
                String updateStanQuery = "UPDATE STAN SET pretragaRezervacijaId = NULL WHERE pretragaRezervacijaId = ?";
                try (PreparedStatement updateStmt = konekcija.prepareStatement(updateStanQuery)) {
                    updateStmt.setInt(1, id);
                    updateStmt.executeUpdate();
                }
            }
        }

        // Brisanje pretrage/rezervacije
        String query = "DELETE FROM PRETRAGA_I_REZERVACIJA WHERE idPRETRAGA_I_REZERVACIJA = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}