package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import dao.ZakupacDAO;
import dao.ZaposleniUAgencijiDAO;
import dao.VlasnikDAO;
import model.Zakupac;
import model.ZaposleniUAgenciji;
import model.Vlasnik;

public class ProzorIzborUloge extends JFrame {

    private JComboBox<String> ulogaCombo;
    private JComboBox<ZaposleniUAgenciji> zaposleniCombo;
    private JComboBox<Zakupac> zakupacCombo;
    private JComboBox<Vlasnik> vlasnikCombo;
    private JPanel korisnikPanel;
    private ZaposleniUAgencijiDAO zaposleniDAO;
    private ZakupacDAO zakupacDAO;
    private VlasnikDAO vlasnikDAO;

    public ProzorIzborUloge() {
        setTitle("Izbor uloge");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1, 10, 10));

        
        zaposleniDAO = new ZaposleniUAgencijiDAO(); 
        zakupacDAO = new ZakupacDAO();
        vlasnikDAO = new VlasnikDAO();

        // Panel za izbor uloge
        JPanel ulogaPanel = new JPanel(new FlowLayout());
        ulogaPanel.add(new JLabel("Izaberite ulogu:"));
        ulogaCombo = new JComboBox<>(new String[]{"Zaposleni", "Zakupac", "Vlasnik"});
        ulogaPanel.add(ulogaCombo);
        add(ulogaPanel);

        // Panel za izbor korisnika
        korisnikPanel = new JPanel(new FlowLayout());
        korisnikPanel.add(new JLabel("Izaberite korisnika:"));
        zaposleniCombo = new JComboBox<>();
        zakupacCombo = new JComboBox<>();
        vlasnikCombo = new JComboBox<>();
        korisnikPanel.add(zaposleniCombo); 
        add(korisnikPanel);

        
        ucitajKorisnike();

       
        ulogaCombo.addActionListener(e -> {
            String uloga = (String) ulogaCombo.getSelectedItem();
            korisnikPanel.removeAll();
            korisnikPanel.add(new JLabel("Izaberite korisnika:"));
            if (uloga.equals("Zaposleni")) {
                korisnikPanel.add(zaposleniCombo);
            } else if (uloga.equals("Zakupac")) {
                korisnikPanel.add(zakupacCombo);
            } else if (uloga.equals("Vlasnik")) {
                korisnikPanel.add(vlasnikCombo);
            }
            korisnikPanel.revalidate();
            korisnikPanel.repaint();
        });

        // Dugme za potvrdu
        JButton potvrdiButton = new JButton("Potvrdi");
        potvrdiButton.addActionListener(e -> otvoriProzorPremaUlozi());
        add(potvrdiButton);

        setVisible(true);
    }

    private void ucitajKorisnike() {
        try {
            // Učitavanje zaposlenih
            List<ZaposleniUAgenciji> zaposleni = zaposleniDAO.getAll(); 
            for (ZaposleniUAgenciji z : zaposleni) {
                zaposleniCombo.addItem(z);
            }

            // Učitavanje zakupaca
            List<Zakupac> zakupci = zakupacDAO.getAllZakupci();
            for (Zakupac z : zakupci) {
                zakupacCombo.addItem(z);
            }

            // Učitavanje vlasnika
            List<Vlasnik> vlasnici = vlasnikDAO.getAll(); 
            for (Vlasnik v : vlasnici) {
                vlasnikCombo.addItem(v);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Greška pri učitavanju korisnika: " + e.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void otvoriProzorPremaUlozi() {
        String uloga = (String) ulogaCombo.getSelectedItem();
        if (uloga.equals("Zaposleni")) {
            ZaposleniUAgenciji izabraniZaposleni = (ZaposleniUAgenciji) zaposleniCombo.getSelectedItem(); 
            if (izabraniZaposleni == null) {
                JOptionPane.showMessageDialog(this, "Morate izabrati zaposlenog!", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new GlavniMeni();
        } else if (uloga.equals("Zakupac")) {
            Zakupac izabraniZakupac = (Zakupac) zakupacCombo.getSelectedItem();
            if (izabraniZakupac == null) {
                JOptionPane.showMessageDialog(this, "Morate izabrati zakupca!", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new ProzorPretragaIRezervacijaZaZakupca(izabraniZakupac); 
        } else if (uloga.equals("Vlasnik")) {
            Vlasnik izabraniVlasnik = (Vlasnik) vlasnikCombo.getSelectedItem();
            if (izabraniVlasnik == null) {
                JOptionPane.showMessageDialog(this, "Morate izabrati vlasnika!", "Greška", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new ProzorStanoviZaVlasnika(izabraniVlasnik); 
        }
        dispose(); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProzorIzborUloge::new);
    }
}