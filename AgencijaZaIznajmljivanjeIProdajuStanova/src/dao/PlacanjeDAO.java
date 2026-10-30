package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import konekcija.Konekcija;
import model.Placanje;

public class PlacanjeDAO {

    private Connection konekcija;

    
    public PlacanjeDAO() {
        try {
            konekcija = Konekcija.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Dodavanje novog plaćanja
    public void createPlacanje(Placanje placanje) throws SQLException {
        String query = "INSERT INTO PLACANJE (datumPlacanja, Iznos, UGOVOR_ZA_IZNAJMLJIVANJE_idUgovor) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setDate(1, placanje.getDatumPlacanja());
            stmt.setDouble(2, placanje.getIznos());
            stmt.setInt(3, placanje.getIdUgovor());
            stmt.executeUpdate();
        }
    }

    // Dohvatanje plaćanja prema ID-u
    public Placanje getPlacanjeById(int id) throws SQLException {
        String query = "SELECT * FROM PLACANJE WHERE idPlacanje = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Placanje(
                    rs.getInt("idPlacanje"),
                    rs.getDate("datumPlacanja"),
                    rs.getDouble("Iznos"),
                    rs.getInt("UGOVOR_ZA_IZNAJMLJIVANJE_idUgovor")
                );
            }
        }
        return null;
    }

    // Dohvatanje svih plaćanja
    public List<Placanje> getAllPlacanja() throws SQLException {
        List<Placanje> placanja = new ArrayList<>();
        String query = "SELECT * FROM PLACANJE";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                placanja.add(new Placanje(
                    rs.getInt("idPlacanje"),
                    rs.getDate("datumPlacanja"),
                    rs.getDouble("Iznos"),
                    rs.getInt("UGOVOR_ZA_IZNAJMLJIVANJE_idUgovor")
                ));
            }
        }
        return placanja;
    }

    // Brisanje plaćanja prema ID-u
    public void deletePlacanje(int id) throws SQLException {
        String query = "DELETE FROM PLACANJE WHERE idPlacanje = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
