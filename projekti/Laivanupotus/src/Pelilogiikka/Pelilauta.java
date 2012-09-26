package Pelilogiikka;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pelilauta {

    Nappula nappula;
    Nappula[][] lauta;
    int korkeus = 10;
    int leveys = 10;
    List<Laiva> laivalista;
    int laukaustenMaara = 0;
    Laiva satunnaisLaiva = null;
    Random generator;

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
        if (osuikoTauluun(nappula.xKoord, nappula.yKoord) && nappula.getLaiva() != null) {
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
    }                                                   //Ylä- ja alapuolella olevat tulostusmetodit ovat
                                                        //vain Mainin testausta ja tekstikäyttöliittymää
    public void tulostaOsumat() {                      //varten. En siirtänyt niitä pois, koska ne käyttävät
        System.out.println("   0 1 2 3 4 5 6 7 8 9\n"    // muutamaa luokan privaattia metodia.
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

    private void vaakaLaivaPienempiEka(Laiva laiva) {
        if (laiva.alkuY < laiva.loppuY) {
            int luku = laiva.alkuY;
            while (luku <= laiva.loppuY) {
                lauta[laiva.alkuX][luku].setLaiva(laiva);
                luku++;
            }
        }
    }

    private void vaakaLaivaIsompiEka(Laiva laiva) {
        if (laiva.alkuY > laiva.loppuY) {
            int luku = laiva.alkuY;
            while (luku >= laiva.loppuY) {
                lauta[laiva.alkuX][luku].setLaiva(laiva);
                luku--;
            }
        }
    }

    private void pystyLaivaPienempiEka(Laiva laiva) {
        if (laiva.alkuX < laiva.loppuX) {
            int luku = laiva.alkuX;
            while (luku <= laiva.loppuX) {
                lauta[luku][laiva.alkuY].setLaiva(laiva);
                luku++;
            }
        }
    }

    private void pystyLaivaIsompiEka(Laiva laiva) {
        if (laiva.alkuX > laiva.loppuX) {
            int luku = laiva.alkuX;
            while (luku >= laiva.loppuX) {
                lauta[luku][laiva.alkuY].setLaiva(laiva);
                luku--;
            }
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
     * @return
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
     * Luo satunnaisen kahden ruudun pituisen laivan, jonka
     * tyyppi on tiedustelija. Jatkaa arpomista niin kauan, kunnes
     * löytää sellaisen joka käy pelikenttään.
     * @return palauttaa luodun laivan.
     */
    public Laiva luoTiedustelija() {
        Laiva palaute = satunnainenLaiva(2, "Tiedustelija");
        while (!kaykoLaiva(palaute)) {
            palaute = satunnainenLaiva(2, "Tiedustelija");
        }
        return palaute;
    }
    /**
     * Luo satunnaisen kolmen ruudun pituisen laivan, jonka
     * tyyppi on miinalautta. Jatkaa arpomista niin kauan, kunnes
     * löytää sellaisen joka käy pelikenttään.
     * @return palauttaa luodun laivan.
     */
    public Laiva luoMiinalautta() {
        Laiva palaute = satunnainenLaiva(3, "Miinalautta");
        while (!kaykoLaiva(palaute)) {
            palaute = satunnainenLaiva(3, "Miinalautta");
        }
        return palaute;
    }
    /**
     * Luo satunnaisen neljän ruudun pituisen laivan, jonka
     * tyyppi on ohjusvene. Jatkaa arpomista niin kauan, kunnes
     * löytää sellaisen joka käy pelikenttään.
     * @return palauttaa luodun laivan.
     */
    public Laiva luoOhjusvene() {
        Laiva palaute = satunnainenLaiva(4, "Ohjusvene");
        while (!kaykoLaiva(palaute)) {
            palaute = satunnainenLaiva(4, "Ohjusvene");
        }
        return palaute;
    }
    /**
     * Luo satunnaisen viiden ruudun pituisen laivan, jonka
     * tyyppi on emoalus. Jatkaa arpomista niin kauan, kunnes
     * löytää sellaisen joka käy pelikenttään.
     * @return palauttaa luodun laivan.
     */
    public Laiva luoEmoalus() {
        Laiva palaute = satunnainenLaiva(5, "Emoalus");
        while (!kaykoLaiva(palaute)) {
            palaute = satunnainenLaiva(5, "Emoalus");
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
            for (int i = laiva.alkuY; i <= laiva.loppuY; i++) {
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
        return lauta[laiva.getLoppuX() + 1][laiva.loppuY].getLaiva() != null;
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
        return lauta[laiva.loppuX][laiva.getLoppuY() + 1].getLaiva() != null;
    }

    private boolean onkoVaakalaivanAllaLaivaa(Laiva laiva, int i) {
        return lauta[laiva.alkuX - 1][i].getLaiva() != null;
    }

    private boolean onkoVaakaLaivanYllaLaivaa(Laiva laiva, int i) {
        return lauta[laiva.alkuX + 1][i].getLaiva() != null;
    }

    private boolean onkoLaivanTilallaJoLaiva(Laiva laiva, int i) {
        return lauta[laiva.alkuX][i].getLaiva() != null;
    }

    private boolean onkoPystylaivanTilallaJoLaivaa(int i, Laiva laiva) {
        return lauta[i][laiva.alkuY].getLaiva() != null;
    }

    private boolean kohdassaOnLaivaJaKohtaanOnOsuttu(int i, int j) {
        return lauta[i][j].getLaiva() != null && lauta[i][j].isOsuttu();
    }

    private boolean kohdassaEiOleLaivaaJaKohtaanOnOsuttu(int i, int j) {
        return lauta[i][j].getLaiva() == null && lauta[i][j].isOsuttu();
    }

    private boolean pystyLaivanPaissaToinenLaiva(Laiva laiva) {
        return osuikoTauluun(laiva.getAlkuX() - 1, laiva.alkuY) && onkoPystylaivanYlapuolellaLaivaa(laiva) || osuikoTauluun(laiva.getLoppuX() + 1, laiva.loppuY) && onkoPystylaivanAlapuolellaLaivaa(laiva);
    }

    private boolean pystylaivanTilallaTaiYllaTaiAllaOnLaiva(int i, Laiva laiva) {
        return osuikoTauluun(i, laiva.getAlkuY() + 1) && onkoPystylaivanOikeallaLaivaa(i, laiva) || osuikoTauluun(i, laiva.alkuY - 1) && onkoPystylaivanVasemmallaLaivaa(i, laiva) || onkoPystylaivanTilallaJoLaivaa(i, laiva);
    }

    private boolean vaakalaivanPaissaToinenLaiva(Laiva laiva) {
        return osuikoTauluun(laiva.alkuX, laiva.getAlkuY() - 1) && onkoVaakalaivanVasemmallaLaivaa(laiva) || osuikoTauluun(laiva.loppuX, laiva.getLoppuY() + 1) && onkoVaakalaivanOikeallaLaivaa(laiva);
    }

    private boolean vaakalaivanTilallaTaiYllaTaiAllaOnLaiva(Laiva laiva, int i) {
        return osuikoTauluun(laiva.alkuX - 1, i) && onkoVaakalaivanAllaLaivaa(laiva, i) || osuikoTauluun(laiva.alkuX + 1, i) && onkoVaakaLaivanYllaLaivaa(laiva, i) || onkoLaivanTilallaJoLaiva(laiva, i);
    }
}
