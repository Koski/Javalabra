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
import java.awt.HeadlessException;
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
import javax.swing.BorderFactory;
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
    private JTextArea ilmoituskentta;
    private JTextArea laivatilasto;
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
        this("");
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
        uusiIkkuna();
    }

    public void luoAlkuikkuna(Container container) {
        BorderLayout layout = new BorderLayout();

        nimipaneeli = new JPanel();
        nimipaneeli.setLayout(layout);

        nimikentta = new JTextField();
        JTextArea ohjeet = new JTextArea(getTervehdysJaOhjeet());
        asetaKenttiinFonttiJaEditable(ohjeet);

        JButton pelinappi = new JButton("Pelaamaan!");
        asetaPelinapinToiminnallisuus(pelinappi);

        asetaKentatPaneeliin(pelinappi, ohjeet);

        container.add(nimipaneeli);
    }

    public void luoPelialusta(Container container) {

        frame.remove(nimipaneeli);
        GridLayout layout = new GridLayout(lauta.getKorkeus() + 1, lauta.getLeveys() + 1, 2, 2);
        KlikkaustenKuuntelija kuuntelija = new KlikkaustenKuuntelija(this);
        JPanel paneeli = new JPanel();
        ilmoituskentta = new JTextArea();
        laivatilasto = new JTextArea();
        JButton tilastot = new JButton("Top 5");

        paneeli.setLayout(layout);

        teeRuudut(paneeli, kuuntelija);

        satunnaistenLaivojenAsetus();

        ilmoituskentta.setEditable(false);
        laivatilasto.setEditable(false);
        laivatilasto.setText(laivat());

        setTilastojenTarkastelu(tilastot);

        asetaTekstikenttiinFontti(tilastot);

        lisaaKentatContaineriin(container, tilastot);

        container.add(paneeli);
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
        asetaLaivatilasto(laivat());

    }

    public void asetailmoituskentta(String teksti) {
        this.ilmoituskentta.setText(teksti);
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

    public void lataaTiedostosta() throws FileNotFoundException, IOException {
        tilasto.clear();
        if (!tiedosto.exists()) {
            tiedosto.createNewFile();
        }
        lukija = new Scanner(this.tiedosto);
        try {
            lukija = new Scanner(this.tiedosto);
        } catch (Exception e) {
            System.out.println("Tiedoston lukeminen epäonnistui " + e.getMessage());
            return;
        }
        while (lukija.hasNextLine()) {
            Pelaaja apuPelaaja = new Pelaaja("apumuuttuja");
            String rivi = lukija.nextLine();
            String[] osat = rivi.split(" ");
            apuPelaaja.setNimi(osat[0]);
            apuPelaaja.setPisteet(Integer.parseInt(osat[1]));
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
        return "\n\n\nTervetuloa pelaaman laivanupotusta!\n\n"
                + "Syötä nimesi tai nickisi ylläolevaan kenttään "
                + "(oltava 1-16 merkkiä).\n"
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

    private String topViisi() throws FileNotFoundException, IOException {
        if (!tiedosto.exists()) {
            tiedosto.createNewFile();
        }
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

    private static void ilmoita(String viesti, String otsikko) {
        JOptionPane.showMessageDialog(null, viesti, otsikko, JOptionPane.PLAIN_MESSAGE);
    }

    private void uusiIkkuna() throws HeadlessException {
        frame = new JFrame("Laivanupotus");
        frame.setPreferredSize(new Dimension(1000, 800));
        luoAlkuikkuna(frame);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
    }

    private void setUudenPelinLuominen(JButton uusiPeli) {
        uusiPeli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                lauta = new Pelilauta();
                uusiIkkuna();
            }
        });
    }

    private void setTilastojenTarkastelu(JButton tilastot) {
        tilastot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jarjestaPisteidenMukaan();
                try {
                    ilmoita(topViisi(), "Top 5");
                } catch (IOException ex) {
                    Logger.getLogger(Kayttoliittyma.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void satunnaistenLaivojenAsetus() {
        lauta.laivanAsetus(lauta.luoSatunnainenLaiva(5, "Emoalus"));
        lauta.laivanAsetus(lauta.luoSatunnainenLaiva(3, "Miinalautta"));
        lauta.laivanAsetus(lauta.luoSatunnainenLaiva(3, "Miinalautta"));
        lauta.laivanAsetus(lauta.luoSatunnainenLaiva(4, "Ohjusvene"));
        lauta.laivanAsetus(lauta.luoSatunnainenLaiva(2, "Tiedustelija"));
    }

    private void asetaAakkonen(int j, JPanel paneeli) {
        JTextArea area = new JTextArea((char) ('A' - 1 + j) + "");
        area.setEditable(false);
        area.setFont(boldi);
        paneeli.add(area);
    }

    private void asetaNumero(int i, JPanel paneeli) {
        JTextArea area = new JTextArea(i + "");
        area.setEditable(false);
        area.setFont(boldi);
        paneeli.add(area);
    }

    private void asetaPeliruutu(int i, int j, JPanel paneeli, KlikkaustenKuuntelija kuuntelija) {
        Nappula nappi = new Nappula(null, false, i - 1, j - 1);
        lauta.getLauta()[i - 1][j - 1] = nappi;
        paneeli.add(nappi);
        nappi.vaihdaSiniseksi();
        nappi.addActionListener(kuuntelija);
    }

    private void teeRuudut(JPanel paneeli, KlikkaustenKuuntelija kuuntelija) {
        for (int i = 0; i <= lauta.getKorkeus(); i++) {
            for (int j = 0; j <= lauta.getLeveys(); j++) {
                if (i == 0 && j == 0) {
                    JButton uusiPeli = new JButton("<html>Uusi<p>peli</html>");
                    uusiPeli.setFont(new Font("s", 10, 12));
                    paneeli.add(uusiPeli);
                    setUudenPelinLuominen(uusiPeli);
                } else if (i == 0) {
                    asetaAakkonen(j, paneeli);
                } else if (j == 0) {
                    asetaNumero(i, paneeli);
                } else {
                    asetaPeliruutu(i, j, paneeli, kuuntelija);
                }
            }
        }
    }

    private void asetaPelinapinToiminnallisuus(JButton nappi) {
        nappi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nimikentta.getText().isEmpty() || nimikentta.getText().length() > 16) {
                    ilmoita("Syötithän nimesi oikein?\nLuithan ohjeet? :)", "Virhe!");
                    frame.dispose();
                    uusiIkkuna();
                } else {
                    pelaaja.setNimi(nimikentta.getText().replaceAll(" ", "_"));
                    luoPelialusta(frame);

                }
            }
        });
    }

    private void asetaTekstikenttiinFontti(JButton tilastot) {
        tilastot.setFont(boldi);
        laivatilasto.setFont(boldi);
        ilmoituskentta.setFont(boldi);
    }

    private void lisaaKentatContaineriin(Container container, JButton tilastot) {
        container.add(tilastot, BorderLayout.NORTH);
        container.add(ilmoituskentta, BorderLayout.SOUTH);
        container.add(laivatilasto, BorderLayout.EAST);
    }

    private void asetaKentatPaneeliin(JButton pelinappi, JTextArea ohjeet) {
        nimipaneeli.add(nimikentta, BorderLayout.NORTH);
        nimipaneeli.add(pelinappi, BorderLayout.SOUTH);
        nimipaneeli.add(ohjeet, BorderLayout.CENTER);
    }

    private void asetaLaivatilasto(String teksti) {
        this.laivatilasto.setText(teksti);
    }

    private String laivat() {
        String palaute = "Jäljellä olevat laivat: \n\n";
        for (Laiva laiva : lauta.getLaivalista()) {
            palaute = palaute + laiva.getTyyppi() + " (" + laiva.getKoko() + ") " + "\n";
        }
        return palaute;
    }

    private void asetaKenttiinFonttiJaEditable(JTextArea ohjeet) {
        nimikentta.setEditable(true);
        nimikentta.setFont(boldi);
        ohjeet.setFont(boldi);
        ohjeet.setEditable(false);
    }
}
