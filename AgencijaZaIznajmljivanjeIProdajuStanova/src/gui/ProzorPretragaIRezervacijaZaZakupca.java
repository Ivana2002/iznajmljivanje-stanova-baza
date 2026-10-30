package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import dao.*;
import model.*;

public class ProzorPretragaIRezervacijaZaZakupca extends JFrame {

    private JTable tabelaStanovi;
    private DefaultTableModel modelTabeleStanovi;
    private JTable tabelaPretraga;
    private DefaultTableModel modelTabele;
    private PretragaIRezervacijaDAO pretragaIRezervacijaDAO;
    private StanDAO stanDAO;
    private VlasnikDAO vlasnikDAO;
    private ZgradaDAO zgradaDAO;
    private AdresaZgradeDAO adresaZgradeDAO;
    private Zakupac zakupac;
    private GradDAO gradDAO;

    private JButton pretraziStanoveButton;
    private JButton rezervisiButton;

    private JComboBox<String> gradCombo;
    private JTextField lokacijaField;
    private JComboBox<String> tipZaIznajmljivanjeCombo; 
    private JTextField rezervacijaODField;
    private JTextField rezervacijaDOField;
    private JCheckBox dostupnostCheckBox;

    public ProzorPretragaIRezervacijaZaZakupca(Zakupac zakupac) {
        this.zakupac = zakupac;
        setTitle("Pretraga i rezervacija stanova - Zakupac: " + zakupac.getIme() + " " + zakupac.getPrezime());
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

       
        pretragaIRezervacijaDAO = new PretragaIRezervacijaDAO();
        stanDAO = new StanDAO();
        vlasnikDAO = new VlasnikDAO();
        zgradaDAO = new ZgradaDAO();
        adresaZgradeDAO = new AdresaZgradeDAO();
        gradDAO = new GradDAO();

        
        JPanel pretragaPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        pretragaPanel.setBorder(BorderFactory.createTitledBorder("Pretraga stanova"));

        gradCombo = new JComboBox<>();
        gradCombo.addItem(""); 
        try {
            List<Grad> gradovi = gradDAO.getAllGradovi();
            for (Grad grad : gradovi) {
                gradCombo.addItem(grad.getNaziv());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju gradova: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        lokacijaField = new JTextField(20);
        tipZaIznajmljivanjeCombo = new JComboBox<>();
        tipZaIznajmljivanjeCombo.addItem("");
        tipZaIznajmljivanjeCombo.addItem("Na dan");
        tipZaIznajmljivanjeCombo.addItem("Na mesec");
        tipZaIznajmljivanjeCombo.addItem("Na godinu");
        tipZaIznajmljivanjeCombo.addItem("Po dogovoru");
        rezervacijaODField = new JTextField("", 10);
        rezervacijaDOField = new JTextField("", 10);
        dostupnostCheckBox = new JCheckBox("Dostupno", true);
        pretraziStanoveButton = new JButton("Pretraži stanove");

        pretragaPanel.add(new JLabel("Grad:"));
        pretragaPanel.add(gradCombo);
        pretragaPanel.add(new JLabel("Lokacija:"));
        pretragaPanel.add(lokacijaField);
        pretragaPanel.add(new JLabel("Tip za iznajmljivanje:"));
        pretragaPanel.add(tipZaIznajmljivanjeCombo);
        pretragaPanel.add(new JLabel("Rezervacija OD (YYYY-MM-DD):"));
        pretragaPanel.add(rezervacijaODField);
        pretragaPanel.add(new JLabel("Rezervacija DO (YYYY-MM-DD):"));
        pretragaPanel.add(rezervacijaDOField);
        pretragaPanel.add(new JLabel("Dostupnost:"));
        pretragaPanel.add(dostupnostCheckBox);
        pretragaPanel.add(new JLabel(""));
        pretragaPanel.add(pretraziStanoveButton);

        add(pretragaPanel, BorderLayout.NORTH);

        
        String[] koloneStanovi = {"ID", "Površina", "Cijena", "Vlasnik", "Tip iznajmljivanja", "Broj stana", "Sprat", "Zgrada"};
        modelTabeleStanovi = new DefaultTableModel(koloneStanovi, 0);
        tabelaStanovi = new JTable(modelTabeleStanovi);
        JScrollPane scrollPaneStanovi = new JScrollPane(tabelaStanovi);

        
        JPanel pretragaRezervacijaPanel = new JPanel(new BorderLayout());
        String[] kolone = {"ID", "Lokacija", "Tip za iznajmljivanje", "Rezervacija OD", "Rezervacija DO", "Dostupnost"};
        modelTabele = new DefaultTableModel(kolone, 0);
        tabelaPretraga = new JTable(modelTabele);
        JScrollPane scrollPane = new JScrollPane(tabelaPretraga);
        pretragaRezervacijaPanel.add(scrollPane, BorderLayout.CENTER);

        ucitajPodatkeOPretrazi();

        // Dugme za rezervaciju
        JPanel kontrolniPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rezervisiButton = new JButton("Rezerviši stan");

        rezervisiButton.addActionListener(e -> rezervisiStan());
        pretraziStanoveButton.addActionListener(e -> pretraziStanove(true));

        kontrolniPanel.add(rezervisiButton);
        pretragaRezervacijaPanel.add(kontrolniPanel, BorderLayout.SOUTH);

      
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPaneStanovi, pretragaRezervacijaPanel);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void pretraziStanove(boolean prikaziPoruku) {
        modelTabeleStanovi.setRowCount(0);
        try {
            String grad = (String) gradCombo.getSelectedItem();
            String lokacija = lokacijaField.getText().trim();
            Date rezervacijaOD = rezervacijaODField.getText().trim().isEmpty() ? null : Date.valueOf(rezervacijaODField.getText().trim());
            Date rezervacijaDO = rezervacijaDOField.getText().trim().isEmpty() ? null : Date.valueOf(rezervacijaDOField.getText().trim());
            boolean dostupnost = dostupnostCheckBox.isSelected();

            // Validacija datuma
            if (rezervacijaOD != null && rezervacijaDO != null) {
                if (rezervacijaDO.before(rezervacijaOD)) {
                    JOptionPane.showMessageDialog(this, "Datum 'Rezervacija DO' mora biti poslije 'Rezervacija OD'!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (rezervacijaOD.equals(rezervacijaDO)) {
                    rezervacijaDO = new Date(rezervacijaDO.getTime() + 24 * 60 * 60 * 1000);
                }
            }

            List<Stan> stanovi = stanDAO.getStaniByTipIznajmljivanje(null); // Vraća sve stanove
            if (stanovi.isEmpty()) {
                if (prikaziPoruku) {
                    JOptionPane.showMessageDialog(this, "Nema stanova u bazi!", "Obavještenje", JOptionPane.INFORMATION_MESSAGE);
                }
                return;
            }

            for (Stan stan : stanovi) {
                boolean matches = true;
                String dostupnostStatus = "Dostupno";

                // Provera grada i lokacije
                Zgrada zgrada = zgradaDAO.getZgradaById(stan.getZgradaId());
                AdresaZgrade adresa = adresaZgradeDAO.getAdresaZgradeById(zgrada.getAdresaId());
                Grad gradObj = adresa != null ? gradDAO.getGradById(adresa.getGradId()) : null;
                String stanGrad = gradObj != null ? gradObj.getNaziv() : "";
                String stanLokacija = adresa != null ? adresa.getUlica() + " " + adresa.getBroj() : "";

                if (grad != null && !grad.isEmpty() && !stanGrad.equalsIgnoreCase(grad)) {
                    matches = false;
                }
                if (!lokacija.isEmpty() && !stanLokacija.toLowerCase().contains(lokacija.toLowerCase())) {
                    matches = false;
                }

                // Provera dostupnosti na osnovu datuma
                if (stan.getPretragaRezervacijaId() != null) {
                    PretragaIRezervacija pretraga = pretragaIRezervacijaDAO.getPretragaIRezervacijaById(stan.getPretragaRezervacijaId());
                    if (pretraga != null && rezervacijaOD != null && rezervacijaDO != null) {
                        Date postojecaRezervacijaOD = pretraga.getRezervacijaOD();
                        Date postojecaRezervacijaDO = pretraga.getRezervacijaDO();
                        if (postojecaRezervacijaOD != null && postojecaRezervacijaDO != null) {
                            if (rezervacijaOD.before(postojecaRezervacijaDO) && rezervacijaDO.after(postojecaRezervacijaOD)) {
                                dostupnostStatus = "Rezervisano";
                                if (dostupnost) {
                                    matches = false; 
                                }
                            } else {
                                
                                stan.setPretragaRezervacijaId(null);
                                stanDAO.updateStan(stan);
                                dostupnostStatus = "Dostupno";
                            }
                        }
                    }
                }

                if (matches) {
                    Vlasnik vlasnik = vlasnikDAO.getVlasnikById(stan.getVlasnikId());
                    Object[] podaci = {
                        stan.getIdStan(),
                        stan.getPovrsina(),
                        stan.getCijena(),
                        vlasnik != null ? vlasnik.getIme() + " " + vlasnik.getPrezime() : "Nepoznato",
                        stan.getVrstaUslugeIznajmljivanje(),
                        stan.getBrojStana(),
                        stan.getSprat(),
                        zgrada != null ? zgrada.toString() : "Nepoznato",
                        dostupnostStatus
                    };
                    modelTabeleStanovi.addRow(podaci);
                }
            }

            if (modelTabeleStanovi.getRowCount() == 0 && prikaziPoruku) {
                JOptionPane.showMessageDialog(this, "Nema stanova koji ispunjavaju zadate kriterijume!", "Obavještenje", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Greška pri pretrazi stanova: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    private void ucitajPodatkeOPretrazi() {
        modelTabele.setRowCount(0);
        try {
            List<PretragaIRezervacija> svePretrage = pretragaIRezervacijaDAO.getAllPretrage();
            for (PretragaIRezervacija pretraga : svePretrage) {
                if (pretraga.getZakupacId() != zakupac.getIdZakupac()) {
                    continue; 
                }
                Object[] podaci = {
                    pretraga.getIdPretragaIRezervacija(),
                    pretraga.getLokacija(),
                    pretraga.getTipZaIznajmljivanje(),
                    pretraga.getRezervacijaOD(),
                    pretraga.getRezervacijaDO(),
                    pretraga.getDostupnost()
                };
                modelTabele.addRow(podaci);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju pretraga/rezervacija: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void rezervisiStan() {
        int selectedRow = tabelaStanovi.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Morate izabrati stan za rezervaciju!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idStan = (int) modelTabeleStanovi.getValueAt(selectedRow, 0);
        try {
            Stan stan = stanDAO.getStanById(idStan);

            // Provjera dostupnosti na osnovu datuma
            Date rezervacijaOD = Date.valueOf(rezervacijaODField.getText().trim());
            Date rezervacijaDO = Date.valueOf(rezervacijaDOField.getText().trim());
            if (stan.getPretragaRezervacijaId() != null) {
                PretragaIRezervacija pretraga = pretragaIRezervacijaDAO.getPretragaIRezervacijaById(stan.getPretragaRezervacijaId());
                if (pretraga != null) {
                    Date postojecaRezervacijaOD = pretraga.getRezervacijaOD();
                    Date postojecaRezervacijaDO = pretraga.getRezervacijaDO();
                    if (postojecaRezervacijaOD != null && postojecaRezervacijaDO != null) {
                        if (rezervacijaOD.before(postojecaRezervacijaDO) && rezervacijaDO.after(postojecaRezervacijaOD)) {
                            JOptionPane.showMessageDialog(this, "Ovaj stan je već rezervisan u tom periodu!", "Greška", JOptionPane.ERROR_MESSAGE);
                            return;
                        } else {
                            
                            stan.setPretragaRezervacijaId(null);
                            stanDAO.updateStan(stan);
                        }
                    }
                }
            }

            String grad = (String) gradCombo.getSelectedItem();
            String lokacijaUnos = lokacijaField.getText().trim();
            String lokacija = (grad != null && !grad.isEmpty() ? grad + " " : "") + lokacijaUnos;
            String tipZaIznajmljivanje = (String) tipZaIznajmljivanjeCombo.getSelectedItem();
            boolean dostupnost = dostupnostCheckBox.isSelected();

            if (lokacija.trim().isEmpty() || tipZaIznajmljivanje.isEmpty() || rezervacijaOD == null || rezervacijaDO == null) {
                JOptionPane.showMessageDialog(this, "Morate popuniti sva polja za pretragu!", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PretragaIRezervacija novaPretraga = new PretragaIRezervacija(
                0, 
                zakupac.getIdZakupac(),
                lokacija,
                tipZaIznajmljivanje,
                rezervacijaOD,
                rezervacijaDO,
                dostupnost
            );

            pretragaIRezervacijaDAO.createPretragaIRezervacija(novaPretraga);
            stan.setPretragaRezervacijaId(novaPretraga.getIdPretragaIRezervacija());
            stanDAO.updateStan(stan);

            ucitajPodatkeOPretrazi();
            pretraziStanove(false); 

            JOptionPane.showMessageDialog(this, "Stan uspješno rezervisan!", "Uspješno", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Greška pri rezervaciji stana: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}