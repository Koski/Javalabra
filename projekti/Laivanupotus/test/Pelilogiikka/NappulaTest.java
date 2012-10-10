/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Pelilogiikka;

import Pelilogiikka.Nappula;
import java.awt.Color;
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
public class NappulaTest {
    
    Nappula nappula;
    Laiva tiedustelija;
    
    public NappulaTest() {
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
        nappula = new Nappula(null, false, 3, 3);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void vaihtuukoTausta() {
        
        nappula.vaihdaSiniseksi();       
        assertEquals(51,nappula.getBackground().getRed(), 0.001);
        assertEquals(204,nappula.getBackground().getGreen(), 0.001);
        assertEquals(255,nappula.getBackground().getBlue(), 0.001);      
    }
    @Test
    public void onkoKohdassaLaiva() {
        assertEquals(null, nappula.getLaiva());
        nappula.setLaiva(tiedustelija);
        assertEquals(tiedustelija, nappula.getLaiva());
    }   
}
