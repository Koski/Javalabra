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

    public void alustaLauta() {
        for (int i = 0; i < korkeus; i++) {
            for (int j = 0; j < leveys; j++) {
                lauta[i][j] = new Nappula(null, false, i, j);
            }
        }
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

    public void tulitus(Nappula nappula) {
        if (osuikoTauluun(nappula.xKoord, nappula.yKoord) && nappula.getLaiva() != null) {
            nappula.setOsuttu(true);
            nappula.getLaiva().osumienLkm++;
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
    }

    public void tulostaOsumat() {
        System.out.println("   0 1 2 3 4 5 6 7 8 9\n"
                + "   ___________________");
        for (int i = 0; i < korkeus; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < leveys; j++) {
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

    public boolean osuikoTauluun(int x, int y) {
        return x >= 0 && x < korkeus && y >= 0 && y < leveys;
    }

    public int pisteet() {
        return 10000 - laukaustenMaara * 100;
    }

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

    public boolean kaykoLaiva(Laiva laiva) {
        boolean palaute = true;
        if (laiva.onkoPystyssa()) {
            if (pystyLaivanPaissaToinenLaiva(laiva)) {
                palaute = false;
            }
            for (int i = laiva.getAlkuX(); i <= laiva.getLoppuX(); i++) {
                if (pystylaivanTilallaTaiYllaTaiAllaOnLaiva(i, laiva)) {
                    palaute = false;
                }
            }
        } else {
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
