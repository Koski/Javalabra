package Pelilogiikka;
/**
 * Pelaaja luokka määrittelee eri nimiset pelaajat
 * ja sitoo pelaajan saamat pisteet hänen nimeensä.
 * @author anttkari
 */
public class Pelaaja {
    /**
     * Pelaajan nimi / nicki.
     */
    private String nimi;
    /**
     * Pelaajan saavuttamat pisteet pelin aikana.
     */
    private int pisteet;

    public Pelaaja(String nimi) {
        this.nimi=nimi;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setPisteet(int pisteet) {
        this.pisteet = pisteet;
    }

    public int getPisteet() {
        return pisteet;
    }
      
}
