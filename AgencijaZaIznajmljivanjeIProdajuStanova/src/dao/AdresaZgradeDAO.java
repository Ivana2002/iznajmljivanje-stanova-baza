package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import konekcija.Konekcija;
import model.AdresaZgrade;

public class AdresaZgradeDAO {

    private Connection konekcija;

    
    public AdresaZgradeDAO() {
        try {
            konekcija = Konekcija.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Dodavanje nove adrese zgrade
    public void createAdresaZgrade(AdresaZgrade adresa) throws SQLException {
        String query = "INSERT INTO ADRESA_ZGRADE (Ulica, Broj, GRAD_idGrad) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = konekcija.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, adresa.getUlica());
            stmt.setString(2, adresa.getBroj());
            stmt.setInt(3, adresa.getGradId());
            stmt.executeUpdate();

            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                adresa.setIdAdresa(rs.getInt(1)); 
            }
        }
    }

    // Dohvatanje adrese prema ID-u
    public AdresaZgrade getAdresaZgradeById(int id) throws SQLException {
        String query = "SELECT * FROM ADRESA_ZGRADE WHERE idAdresa = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new AdresaZgrade(
                    rs.getInt("idAdresa"),
                    rs.getString("Ulica"),
                    rs.getString("Broj"),
                    rs.getInt("GRAD_idGrad")
                );
            }
        }
        return null;
    }

    // Dohvatanje svih adresa zgrada
    public List<AdresaZgrade> getAllAdrese() throws SQLException {
        List<AdresaZgrade> adrese = new ArrayList<>();
        String query = "SELECT * FROM ADRESA_ZGRADE";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                adrese.add(new AdresaZgrade(
                    rs.getInt("idAdresa"),
                    rs.getString("Ulica"),
                    rs.getString("Broj"),
                    rs.getInt("GRAD_idGrad")
                ));
            }
        }
        return adrese;
    }

    // Ažuriranje podataka o adresi zgrade
    public void updateAdresaZgrade(AdresaZgrade adresa) throws SQLException {
        String query = "UPDATE ADRESA_ZGRADE SET Ulica = ?, Broj = ?, GRAD_idGrad = ? WHERE idAdresa = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setString(1, adresa.getUlica());
            stmt.setString(2, adresa.getBroj());
            stmt.setInt(3, adresa.getGradId());
            stmt.setInt(4, adresa.getIdAdresa());
            stmt.executeUpdate();
        }
    }

    // Brisanje adrese zgrade prema ID-u
    public void deleteAdresaZgrade(int id) throws SQLException {
       
        String checkQuery = "SELECT COUNT(*) FROM ZGRADA WHERE ADRESA_ZGRADE_idAdresa = ?";
        try (PreparedStatement checkStmt = konekcija.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new SQLException("Ne može se obrisati adresa jer postoje zgrade koje je koriste.");
            }
        }

        // Brisanje adrese
        String query = "DELETE FROM ADRESA_ZGRADE WHERE idAdresa = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}