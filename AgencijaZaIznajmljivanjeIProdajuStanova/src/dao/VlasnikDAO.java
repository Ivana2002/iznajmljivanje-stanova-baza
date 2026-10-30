package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import konekcija.Konekcija;
import model.Vlasnik;

public class VlasnikDAO {
    
    private Connection konekcija;
    
    public VlasnikDAO() {
        try {
            konekcija = Konekcija.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Metoda za pronalaženje vlasnika po ID-u
    public Vlasnik getVlasnikById(int idVlasnik) throws SQLException {
        String querry = "SELECT * FROM VLASNIK WHERE idVlasnik = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(querry)) {
            stmt.setInt(1, idVlasnik);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Vlasnik(
                        rs.getInt("idVlasnik"),
                        rs.getString("Ime"),
                        rs.getString("Prezime"),
                        rs.getString("Email"),
                        rs.getString("Telefon")
                );
            }
        }
        return null;
    }
    
    // Metoda za dobavljanje svih vlasnika
    public List<Vlasnik> getAll() throws SQLException {
        List<Vlasnik> vlasnici = new ArrayList<>();
        String querry = "SELECT * FROM VLASNIK";
        try (PreparedStatement stmt = konekcija.prepareStatement(querry)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                vlasnici.add(new Vlasnik(
                        rs.getInt("idVlasnik"),
                        rs.getString("Ime"),
                        rs.getString("Prezime"),
                        rs.getString("Email"),
                        rs.getString("Telefon")
                ));
            }
        }
        return vlasnici;
    }
    
    // Metoda za dodavanje novog vlasnika
    public void addVlasnik(Vlasnik vlasnik) throws SQLException {
        String querry = "INSERT INTO VLASNIK (Ime, Prezime, Email, Telefon) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = konekcija.prepareStatement(querry)) {
            stmt.setString(1, vlasnik.getIme());
            stmt.setString(2, vlasnik.getPrezime());
            stmt.setString(3, vlasnik.getEmail());
            stmt.setString(4, vlasnik.getTelefon());
            stmt.executeUpdate();
        }
    }
    
    // Metoda za ažuriranje podataka o vlasniku
    public void updateVlasnik(Vlasnik vlasnik) throws SQLException {
        String querry = "UPDATE VLASNIK SET Ime=?, Prezime=?, Email=?, Telefon=? WHERE idVlasnik=?";
        try (PreparedStatement stmt = konekcija.prepareStatement(querry)) {
            stmt.setString(1, vlasnik.getIme());
            stmt.setString(2, vlasnik.getPrezime());
            stmt.setString(3, vlasnik.getEmail());
            stmt.setString(4, vlasnik.getTelefon());
            stmt.setInt(5, vlasnik.getIdVlasnik());
            stmt.executeUpdate();
        }
    }
    
    // Metoda za brisanje vlasnika prema ID-u
    public void deleteVlasnik(int idVlasnik) throws SQLException {
        String querry = "DELETE FROM VLASNIK WHERE idVlasnik = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(querry)) {
            stmt.setInt(1, idVlasnik);
            stmt.executeUpdate();
        }
    }
}
