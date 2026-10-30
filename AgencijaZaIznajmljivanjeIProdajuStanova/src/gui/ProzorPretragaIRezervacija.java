package gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import dao.PretragaIRezervacijaDAO;
import dao.ZakupacDAO;
import model.PretragaIRezervacija;
import model.Zakupac;

public class ProzorPretragaIRezervacija extends JFrame {

    private JTable tabelaPretraga;
    private DefaultTableModel modelTabele;
    private PretragaIRezervacijaDAO pretragaIRezervacijaDAO;
    private ZakupacDAO zakupacDAO;

    private JButton dodajButton;
    private JButton izmijeniButton;
    private JButton obrisiButton;

    public ProzorPretragaIRezervacija() {
        setTitle("Upravljanje pretragama i rezervacijama");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

       
        pretragaIRezervacijaDAO = new PretragaIRezervacijaDAO();
        zakupacDAO = new ZakupacDAO();

        // Kreiranje tabele
        String[] kolone = {"ID", "Lokacija", "Tip za iznajmljivanje", "Rezervacija OD", "Rezervacija DO", "Dostupnost", "Zakupac"};
        modelTabele = new DefaultTableModel(kolone, 0);
        tabelaPretraga = new JTable(modelTabele);
        JScrollPane scrollPane = new JScrollPane(tabelaPretraga);
        add(scrollPane, BorderLayout.CENTER);

        ucitajPodatkeOPretrazi();

        // Dugmad za kontrolu
        JPanel kontrolniPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dodajButton = new JButton("Dodaj pretragu/rezervaciju");
        izmijeniButton = new JButton("Izmijeni pretragu/rezervaciju");
        obrisiButton = new JButton("Obriši pretragu/rezervaciju");

        dodajButton.addActionListener(e -> prikaziFormuZaDodavanje());
        izmijeniButton.addActionListener(e -> prikaziFormuZaIzmjenu());
        obrisiButton.addActionListener(e -> obrisiPretragu());

        kontrolniPanel.add(dodajButton);
        kontrolniPanel.add(izmijeniButton);
        kontrolniPanel.add(obrisiButton);

        add(kontrolniPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void ucitajPodatkeOPretrazi() {
        modelTabele.setRowCount(0);
        try {
            List<PretragaIRezervacija> svePretrage = pretragaIRezervacijaDAO.getAllPretrage();
            for (PretragaIRezervacija pretraga : svePretrage) {
                Zakupac zakupac = zakupacDAO.getZakupacById(pretraga.getZakupacId());
                Object[] podaci = {
                    pretraga.getIdPretragaIRezervacija(),
                    pretraga.getLokacija(),
                    pretraga.getTipZaIznajmljivanje(),
                    pretraga.getRezervacijaOD(),
                    pretraga.getRezervacijaDO(),
                    pretraga.getDostupnost(),
                    (zakupac != null ? zakupac.getIme() + " " + zakupac.getPrezime() : "Nepoznato")
                };
                modelTabele.addRow(podaci);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju pretraga/rezervacija: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void prikaziFormuZaDodavanje() {
        JDialog dodajDialog = new JDialog(this, "Dodavanje pretrage/rezervacije", true);
        dodajDialog.setLayout(new GridLayout(0, 2, 5, 5));
        ((JComponent) dodajDialog.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField lokacijaField = new JTextField(20);
        JTextField tipZaIznajmljivanjeField = new JTextField(20);
        JTextField rezervacijaODField = new JTextField("2025-01-01", 10);
        JTextField rezervacijaDOField = new JTextField("2025-01-31", 10);
        JCheckBox dostupnostCheckBox = new JCheckBox("Dostupno", true);
        JComboBox<Zakupac> zakupacCombo = new JComboBox<>();

        try {
            List<Zakupac> sviZakupci = zakupacDAO.getAllZakupci();
            for (Zakupac zakupac : sviZakupci) {
                zakupacCombo.addItem(zakupac);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(dodajDialog, "Greška pri učitavanju zakupaca: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dodajDialog.add(new JLabel("Lokacija:"));
        dodajDialog.add(lokacijaField);
        dodajDialog.add(new JLabel("Tip za iznajmljivanje:"));
        dodajDialog.add(tipZaIznajmljivanjeField);
        dodajDialog.add(new JLabel("Rezervacija OD (YYYY-MM-DD):"));
        dodajDialog.add(rezervacijaODField);
        dodajDialog.add(new JLabel("Rezervacija DO (YYYY-MM-DD):"));
        dodajDialog.add(rezervacijaDOField);
        dodajDialog.add(new JLabel("Dostupnost:"));
        dodajDialog.add(dostupnostCheckBox);
        dodajDialog.add(new JLabel("Zakupac:"));
        dodajDialog.add(zakupacCombo);

        JButton sacuvajButton = new JButton("Sačuvaj");
        JButton odustaniButton = new JButton("Odustani");

        sacuvajButton.addActionListener(e -> {
            if (lokacijaField.getText().trim().isEmpty() || tipZaIznajmljivanjeField.getText().trim().isEmpty() ||
                rezervacijaODField.getText().trim().isEmpty() || rezervacijaDOField.getText().trim().isEmpty() ||
                zakupacCombo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(dodajDialog, "Morate popuniti sva polja!", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String lokacija = lokacijaField.getText().trim();
                String tipZaIznajmljivanje = tipZaIznajmljivanjeField.getText().trim();
                Date rezervacijaOD = Date.valueOf(rezervacijaODField.getText().trim());
                Date rezervacijaDO = Date.valueOf(rezervacijaDOField.getText().trim());
                boolean dostupnost = dostupnostCheckBox.isSelected();

                
                Zakupac izabraniZakupac = (Zakupac) zakupacCombo.getSelectedItem();
                
               
                PretragaIRezervacija novaPretraga = new PretragaIRezervacija(
                    0, 
                    izabraniZakupac.getIdZakupac(), 
                    lokacija,
                    tipZaIznajmljivanje,
                    rezervacijaOD,
                    rezervacijaDO,
                    dostupnost
                );

                pretragaIRezervacijaDAO.createPretragaIRezervacija(novaPretraga);
                ucitajPodatkeOPretrazi();

                JOptionPane.showMessageDialog(dodajDialog, "Pretraga/rezervacija uspješno dodata!", "Uspešno", JOptionPane.INFORMATION_MESSAGE);
                dodajDialog.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dodajDialog, "Greška pri dodavanju pretrage/rezervacije: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });

        odustaniButton.addActionListener(e -> dodajDialog.dispose());

        dodajDialog.add(sacuvajButton);
        dodajDialog.add(odustaniButton);

        dodajDialog.pack();
        dodajDialog.setLocationRelativeTo(this);
        dodajDialog.setVisible(true);
    }

    private void prikaziFormuZaIzmjenu() {
        int selectedRow = tabelaPretraga.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Morate izabrati pretragu/rezervaciju za izmjenu!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idPretraga = (int) modelTabele.getValueAt(selectedRow, 0);
        try {
            PretragaIRezervacija pretraga = pretragaIRezervacijaDAO.getPretragaIRezervacijaById(idPretraga);
            if (pretraga == null) {
                JOptionPane.showMessageDialog(this, "Pretraga/rezervacija nije pronađena!", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JDialog izmijeniDialog = new JDialog(this, "Izmjena pretrage/rezervacije", true);
            izmijeniDialog.setLayout(new GridLayout(0, 2, 5, 5));
            ((JComponent) izmijeniDialog.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JTextField lokacijaField = new JTextField(pretraga.getLokacija(), 20);
            JTextField tipZaIznajmljivanjeField = new JTextField(pretraga.getTipZaIznajmljivanje(), 20);
            JTextField rezervacijaODField = new JTextField(pretraga.getRezervacijaOD() != null ? pretraga.getRezervacijaOD().toString() : "", 10);
            JTextField rezervacijaDOField = new JTextField(pretraga.getRezervacijaDO() != null ? pretraga.getRezervacijaDO().toString() : "", 10);
            JCheckBox dostupnostCheckBox = new JCheckBox("Dostupno", pretraga.getDostupnost());
            JComboBox<Zakupac> zakupacCombo = new JComboBox<>();

            try {
                List<Zakupac> sviZakupci = zakupacDAO.getAllZakupci();
                for (Zakupac zakupac : sviZakupci) {
                    zakupacCombo.addItem(zakupac);
                    if (zakupac.getIdZakupac() == pretraga.getZakupacId()) {
                        zakupacCombo.setSelectedItem(zakupac);
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(izmijeniDialog, "Greška pri učitavanju zakupaca: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            izmijeniDialog.add(new JLabel("Lokacija:"));
            izmijeniDialog.add(lokacijaField);
            izmijeniDialog.add(new JLabel("Tip za iznajmljivanje:"));
            izmijeniDialog.add(tipZaIznajmljivanjeField);
            izmijeniDialog.add(new JLabel("Rezervacija OD (YYYY-MM-DD):"));
            izmijeniDialog.add(rezervacijaODField);
            izmijeniDialog.add(new JLabel("Rezervacija DO (YYYY-MM-DD):"));
            izmijeniDialog.add(rezervacijaDOField);
            izmijeniDialog.add(new JLabel("Dostupnost:"));
            izmijeniDialog.add(dostupnostCheckBox);
            izmijeniDialog.add(new JLabel("Zakupac:"));
            izmijeniDialog.add(zakupacCombo);

            JButton sacuvajButton = new JButton("Sačuvaj");
            JButton odustaniButton = new JButton("Odustani");

            sacuvajButton.addActionListener(e -> {
                if (lokacijaField.getText().trim().isEmpty() || tipZaIznajmljivanjeField.getText().trim().isEmpty() ||
                    rezervacijaODField.getText().trim().isEmpty() || rezervacijaDOField.getText().trim().isEmpty() ||
                    zakupacCombo.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(izmijeniDialog, "Morate popuniti sva polja!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    String lokacija = lokacijaField.getText().trim();
                    String tipZaIznajmljivanje = tipZaIznajmljivanjeField.getText().trim();
                    Date rezervacijaOD = Date.valueOf(rezervacijaODField.getText().trim());
                    Date rezervacijaDO = Date.valueOf(rezervacijaDOField.getText().trim());
                    boolean dostupnost = dostupnostCheckBox.isSelected();

                 
                    Zakupac izabraniZakupac = (Zakupac) zakupacCombo.getSelectedItem();

                    // Ažuriranje pretrage/rezervacije
                    PretragaIRezervacija izmijenjenaPretraga = new PretragaIRezervacija(
                        idPretraga,
                        izabraniZakupac.getIdZakupac(), 
                        lokacija,
                        tipZaIznajmljivanje,
                        rezervacijaOD,
                        rezervacijaDO,
                        dostupnost
                    );

                    pretragaIRezervacijaDAO.updatePretragaIRezervacija(izmijenjenaPretraga);
                    ucitajPodatkeOPretrazi();

                    JOptionPane.showMessageDialog(izmijeniDialog, "Pretraga/rezervacija uspješno izmijenjena!", "Uspješno", JOptionPane.INFORMATION_MESSAGE);
                    izmijeniDialog.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(izmijeniDialog, "Greška pri izmjeni pretrage/rezervacije: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                }
            });

            odustaniButton.addActionListener(e -> izmijeniDialog.dispose());

            izmijeniDialog.add(sacuvajButton);
            izmijeniDialog.add(odustaniButton);

            izmijeniDialog.pack();
            izmijeniDialog.setLocationRelativeTo(this);
            izmijeniDialog.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju pretrage/rezervacije: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void obrisiPretragu() {
        int selectedRow = tabelaPretraga.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Morate izabrati pretragu/rezervaciju za brisanje!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idPretraga = (int) modelTabele.getValueAt(selectedRow, 0);
        int potvrda = JOptionPane.showConfirmDialog(this, "Da li ste sigurni da želite da obrišete ovu pretragu/rezervaciju?", "Potvrda brisanja", JOptionPane.YES_NO_OPTION);
        if (potvrda == JOptionPane.YES_OPTION) {
            try {
                pretragaIRezervacijaDAO.deletePretragaIRezervacija(idPretraga);
                ucitajPodatkeOPretrazi();
                JOptionPane.showMessageDialog(this, "Pretraga/rezervacija uspješno obrisana!", "Uspešno", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Greška pri brisanju pretrage/rezervacije: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProzorPretragaIRezervacija::new);
    }
}