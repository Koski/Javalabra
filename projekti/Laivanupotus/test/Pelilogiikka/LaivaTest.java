package Pelilogiikka;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Pelilogiikka.Laiva;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author anttkari
 */
public class LaivaTest {
    
        Laiva tiedustelija;
        Laiva emoalus;
        Laiva vinoalus;
        
    public LaivaTest() {
    
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        tiedustelija = new Laiva("tiedustelija",2,1,2,2,2);
        emoalus = new Laiva("emoalus",5,1,0,1,4);
        vinoalus = new Laiva("emoalus",5,1,0,5,4);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void onkoPituusOikea() {
        
        assertEquals(2,tiedustelija.getKoko(),0.001);
    }
    @Test
    public void kirjaantuukoOsumat() {
        assertEquals(0,emoalus.getOsumienLkm(),0.001);
        emoalus.laivaanOsui();
        assertEquals(1,emoalus.getOsumienLkm(),0.001);
        emoalus.laivaanOsui();
        assertEquals(2,emoalus.getOsumienLkm(),0.001);
    }
    @Test
    public void uppoaakoAlus() {
        assertEquals(false,tiedustelija.onkoUponnut());
        tiedustelija.laivaanOsui();
        tiedustelija.laivaanOsui();
        assertEquals(true,tiedustelija.onkoUponnut());
    }
    @Test
    public void onkoPystyssa() {
   
        assertEquals(true,tiedustelija.onkoPystyssa());
    }
    @Test
    public void onkoVaakasuorassa() {
        assertEquals(true,emoalus.onkoVaaka());
        assertEquals(false,emoalus.onkoPystyssa());
    }
    @Test
    public void onkoOikein() {
        assertEquals(true,tiedustelija.onkoLaivaOikein());
        assertEquals(true,emoalus.onkoLaivaOikein());
        assertEquals(false,vinoalus.onkoLaivaOikein());
    }
    @Test
    public void kasvaakoOsumienLkmJosLaivaOnUponnut() {
        emoalus.setOsumienLkm(emoalus.getKoko());
        emoalus.laivaanOsui();
        assertEquals(emoalus.getKoko(), emoalus.getOsumienLkm(),0.001);
    }
              
}
