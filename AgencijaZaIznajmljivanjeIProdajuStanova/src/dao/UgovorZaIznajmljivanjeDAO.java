package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import konekcija.Konekcija;
import model.UgovorZaIznajmljivanje;

public class UgovorZaIznajmljivanjeDAO {

    Connection konekcija;

    public UgovorZaIznajmljivanjeDAO() {
        try {
            konekcija = Konekcija.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Dohvatanje ugovora po ID-u
    public UgovorZaIznajmljivanje getUgovorById(int id) throws SQLException {
        String query = "SELECT * FROM UGOVOR_ZA_IZNAJMLJIVANJE WHERE idUgovor = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UgovorZaIznajmljivanje(
                        rs.getInt("idUgovor"),
                        rs.getDate("datumPocetka"),
                        rs.getDate("datumKraja"),
                        rs.getDouble("cijenaIznajmljivanja"),
                        rs.getInt("ZAKUPAC_idZakupac"),
                        rs.getInt("STAN_idStan"),
                        rs.getInt("STATUS_ZAKUPA_idStatus_Zakupa"),
                        rs.getInt("VLASNIK_idVlasnik"),
                        rs.getInt("ZAPOSLENI_idZaposelni")
                );
            }
        }
        return null;
    }

    // Dohvatanje svih ugovora
    public List<UgovorZaIznajmljivanje> getAll() throws SQLException {
        List<UgovorZaIznajmljivanje> ugovori = new ArrayList<>();
        String query = "SELECT * FROM UGOVOR_ZA_IZNAJMLJIVANJE";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ugovori.add(new UgovorZaIznajmljivanje(
                        rs.getInt("idUgovor"),
                        rs.getDate("datumPocetka"),
                        rs.getDate("datumKraja"),
                        rs.getDouble("cijenaIznajmljivanja"),
                        rs.getInt("ZAKUPAC_idZakupac"),
                        rs.getInt("STAN_idStan"),
                        rs.getInt("STATUS_ZAKUPA_idStatus_Zakupa"),
                        rs.getInt("VLASNIK_idVlasnik"),
                        rs.getInt("ZAPOSLENI_idZaposelni")
                ));
            }
        }
        return ugovori;
    }

    // Dodavanje novog ugovora
    public void addUgovor(UgovorZaIznajmljivanje ugovor) throws SQLException {
        String query = "INSERT INTO UGOVOR_ZA_IZNAJMLJIVANJE (datumPocetka, datumKraja, cijenaIznajmljivanja, " +
                       "ZAKUPAC_idZakupac, STAN_idStan, STATUS_ZAKUPA_idStatus_Zakupa, VLASNIK_idVlasnik, ZAPOSLENI_idZaposelni) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setDate(1, ugovor.getDatumPocetka());
            stmt.setDate(2, ugovor.getDatumKraja());
            stmt.setDouble(3, ugovor.getCijenaIznajmljivanja());
            stmt.setInt(4, ugovor.getZakupacId());
            stmt.setInt(5, ugovor.getStanId());
            stmt.setInt(6, ugovor.getStatusZakupaId());
            stmt.setInt(7, ugovor.getVlasnikId());
            stmt.setInt(8, ugovor.getZaposleniId());
            stmt.executeUpdate();
        }
    }

    // Ažuriranje postojećeg ugovora
    public void updateUgovor(UgovorZaIznajmljivanje ugovor) throws SQLException {
        String query = "UPDATE UGOVOR_ZA_IZNAJMLJIVANJE SET datumPocetka=?, datumKraja=?, cijenaIznajmljivanja=?, " +
                       "ZAKUPAC_idZakupac=?, STAN_idStan=?, STATUS_ZAKUPA_idStatus_Zakupa=?, VLASNIK_idVlasnik=?, " +
                       "ZAPOSLENI_idZaposelni=? WHERE idUgovor=?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setDate(1, ugovor.getDatumPocetka());
            stmt.setDate(2, ugovor.getDatumKraja());
            stmt.setDouble(3, ugovor.getCijenaIznajmljivanja());
            stmt.setInt(4, ugovor.getZakupacId());
            stmt.setInt(5, ugovor.getStanId());
            stmt.setInt(6, ugovor.getStatusZakupaId());
            stmt.setInt(7, ugovor.getVlasnikId());
            stmt.setInt(8, ugovor.getZaposleniId());
            stmt.setInt(9, ugovor.getIdUgovor());
            stmt.executeUpdate();
        }
    }

    // Brisanje ugovora po ID-u
    public void deleteUgovor(int idUgovor) throws SQLException {
        String query = "DELETE FROM UGOVOR_ZA_IZNAJMLJIVANJE WHERE idUgovor = ?";
        try (PreparedStatement stmt = konekcija.prepareStatement(query)) {
            stmt.setInt(1, idUgovor);
            stmt.executeUpdate();
        }
    }
}
