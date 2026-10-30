package gui;

import javax.swing.*;
import java.awt.GridLayout;

public class GlavniMeni extends JFrame {

    public GlavniMeni() {
        setTitle("Aplikacija za iznajmljivanje stanova");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        JButton stanoviButton = new JButton("Stanovi");
        JButton korisniciButton = new JButton("Korisnici");
      

        stanoviButton.addActionListener(e -> new ProzorStanovi());
        korisniciButton.addActionListener(e -> new ProzorKorisnici());
     

        add(stanoviButton);
        add(korisniciButton);
  

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProzorIzborUloge::new); 
    }
}