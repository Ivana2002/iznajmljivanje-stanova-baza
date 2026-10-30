package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import konekcija.Konekcija;
import model.Drzava;

public class DrzavaDAO {

    private Connection konekcija;

    
    public DrzavaDAO() {
        try {
            konekcija = Konekcija.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Dodavanje nove države
    public void createDrzava(Drzava drzava) throws SQLException {
        String query = "INSERT INTO DRZAVA (Naziv) VALUES (?)";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setString(1, drzava.getNaziv());
            stmt.executeUpdate();
        }
    }

    // Dohvatanje države prema ID-u
    public Drzava getDrzavaById(int id) throws SQLException {
        String query = "SELECT * FROM DRZAVA WHERE idDrzava = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Drzava(
                    rs.getInt("idDrzava"),
                    rs.getString("Naziv")
                );
            }
        }
        return null;
    }

    // Dohvatanje svih država
    public List<Drzava> getAllDrzave() throws SQLException {
        List<Drzava> drzave = new ArrayList<>();
        String query = "SELECT * FROM DRZAVA";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                drzave.add(new Drzava(
                    rs.getInt("idDrzava"),
                    rs.getString("Naziv")
                ));
            }
        }
        return drzave;
    }

    // Ažuriranje podataka o državi
    public void updateDrzava(Drzava drzava) throws SQLException {
        String query = "UPDATE DRZAVA SET Naziv = ? WHERE idDrzava = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setString(1, drzava.getNaziv());
            stmt.setInt(2, drzava.getIdDrzava());
            stmt.executeUpdate();
        }
    }

    // Brisanje države prema ID-u
    public void deleteDrzava(int id) throws SQLException {
        String query = "DELETE FROM DRZAVA WHERE idDrzava = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
