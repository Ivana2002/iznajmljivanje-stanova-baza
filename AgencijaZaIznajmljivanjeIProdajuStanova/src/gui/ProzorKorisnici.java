package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

import dao.ZakupacDAO;
import dao.ZaposleniUAgencijiDAO;
import dao.VlasnikDAO;
import model.Zakupac;
import model.ZaposleniUAgenciji;
import model.Vlasnik;

public class ProzorKorisnici extends JFrame {

    private JTabbedPane tabbedPane;

    
    private JTable tabelaZaposleni;
    private DefaultTableModel modelTabeleZaposleni;
    private ZaposleniUAgencijiDAO zaposleniUAgencijiDAO;
    private JButton dodajZaposleniButton;
    private JButton izmijeniZaposleniButton;
    private JButton obrisiZaposleniButton;

    
    private JTable tabelaZakupci;
    private DefaultTableModel modelTabeleZakupci;
    private ZakupacDAO zakupacDAO;
    private JButton dodajZakupacButton;
    private JButton izmijeniZakupacButton;
    private JButton obrisiZakupacButton;

    
    private JTable tabelaVlasnici;
    private DefaultTableModel modelTabeleVlasnici;
    private VlasnikDAO vlasnikDAO;
    private JButton dodajVlasnikButton;
    private JButton izmijeniVlasnikButton;
    private JButton obrisiVlasnikButton;

    public ProzorKorisnici() {
        setTitle("Upravljanje korisnicima");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        
        zaposleniUAgencijiDAO = new ZaposleniUAgencijiDAO();
        zakupacDAO = new ZakupacDAO();
        vlasnikDAO = new VlasnikDAO();

    
        tabbedPane = new JTabbedPane();

       
        JPanel zaposleniPanel = new JPanel(new BorderLayout());
        String[] koloneZaposleni = {"ID", "Ime", "Prezime", "Telefon", "Email"};
        modelTabeleZaposleni = new DefaultTableModel(koloneZaposleni, 0);
        tabelaZaposleni = new JTable(modelTabeleZaposleni);
        JScrollPane scrollPaneZaposleni = new JScrollPane(tabelaZaposleni);
        zaposleniPanel.add(scrollPaneZaposleni, BorderLayout.CENTER);

        ucitajPodatkeOZaposlenima();

        JPanel kontrolniPanelZaposleni = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dodajZaposleniButton = new JButton("Dodaj zaposlenog");
        izmijeniZaposleniButton = new JButton("Izmijeni zaposlenog");
        obrisiZaposleniButton = new JButton("Obriši zaposlenog");

        dodajZaposleniButton.addActionListener(e -> prikaziFormuZaDodavanjeZaposlenog());
        izmijeniZaposleniButton.addActionListener(e -> prikaziFormuZaIzmjenuZaposlenog());
        obrisiZaposleniButton.addActionListener(e -> obrisiZaposlenog());

        kontrolniPanelZaposleni.add(dodajZaposleniButton);
        kontrolniPanelZaposleni.add(izmijeniZaposleniButton);
        kontrolniPanelZaposleni.add(obrisiZaposleniButton);
        zaposleniPanel.add(kontrolniPanelZaposleni, BorderLayout.SOUTH);

        tabbedPane.addTab("Zaposleni", zaposleniPanel);

        // Tab za zakupce
        JPanel zakupciPanel = new JPanel(new BorderLayout());
        String[] koloneZakupci = {"ID", "Ime", "Prezime", "Telefon", "Email"};
        modelTabeleZakupci = new DefaultTableModel(koloneZakupci, 0);
        tabelaZakupci = new JTable(modelTabeleZakupci);
        JScrollPane scrollPaneZakupci = new JScrollPane(tabelaZakupci);
        zakupciPanel.add(scrollPaneZakupci, BorderLayout.CENTER);

        ucitajPodatkeOZakupcima();

        JPanel kontrolniPanelZakupci = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dodajZakupacButton = new JButton("Dodaj zakupca");
        izmijeniZakupacButton = new JButton("Izmeni zakupca");
        obrisiZakupacButton = new JButton("Obriši zakupca");

        dodajZakupacButton.addActionListener(e -> prikaziFormuZaDodavanjeZakupca());
        izmijeniZakupacButton.addActionListener(e -> prikaziFormuZaIzmjenuZakupca());
        obrisiZakupacButton.addActionListener(e -> obrisiZakupca());

        kontrolniPanelZakupci.add(dodajZakupacButton);
        kontrolniPanelZakupci.add(izmijeniZakupacButton);
        kontrolniPanelZakupci.add(obrisiZakupacButton);
        zakupciPanel.add(kontrolniPanelZakupci, BorderLayout.SOUTH);

        tabbedPane.addTab("Zakupci", zakupciPanel);

      
        JPanel vlasniciPanel = new JPanel(new BorderLayout());
        String[] koloneVlasnici = {"ID", "Ime", "Prezime", "Telefon", "Email"};
        modelTabeleVlasnici = new DefaultTableModel(koloneVlasnici, 0);
        tabelaVlasnici = new JTable(modelTabeleVlasnici);
        JScrollPane scrollPaneVlasnici = new JScrollPane(tabelaVlasnici);
        vlasniciPanel.add(scrollPaneVlasnici, BorderLayout.CENTER);

        ucitajPodatkeOVlasnicima();

        JPanel kontrolniPanelVlasnici = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dodajVlasnikButton = new JButton("Dodaj vlasnika");
        izmijeniVlasnikButton = new JButton("Izmijeni vlasnika");
        obrisiVlasnikButton = new JButton("Obriši vlasnika");

        dodajVlasnikButton.addActionListener(e -> prikaziFormuZaDodavanjeVlasnika());
        izmijeniVlasnikButton.addActionListener(e -> prikaziFormuZaIzmjenuVlasnika());
        obrisiVlasnikButton.addActionListener(e -> obrisiVlasnika());

        kontrolniPanelVlasnici.add(dodajVlasnikButton);
        kontrolniPanelVlasnici.add(izmijeniVlasnikButton);
        kontrolniPanelVlasnici.add(obrisiVlasnikButton);
        vlasniciPanel.add(kontrolniPanelVlasnici, BorderLayout.SOUTH);

        tabbedPane.addTab("Vlasnici", vlasniciPanel);

        
        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }

    // Metode za zaposlene
    private void ucitajPodatkeOZaposlenima() {
        modelTabeleZaposleni.setRowCount(0);
        try {
            List<ZaposleniUAgenciji> sviZaposleni = zaposleniUAgencijiDAO.getAll(); 
            for (ZaposleniUAgenciji zaposleni : sviZaposleni) {
                Object[] podaci = {
                    zaposleni.getIdZaposleni(),
                    zaposleni.getIme(),
                    zaposleni.getPrezime(),
                    zaposleni.getTelefon(),
                    zaposleni.getEmail()
                };
                modelTabeleZaposleni.addRow(podaci);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju zaposlenih: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void prikaziFormuZaDodavanjeZaposlenog() {
        JDialog dodajDialog = new JDialog(this, "Dodavanje zaposlenog", true);
        dodajDialog.setLayout(new GridLayout(0, 2, 5, 5));
        ((JComponent) dodajDialog.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField imeField = new JTextField(20);
        JTextField prezimeField = new JTextField(20);
        JTextField telefonField = new JTextField(20);
        JTextField emailField = new JTextField(20);

        dodajDialog.add(new JLabel("Ime:"));
        dodajDialog.add(imeField);
        dodajDialog.add(new JLabel("Prezime:"));
        dodajDialog.add(prezimeField);
        dodajDialog.add(new JLabel("Telefon:"));
        dodajDialog.add(telefonField);
        dodajDialog.add(new JLabel("Email:"));
        dodajDialog.add(emailField);

        JButton sacuvajButton = new JButton("Sačuvaj");
        JButton odustaniButton = new JButton("Odustani");

        sacuvajButton.addActionListener(e -> {
            if (imeField.getText().trim().isEmpty() || prezimeField.getText().trim().isEmpty() ||
                telefonField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dodajDialog, "Morate popuniti sva polja!", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String ime = imeField.getText().trim();
                String prezime = prezimeField.getText().trim();
                String telefon = telefonField.getText().trim();
                String email = emailField.getText().trim();

                ZaposleniUAgenciji noviZaposleni = new ZaposleniUAgenciji(0, ime, prezime, telefon, email);
                zaposleniUAgencijiDAO.addZaposleni(noviZaposleni); 
                ucitajPodatkeOZaposlenima();

                JOptionPane.showMessageDialog(dodajDialog, "Zaposleni uspješno dodat!", "Uspešno", JOptionPane.INFORMATION_MESSAGE);
                dodajDialog.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dodajDialog, "Greška pri dodavanju zaposlenog: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
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

    private void prikaziFormuZaIzmjenuZaposlenog() {
        int selectedRow = tabelaZaposleni.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Morate izabrati zaposlenog za izmenu!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idZaposleni = (int) modelTabeleZaposleni.getValueAt(selectedRow, 0);
        try {
            ZaposleniUAgenciji zaposleni = zaposleniUAgencijiDAO.getZaposleniById(idZaposleni);
            if (zaposleni == null) {
                JOptionPane.showMessageDialog(this, "Zaposleni nije pronađen!", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JDialog izmjeniDialog = new JDialog(this, "Izmjena zaposlenog", true);
            izmjeniDialog.setLayout(new GridLayout(0, 2, 5, 5));
            ((JComponent) izmjeniDialog.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JTextField imeField = new JTextField(zaposleni.getIme(), 20);
            JTextField prezimeField = new JTextField(zaposleni.getPrezime(), 20);
            JTextField telefonField = new JTextField(zaposleni.getTelefon(), 20);
            JTextField emailField = new JTextField(zaposleni.getEmail(), 20);

            izmjeniDialog.add(new JLabel("Ime:"));
            izmjeniDialog.add(imeField);
            izmjeniDialog.add(new JLabel("Prezime:"));
            izmjeniDialog.add(prezimeField);
            izmjeniDialog.add(new JLabel("Telefon:"));
            izmjeniDialog.add(telefonField);
            izmjeniDialog.add(new JLabel("Email:"));
            izmjeniDialog.add(emailField);

            JButton sacuvajButton = new JButton("Sačuvaj");
            JButton odustaniButton = new JButton("Odustani");

            sacuvajButton.addActionListener(e -> {
                if (imeField.getText().trim().isEmpty() || prezimeField.getText().trim().isEmpty() ||
                    telefonField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(izmjeniDialog, "Morate popuniti sva polja!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    String ime = imeField.getText().trim();
                    String prezime = prezimeField.getText().trim();
                    String telefon = telefonField.getText().trim();
                    String email = emailField.getText().trim();

                    ZaposleniUAgenciji izmijenjeniZaposleni = new ZaposleniUAgenciji(idZaposleni, ime, prezime, telefon, email);
                    zaposleniUAgencijiDAO.updateZaposleni(izmijenjeniZaposleni);
                    ucitajPodatkeOZaposlenima();

                    JOptionPane.showMessageDialog(izmjeniDialog, "Zaposleni uspješno izmenjen!", "Uspješno", JOptionPane.INFORMATION_MESSAGE);
                    izmjeniDialog.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(izmjeniDialog, "Greška pri izmjeni zaposlenog: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            });

            odustaniButton.addActionListener(e -> izmjeniDialog.dispose());

            izmjeniDialog.add(sacuvajButton);
            izmjeniDialog.add(odustaniButton);

            izmjeniDialog.pack();
            izmjeniDialog.setLocationRelativeTo(this);
            izmjeniDialog.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju zaposlenog: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void obrisiZaposlenog() {
        int selectedRow = tabelaZaposleni.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Morate izabrati zaposlenog za brisanje!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idZaposleni = (int) modelTabeleZaposleni.getValueAt(selectedRow, 0);
        int potvrda = JOptionPane.showConfirmDialog(this, "Da li ste sigurni da želite da obrišete ovog zaposlenog?", "Potvrda brisanja", JOptionPane.YES_NO_OPTION);
        if (potvrda == JOptionPane.YES_OPTION) {
            try {
                zaposleniUAgencijiDAO.deleteZaposleni(idZaposleni);
                ucitajPodatkeOZaposlenima();
                JOptionPane.showMessageDialog(this, "Zaposleni uspešno obrisan!", "Uspešno", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Greška pri brisanju zaposlenog: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    // Metode za zakupce
    private void ucitajPodatkeOZakupcima() {
        modelTabeleZakupci.setRowCount(0);
        try {
            List<Zakupac> sviZakupci = zakupacDAO.getAllZakupci();
            for (Zakupac zakupac : sviZakupci) {
                Object[] podaci = {
                    zakupac.getIdZakupac(),
                    zakupac.getIme(),
                    zakupac.getPrezime(),
                    zakupac.getTelefon(),
                    zakupac.getEmail()
                };
                modelTabeleZakupci.addRow(podaci);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju zakupaca: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void prikaziFormuZaDodavanjeZakupca() {
        JDialog dodajDialog = new JDialog(this, "Dodavanje zakupca", true);
        dodajDialog.setLayout(new GridLayout(0, 2, 5, 5));
        ((JComponent) dodajDialog.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField imeField = new JTextField(20);
        JTextField prezimeField = new JTextField(20);
        JTextField telefonField = new JTextField(20);
        JTextField emailField = new JTextField(20);

        dodajDialog.add(new JLabel("Ime:"));
        dodajDialog.add(imeField);
        dodajDialog.add(new JLabel("Prezime:"));
        dodajDialog.add(prezimeField);
        dodajDialog.add(new JLabel("Telefon:"));
        dodajDialog.add(telefonField);
        dodajDialog.add(new JLabel("Email:"));
        dodajDialog.add(emailField);

        JButton sacuvajButton = new JButton("Sačuvaj");
        JButton odustaniButton = new JButton("Odustani");

        sacuvajButton.addActionListener(e -> {
            if (imeField.getText().trim().isEmpty() || prezimeField.getText().trim().isEmpty() ||
                telefonField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dodajDialog, "Morate popuniti sva polja!", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String ime = imeField.getText().trim();
                String prezime = prezimeField.getText().trim();
                String telefon = telefonField.getText().trim();
                String email = emailField.getText().trim();

                Zakupac noviZakupac = new Zakupac(0, ime, prezime, telefon, email);
                zakupacDAO.createZakupac(noviZakupac);
                ucitajPodatkeOZakupcima();

                JOptionPane.showMessageDialog(dodajDialog, "Zakupac uspješno dodat!", "Uspešno", JOptionPane.INFORMATION_MESSAGE);
                dodajDialog.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dodajDialog, "Greška pri dodavanju zakupca: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
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

    private void prikaziFormuZaIzmjenuZakupca() {
        int selectedRow = tabelaZakupci.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Morate izabrati zakupca za izmjenu!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idZakupac = (int) modelTabeleZakupci.getValueAt(selectedRow, 0);
        try {
            Zakupac zakupac = zakupacDAO.getZakupacById(idZakupac);
            if (zakupac == null) {
                JOptionPane.showMessageDialog(this, "Zakupac nije pronađen!", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JDialog izmjeniDialog = new JDialog(this, "Izmjena zakupca", true);
            izmjeniDialog.setLayout(new GridLayout(0, 2, 5, 5));
            ((JComponent) izmjeniDialog.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JTextField imeField = new JTextField(zakupac.getIme(), 20);
            JTextField prezimeField = new JTextField(zakupac.getPrezime(), 20);
            JTextField telefonField = new JTextField(zakupac.getTelefon(), 20);
            JTextField emailField = new JTextField(zakupac.getEmail(), 20);

            izmjeniDialog.add(new JLabel("Ime:"));
            izmjeniDialog.add(imeField);
            izmjeniDialog.add(new JLabel("Prezime:"));
            izmjeniDialog.add(prezimeField);
            izmjeniDialog.add(new JLabel("Telefon:"));
            izmjeniDialog.add(telefonField);
            izmjeniDialog.add(new JLabel("Email:"));
            izmjeniDialog.add(emailField);

            JButton sacuvajButton = new JButton("Sačuvaj");
            JButton odustaniButton = new JButton("Odustani");

            sacuvajButton.addActionListener(e -> {
                if (imeField.getText().trim().isEmpty() || prezimeField.getText().trim().isEmpty() ||
                    telefonField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(izmjeniDialog, "Morate popuniti sva polja!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    String ime = imeField.getText().trim();
                    String prezime = prezimeField.getText().trim();
                    String telefon = telefonField.getText().trim();
                    String email = emailField.getText().trim();

                    Zakupac izmijenjeniZakupac = new Zakupac(idZakupac, ime, prezime, telefon, email);
                    zakupacDAO.updateZakupac(izmijenjeniZakupac);
                    ucitajPodatkeOZakupcima();

                    JOptionPane.showMessageDialog(izmjeniDialog, "Zakupac uspešno izmijenjen!", "Uspješno", JOptionPane.INFORMATION_MESSAGE);
                    izmjeniDialog.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(izmjeniDialog, "Greška pri izmjeni zakupca: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            });

            odustaniButton.addActionListener(e -> izmjeniDialog.dispose());

            izmjeniDialog.add(sacuvajButton);
            izmjeniDialog.add(odustaniButton);

            izmjeniDialog.pack();
            izmjeniDialog.setLocationRelativeTo(this);
            izmjeniDialog.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju zakupca: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void obrisiZakupca() {
        int selectedRow = tabelaZakupci.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Morate izabrati zakupca za brisanje!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idZakupac = (int) modelTabeleZakupci.getValueAt(selectedRow, 0);
        int potvrda = JOptionPane.showConfirmDialog(this, "Da li ste sigurni da želite da obrišete ovog zakupca?", "Potvrda brisanja", JOptionPane.YES_NO_OPTION);
        if (potvrda == JOptionPane.YES_OPTION) {
            try {
                zakupacDAO.deleteZakupac(idZakupac);
                ucitajPodatkeOZakupcima();
                JOptionPane.showMessageDialog(this, "Zakupac uspješno obrisan!", "Uspješno", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Greška pri brisanju zakupca: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    // Metode za vlasnike
    private void ucitajPodatkeOVlasnicima() {
        modelTabeleVlasnici.setRowCount(0);
        try {
            List<Vlasnik> sviVlasnici = vlasnikDAO.getAll(); 
            for (Vlasnik vlasnik : sviVlasnici) {
                Object[] podaci = {
                    vlasnik.getIdVlasnik(),
                    vlasnik.getIme(),
                    vlasnik.getPrezime(),
                    vlasnik.getTelefon(),
                    vlasnik.getEmail()
                };
                modelTabeleVlasnici.addRow(podaci);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju vlasnika: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void prikaziFormuZaDodavanjeVlasnika() {
        JDialog dodajDialog = new JDialog(this, "Dodavanje vlasnika", true);
        dodajDialog.setLayout(new GridLayout(0, 2, 5, 5));
        ((JComponent) dodajDialog.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField imeField = new JTextField(20);
        JTextField prezimeField = new JTextField(20);
        JTextField telefonField = new JTextField(20);
        JTextField emailField = new JTextField(20);

        dodajDialog.add(new JLabel("Ime:"));
        dodajDialog.add(imeField);
        dodajDialog.add(new JLabel("Prezime:"));
        dodajDialog.add(prezimeField);
        dodajDialog.add(new JLabel("Telefon:"));
        dodajDialog.add(telefonField);
        dodajDialog.add(new JLabel("Email:"));
        dodajDialog.add(emailField);

        JButton sacuvajButton = new JButton("Sačuvaj");
        JButton odustaniButton = new JButton("Odustani");

        sacuvajButton.addActionListener(e -> {
            if (imeField.getText().trim().isEmpty() || prezimeField.getText().trim().isEmpty() ||
                telefonField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dodajDialog, "Morate popuniti sva polja!", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String ime = imeField.getText().trim();
                String prezime = prezimeField.getText().trim();
                String telefon = telefonField.getText().trim();
                String email = emailField.getText().trim();

                Vlasnik noviVlasnik = new Vlasnik(0, ime, prezime, email, telefon);
                vlasnikDAO.addVlasnik(noviVlasnik); 
                ucitajPodatkeOVlasnicima();

                JOptionPane.showMessageDialog(dodajDialog, "Vlasnik uspješno dodat!", "Uspješno", JOptionPane.INFORMATION_MESSAGE);
                dodajDialog.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dodajDialog, "Greška pri dodavanju vlasnika: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
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

    private void prikaziFormuZaIzmjenuVlasnika() {
        int selectedRow = tabelaVlasnici.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Morate izabrati vlasnika za izmjenu!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idVlasnik = (int) modelTabeleVlasnici.getValueAt(selectedRow, 0);
        try {
            Vlasnik vlasnik = vlasnikDAO.getVlasnikById(idVlasnik);
            if (vlasnik == null) {
                JOptionPane.showMessageDialog(this, "Vlasnik nije pronađen!", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JDialog izmijeniDialog = new JDialog(this, "Izmjena vlasnika", true);
            izmijeniDialog.setLayout(new GridLayout(0, 2, 5, 5));
            ((JComponent) izmijeniDialog.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JTextField imeField = new JTextField(vlasnik.getIme(), 20);
            JTextField prezimeField = new JTextField(vlasnik.getPrezime(), 20);
            JTextField telefonField = new JTextField(vlasnik.getTelefon(), 20);
            JTextField emailField = new JTextField(vlasnik.getEmail(), 20);

            izmijeniDialog.add(new JLabel("Ime:"));
            izmijeniDialog.add(imeField);
            izmijeniDialog.add(new JLabel("Prezime:"));
            izmijeniDialog.add(prezimeField);
            izmijeniDialog.add(new JLabel("Telefon:"));
            izmijeniDialog.add(telefonField);
            izmijeniDialog.add(new JLabel("Email:"));
            izmijeniDialog.add(emailField);

            JButton sacuvajButton = new JButton("Sačuvaj");
            JButton odustaniButton = new JButton("Odustani");

            sacuvajButton.addActionListener(e -> {
                if (imeField.getText().trim().isEmpty() || prezimeField.getText().trim().isEmpty() ||
                    telefonField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(izmijeniDialog, "Morate popuniti sva polja!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    String ime = imeField.getText().trim();
                    String prezime = prezimeField.getText().trim();
                    String telefon = telefonField.getText().trim();
                    String email = emailField.getText().trim();

                    Vlasnik izmijenjeniVlasnik = new Vlasnik(idVlasnik, ime, prezime, email, telefon);
                    vlasnikDAO.updateVlasnik(izmijenjeniVlasnik);
                    ucitajPodatkeOVlasnicima();

                    JOptionPane.showMessageDialog(izmijeniDialog, "Vlasnik uspješno izmijenjen!", "Uspješno", JOptionPane.INFORMATION_MESSAGE);
                    izmijeniDialog.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(izmijeniDialog, "Greška pri izmjeni vlasnika: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju vlasnika: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void obrisiVlasnika() {
        int selectedRow = tabelaVlasnici.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Morate izabrati vlasnika za brisanje!", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idVlasnik = (int) modelTabeleVlasnici.getValueAt(selectedRow, 0);
        int potvrda = JOptionPane.showConfirmDialog(this, "Da li ste sigurni da želite da obrišete ovog vlasnika?",
                "Potvrda brisanja", JOptionPane.YES_NO_OPTION);
        if (potvrda == JOptionPane.YES_OPTION) {
            try {
                vlasnikDAO.deleteVlasnik(idVlasnik);
                ucitajPodatkeOVlasnicima();
                JOptionPane.showMessageDialog(this, "Vlasnik uspješno obrisan!", "Uspešno", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Greška pri brisanju vlasnika: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProzorKorisnici());
    }
}