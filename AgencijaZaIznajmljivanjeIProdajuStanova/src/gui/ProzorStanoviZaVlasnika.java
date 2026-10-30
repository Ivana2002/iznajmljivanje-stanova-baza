package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

import dao.*;
import model.*;

public class ProzorStanoviZaVlasnika extends JFrame {

    private JTable tabelaStanova;
    private DefaultTableModel modelTabele;
    private StanDAO stanDAO;
    private TipStanaIznajmljivanjeDAO tipStanaIznajmljivanjeDAO;
    private VlasnikDAO vlasnikDAO;
    private ZgradaDAO zgradaDAO;
    private AdresaZgradeDAO adresaZgradeDAO;
    private GradDAO gradDAO;
    private DrzavaDAO drzavaDAO;
    private ZakupacDAO zakupacDAO;
    private PretragaIRezervacijaDAO pretragaIRezervacijaDAO;
    private Vlasnik vlasnik;

    private JButton dodajButton;
    private JButton izmijeniButton;
    private JButton obrisiButton;

    public ProzorStanoviZaVlasnika(Vlasnik vlasnik) {
        this.vlasnik = vlasnik;
        setTitle("Upravljanje stanovima - Vlasnik: " + vlasnik.getIme() + " " + vlasnik.getPrezime());
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        
        stanDAO = new StanDAO();
        tipStanaIznajmljivanjeDAO = new TipStanaIznajmljivanjeDAO();
        vlasnikDAO = new VlasnikDAO();
        zgradaDAO = new ZgradaDAO();
        adresaZgradeDAO = new AdresaZgradeDAO();
        gradDAO = new GradDAO();
        drzavaDAO = new DrzavaDAO();
        zakupacDAO = new ZakupacDAO();
        pretragaIRezervacijaDAO = new PretragaIRezervacijaDAO();

       
        String[] kolone = {"ID", "Površina", "Cijena", "Vlasnik", "Tip iznajmljivanja", "Broj stana", "Sprat", "Zgrada", "Status rezervacije"};
        modelTabele = new DefaultTableModel(kolone, 0);
        tabelaStanova = new JTable(modelTabele);
        JScrollPane scrollPane = new JScrollPane(tabelaStanova);
        add(scrollPane, BorderLayout.CENTER);

        ucitajPodatkeOStanovima();

        // Kontrolni panel
        JPanel kontrolniPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dodajButton = new JButton("Dodaj stan");
        izmijeniButton = new JButton("Izmeni stan");
        obrisiButton = new JButton("Obriši stan");

        dodajButton.addActionListener(e -> prikaziFormuZaDodavanje());
        izmijeniButton.addActionListener(e -> prikaziFormuZaIzmjenu());
        obrisiButton.addActionListener(e -> obrisiStan());

        kontrolniPanel.add(dodajButton);
        kontrolniPanel.add(izmijeniButton);
        kontrolniPanel.add(obrisiButton);

        add(kontrolniPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void ucitajPodatkeOStanovima() {
        modelTabele.setRowCount(0);
        try {
            List<Stan> stanovi = stanDAO.getStaniByVlasnik(vlasnik.getIdVlasnik());
            for (Stan stan : stanovi) {
                Vlasnik vlasnik = vlasnikDAO.getVlasnikById(stan.getVlasnikId());
                Zgrada zgrada = zgradaDAO.getZgradaById(stan.getZgradaId());
                
               
                String rezervacijaStatus = "Nema rezervacije";
                Integer pretragaRezervacijaId = stan.getPretragaRezervacijaId();
                if (pretragaRezervacijaId != null && pretragaRezervacijaId != 0) {
                    PretragaIRezervacija pretraga = pretragaIRezervacijaDAO.getPretragaIRezervacijaById(pretragaRezervacijaId);
                    if (pretraga != null) {
                        Zakupac zakupac = zakupacDAO.getZakupacById(pretraga.getZakupacId());
                        if (zakupac != null) {
                            rezervacijaStatus = "Rezervisano: " + zakupac.getIme() + " " + zakupac.getPrezime() +
                                               " (OD: " + pretraga.getRezervacijaOD() + ", DO: " + pretraga.getRezervacijaDO() + ")";
                        } else {
                            rezervacijaStatus = "Rezervisano (Zakupac nije pronađen)";
                        }
                    } else {
                        
                        stan.setPretragaRezervacijaId(null);
                        stanDAO.updateStan(stan);
                    }
                }

                Object[] podaci = {
                    stan.getIdStan(),
                    stan.getPovrsina(),
                    stan.getCijena(),
                    (vlasnik != null ? vlasnik.getIme() + " " + vlasnik.getPrezime() : "Nepoznato"),
                    stan.getVrstaUslugeIznajmljivanje(),
                    stan.getBrojStana(),
                    stan.getSprat(),
                    (zgrada != null ? zgrada.toString() : "Nepoznato"),
                    rezervacijaStatus
                };
                modelTabele.addRow(podaci);
            }

            if (modelTabele.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Nema stanova za ovog vlasnika!", "Obavještenje", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju stanova: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void prikaziFormuZaDodavanje() {
        JDialog dodajDialog = new JDialog(this, "Dodavanje stana", true);
        dodajDialog.setLayout(new GridLayout(0, 2, 5, 5));
        ((JComponent) dodajDialog.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField povrsinaField = new JTextField(10);
        JTextField cijenaField = new JTextField(10);
        JComboBox<TipStanaIznajmljivanje> tipIznajmljivanjaCombo = new JComboBox<>();
        JTextField brojStanaField = new JTextField(10);
        JTextField spratField = new JTextField(10);
        JComboBox<Zgrada> zgradaCombo = new JComboBox<>();

        try {
            List<TipStanaIznajmljivanje> tipovi = tipStanaIznajmljivanjeDAO.getAllTipoviStana();
            for (TipStanaIznajmljivanje tip : tipovi) {
                tipIznajmljivanjaCombo.addItem(tip);
            }

            List<Zgrada> zgrade = zgradaDAO.getAllZgrade();
            for (Zgrada zgrada : zgrade) {
                zgradaCombo.addItem(zgrada);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(dodajDialog, "Greška pri učitavanju podataka: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dodajDialog.add(new JLabel("Površina:"));
        dodajDialog.add(povrsinaField);
        dodajDialog.add(new JLabel("Cijena:"));
        dodajDialog.add(cijenaField);
        dodajDialog.add(new JLabel("Tip iznajmljivanja:"));
        dodajDialog.add(tipIznajmljivanjaCombo);
        dodajDialog.add(new JLabel("Broj stana:"));
        dodajDialog.add(brojStanaField);
        dodajDialog.add(new JLabel("Sprat:"));
        dodajDialog.add(spratField);
        dodajDialog.add(new JLabel("Zgrada:"));
        dodajDialog.add(zgradaCombo);

        JButton sacuvajButton = new JButton("Sačuvaj");
        JButton odustaniButton = new JButton("Odustani");

        sacuvajButton.addActionListener(e -> {
            if (povrsinaField.getText().trim().isEmpty() || cijenaField.getText().trim().isEmpty() ||
                brojStanaField.getText().trim().isEmpty() || spratField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dodajDialog, "Morate popuniti sva polja!", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double povrsina = Double.parseDouble(povrsinaField.getText().trim());
                double cijena = Double.parseDouble(cijenaField.getText().trim());
                TipStanaIznajmljivanje tipIznajmljivanje = (TipStanaIznajmljivanje) tipIznajmljivanjaCombo.getSelectedItem();
                int brojStana = Integer.parseInt(brojStanaField.getText().trim());
                int sprat = Integer.parseInt(spratField.getText().trim());
                Zgrada zgrada = (Zgrada) zgradaCombo.getSelectedItem();

                Stan noviStan = new Stan(
                    0, 
                    (int) povrsina,
                    cijena,
                    vlasnik.getIdVlasnik(),
                    zgrada.getIdZgrade(),
                    sprat,
                    brojStana,
                    "", 
                    tipIznajmljivanje.getOpis(),
                    0, 
                    null,
                    null, 
                    tipIznajmljivanje.getIdTipStana()
                );

                stanDAO.createStan(noviStan);
                ucitajPodatkeOStanovima();

                JOptionPane.showMessageDialog(dodajDialog, "Stan uspješno dodat!", "Uspješno", JOptionPane.INFORMATION_MESSAGE);
                dodajDialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dodajDialog, "Površina, cijena, broj stana i sprat moraju biti brojevi!", "Greška", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dodajDialog, "Greška pri dodavanju stana: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
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
        int selectedRow = tabelaStanova.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Morate izabrati stan za izmjenu!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idStan = (int) modelTabele.getValueAt(selectedRow, 0);
        try {
            Stan stan = stanDAO.getStanById(idStan);
            if (stan == null || stan.getVlasnikId() != vlasnik.getIdVlasnik()) {
                JOptionPane.showMessageDialog(this, "Stan nije pronađen ili nemate ovlašćenje za izmjenu!", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JDialog izmijeniDialog = new JDialog(this, "Izmena stana", true);
            izmijeniDialog.setLayout(new GridLayout(0, 2, 5, 5));
            ((JComponent) izmijeniDialog.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JTextField povrsinaField = new JTextField(String.valueOf(stan.getPovrsina()), 10);
            JTextField cijenaField = new JTextField(String.valueOf(stan.getCijena()), 10);
            JComboBox<TipStanaIznajmljivanje> tipIznajmljivanjeCombo = new JComboBox<>();
            JTextField brojStanaField = new JTextField(String.valueOf(stan.getBrojStana()), 10);
            JTextField spratField = new JTextField(String.valueOf(stan.getSprat()), 10);
            JComboBox<Zgrada> zgradaCombo = new JComboBox<>();

            try {
                List<TipStanaIznajmljivanje> tipovi = tipStanaIznajmljivanjeDAO.getAllTipoviStana();
                for (TipStanaIznajmljivanje tip : tipovi) {
                    tipIznajmljivanjeCombo.addItem(tip);
                    if (tip.getIdTipStana() == stan.getTipStanaIznajmljivanjeId()) {
                        tipIznajmljivanjeCombo.setSelectedItem(tip);
                    }
                }

                List<Zgrada> zgrade = zgradaDAO.getAllZgrade();
                for (Zgrada zgrada : zgrade) {
                    zgradaCombo.addItem(zgrada);
                    if (zgrada.getIdZgrade() == stan.getZgradaId()) {
                        zgradaCombo.setSelectedItem(zgrada);
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(izmijeniDialog, "Greška pri učitavanju podataka: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            izmijeniDialog.add(new JLabel("Površina:"));
            izmijeniDialog.add(povrsinaField);
            izmijeniDialog.add(new JLabel("Cijena:"));
            izmijeniDialog.add(cijenaField);
            izmijeniDialog.add(new JLabel("Tip iznajmljivanja:"));
            izmijeniDialog.add(tipIznajmljivanjeCombo);
            izmijeniDialog.add(new JLabel("Broj stana:"));
            izmijeniDialog.add(brojStanaField);
            izmijeniDialog.add(new JLabel("Sprat:"));
            izmijeniDialog.add(spratField);
            izmijeniDialog.add(new JLabel("Zgrada:"));
            izmijeniDialog.add(zgradaCombo);

            JButton sacuvajButton = new JButton("Sačuvaj");
            JButton odustaniButton = new JButton("Odustani");

            sacuvajButton.addActionListener(e -> {
                if (povrsinaField.getText().trim().isEmpty() || cijenaField.getText().trim().isEmpty() ||
                    brojStanaField.getText().trim().isEmpty() || spratField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(izmijeniDialog, "Morate popuniti sva polja!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    double povrsina = Double.parseDouble(povrsinaField.getText().trim());
                    double cijena = Double.parseDouble(cijenaField.getText().trim());
                    TipStanaIznajmljivanje tipIznajmljivanje = (TipStanaIznajmljivanje) tipIznajmljivanjeCombo.getSelectedItem();
                    int brojStana = Integer.parseInt(brojStanaField.getText().trim());
                    int sprat = Integer.parseInt(spratField.getText().trim());
                    Zgrada zgrada = (Zgrada) zgradaCombo.getSelectedItem();

                    Stan izmenjeniStan = new Stan(
                        idStan,
                        (int) povrsina,
                        cijena,
                        vlasnik.getIdVlasnik(),
                        zgrada.getIdZgrade(),
                        sprat,
                        brojStana,
                        stan.getVrstaUslugeProdaja(),
                        tipIznajmljivanje.getOpis(),
                        stan.getCijenaProdaje(),
                        stan.getPretragaRezervacijaId(),
                        null,
                        tipIznajmljivanje.getIdTipStana()
                    );

                    stanDAO.updateStan(izmenjeniStan);
                    ucitajPodatkeOStanovima();

                    JOptionPane.showMessageDialog(izmijeniDialog, "Stan uspešno izmijenjen!", "Uspešno", JOptionPane.INFORMATION_MESSAGE);
                    izmijeniDialog.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(izmijeniDialog, "Površina, cijena, broj stana i sprat moraju biti brojevi!", "Greška", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(izmijeniDialog, "Greška pri izmjeni stana: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            });

            odustaniButton.addActionListener(e -> izmijeniDialog.dispose());

            izmijeniDialog.add(sacuvajButton);
            izmijeniDialog.add(odustaniButton);

            izmijeniDialog.pack();
            izmijeniDialog.setLocationRelativeTo(this);
            izmijeniDialog.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju stana: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void obrisiStan() {
        int selectedRow = tabelaStanova.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Morate izabrati stan za brisanje!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idStan = (int) modelTabele.getValueAt(selectedRow, 0);
        try {
            Stan stan = stanDAO.getStanById(idStan);
            if (stan == null || stan.getVlasnikId() != vlasnik.getIdVlasnik()) {
                JOptionPane.showMessageDialog(this, "Stan nije pronađen ili nemate ovlašćenje za brisanje!", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int potvrda = JOptionPane.showConfirmDialog(this, "Da li ste sigurni da želite da obrišete ovaj stan?", "Potvrda brisanja", JOptionPane.YES_NO_OPTION);
            if (potvrda == JOptionPane.YES_OPTION) {
                stanDAO.deleteStan(idStan);
                ucitajPodatkeOStanovima();
                JOptionPane.showMessageDialog(this, "Stan uspješno obrisan!", "Uspješno", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Greška pri brisanju stana: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}