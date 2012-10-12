/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Kayttoliittyma;

import Pelilogiikka.Nappula;
import Pelilogiikka.Pelilauta;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author anttkari
 */
public class KlikkaustenKuuntelija implements ActionListener {

    private Kayttoliittyma kayttoliittyma;

    public KlikkaustenKuuntelija(Kayttoliittyma kayttoliittyma) {
        this.kayttoliittyma = kayttoliittyma;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (peliLoppui()) {
            return;
        }

        kayttoliittyma.asetailmoituskentta("");
        Nappula nappi = (Nappula) e.getSource();
        nappi.setOsuttu(true);
        kayttoliittyma.getLauta().tulitus(nappi);
        kayttoliittyma.paivita();

        if (laivaanOsuiMuttaPeliEiLoppunut(nappi)) {
            kayttoliittyma.asetailmoituskentta(nappi.getLaiva().getTyyppi() + " upposi");
        } else if (peliLoppui()) {
            kayttoliittyma.asetailmoituskentta(nappi.getLaiva().getTyyppi()
                    + " upposi ja peli loppui! Pisteesi: " + kayttoliittyma.getLauta().pisteet());
            try {
                kirjaaUusiPelaaja();
            } catch (IOException ex) {
                Logger.getLogger(KlikkaustenKuuntelija.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private boolean laivaanOsuiMuttaPeliEiLoppunut(Nappula nappi) {
        return nappi.getLaiva() != null && nappi.getLaiva().onkoUponnut() && !peliLoppui();
    }

    private void kirjaaUusiPelaaja() throws FileNotFoundException, IOException {
        kayttoliittyma.lataaTiedostosta();
        kayttoliittyma.lisaaNimiJaPisteet();
        kayttoliittyma.kirjoitaTiedostoon();
    }

    private boolean peliLoppui() {
        return kayttoliittyma.getLauta().loppuikoPeli();
    }
}
