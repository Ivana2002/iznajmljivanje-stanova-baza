package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import konekcija.Konekcija;
import model.TipStanaProdaja;

public class TipStanaProdajaDAO {

    private Connection konekcija;

    
    public TipStanaProdajaDAO() {
        try {
            konekcija = Konekcija.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Dodavanje novog tipa stana za prodaju
    public void createTipStanaProdaja(TipStanaProdaja tipStana) throws SQLException {
        String query = "INSERT INTO TIP_STANA_PRODAJA (brojSoba, Kvadratura, Balkon, STAN_idStan) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, tipStana.getBrojSoba());
            stmt.setInt(2, tipStana.getKvadratura());
            stmt.setString(3, tipStana.getBalkon());
            stmt.setInt(4, tipStana.getStanIdStan());
            stmt.executeUpdate();
        }
    }

    // Dohvatanje tipa stana za prodaju prema ID-u
    public TipStanaProdaja getTipStanaProdajaById(int id) throws SQLException {
        String query = "SELECT * FROM TIP_STANA_PRODAJA WHERE idTipStana = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TipStanaProdaja(
                    rs.getInt("idTipStana"),
                    rs.getInt("brojSoba"),
                    rs.getInt("Kvadratura"),
                    rs.getString("Balkon"),
                    rs.getInt("STAN_idStan")
                );
            }
        }
        return null;
    }

    // Dohvatanje svih tipova stanova za prodaju
    public List<TipStanaProdaja> getAllTipoviStana() throws SQLException {
        List<TipStanaProdaja> tipoviStana = new ArrayList<>();
        String query = "SELECT * FROM TIP_STANA_PRODAJA";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tipoviStana.add(new TipStanaProdaja(
                    rs.getInt("idTipStana"),
                    rs.getInt("brojSoba"),
                    rs.getInt("Kvadratura"),
                    rs.getString("Balkon"),
                    rs.getInt("STAN_idStan")
                ));
            }
        }
        return tipoviStana;
    }
}
