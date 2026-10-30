package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import konekcija.Konekcija;
import model.Zgrada;

public class ZgradaDAO {
    
    private Connection konekcija;

    
    public ZgradaDAO() {
        try {
            konekcija = Konekcija.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Dodavanje nove zgrade
    public void createZgrada(Zgrada zgrada) throws SQLException {
        String query = "INSERT INTO ZGRADA (brojSpratova, godinaIzgradnje, imaLift, ADRESA_ZGRADE_idAdresa) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = konekcija.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, zgrada.getBrojSpratova());
            stmt.setInt(2, zgrada.getGodinaIzgradnje());
            stmt.setBoolean(3, zgrada.isImaLift());
            stmt.setInt(4, zgrada.getAdresaId());
            stmt.executeUpdate();

           
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                zgrada.setIdZgrade(rs.getInt(1)); 
            }
        }
    }

    // Dohvatanje zgrade prema ID-u
    public Zgrada getZgradaById(int id) throws SQLException {
        String query = "SELECT * FROM ZGRADA WHERE idZgrade = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Zgrada(
                    rs.getInt("idZgrade"),
                    rs.getInt("brojSpratova"),
                    rs.getInt("godinaIzgradnje"),
                    rs.getBoolean("imaLift"),
                    rs.getInt("ADRESA_ZGRADE_idAdresa")
                );
            }
        }
        return null;
    }

    // Dohvatanje svih zgrada
    public List<Zgrada> getAllZgrade() throws SQLException {
        List<Zgrada> zgrade = new ArrayList<>();
        String query = "SELECT * FROM ZGRADA";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                zgrade.add(new Zgrada(
                    rs.getInt("idZgrade"),
                    rs.getInt("brojSpratova"),
                    rs.getInt("godinaIzgradnje"),
                    rs.getBoolean("imaLift"),
                    rs.getInt("ADRESA_ZGRADE_idAdresa")
                ));
            }
        }
        return zgrade;
    }

    // Ažuriranje podataka o zgradi
    public void updateZgrada(Zgrada zgrada) throws SQLException {
        String query = "UPDATE ZGRADA SET brojSpratova = ?, godinaIzgradnje = ?, imaLift = ?, ADRESA_ZGRADE_idAdresa = ? WHERE idZgrade = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, zgrada.getBrojSpratova());
            stmt.setInt(2, zgrada.getGodinaIzgradnje());
            stmt.setBoolean(3, zgrada.isImaLift());
            stmt.setInt(4, zgrada.getAdresaId());
            stmt.setInt(5, zgrada.getIdZgrade());
            stmt.executeUpdate();
        }
    }

    // Brisanje zgrade prema ID-u
    public void deleteZgrada(int id) throws SQLException {
       
        String checkQuery = "SELECT COUNT(*) FROM STAN WHERE ZGRADA_idZgrade = ?";
        try (PreparedStatement checkStmt = konekcija.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new SQLException("Ne može se obrisati zgrada jer postoje stanovi koji je koriste.");
            }
        }

        // Brisanje zgrade
        String query = "DELETE FROM ZGRADA WHERE idZgrade = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}