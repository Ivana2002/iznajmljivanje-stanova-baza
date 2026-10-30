package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import konekcija.Konekcija;
import model.ZaposleniUAgenciji;

public class ZaposleniUAgencijiDAO {

    Connection konekcija;

    
    public ZaposleniUAgencijiDAO() {
        try {
            konekcija = Konekcija.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Dohvatanje zaposlenog po ID-u
    public ZaposleniUAgenciji getZaposleniById(int id) throws SQLException {
        String query = "SELECT * FROM ZAPOSLENI_U_AGENCIJI WHERE idZaposleni = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ZaposleniUAgenciji(
                    rs.getInt("idZaposleni"),
                    rs.getString("ime"),
                    rs.getString("prezime"),
                    rs.getString("telefon"),
                    rs.getString("email")
                );
            }
        }
        return null;
    }

    // Dohvatanje svih zaposlenih
    public List<ZaposleniUAgenciji> getAll() throws SQLException {
        List<ZaposleniUAgenciji> zaposleni = new ArrayList<>();
        String query = "SELECT * FROM ZAPOSLENI_U_AGENCIJI";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                zaposleni.add(new ZaposleniUAgenciji(
                    rs.getInt("idZaposleni"),
                    rs.getString("ime"),
                    rs.getString("prezime"),
                    rs.getString("telefon"),
                    rs.getString("email")
                ));
            }
        }
        return zaposleni;
    }

    // Dodavanje novog zaposlenog
    public void addZaposleni(ZaposleniUAgenciji zaposleni) throws SQLException {
        String query = "INSERT INTO ZAPOSLENI_U_AGENCIJI (ime, prezime, telefon, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setString(1, zaposleni.getIme());
            stmt.setString(2, zaposleni.getPrezime());
            stmt.setString(3, zaposleni.getTelefon());
            stmt.setString(4, zaposleni.getEmail());
            stmt.executeUpdate();
        }
    }

    // Ažuriranje podataka o zaposlenom
    public void updateZaposleni(ZaposleniUAgenciji zaposleni) throws SQLException {
        String query = "UPDATE ZAPOSLENI_U_AGENCIJI SET ime=?, prezime=?, telefon=?, email=? WHERE idZaposleni=?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setString(1, zaposleni.getIme());
            stmt.setString(2, zaposleni.getPrezime());
            stmt.setString(3, zaposleni.getTelefon());
            stmt.setString(4, zaposleni.getEmail());
            stmt.setInt(5, zaposleni.getIdZaposleni());
            stmt.executeUpdate();
        }
    }

    // Brisanje zaposlenog prema ID-u
    public void deleteZaposleni(int idZaposleni) throws SQLException {
        String query = "DELETE FROM ZAPOSLENI_U_AGENCIJI WHERE idZaposleni = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, idZaposleni);
            stmt.executeUpdate();
        }
    }
}
