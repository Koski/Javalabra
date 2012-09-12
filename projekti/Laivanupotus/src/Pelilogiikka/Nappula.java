/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Pelilogiikka;

public class Nappula {
    
    boolean laiva;
    boolean osuttu;
    int xKoord;
    int yKoord;

    public Nappula(boolean laiva, boolean osuttu, int xKoord, int yKoord) {
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

    public void setLaiva(boolean laiva) {
        this.laiva = laiva;
    }

    public void setOsuttu(boolean osuttu) {
        this.osuttu = osuttu;
    }

    public boolean isLaiva() {
        return laiva;
    }

    public boolean isOsuttu() {
        return osuttu;
    }
    
}
