package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import dao.*;
import model.*;

public class ProzorStanovi extends JFrame implements ActionListener {

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

    private JButton dodajButton;
    private JButton izmijeniButton;
    private JButton obrisiButton;

    public ProzorStanovi() {
        setTitle("Upravljanje stanovima za iznajmljivanje");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        // Dodajemo kolonu "Zgrada" u tabelu
        String[] kolone = {"ID", "Površina", "Cijena", "Vlasnik", "Tip iznajmljivanja", "Broj stana", "Sprat", "Zgrada", "Zakupac"};
        modelTabele = new DefaultTableModel(kolone, 0);
        tabelaStanova = new JTable(modelTabele);
        JScrollPane scrollPane = new JScrollPane(tabelaStanova);
        add(scrollPane, BorderLayout.CENTER);

        ucitajPodatkeOStanovima();

        JPanel kontrolniPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dodajButton = new JButton("Dodaj stan");
        izmijeniButton = new JButton("Izmijeni stan");
        obrisiButton = new JButton("Obriši stan");
        JButton upravljanjePretragamaButton = new JButton("Upravljanje pretragama/rezervacijama"); 

        dodajButton.addActionListener(this);
        izmijeniButton.addActionListener(this);
        obrisiButton.addActionListener(this);
        upravljanjePretragamaButton.addActionListener(e -> {
            new ProzorPretragaIRezervacija(); 
            ucitajPodatkeOStanovima(); 
        });

        kontrolniPanel.add(dodajButton);
        kontrolniPanel.add(izmijeniButton);
        kontrolniPanel.add(obrisiButton);
        kontrolniPanel.add(upravljanjePretragamaButton);

        add(kontrolniPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Dodaj stan")) {
            prikaziFormuZaDodavanjeStana();
        } else if (e.getActionCommand().equals("Izmijeni stan")) {
            prikaziFormuZaIzmjenuStana();
        } else if (e.getActionCommand().equals("Obriši stan")) {
            obrisiStan();
        }
    }

    private void ucitajPodatkeOStanovima() {
        modelTabele.setRowCount(0);
        try {
            List<Stan> sviStanovi = stanDAO.getAllStani();
            for (Stan stan : sviStanovi) {
                Vlasnik vlasnik = vlasnikDAO.getVlasnikById(stan.getVlasnikId());
                TipStanaIznajmljivanje tip = tipStanaIznajmljivanjeDAO.getTipStanaIznajmljivanjeById(stan.getTipStanaIznajmljivanjeId());
                Zgrada zgrada = zgradaDAO.getZgradaById(stan.getZgradaId());
                
             
                PretragaIRezervacija pretraga = null;
                if (stan.getPretragaRezervacijaId() != null) {
                    pretraga = pretragaIRezervacijaDAO.getPretragaIRezervacijaById(stan.getPretragaRezervacijaId());
                }
                
                Zakupac zakupac = null;
                if (pretraga != null) {
                    zakupac = zakupacDAO.getZakupacById(pretraga.getZakupacId());
                }

              
                String zgradaInfo = "Nepoznato";
                if (zgrada != null) {
                    AdresaZgrade adresa = adresaZgradeDAO.getAdresaZgradeById(zgrada.getAdresaId());
                    if (adresa != null) {
                        Grad grad = gradDAO.getGradById(adresa.getGradId());
                        if (grad != null) {
                            Drzava drzava = drzavaDAO.getDrzavaById(grad.getDrzavaId());
                            zgradaInfo = adresa.getUlica() + " " + adresa.getBroj() + ", " + grad.getNaziv() + ", " + (drzava != null ? drzava.getNaziv() : "Nepoznato");
                        }
                    }
                }

                Object[] podaci = {
                        stan.getIdStan(),
                        stan.getPovrsina(),
                        stan.getCijena(),
                        (vlasnik != null ? vlasnik.getIme() + " " + vlasnik.getPrezime() : "Nepoznato"),
                        (tip != null ? tip.getOpis() : "Nepoznato"),
                        stan.getBrojStana(),
                        stan.getSprat(),
                        zgradaInfo,
                        (zakupac != null ? zakupac.getIme() + " " + zakupac.getPrezime() : "Nema zakupca") 
                };
                modelTabele.addRow(podaci);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju stanova iz baze: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void prikaziFormuZaDodavanjeStana() {
        JDialog dodajDialog = new JDialog(this, "Dodavanje novog stana", true);
        dodajDialog.setLayout(new GridLayout(0, 2, 5, 5));
        ((JComponent) dodajDialog.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField povrsinaField = new JTextField(10);
        JTextField cijenaField = new JTextField(10);
        JTextField brojStanaField = new JTextField(5);
        JTextField spratField = new JTextField(5);
        JTextField vrstaUslugeIznajmljivanjeField = new JTextField(20);
        JComboBox<Vlasnik> vlasnikCombo = new JComboBox<>();
        JComboBox<TipStanaIznajmljivanje> tipStanaCombo = new JComboBox<>();
        JComboBox<Zgrada> zgradaCombo = new JComboBox<>();
        JComboBox<PretragaIRezervacija> pretragaCombo = new JComboBox<>(); 

        try {
          
            List<Vlasnik> sviVlasnici = vlasnikDAO.getAll();
            vlasnikCombo.removeAllItems();
            for (Vlasnik vlasnik : sviVlasnici) {
                vlasnikCombo.addItem(vlasnik);
            }

            
            List<TipStanaIznajmljivanje> sviTipoviStanova = tipStanaIznajmljivanjeDAO.getAllTipoviStana();
            tipStanaCombo.removeAllItems();
            for (TipStanaIznajmljivanje tip : sviTipoviStanova) {
                tipStanaCombo.addItem(tip);
            }

           
            List<Zgrada> sveZgrade = zgradaDAO.getAllZgrade();
            zgradaCombo.removeAllItems();
            for (Zgrada zgrada : sveZgrade) {
                AdresaZgrade adresa = adresaZgradeDAO.getAdresaZgradeById(zgrada.getAdresaId());
                String zgradaInfo = "Nepoznato";
                if (adresa != null) {
                    Grad grad = gradDAO.getGradById(adresa.getGradId());
                    if (grad != null) {
                        Drzava drzava = drzavaDAO.getDrzavaById(grad.getDrzavaId());
                        zgradaInfo = adresa.getUlica() + " " + adresa.getBroj() + ", " + grad.getNaziv() + ", " + (drzava != null ? drzava.getNaziv() : "Nepoznato");
                    }
                }
                zgrada.setZgradaInfo(zgradaInfo);
                zgradaCombo.addItem(zgrada);
            }

         
            List<PretragaIRezervacija> svePretrage = pretragaIRezervacijaDAO.getAllPretrage();
            pretragaCombo.removeAllItems();
            pretragaCombo.addItem(null); 
            for (PretragaIRezervacija pretraga : svePretrage) {
                pretragaCombo.addItem(pretraga);
            }
            pretragaCombo.setSelectedItem(null); 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(dodajDialog, "Greška pri učitavanju vlasnika, tipova stanova, zgrada ili pretraga: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dodajDialog.add(new JLabel("Površina:"));
        dodajDialog.add(povrsinaField);
        dodajDialog.add(new JLabel("Cijena:"));
        dodajDialog.add(cijenaField);
        dodajDialog.add(new JLabel("Broj stana:"));
        dodajDialog.add(brojStanaField);
        dodajDialog.add(new JLabel("Sprat:"));
        dodajDialog.add(spratField);
        dodajDialog.add(new JLabel("Vrsta usluge iznajmljivanje:"));
        dodajDialog.add(vrstaUslugeIznajmljivanjeField);
        dodajDialog.add(new JLabel("Vlasnik:"));
        dodajDialog.add(vlasnikCombo);
        dodajDialog.add(new JLabel("Tip iznajmljivanje:"));
        dodajDialog.add(tipStanaCombo);
        dodajDialog.add(new JLabel("Zgrada:"));
        dodajDialog.add(zgradaCombo);
        dodajDialog.add(new JLabel("Pretraga/Rezervacija:"));
        dodajDialog.add(pretragaCombo);

        JButton sacuvajButton = new JButton("Sačuvaj");
        JButton odustaniButton = new JButton("Odustani");

        sacuvajButton.addActionListener(e -> {
            if (povrsinaField.getText().trim().isEmpty() || cijenaField.getText().trim().isEmpty() ||
                brojStanaField.getText().trim().isEmpty() || spratField.getText().trim().isEmpty() ||
                vrstaUslugeIznajmljivanjeField.getText().trim().isEmpty() ||
                vlasnikCombo.getSelectedItem() == null || tipStanaCombo.getSelectedItem() == null ||
                zgradaCombo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(dodajDialog, "Morate popuniti sva obavezna polja!", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int povrsina = Integer.parseInt(povrsinaField.getText().trim());
                double cijena = Double.parseDouble(cijenaField.getText().trim());
                int brojStana = Integer.parseInt(brojStanaField.getText().trim());
                int sprat = Integer.parseInt(spratField.getText().trim());
                String vrstaUslugeIznajmljivanje = vrstaUslugeIznajmljivanjeField.getText().trim();
                Vlasnik vlasnik = (Vlasnik) vlasnikCombo.getSelectedItem();
                TipStanaIznajmljivanje tip = (TipStanaIznajmljivanje) tipStanaCombo.getSelectedItem();
                Zgrada zgrada = (Zgrada) zgradaCombo.getSelectedItem();
                PretragaIRezervacija pretraga = (PretragaIRezervacija) pretragaCombo.getSelectedItem();

               
                Stan noviStan = new Stan(
                    0, 
                    povrsina,
                    cijena,
                    vlasnik.getIdVlasnik(),
                    zgrada.getIdZgrade(),
                    sprat,
                    brojStana,
                    null, 
                    vrstaUslugeIznajmljivanje,
                    0.0, 
                    pretraga != null ? pretraga.getIdPretragaIRezervacija() : null, 
                    0, 
                    tip.getIdTipStana()
                );

                stanDAO.createStan(noviStan);
                ucitajPodatkeOStanovima();

                JOptionPane.showMessageDialog(dodajDialog, "Stan je uspješno dodat!", "Uspješno", JOptionPane.INFORMATION_MESSAGE);
                dodajDialog.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dodajDialog, "Greška pri dodavanju stana: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });

        odustaniButton.addActionListener(e -> dodajDialog.dispose());

        dodajDialog.add(sacuvajButton);
        dodajDialog.add(odustaniButton);

        dodajDialog.pack();
        dodajDialog.setLocationRelativeTo(this);
        dodajDialog.setVisible(true);
    }
    private void prikaziFormuZaIzmjenuStana() {
        int selectedRow = tabelaStanova.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Molimo izaberite stan za izmjenu.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idStan = (int) modelTabele.getValueAt(selectedRow, 0);
        try {
            Stan stan = stanDAO.getStanById(idStan);
            if (stan == null) {
                JOptionPane.showMessageDialog(this, "Stan nije pronađen.", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }

           
            Vlasnik vlasnik = vlasnikDAO.getVlasnikById(stan.getVlasnikId());
            Zgrada zgrada = zgradaDAO.getZgradaById(stan.getZgradaId());
            TipStanaIznajmljivanje tipIznajmljivanje = tipStanaIznajmljivanjeDAO.getTipStanaIznajmljivanjeById(stan.getTipStanaIznajmljivanjeId());
            
         
            PretragaIRezervacija pretraga = null;
            if (stan.getPretragaRezervacijaId() != null) {
                pretraga = pretragaIRezervacijaDAO.getPretragaIRezervacijaById(stan.getPretragaRezervacijaId());
            }

           
            JDialog dialog = new JDialog(this, "Izmjena stana", true);
            dialog.setLayout(new GridLayout(0, 2, 10, 10)); 
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(this); 

           
            dialog.add(new JLabel("Površina (m²):"));
            JTextField povrsinaField = new JTextField(String.valueOf(stan.getPovrsina()));
            dialog.add(povrsinaField);

            dialog.add(new JLabel("Cijena (po danu):"));
            JTextField cijenaField = new JTextField(String.valueOf(stan.getCijena()));
            dialog.add(cijenaField);

            dialog.add(new JLabel("Sprat:"));
            JTextField spratField = new JTextField(String.valueOf(stan.getSprat()));
            dialog.add(spratField);

            dialog.add(new JLabel("Broj stana:"));
            JTextField brojStanaField = new JTextField(String.valueOf(stan.getBrojStana()));
            dialog.add(brojStanaField);

            
            dialog.add(new JLabel("Vlasnik:"));
            JLabel vlasnikLabel = new JLabel(vlasnik != null ? vlasnik.getIme() + " " + vlasnik.getPrezime() : "Nepoznato");
            dialog.add(vlasnikLabel);

           
            dialog.add(new JLabel("Zgrada:"));
            JLabel zgradaLabel = new JLabel(zgrada != null ? "Zgrada ID: " + zgrada.getIdZgrade() : "Nepoznato");
            dialog.add(zgradaLabel);

           
            JButton potvrdiButton = new JButton("Potvrdi");
            JButton otkaziButton = new JButton("Otkaži");

         
            potvrdiButton.addActionListener(e -> {
                try {
                  
                    stan.setPovrsina(Integer.parseInt(povrsinaField.getText()));
                    stan.setCijena(Double.parseDouble(cijenaField.getText()));
                    stan.setSprat(Integer.parseInt(spratField.getText()));
                    stan.setBrojStana(Integer.parseInt(brojStanaField.getText()));
                    stan.setTipStanaProdajaId(null); 

                    
                    stanDAO.updateStan(stan);

                 
                    ucitajPodatkeOStanovima();

                  
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, "Stan uspješno izmijenjen!", "Uspjeh", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Molimo unesite ispravne numeričke vrijednosti.", "Greška", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialog, "Greška pri ažuriranju stana: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            });

           
            otkaziButton.addActionListener(e -> dialog.dispose());

           
            dialog.add(potvrdiButton);
            dialog.add(otkaziButton);

         
            dialog.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Greška pri dohvatanju podataka: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
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
        int potvrda = JOptionPane.showConfirmDialog(this, "Da li ste sigurni da želite da obrišete ovaj stan?", "Potvrda brisanja", JOptionPane.YES_NO_OPTION);
        if (potvrda == JOptionPane.YES_OPTION) {
            try {
                stanDAO.deleteStan(idStan);
                ucitajPodatkeOStanovima();
                JOptionPane.showMessageDialog(this, "Stan je uspješno obrisan!", "Uspješno", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Greška pri brisanju stana: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProzorStanovi::new);
    }
}