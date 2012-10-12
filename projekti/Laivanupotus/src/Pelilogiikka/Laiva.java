package Pelilogiikka;

/**
 * Laivat sijaisevat pelilaudalla ja niitä pyritään
 * tuhoamaan
 * @author anttkari
 */
public class Laiva {
    /**
     * Laivan tyyppi, esim ohjusvene tai miinalautta.
     */
    private String tyyppi;
    /**
     * Laivan koko laudalla, siis montako saraketta laiva peittää.
     */
    private int koko;
    /**
     * Laivan alkupään X-koordinaatti.
     */
    private int alkuX;
    /**
     * laivan alkupään Y-koordinaatti.
     */
    private int alkuY;
    /**
     * laivan loppupään X-koordinaatti.
     */
    private int loppuX;
    /**
     * laivan loppupään Y-koordinaatti.
     */
    private int loppuY;
    /**
     * Pitää kirjaa laivan osumista, voi olla korkeintaan
     * laivan koon verran.
     */
    private int osumienLkm;
    /**
     * Laivan konstruktori, osumien lukumäärä alustuu nollaksi
     * ja muut arvot antaa käyttäjä.
     * @param tyyppi
     * @param koko
     * @param alkuX
     * @param alkuY
     * @param loppuX
     * @param loppuY 
     */
    public Laiva(String tyyppi, int koko, int alkuX, int alkuY, int loppuX, int loppuY) {
        this.tyyppi = tyyppi;
        this.koko = koko;
        this.alkuX = alkuX;
        this.alkuY = alkuY;
        this.loppuX = loppuX;
        this.loppuY = loppuY;
        this.osumienLkm = 0;
    }

    /**
     * Kasvattaa tietyn laivan osumien lukumäärää, jos laiva ei ole vielä
     * uponnut.
     */
    public void laivaanOsui() {
        if (osumienLkm < koko) {
            osumienLkm++;
        }
    }

    /**
     * Kertoo onko kyseinen laiva uponnut. Tarkistus tapahtuu vertaamalla laivan
     * osumien lukumäärää laivan kokoon.
     *
     * @return Palauttee true jos laivan uponnut ja false jos ei ole.
     */
    public boolean onkoUponnut() {
        if (osumienLkm == koko) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Tarkistaa onko laiva pystysuunnassa pelilaudalla.
     *
     * @return Palauttaa true jos laiva on pystyssä ja false jos ei ole.
     */
    public boolean onkoPystyssa() {
        if (this.alkuY == this.loppuY) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Tarkistaa onko laiva vaakasuunnassa pelilaudalla.
     *
     * @return Palauttaa true jos laiva on vaakasuuntainen ja false jos ei ole.
     */
    public boolean onkoVaaka() {
        if (alkuX == loppuX) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Tarkistaa että laiva on joko vaaka- tai pystysuuntainen ja että laivan
     * annetut koordinaatit vastaavat myös annettua pituutta.
     *
     * @return palauttaa true jos laiva on oikein ja false jos laiva on jotenki
     * väärin.
     */
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

    private boolean onkoVaakaPituusOikea() {
        if (onkoVaaka() && Math.abs(alkuY - loppuY) + 1 == koko) {
            return true;
        } else {
            return false;
        }
    }

    private boolean onkoPystyPituusOikea() {
        if (onkoPystyssa() && Math.abs(alkuX - loppuX) + 1 == koko) {
            return true;
        } else {
            return false;
        }
    }
}
