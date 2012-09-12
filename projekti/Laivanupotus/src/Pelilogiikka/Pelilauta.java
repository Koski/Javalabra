package Pelilogiikka;


public class Pelilauta {

    Nappula nappula;
    Nappula[][] lauta;
    int korkeus = 10;
    int leveys = 10;

    public Pelilauta() {
        lauta = new Nappula[korkeus][leveys];
    }

    public void alustaLauta() {
        for (int i = 0; i < korkeus; i++) {
            for (int j = 0; j < leveys; j++) {
                lauta[i][j] = new Nappula(false, false, i, j);
            }
        }
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

    public void asetaOsaLaivaa(Nappula nappula) {
        if (osuikoTauluun(nappula.xKoord, nappula.yKoord)) {
            nappula.setLaiva(true);
        }
    }
    
    public void tulitus(Nappula nappula) {
        if (osuikoTauluun(nappula.xKoord, nappula.yKoord) && nappula.isLaiva() ) {
            nappula.setOsuttu(true);
        }
    }
    
    public void tulostaLaivat() {
        for (int i = 0; i < korkeus; i++) {
            for (int j = 0; j < leveys; j++) {
                System.out.print(lauta[i][j].isLaiva()+ " ");
            }
            System.out.println("");
        }
    }
    public boolean loppuikoPeli() {
        int luku=0;
        for (int i = 0; i < korkeus; i++) {
            for (int j = 0; j < leveys; j++) {
                if (lauta[i][j].isOsuttu()) {
                    luku ++;
                }
            }
        }
        if (luku==22) {
            return true;
        } else {
            return false;
        }
    }
    public void laivanAsetus(Laiva laiva) {   

        if (laiva.onkoVaaka()) {
            vaakaLaivaPienempiEka(laiva);
            vaakaLaivaIsompiEka(laiva);            
        }
        if (laiva.onkoPystyssa()) {
            pystyLaivaPienempiEka(laiva);
            pystyLaivaIsompiEka(laiva);
        } 
    }

    private void vaakaLaivaPienempiEka(Laiva laiva) {
        if (laiva.alkuY<laiva.loppuY) {
            int luku=laiva.alkuY;
            while (luku<=laiva.loppuY) {
                lauta[laiva.alkuX][luku].setLaiva(true);
                luku ++;
            }
        }
    }

    private void vaakaLaivaIsompiEka(Laiva laiva) {
        if (laiva.alkuY>laiva.loppuY) {
            int luku=laiva.alkuY;
            while (luku>=laiva.loppuY) {
                lauta[laiva.alkuX][luku].setLaiva(true);
                luku --;
            }
        }
    }

    private void pystyLaivaPienempiEka(Laiva laiva) {
        if (laiva.alkuX<laiva.loppuX) {
            int luku=laiva.alkuX;
            while (luku<=laiva.loppuX) {
                lauta[luku][laiva.alkuY].setLaiva(true);
                luku ++;
            }
        }
    }

    private void pystyLaivaIsompiEka(Laiva laiva) {
        if (laiva.alkuX>laiva.loppuX) {
            int luku=laiva.alkuX;
            while (luku>=laiva.loppuX) {
                lauta[luku][laiva.alkuY].setLaiva(true);
                luku --;
            }
        }
    }

    public boolean osuikoTauluun(int x, int y) {
        return x>=0 && x<korkeus && y>=0 && y<leveys;
    }
    
}
