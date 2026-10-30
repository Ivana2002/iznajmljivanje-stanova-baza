package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import konekcija.Konekcija;
import model.RecenzijeStanovaZaIznajmljivanje;

public class RecenzijeStanovaZaIznajmljivanjeDAO {

    private Connection konekcija;

    
    public RecenzijeStanovaZaIznajmljivanjeDAO() {
        try {
            konekcija = Konekcija.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Dodavanje nove recenzije
    public void createRecenzija(RecenzijeStanovaZaIznajmljivanje recenzija) throws SQLException {
        String query = "INSERT INTO RECENZIJE_STANOVA_ZA_IZNAJMLJIVANJE (ocjena, brojStana, komentar, datumRecenzije, klijent, STAN_idStan) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setDouble(1, recenzija.getOcjena());
            stmt.setInt(2, recenzija.getBrojStana());
            stmt.setString(3, recenzija.getKomentar());
            stmt.setDate(4, recenzija.getDatumRecenzije());
            stmt.setString(5, recenzija.getKlijent());
            stmt.setInt(6, recenzija.getIdStan());
            stmt.executeUpdate();
        }
    }

    // Dohvatanje recenzije prema ID-u
    public RecenzijeStanovaZaIznajmljivanje getRecenzijaById(int id) throws SQLException {
        String query = "SELECT * FROM RECENZIJE_STANOVA_ZA_IZNAJMLJIVANJE WHERE idRECENZIJE_STANOVA_ZA_IZNAJMLJIVANJE = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new RecenzijeStanovaZaIznajmljivanje(
                    rs.getInt("idRECENZIJE_STANOVA_ZA_IZNAJMLJIVANJE"),
                    rs.getDouble("ocjena"),
                    rs.getInt("brojStana"),
                    rs.getString("komentar"),
                    rs.getDate("datumRecenzije"),
                    rs.getString("klijent"),
                    rs.getInt("STAN_idStan")
                );
            }
        }
        return null;
    }

    // Dohvatanje svih recenzija
    public List<RecenzijeStanovaZaIznajmljivanje> getAllRecenzije() throws SQLException {
        List<RecenzijeStanovaZaIznajmljivanje> recenzije = new ArrayList<>();
        String query = "SELECT * FROM RECENZIJE_STANOVA_ZA_IZNAJMLJIVANJE";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                recenzije.add(new RecenzijeStanovaZaIznajmljivanje(
                    rs.getInt("idRECENZIJE_STANOVA_ZA_IZNAJMLJIVANJE"),
                    rs.getDouble("ocjena"),
                    rs.getInt("brojStana"),
                    rs.getString("komentar"),
                    rs.getDate("datumRecenzije"),
                    rs.getString("klijent"),
                    rs.getInt("STAN_idStan")
                ));
            }
        }
        return recenzije;
    }

    // Dohvatanje recenzija prema ID-u stana
    public List<RecenzijeStanovaZaIznajmljivanje> getRecenzijeByStanId(int stanId) throws SQLException {
        List<RecenzijeStanovaZaIznajmljivanje> recenzije = new ArrayList<>();
        String query = "SELECT * FROM RECENZIJE_STANOVA_ZA_IZNAJMLJIVANJE WHERE STAN_idStan = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, stanId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                recenzije.add(new RecenzijeStanovaZaIznajmljivanje(
                    rs.getInt("idRECENZIJE_STANOVA_ZA_IZNAJMLJIVANJE"),
                    rs.getDouble("ocjena"),
                    rs.getInt("brojStana"),
                    rs.getString("komentar"),
                    rs.getDate("datumRecenzije"),
                    rs.getString("klijent"),
                    rs.getInt("STAN_idStan")
                ));
            }
        }
        return recenzije;
    }

    // Brisanje recenzije prema ID-u
    public void deleteRecenzija(int id) throws SQLException {
        String query = "DELETE FROM RECENZIJE_STANOVA_ZA_IZNAJMLJIVANJE WHERE idRECENZIJE_STANOVA_ZA_IZNAJMLJIVANJE = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
