/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Pelilogiikka;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
/**
 * Nappulat sijaitsevat pelilaudalla ja kuvaavat yhtä kohtaa. Nappulalla on omat
 * koordinaatit, tieto siitä onko nappulaan ammuttu ja viite siihen laivaan, joka
 * on kyseisessä koordinaatissa, jos ylipäätään on.
 * @author anttkari
 */
public class Nappula extends JButton {
    /**
     * kuva osutusta laivan kohdasta, räjähdys.
     */
    private ImageIcon osumaKuva = new ImageIcon("/cs/fs/home/anttkari/NetBeansProjects/Laivanupotus/src/Kuvat/wham.jpg");
    /**
     * kuva ohiammutusta laivan kohdasta, kuvana on ruksi.
     */
    private ImageIcon ohikuva = new ImageIcon("/cs/fs/home/anttkari/NetBeansProjects/Laivanupotus/src/Kuvat/ruksi.jpeg");
    /**
     * Väri tehty kuvaamaan kirkasta vettä.
     */
    private Color vaaleanSininen = new Color(51,204,255);
    /**
     * Nappulalla on viite siihen laivaan, joka sijaitsee nappulan
     * kohdassa, jos sijaitsee.
     */
    private Laiva laiva;
    /**
     * Pitää kirjaa siitä, onko nappulaan vielä ammuttu (eli osuttu), riippumatta
     * siitä onko kohdassa laivaa vai ei.
     */
    private boolean osuttu;
    /**
     * Nappulan sijaintia kuvaava X-koordinaatti.
     */
    private int xKoord;
    /**
     * Nappulan sijaintia kuvaava Y-koordinaatti.
     */
    private int yKoord;
    /**
     * Luo nappulan, jolla on kayttajan antamat arvot.
     * @param laiva
     * @param osuttu
     * @param xKoord
     * @param yKoord 
     */
    public Nappula(Laiva laiva, boolean osuttu, int xKoord, int yKoord) {
        this.laiva = laiva;
        this.osuttu = osuttu;
        this.xKoord = xKoord;
        this.yKoord = yKoord;

    }

    public void setxKoord(int xKoord) {
        this.xKoord = xKoord;
    }

    public void setyKoord(int yKoord) {
        this.yKoord = yKoord;
    }

    public int getxKoord() {
        return xKoord;
    }

    public int getyKoord() {
        return yKoord;
    }

    public void setLaiva(Laiva laiva) {
        this.laiva = laiva;
    }

    public void setOsuttu(boolean osuttu) {
        this.osuttu = osuttu;
    }

    public Laiva getLaiva() {
        return laiva;
    }

    public boolean isOsuttu() {
        return osuttu;
    }

    /**
     * Vaihtaa nappulan taustan vaaleansiniseksi (vesi).
     */
    public void vaihdaSiniseksi() {
        setBackground(vaaleanSininen);
    }

    /**
     * Vaihtaa nappulan kuvan räjähdykseksi (kuvaa osuttua laivaa).
     */
    public void vaihdaOsutuksiLaivaksi() {
        if (isOsuttu() && getLaiva() != null) {
            this.setIcon(osumaKuva);
            this.setDisabledIcon(osumaKuva);
        }
    }

    /**
     * Vaihtaa nappulan kuvan ruksiksi (kuvaa ohiammuttua kohtaa).
     */
    public void vaihdaOhiLaukaukseksi() {
        if (isOsuttu()) {
           this.setIcon(ohikuva);
           this.setDisabledIcon(ohikuva);
        }
    }
}
