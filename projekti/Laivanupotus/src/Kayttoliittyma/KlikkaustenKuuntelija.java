/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Kayttoliittyma;

import Pelilogiikka.Nappula;
import Pelilogiikka.Pelilauta;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        if (!kayttoliittyma.getLauta().loppuikoPeli()) {
            kayttoliittyma.asetaYlaTeksti("");
            Nappula nappi = (Nappula) e.getSource();
            nappi.setOsuttu(true);
            kayttoliittyma.getLauta().tulitus(nappi);
            kayttoliittyma.paivita();
            nappi.setEnabled(false);
            kayttoliittyma.asetaAlaTeksti("Jäljellä olevat laivat:\n" + kayttoliittyma.laivat());
            if (nappi.getLaiva() != null && nappi.getLaiva().onkoUponnut() && !kayttoliittyma.getLauta().loppuikoPeli()) {
                kayttoliittyma.asetaYlaTeksti(nappi.getLaiva().getTyyppi() + " upposi");
            }
            if (kayttoliittyma.getLauta().loppuikoPeli()) {
                kayttoliittyma.asetaYlaTeksti(nappi.getLaiva().getTyyppi()
                        + " upposi ja peli loppui! Pisteesi: " + kayttoliittyma.getLauta().pisteet());
                try {
                    kayttoliittyma.lataaTiedostosta();
                    kayttoliittyma.lisaaNimiJaPisteet();
                    kayttoliittyma.kirjoitaTiedostoon();
                } catch (IOException ex) {
                    Logger.getLogger(KlikkaustenKuuntelija.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
