package Pelilogiikka;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Pelilogiikka.Laiva;
import Pelilogiikka.Nappula;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import Pelilogiikka.Pelilauta;
import java.util.List;

/**
 *
 * @author anttkari
 */
public class PelilautaTest {

    Pelilauta lauta;
    Nappula[][] nappulat;
    Laiva testilaiva;

    public PelilautaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        lauta = new Pelilauta();
        lauta.alustaLauta();
        nappulat = lauta.getLauta();
        testilaiva = new Laiva("ohjusvene", 4, 2, 1, 5, 1);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void alustettuLauta() {
        boolean totuus = false;
        for (int i = 0; i < lauta.getKorkeus(); i++) {
            for (int j = 0; j < lauta.getLeveys(); j++) {
                if (nappulat[i][j].getLaiva() != null) {
                    totuus = true;
                }
            }
        }
        assertFalse(totuus);
    }
    @Test
    public void asettuukoLaivaLaudalle() {
        lauta.laivanAsetus(testilaiva);
        int luku=0;
        for (int i = 0; i < lauta.getKorkeus(); i++) {
            for (int j = 0; j < lauta.getLeveys(); j++) {
                if (nappulat[i][j].getLaiva()!=null) {
                    luku++;
                }
            }
        }
        assertEquals(testilaiva.getKoko(),luku,0.001);
    }
    @Test
    public void lisataankoLaivatListaanKunNeAsetetaanLaudalle() {
        assertEquals(0,lauta.getLaivalista().size(),0.001);
        lauta.laivanAsetus(testilaiva);
        assertEquals(1,lauta.getLaivalista().size(),0.001);
    }
    @Test
    public void osuukoTulitus() {
        lauta.laivanAsetus(testilaiva);
        lauta.tulitus(nappulat[2][1]);
        assertTrue(nappulat[2][1].isOsuttu());
    }

    @Test
    public void osuukoOhiMennytTulitus() {
        lauta.laivanAsetus(testilaiva);
        lauta.tulitus(nappulat[3][6]);
        assertTrue(nappulat[3][6].isOsuttu());
    }

    @Test
    public void osuukoTauluun() {
        assertEquals(true, lauta.osuikoTauluun(2, 4));
        assertEquals(false, lauta.osuikoTauluun(-2, 15));
    }

    @Test
    public void meneekoLaivaToisenLaivanViereen() {
        Laiva emoalus = new Laiva("emo", 5, 3, 2, 3, 6);
        lauta.laivanAsetus(testilaiva);
        assertFalse(lauta.kaykoLaiva(emoalus));
    }

    @Test
    public void meneekoLaivaToisenLaivanKulmaan() {
        Laiva emoalus = new Laiva("emo", 5, 1, 2, 1, 6);
        lauta.laivanAsetus(testilaiva);
        assertTrue(lauta.kaykoLaiva(emoalus));
    }
    @Test
    public void meneekoKaksiLaivaaTaysinPaallekkain() {
        Laiva tiedustelija = new Laiva("vene",2,3,1,4,1);
        lauta.laivanAsetus(testilaiva);
        assertFalse(lauta.kaykoLaiva(tiedustelija));
    }
    @Test
    public void loytyykoSatunnainenLaivaLaudalta() {
        int luku = 0;
        lauta.laivanAsetus(lauta.satunnainenLaiva(4, "testivene"));
        for (int i = 0; i < lauta.getKorkeus(); i++) {
            for (int j = 0; j < lauta.getLeveys(); j++) {
                if (nappulat[i][j].getLaiva() != null) {
                    luku++;
                }
            }
        }
        assertEquals(4,luku,0.001);
    }
    @Test
    public void saadaankoOikeaNappula() {
        int xKoord=lauta.getNappula(2, 3).getxKoord();
        int yKoord=lauta.getNappula(4, 5).getyKoord();
        assertEquals(2,xKoord,0.001);
        assertEquals(5,yKoord,0.001);
    }
    @Test
    public void paattyykoPeliKunLaivatOnTuhottu() {
        lauta.laivanAsetus(testilaiva);
        for (int i = testilaiva.getAlkuX(); i <= testilaiva.getLoppuX(); i++) {
            lauta.tulitus(lauta.getNappula(i, testilaiva.getAlkuY()));
        }
        assertTrue(lauta.loppuikoPeli());
    }
    @Test
    public void paattyykoPeliEnnenAikoja() {
        lauta.laivanAsetus(testilaiva);
        lauta.tulitus(lauta.getNappula(3, 1));
        assertFalse(lauta.loppuikoPeli());
    }

    
}
