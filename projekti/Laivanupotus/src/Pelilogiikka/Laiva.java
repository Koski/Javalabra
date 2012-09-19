package Pelilogiikka;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Laiva {

    String tyyppi;
    int koko;
    int alkuX;
    int alkuY;
    int loppuX;
    int loppuY;
    int osumienLkm;
    Map laivanKoordinaatit;

    public Laiva(String tyyppi, int koko, int alkuX, int alkuY, int loppuX, int loppuY) {
        this.tyyppi = tyyppi;
        this.koko = koko;
        this.alkuX = alkuX;
        this.alkuY = alkuY;
        this.loppuX = loppuX;
        this.loppuY = loppuY;
        this.osumienLkm = 0;    
    }
    
    public void laivaanOsui() {
        if (osumienLkm<koko) {
        osumienLkm++;
        }
    }
    
    public boolean onkoUponnut() {
        if (osumienLkm==koko) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean onkoPystyssa() {
        if (this.alkuY == this.loppuY) {
            return true;
        } else {
            return false;
        }
    }

    public boolean onkoVaaka() {
        if (alkuX == loppuX) {
            return true;
        } else {
            return false;
        }
    }
    public boolean onkoVaakaPituusOikea() {
        if (onkoVaaka() && Math.abs(alkuY-loppuY)+1==koko) {
            return true;
        } else {
            return false;
        }
    }
    public boolean onkoPystyPituusOikea() {
        if (onkoPystyssa() && Math.abs(alkuX-loppuX)+1==koko) {
            return true;
        } else {
            return false;
        }
    }
    public boolean onkoLaivaOikein() {
        if (onkoVaaka() && onkoVaakaPituusOikea() || onkoPystyssa() && onkoPystyPituusOikea()) {
            return true;
        } else {
            return false;
        }
    }
    
    public int getKoko() {
        return koko;
    }

    public String getTyyppi() {
        return tyyppi;
    }

    public int getAlkuX() {
        return alkuX;
    }

    public int getAlkuY() {
        return alkuY;
    }

    public int getLoppuX() {
        return loppuX;
    }

    public int getLoppuY() {
        return loppuY;
    }

    public int getOsumienLkm() {
        return osumienLkm;
    }

    public void setOsumienLkm(int osumienLkm) {
        this.osumienLkm = osumienLkm;
    }
    
}
