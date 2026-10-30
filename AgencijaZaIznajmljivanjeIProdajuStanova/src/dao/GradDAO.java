package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import konekcija.Konekcija;
import model.Grad;

public class GradDAO {

    private Connection konekcija;

    
    public GradDAO() {
        try {
            konekcija = Konekcija.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Dodavanje novog grada
    public void createGrad(Grad grad) throws SQLException {
        String query = "INSERT INTO GRAD (Naziv, DRZAVA_idDrzava) VALUES (?, ?)";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setString(1, grad.getNaziv());
            stmt.setInt(2, grad.getDrzavaId());
            stmt.executeUpdate();
        }
    }

    // Dohvatanje grada prema ID-u
    public Grad getGradById(int id) throws SQLException {
        String query = "SELECT * FROM GRAD WHERE idGrad = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Grad(
                    rs.getInt("idGrad"),
                    rs.getString("Naziv"),
                    rs.getInt("DRZAVA_idDrzava")
                );
            }
        }
        return null;
    }

    // Dohvatanje svih gradova
    public List<Grad> getAllGradovi() throws SQLException {
        List<Grad> gradovi = new ArrayList<>();
        String query = "SELECT * FROM GRAD";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                gradovi.add(new Grad(
                    rs.getInt("idGrad"),
                    rs.getString("Naziv"),
                    rs.getInt("DRZAVA_idDrzava")
                ));
            }
        }
        return gradovi;
    }

    // Ažuriranje podataka o gradu
    public void updateGrad(Grad grad) throws SQLException {
        String query = "UPDATE GRAD SET Naziv = ?, DRZAVA_idDrzava = ? WHERE idGrad = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setString(1, grad.getNaziv());
            stmt.setInt(2, grad.getDrzavaId());
            stmt.setInt(3, grad.getIdGrad());
            stmt.executeUpdate();
        }
    }

    // Brisanje grada prema ID-u
    public void deleteGrad(int id) throws SQLException {
        String query = "DELETE FROM GRAD WHERE idGrad = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
