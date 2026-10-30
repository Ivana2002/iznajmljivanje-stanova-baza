package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import konekcija.Konekcija;
import model.UgovorZaProdaju;

public class UgovorZaProdajuDAO {

    private Connection konekcija;

    
    public UgovorZaProdajuDAO() {
        try {
            konekcija = Konekcija.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Dodavanje novog ugovora za prodaju
    public void createUgovorZaProdaju(UgovorZaProdaju ugovor) throws SQLException {
        String query = "INSERT INTO UGOVOR_ZA_PRODAJU (datumProdaje, imeKupca, brojStana, STAN_idStan, tipStana, Kvadratura,  Balkon, ImeProdavca, cijenaStana, ZAPOSLENI_U_AGENCIJI_idZaposelni, ZAKUPAC_idZakupac, VLASNIK_idVlasnik) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setDate(1, new java.sql.Date(ugovor.getDatumProdaje().getTime()));
            stmt.setString(2, ugovor.getImeKupca());
            stmt.setInt(3, ugovor.getBrojStana());
            stmt.setInt(4, ugovor.getStanIdStan());
            stmt.setString(5, ugovor.getTipStana());
            stmt.setInt(6, ugovor.getKvadratura());
           
            stmt.setString(7, ugovor.getBalkon());
            stmt.setString(8, ugovor.getImeProdavca());
            stmt.setDouble(9, ugovor.getCijenaStana());
            stmt.setInt(10, ugovor.getZaposleniIdZaposleni());
            stmt.setInt(11, ugovor.getZakupacIdZakupac());
            stmt.setInt(12, ugovor.getVlasnikIdVlasnik());
            stmt.executeUpdate();
        }
    }

    // Dohvatanje ugovora za prodaju prema ID-u
    public UgovorZaProdaju getUgovorZaProdajuById(int id) throws SQLException {
        String query = "SELECT * FROM UGOVOR_ZA_PRODAJU WHERE idUgovor = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UgovorZaProdaju(
                    rs.getInt("idUgovor"),
                    rs.getDate("datumProdaje"),
                    rs.getString("imeKupca"),
                    rs.getInt("brojStana"),
                    rs.getInt("STAN_idStan"),
                    rs.getString("tipStana"),
                    rs.getInt("Kvadratura"),
             
                    rs.getString("Balkon"),
                    rs.getString("ImeProdavca"),
                    rs.getDouble("cijenaStana"),
                    rs.getInt("ZAPOSLENI_U_AGENCIJI_idZaposelni"),
                    rs.getInt("ZAKUPAC_idZakupac"),
                    rs.getInt("VLASNIK_idVlasnik")
                );
            }
        }
        return null;
    }

    // Dohvatanje svih ugovora za prodaju
    public List<UgovorZaProdaju> getAllUgovoriZaProdaju() throws SQLException {
        List<UgovorZaProdaju> ugovori = new ArrayList<>();
        String query = "SELECT * FROM UGOVOR_ZA_PRODAJU";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ugovori.add(new UgovorZaProdaju(
                    rs.getInt("idUgovor"),
                    rs.getDate("datumProdaje"),
                    rs.getString("imeKupca"),
                    rs.getInt("brojStana"),
                    rs.getInt("STAN_idStan"),
                    rs.getString("tipStana"),
                    rs.getInt("Kvadratura"),
                   
                    rs.getString("Balkon"),
                    rs.getString("ImeProdavca"),
                    rs.getDouble("cijenaStana"),
                    rs.getInt("ZAPOSLENI_U_AGENCIJI_idZaposelni"),
                    rs.getInt("ZAKUPAC_idZakupac"),
                    rs.getInt("VLASNIK_idVlasnik")
                ));
            }
        }
        return ugovori;
    }
}
