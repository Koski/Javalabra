/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Pelilogiikka.Nappula;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import Pelilogiikka.Pelilauta;

/**
 *
 * @author anttkari
 */
public class PelilautaTest {
    
    Pelilauta lauta;
    Nappula[][] nappulat;
    
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
    }
    
    @After
    public void tearDown() {
    }
    @Test
    public void alustettuLauta() {
        boolean totuus=false;    
        for (int i = 0; i < lauta.getKorkeus(); i++) {
            for (int j = 0; j < lauta.getLeveys(); j++) {
                if(nappulat[i][j].isLaiva()) {
                   totuus=true; 
                }
            }
        }
        assertFalse(totuus);
    }
       
    @Test
    public void osuukoTulitus() {
        lauta.asetaOsaLaivaa(nappulat[0][0]);
        lauta.tulitus(nappulat[0][0]);
        assertEquals(true,nappulat[0][0].isOsuttu());
    }
    @Test
    public void meneekoTulitusOhi() {
        lauta.asetaOsaLaivaa(nappulat[3][5]);
        lauta.tulitus(nappulat[3][6]);
        assertFalse(nappulat[3][6].isOsuttu());
    }
    @Test
    public void osuukoTauluun() {
        assertEquals(true,lauta.osuikoTauluun(2, 4));
        assertEquals(false,lauta.osuikoTauluun(-2, 15));
    }
   
}
