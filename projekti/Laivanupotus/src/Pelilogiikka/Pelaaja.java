package Pelilogiikka;

public class Pelaaja {
    String nimi;
    int pisteet;

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
