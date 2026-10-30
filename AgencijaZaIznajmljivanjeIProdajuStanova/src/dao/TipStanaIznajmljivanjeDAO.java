package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import konekcija.Konekcija;
import model.TipStanaIznajmljivanje;

public class TipStanaIznajmljivanjeDAO {

    private Connection konekcija;

    
    public TipStanaIznajmljivanjeDAO() {
        try {
            konekcija = Konekcija.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Dodavanje novog tipa stana
    public void createTipStanaIznajmljivanje(TipStanaIznajmljivanje tipStana) throws SQLException {
        String query = "INSERT INTO TIP_STANA_IZNAJMLJIVANJE (Opis) VALUES (?)";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setString(1, tipStana.getOpis());
            stmt.executeUpdate();
        }
    }

    // Dohvatanje tipa stana prema ID-u
    public TipStanaIznajmljivanje getTipStanaIznajmljivanjeById(int id) throws SQLException {
        String query = "SELECT * FROM TIP_STANA_IZNAJMLJIVANJE WHERE idTip_Stana = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TipStanaIznajmljivanje(
                    rs.getInt("idTip_Stana"),
                    rs.getString("Opis")
                );
            }
        }
        return null;
    }

    // Dohvatanje svih tipova stana
    public List<TipStanaIznajmljivanje> getAllTipoviStana() throws SQLException {
        List<TipStanaIznajmljivanje> tipoviStana = new ArrayList<>();
        String query = "SELECT * FROM TIP_STANA_IZNAJMLJIVANJE";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tipoviStana.add(new TipStanaIznajmljivanje(
                    rs.getInt("idTip_Stana"),
                    rs.getString("Opis")
                ));
            }
        }
        return tipoviStana;
    }

    // Ažuriranje tipa stana
    public void updateTipStanaIznajmljivanje(TipStanaIznajmljivanje tipStana) throws SQLException {
        String query = "UPDATE TIP_STANA_IZNAJMLJIVANJE SET Opis = ? WHERE idTip_Stana = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setString(1, tipStana.getOpis());
            stmt.setInt(2, tipStana.getIdTipStana());
            stmt.executeUpdate();
        }
    }

    // Brisanje tipa stana prema ID-u
    public void deleteTipStanaIznajmljivanje(int id) throws SQLException {
        String query = "DELETE FROM TIP_STANA_IZNAJMLJIVANJE WHERE idTip_Stana = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
