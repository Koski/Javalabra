/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Kayttoliittyma;

import Pelilogiikka.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 *
 * @author anttkari
 */
public class Kayttoliittyma implements Runnable {

    private JFrame frame;
    private Pelilauta lauta;
    private String viesti;
    private JTextArea ylaTeksti;
    private JTextArea alaTeksti;
    private Font boldi;
//    private JPanel paneeli;

    public Kayttoliittyma() {
        lauta = new Pelilauta();
    }

    public Pelilauta getLauta() {
        return lauta;
    }

//    public JPanel getPaneeli() {
//        return paneeli;
//    }
    public void setViesti(String viesti) {
        this.viesti = viesti;
    }

    public String getViesti() {
        return viesti;
    }

    @Override
    public void run() {
        frame = new JFrame("Laivanupotus");
        frame.setPreferredSize(new Dimension(1000, 800));
        luoKomponentit(frame);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);

    }

    public void luoKomponentit(Container container) {
        GridLayout layout = new GridLayout(lauta.getKorkeus() + 1, lauta.getLeveys() + 1, 2, 2);

        JPanel paneeli = new JPanel();
        paneeli.setLayout(layout);
        KlikkaustenKuuntelija kuuntelija = new KlikkaustenKuuntelija(this);

        for (int i = 0; i <= lauta.getKorkeus(); i++) {
            for (int j = 0; j <= lauta.getLeveys(); j++) {
                if (i == 0 && j == 0) {
                    JLabel label = new JLabel(new ImageIcon("/kuvat/merkkarilippu.jpeg"));
                    paneeli.add(label);
                } else if (i == 0) {
                    JTextArea area = new JTextArea((char) ('A' - 1 + j) + "");
                    area.setEditable(false);
                    paneeli.add(area);
                } else if (j == 0) {
                    JTextArea area = new JTextArea(i + "");
                    paneeli.add(area);
                } else {
                    Nappula nappi = new Nappula(null, false, i - 1, j - 1);
                    lauta.getLauta()[i - 1][j - 1] = nappi;
                    paneeli.add(nappi);
                    nappi.vaihdaSiniseksi();
                    nappi.addActionListener(kuuntelija);
                }
            }
        }

        lauta.laivanAsetus(lauta.luoEmoalus());
        lauta.laivanAsetus(lauta.luoMiinalautta());
        lauta.laivanAsetus(lauta.luoMiinalautta());
        lauta.laivanAsetus(lauta.luoOhjusvene());
        lauta.laivanAsetus(lauta.luoTiedustelija());

        container.add(paneeli);

        boldi = new Font("fontti", 10, 20);
        
        ylaTeksti = new JTextArea();
        ylaTeksti.setFont(boldi);
        ylaTeksti.setSize(100, 100);
        ylaTeksti.setEditable(false);
        alaTeksti = new JTextArea();
        alaTeksti.setEditable(false);
        alaTeksti.setFont(boldi);
        alaTeksti.setText("Jäljellä olevat laivat:\n" + laivat());
        
        container.add(ylaTeksti, BorderLayout.SOUTH);
        container.add(alaTeksti, BorderLayout.EAST);

    }

    public void paivita() {
        for (int i = 0; i < lauta.getKorkeus(); i++) {
            for (int j = 0; j < lauta.getLeveys(); j++) {
                lauta.getLauta()[i][j].vaihdaOhiLaukaukseksi();
                lauta.getLauta()[i][j].vaihdaOsutuksiLaivaksi();
            }
        }

    }

    public void napitPoisKaytosta() {
        JPanel paneeli = (JPanel) frame.getContentPane().getComponentAt(0, 0);
        for (int i = 0; i < lauta.getKorkeus() + 1; i++) {
            for (int j = 0; j < lauta.getLeveys(); j++) {
                paneeli.getComponentAt(i, j).setEnabled(false);
            }
        }
    }

    public void asetaYlaTeksti(String teksti) {
        this.ylaTeksti.setText(teksti);
    }

    public void asetaAlaTeksti(String teksti) {
        this.alaTeksti.setText(teksti);
    }

    public String laivat() {
        String palaute = "\n";
        for (Laiva laiva : lauta.getLaivalista()) {
            palaute = palaute + laiva.getTyyppi() + " (" + laiva.getKoko() + ") " + "\n";
        }
        return palaute;
    }
}
