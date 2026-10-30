package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import konekcija.Konekcija;
import model.Zakupac;

public class ZakupacDAO {

    private Connection konekcija;

    
    public ZakupacDAO() {
        try {
            konekcija = Konekcija.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Dodavanje novog zakupca
    public void createZakupac(Zakupac zakupac) throws SQLException {
        String query = "INSERT INTO ZAKUPAC (Ime, Prezime, Telefon, Email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = konekcija.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, zakupac.getIme());
            stmt.setString(2, zakupac.getPrezime());
            stmt.setString(3, zakupac.getTelefon());
            stmt.setString(4, zakupac.getEmail());
            stmt.executeUpdate();

           
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                zakupac.setIdZakupac(rs.getInt(1)); 
            }
        }
    }

    // Dohvatanje zakupca prema ID-u
    public Zakupac getZakupacById(int id) throws SQLException {
        String query = "SELECT * FROM ZAKUPAC WHERE idZakupac = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Zakupac(
                    rs.getInt("idZakupac"),
                    rs.getString("Ime"),
                    rs.getString("Prezime"),
                    rs.getString("Telefon"),
                    rs.getString("Email")
                );
            }
        }
        return null;
    }

    // Dohvatanje svih zakupaca
    public List<Zakupac> getAllZakupci() throws SQLException {
        List<Zakupac> zakupci = new ArrayList<>();
        String query = "SELECT * FROM ZAKUPAC";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                zakupci.add(new Zakupac(
                    rs.getInt("idZakupac"),
                    rs.getString("Ime"),
                    rs.getString("Prezime"),
                    rs.getString("Telefon"),
                    rs.getString("Email")
                ));
            }
        }
        return zakupci;
    }

    // Ažuriranje podataka o zakupcu
    public void updateZakupac(Zakupac zakupac) throws SQLException {
        String query = "UPDATE ZAKUPAC SET Ime = ?, Prezime = ?, Telefon = ?, Email = ? WHERE idZakupac = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setString(1, zakupac.getIme());
            stmt.setString(2, zakupac.getPrezime());
            stmt.setString(3, zakupac.getTelefon());
            stmt.setString(4, zakupac.getEmail());
            stmt.setInt(5, zakupac.getIdZakupac());
            stmt.executeUpdate();
        }
    }

    // Brisanje zakupca prema ID-u
    public void deleteZakupac(int id) throws SQLException {
        // Provera da li postoje pretrage/rezervacije koje referenciraju ovog zakupca
        String checkQuery = "SELECT COUNT(*) FROM PRETRAGA_I_REZERVACIJA WHERE ZAKUPAC_idZakupac = ?";
        try (PreparedStatement checkStmt = konekcija.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new SQLException("Ne može se obrisati zakupac jer postoje pretrage/rezervacije koje ga koriste.");
            }
        }

        // Brisanje zakupca
        String query = "DELETE FROM ZAKUPAC WHERE idZakupac = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}