package Pelilogiikka;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Pelilaudalla on laivoja, joita pyritään tuhoamaan. Lauta
 * pitää kirjaa siinä sijaitsevista laivoista ja poistaa niitä
 * sitä mukaan kun ne tuhoutuvat.
 * @author anttkari
 */
public class Pelilauta {
    /**
     * Matriisi, joka sisältää Nappula olioita, joilla on tiedot
     * koordinaateista, laivoista ja osumisesta.
     */
    private Nappula[][] lauta;
    /**
     * Pelilaudan korkeus.
     */
    private int korkeus = 10;
    /**
     * Pelilaudan leveys.
     */
    private int leveys = 10;
    /**
     * Lista laivoista, jotka ovat laudalla.
     */
    private List<Laiva> laivalista;
    /**
     * Muuttujaan kirjataan +1 aina kun lautaa tulitetaan, osui
     * se laivaan tai ei.
     */
    private int laukaustenMaara = 0;
    /**
     * Kenttä satunnaisten laivojen luontia varten.
     */
    private Laiva satunnaisLaiva = null;
    /**
     * Satunnaisten lukujen generoimista varten käytetty olio
     * (satunnaiset laivat).
     */
    private Random generator;
    /**
     * Luo 10x10 kokoisen pelilaudan (tässä tapauksessa), alustaa
     * tyhjän laivalistan ja satunnaislukuja generoivan olion.
     */
    public Pelilauta() {
        lauta = new Nappula[korkeus][leveys];
        laivalista = new ArrayList<Laiva>();
        generator = new Random();
    }

    /**
     * Luo jokaiseen pelilaudan kohtaan Nappulaolion ja alustaa nappulat niin
     * että mihinkään ei ole osuttu eikä missään ole laivaa. Koordinaatit ovat
     * välillä 0-9.
     */
    public void alustaLauta() {
        for (int i = 0; i < korkeus; i++) {
            for (int j = 0; j < leveys; j++) {
                lauta[i][j] = new Nappula(null, false, i, j);
            }
        }
    }

    public int getLaukaustenMaara() {
        return laukaustenMaara;
    }

    public List<Laiva> getLaivalista() {
        return laivalista;
    }

    public Nappula[][] getLauta() {
        return lauta;
    }

    public int getKorkeus() {
        return korkeus;
    }

    public int getLeveys() {
        return leveys;
    }

    /**
     * Antaa palautteena nappulan, jolla on annetut koordinaatit
     *
     * @param x käyttäjän antama syöte, x-koordinaatti
     * @param y käyttäjän antama syöte, y-koordinaatti
     * @return koordinaatteja vastaava nappula
     */
    public Nappula getNappula(int x, int y) {
        Nappula palaute = null;
        if (osuikoTauluun(x, y)) {
            for (int i = 0; i < korkeus; i++) {
                for (int j = 0; j < leveys; j++) {
                    if (lauta[i][j].getxKoord() == x && lauta[i][j].getyKoord() == y) {
                        palaute = lauta[i][j];
                    }
                }
            }
        }
        return palaute;
    }

    /**
     * Asettaa annetun nappulan "osuttu" parametriin true. Jos kohdassa oli
     * laiva, kasvatetaan kyseisen laivan osumien lukumäärää Laukausten määrää
     * myös kasvatetaan aina tulituksessa
     *
     * @param nappula käyttäjän antama syöte
     */
    public void tulitus(Nappula nappula) {
        if (osuikoTauluun(nappula.getxKoord(), nappula.getyKoord()) && nappula.getLaiva() != null) {
            nappula.setOsuttu(true);
            nappula.getLaiva().laivaanOsui();
            if (nappula.getLaiva().onkoUponnut()) {
                laivalista.remove(nappula.getLaiva());
            }
            laukaustenMaara++;
        } else {
            nappula.setOsuttu(true);
            laukaustenMaara++;
        }
    }
    /**
     * Tekstikäyttöliittymää varten tehty tulostusmetodi,
     * ei relevantti ohjelman suorituksen kannalta.
     */
    public void tulostaLaivat() {
        for (int i = 0; i < korkeus; i++) {

            for (int j = 0; j < leveys; j++) {
                if (kohdassaOnLaivaJaKohtaanOnOsuttu(i, j)) {
                    System.out.print("X ");
                } else if (lauta[i][j].getLaiva() != null) {
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println("");
        }
    }                                                   
    /**    
     * Tekstikäyttöliittymää ja logiikan testausta tehty tulostusmetodi,
     * ei relevantti ohjelman suorituksen kannalta.
     */                                                  
    public void tulostaOsumat() {                      
        System.out.println("   0 1 2 3 4 5 6 7 8 9\n"   
                + "   ___________________");
        for (int i = 0; i < getKorkeus(); i++) {
            System.out.print(i + " |");
            for (int j = 0; j < getLeveys(); j++) {
                if (kohdassaOnLaivaJaKohtaanOnOsuttu(i, j)) {
                    System.out.print("* ");
                } else if (kohdassaEiOleLaivaaJaKohtaanOnOsuttu(i, j)) {
                    System.out.print("X ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println("");
        }
    }

    /**
     * Kertoo onko kaikki pelilaudan laivat uponneet
     *
     * @return palauttaa true jos kaikki laivat ovat uponneet ja falsen jos
     * yksikin laiva on edes osittain "hengissä".
     */
    public boolean loppuikoPeli() {
        int uponneet = 0;
        for (Laiva laiva : laivalista) {
            if (laiva.onkoUponnut()) {
                uponneet++;
            }
        }
        if (uponneet == laivalista.size()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Asettaa annetun laivan omiin koordinaatteihinsa laudalle sekä lisää
     * laivan listaan, jossa pidetään kirjaa kaikista laivoista
     *
     * @param laiva käyttäjän antama syöte
     */
    public void laivanAsetus(Laiva laiva) {

        if (laiva.onkoVaaka()) {
            vaakaLaivaPienempiEka(laiva);
            vaakaLaivaIsompiEka(laiva);
            laivalista.add(laiva);
        }
        if (laiva.onkoPystyssa()) {
            pystyLaivaPienempiEka(laiva);
            pystyLaivaIsompiEka(laiva);
            laivalista.add(laiva);
        }
    }

    

    /**
     * Tarkistaa pysyvätkö annetut parametrit, siis koordinaatit, pelilaudan
     * rajoissa
     *
     * @param x käyttäjän antama syöte, x-koordinaatti
     * @param y käyttäjän antama syöte, y-koordinaatti
     * @return Palauttee true, jos koordinaatit mahtuvat laudalle ja muutoin
     * falsen
     */
    public boolean osuikoTauluun(int x, int y) {
        return x >= 0 && x < korkeus && y >= 0 && y < leveys;
    }

    /**
     * Antaa pelaajan pisteet, mitä enemmän laukauksia, sitä huonommat pisteet.
     * Jokainen laukaus vähentää 100 pistettä. Maksimipisteet riippuvat siis laivojen
     * määrästä.
     *
     * @return pisteet riipuen laukausten maarasta.
     */
    public int pisteet() {
        return 10000 - laukaustenMaara * 100;
    }

    /**
     * Arpoo laivalle satunnaiset alku- ja loppukoordinaatit. Laivan suunta, eli
     * pysty tai vaaka, on myös satunnainen. Koko ja tyyppi määritellään itse.
     *
     * @param koko käyttäjän antama syöte
     * @param tyyppi käyttäjän antama syöte
     * @return palauttaa arvotun laivan.
     */
    public Laiva satunnainenLaiva(int koko, String tyyppi) {
        int luku = generator.nextInt(2);
        if (luku == 0) {
            int yKoord = generator.nextInt(10);
            int xKoord = generator.nextInt(10);
            if (leveys - 1 - koko < xKoord) {
                satunnaisLaiva = new Laiva(tyyppi, koko, yKoord, leveys - koko, yKoord, leveys - 1);
            } else {
                satunnaisLaiva = new Laiva(tyyppi, koko, yKoord, xKoord, yKoord, xKoord + koko - 1);
            }
        } else {
            int yKoord = generator.nextInt(10);
            int xKoord = generator.nextInt(10);
            if (korkeus - 1 - koko < yKoord) {
                satunnaisLaiva = new Laiva(tyyppi, koko, korkeus - koko, xKoord, korkeus - 1, xKoord);
            } else {
                satunnaisLaiva = new Laiva(tyyppi, koko, yKoord, xKoord, yKoord + koko - 1, xKoord);
            }
        }
        return satunnaisLaiva;
    }
    /**
     * Luo satunnaisen laivan ja tarkistaa meneekö se kenttään sovituilla
     * säännöillä, jos ei mene, niin luo uusia laivoja niin kauan että
     * luotu laiva menee kenttään.
     * @param koko käyttäjän antama syöte
     * @param tyyppi käyttäjän antama syöte
     * @return satunnainen laiva joka käy kenttään.
     */
    public Laiva luoSatunnainenLaiva(int koko, String tyyppi) {
        Laiva palaute = satunnainenLaiva(koko, tyyppi);
        while (!kaykoLaiva(palaute)) {
            palaute = satunnainenLaiva(koko, tyyppi);
        }
        return palaute;
    }

    /**
     * Selvittää, käykö syötteenä saatu laiva peli- laudalle. Laivat eivät saa
     * olla kiinni toisissaan sivusta tai päistä, kulmat saavat olla vierekkäin.
     *
     * @param laiva käyttäjän antama syöte
     * @return palauttaa true, jos kyseinen laiva sopii laudalle ja false, jos
     * laiva ei sovi.
     */
    public boolean kaykoLaiva(Laiva laiva) {
        boolean palaute = true;
        if (laiva.onkoPystyssa() && laiva.onkoLaivaOikein()) {
            if (pystyLaivanPaissaToinenLaiva(laiva)) {
                palaute = false;
            }
            for (int i = laiva.getAlkuX(); i <= laiva.getLoppuX(); i++) {
                if (pystylaivanTilallaTaiYllaTaiAllaOnLaiva(i, laiva)) {
                    palaute = false;
                }
            }
        } else if (laiva.onkoVaaka() && laiva.onkoLaivaOikein()) {
            if (vaakalaivanPaissaToinenLaiva(laiva)) {
                palaute = false;
            }
            for (int i = laiva.getAlkuY(); i <= laiva.getLoppuY(); i++) {
                if (vaakalaivanTilallaTaiYllaTaiAllaOnLaiva(laiva, i)) {
                    palaute = false;
                }
            }
        }
        return palaute;
    }

    private boolean onkoPystylaivanYlapuolellaLaivaa(Laiva laiva) {
        return onkoPystylaivanTilallaJoLaivaa(laiva.getAlkuX() - 1, laiva);
    }

    private boolean onkoPystylaivanAlapuolellaLaivaa(Laiva laiva) {
        return lauta[laiva.getLoppuX() + 1][laiva.getLoppuY()].getLaiva() != null;
    }

    private boolean onkoPystylaivanOikeallaLaivaa(int i, Laiva laiva) {
        return lauta[i][laiva.getAlkuY() + 1].getLaiva() != null;
    }

    private boolean onkoPystylaivanVasemmallaLaivaa(int i, Laiva laiva) {
        return lauta[i][laiva.getAlkuY() - 1].getLaiva() != null;
    }

    private boolean onkoVaakalaivanVasemmallaLaivaa(Laiva laiva) {
        return onkoLaivanTilallaJoLaiva(laiva, laiva.getAlkuY() - 1);
    }

    private boolean onkoVaakalaivanOikeallaLaivaa(Laiva laiva) {
        return lauta[laiva.getLoppuX()][laiva.getLoppuY() + 1].getLaiva() != null;
    }

    private boolean onkoVaakalaivanAllaLaivaa(Laiva laiva, int i) {
        return lauta[laiva.getAlkuX() - 1][i].getLaiva() != null;
    }

    private boolean onkoVaakaLaivanYllaLaivaa(Laiva laiva, int i) {
        return lauta[laiva.getAlkuX() + 1][i].getLaiva() != null;
    }

    private boolean onkoLaivanTilallaJoLaiva(Laiva laiva, int i) {
        return lauta[laiva.getAlkuX()][i].getLaiva() != null;
    }

    private boolean onkoPystylaivanTilallaJoLaivaa(int i, Laiva laiva) {
        return lauta[i][laiva.getAlkuY()].getLaiva() != null;
    }

    private boolean kohdassaOnLaivaJaKohtaanOnOsuttu(int i, int j) {
        return lauta[i][j].getLaiva() != null && lauta[i][j].isOsuttu();
    }

    private boolean kohdassaEiOleLaivaaJaKohtaanOnOsuttu(int i, int j) {
        return lauta[i][j].getLaiva() == null && lauta[i][j].isOsuttu();
    }

    private boolean pystyLaivanPaissaToinenLaiva(Laiva laiva) {
        return osuikoTauluun(laiva.getAlkuX() - 1, laiva.getAlkuY()) && onkoPystylaivanYlapuolellaLaivaa(laiva) || osuikoTauluun(laiva.getLoppuX() + 1, laiva.getLoppuY()) && onkoPystylaivanAlapuolellaLaivaa(laiva);
    }

    private boolean pystylaivanTilallaTaiYllaTaiAllaOnLaiva(int i, Laiva laiva) {
        return osuikoTauluun(i, laiva.getAlkuY() + 1) && onkoPystylaivanOikeallaLaivaa(i, laiva) || osuikoTauluun(i, laiva.getAlkuY() - 1) && onkoPystylaivanVasemmallaLaivaa(i, laiva) || onkoPystylaivanTilallaJoLaivaa(i, laiva);
    }

    private boolean vaakalaivanPaissaToinenLaiva(Laiva laiva) {
        return osuikoTauluun(laiva.getAlkuX(), laiva.getAlkuY() - 1) && onkoVaakalaivanVasemmallaLaivaa(laiva) || osuikoTauluun(laiva.getLoppuX(), laiva.getLoppuY() + 1) && onkoVaakalaivanOikeallaLaivaa(laiva);
    }

    private boolean vaakalaivanTilallaTaiYllaTaiAllaOnLaiva(Laiva laiva, int i) {
        return osuikoTauluun(laiva.getAlkuX() - 1, i) && onkoVaakalaivanAllaLaivaa(laiva, i) || osuikoTauluun(laiva.getAlkuX() + 1, i) && onkoVaakaLaivanYllaLaivaa(laiva, i) || onkoLaivanTilallaJoLaiva(laiva, i);
    }
    private void vaakaLaivaPienempiEka(Laiva laiva) {
        if (laiva.getAlkuY() < laiva.getLoppuY()) {
            int luku = laiva.getAlkuY();
            while (luku <= laiva.getLoppuY()) {
                lauta[laiva.getAlkuX()][luku].setLaiva(laiva);
                luku++;
            }
        }
    }

    private void vaakaLaivaIsompiEka(Laiva laiva) {
        if (laiva.getAlkuY() > laiva.getLoppuY()) {
            int luku = laiva.getAlkuY();
            while (luku >= laiva.getLoppuY()) {
                lauta[laiva.getAlkuX()][luku].setLaiva(laiva);
                luku--;
            }
        }
    }

    private void pystyLaivaPienempiEka(Laiva laiva) {
        if (laiva.getAlkuX() < laiva.getLoppuX()) {
            int luku = laiva.getAlkuX();
            while (luku <= laiva.getLoppuX()) {
                lauta[luku][laiva.getAlkuY()].setLaiva(laiva);
                luku++;
            }
        }
    }

    private void pystyLaivaIsompiEka(Laiva laiva) {
        if (laiva.getAlkuX() > laiva.getLoppuX()) {
            int luku = laiva.getAlkuX();
            while (luku >= laiva.getLoppuX()) {
                lauta[luku][laiva.getAlkuY()].setLaiva(laiva);
                luku--;
            }
        }
    }
}
