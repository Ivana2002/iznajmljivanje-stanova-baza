package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import konekcija.Konekcija;
import model.StatusZakupaIProdaje;

public class StatusZakupaIProdajeDAO {

    private Connection konekcija;

    
    public StatusZakupaIProdajeDAO() {
        try {
            konekcija = Konekcija.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Dodavanje novog statusa
    public void createStatus(StatusZakupaIProdaje status) throws SQLException {
        String query = "INSERT INTO STATUS_ZAKUPA_I_PRODAJE (Opis) VALUES (?)";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setString(1, status.getOpis());
            stmt.executeUpdate();
        }
    }

    // Dohvatanje statusa prema ID-u
    public StatusZakupaIProdaje getStatusById(int id) throws SQLException {
        String query = "SELECT * FROM STATUS_ZAKUPA_I_PRODAJE WHERE idStatus_Zakupa = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new StatusZakupaIProdaje(
                    rs.getInt("idStatus_Zakupa"),
                    rs.getString("Opis")
                );
            }
        }
        return null;
    }

    // Dohvatanje svih statusa
    public List<StatusZakupaIProdaje> getAllStatusi() throws SQLException {
        List<StatusZakupaIProdaje> statusi = new ArrayList<>();
        String query = "SELECT * FROM STATUS_ZAKUPA_I_PRODAJE";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                statusi.add(new StatusZakupaIProdaje(
                    rs.getInt("idStatus_Zakupa"),
                    rs.getString("Opis")
                ));
            }
        }
        return statusi;
    }
}
