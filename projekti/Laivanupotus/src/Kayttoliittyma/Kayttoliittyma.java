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
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
    private ImageIcon lippu = new ImageIcon("/cs/fs/home/anttkari/NetBeansProjects/Laivanupotus/src/Kuvat/merggari2.jpeg");
    private Pelaaja pelaaja;
    private File tiedosto;
    private FileWriter kirjoittaja;
    private List<Pelaaja> tilasto;
    private JTextField nimikentta;
    private JPanel nimipaneeli;
    private Scanner lukija;

    public Kayttoliittyma() throws IOException {
        this("pena");
    }

    public Kayttoliittyma(String nimi) {
        lauta = new Pelilauta();
        tiedosto = new File("Rankinglista");
        tilasto = new ArrayList<Pelaaja>();
        pelaaja = new Pelaaja(nimi);
        boldi = new Font("isoTeksti", 10, 20);
    }

    public Pelilauta getLauta() {
        return lauta;
    }

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
        luoAlkuikkuna(frame);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);

    }

    public void luoAlkuikkuna(Container container) {
        BorderLayout layout = new BorderLayout();

        nimipaneeli = new JPanel();
        nimipaneeli.setLayout(layout);

        nimikentta = new JTextField();
        nimikentta.setEditable(true);
        nimikentta.setFont(boldi);

        JTextArea ohjeet = new JTextArea(getTervehdysJaOhjeet());
        ohjeet.setFont(boldi);

        JButton nappi = new JButton("Ok");
        nappi.setMaximumSize(new Dimension(20, 20));

        nimipaneeli.add(nimikentta, BorderLayout.NORTH);
        nimipaneeli.add(nappi, BorderLayout.SOUTH);
        nimipaneeli.add(ohjeet, BorderLayout.CENTER);

        container.add(nimipaneeli);

        nappi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pelaaja.setNimi(nimikentta.getText());
                luoPelialusta(frame);
            }
        });

    }

    public void luoPelialusta(Container container) {

        frame.remove(nimipaneeli);
        GridLayout layout = new GridLayout(lauta.getKorkeus() + 1, lauta.getLeveys() + 1, 2, 2);

        JPanel paneeli = new JPanel();
        paneeli.setLayout(layout);

        KlikkaustenKuuntelija kuuntelija = new KlikkaustenKuuntelija(this);

        for (int i = 0; i <= lauta.getKorkeus(); i++) {
            for (int j = 0; j <= lauta.getLeveys(); j++) {
                if (i == 0 && j == 0) {
                    JLabel label = new JLabel(lippu);
                    paneeli.add(label);
                } else if (i == 0) {
                    JTextArea area = new JTextArea((char) ('A' - 1 + j) + "");
                    area.setEditable(false);
                    area.setFont(boldi);
                    paneeli.add(area);
                } else if (j == 0) {
                    JTextArea area = new JTextArea(i + "");
                    area.setEditable(false);
                    area.setFont(boldi);
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

        JButton tilastot = new JButton("Top 5");
        tilastot.setFont(boldi);

        tilastot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jarjestaPisteidenMukaan();
                try {
                    ilmoita(topViisi());
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Kayttoliittyma.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        ylaTeksti = new JTextArea();
        ylaTeksti.setFont(boldi);
        ylaTeksti.setSize(100, 100);
        ylaTeksti.setEditable(false);

        alaTeksti = new JTextArea();
        alaTeksti.setEditable(false);
        alaTeksti.setFont(boldi);
        alaTeksti.setText("Jäljellä olevat laivat: \n" + laivat());

        container.add(tilastot, BorderLayout.NORTH);
        container.add(ylaTeksti, BorderLayout.SOUTH);
        container.add(alaTeksti, BorderLayout.EAST);

        frame.pack();
        frame.getContentPane().repaint();
    }

    public void paivita() {
        for (int i = 0; i < lauta.getKorkeus(); i++) {
            for (int j = 0; j < lauta.getLeveys(); j++) {
                lauta.getLauta()[i][j].vaihdaOhiLaukaukseksi();
                lauta.getLauta()[i][j].vaihdaOsutuksiLaivaksi();
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

    public void kirjoitaTiedostoon() throws IOException {
        tiedosto.delete();
        this.tiedosto = new File("Rankinglista");

        kirjoittaja = new FileWriter(tiedosto);

        for (Pelaaja yksilo : tilasto) {
            kirjoittaja.write(yksilo.getNimi() + " ");
            kirjoittaja.append(yksilo.getPisteet() + "\n");
        }

        kirjoittaja.close();
    }

    public void lataaTiedostosta() throws FileNotFoundException {
        lukija = new Scanner(tiedosto);

        try {
            lukija = new Scanner(this.tiedosto);
        } catch (Exception e) {
            System.out.println("Tiedoston lukeminen epäonnistui " + e.getMessage());
            return;
        }
        while (lukija.hasNextLine()) {
            Pelaaja apuPelaaja = new Pelaaja("apu");
            String rivi = lukija.nextLine();
            String[] osat = rivi.split(" ");
            int pisteet = Integer.parseInt(osat[1]);
            apuPelaaja.setNimi(osat[0]);
            apuPelaaja.setPisteet(pisteet);
            tilasto.add(apuPelaaja);
        }
        lukija.close();
    }

    public void lisaaNimiJaPisteet() {
        pelaaja.setPisteet(getLauta().pisteet());
        tilasto.add(pelaaja);
        jarjestaPisteidenMukaan();

    }

    private String getTervehdysJaOhjeet() {
        return "Tervetuloa pelaaman laivanupotusta!\n\n"
                + "Syötä nimesi tai nickisi ylläolevaan kenttään.\n"
                + "Tietokone arpoo sinulle 5 laivaa pelikentälle. "
                + "Laivojen sivut eivät voi olla vierekkäin, mutta\n"
                + "kulmat voivat koskettaa. Pyri tuhoamaan laivat "
                + "mahdollisimman vähillä ammuksilla, pisteesi \n"
                + "riippuvat tästä. Kun olet valmis taistoon, "
                + "paina allaolevaa nappia.\n\n"
                + "Onnea peliin!";
    }

    private void jarjestaPisteidenMukaan() {
        for (int i = 0; i < tilasto.size(); i++) {
            for (int j = i + 1; j < tilasto.size(); j++) {
                if (tilasto.get(i).getPisteet() < tilasto.get(j).getPisteet()) {
                    Pelaaja apu = tilasto.get(i);
                    tilasto.set(i, tilasto.get(j));
                    tilasto.set(j, apu);
                }
            }
        }
    }

    private String topViisi() throws FileNotFoundException {
        lukija = new Scanner(tiedosto);

        lukija = new Scanner(this.tiedosto);
        try {
            lukija = new Scanner(this.tiedosto);
        } catch (Exception e) {
            System.out.println("Tiedoston lukeminen epäonnistui " + e.getMessage());
            return "";
        }

        String palautus = "";
        for (int i = 0; i < 5; i++) {
            if (lukija.hasNextLine()) {
                String rivi = lukija.nextLine();
                String[] osat = rivi.split(" ");
                palautus += i + 1 + "." + osat[0] + " " + osat[1] + "\n";
            }
        }
        return palautus;
    }

    private static void ilmoita(String viesti) {
        JOptionPane.showMessageDialog(null,
                viesti, "", JOptionPane.PLAIN_MESSAGE);
    }
}
