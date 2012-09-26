/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Pelilogiikka;

import java.awt.Color;
import javax.swing.JButton;

public class Nappula extends JButton{
    
    Laiva laiva;
    boolean osuttu;
    int xKoord;
    int yKoord;

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
    public void vaihdaSiniseksi(){
        setBackground(Color.CYAN.darker());
    }
    /**
     * Vaihtaa nappulan taustan punaiseksi (kuvaa osuttua laivaa).
     */
    public void vaihdaOsutuksiLaivaksi() {
        if (isOsuttu() && getLaiva()!=null) {
            setBackground(Color.red);
        }
    }
    /**
     * Vaihtaa nappulan taustan mustaksi (kuvaa ohiammuttua kohtaa).
     */
    public void vaihdaOhiLaukaukseksi() {
        if (isOsuttu()) {
            setBackground(Color.BLACK);
        }
    }
}
