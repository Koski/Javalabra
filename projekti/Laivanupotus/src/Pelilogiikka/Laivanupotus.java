package Pelilogiikka;

import java.util.Random;
import java.util.Scanner;
/**
 * Tämä luokka on tehty alkupäässä toimintalogiikan testaamista
 * varten. Ohjelman suorituksen kannalta turha.
 * @author anttkari
 */
public class Laivanupotus {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        Pelilauta lauta = new Pelilauta();
        lauta.alustaLauta();
//        Laiva ohjusvene = new Laiva("ohjusvene",3,1,2,1,4);
//        Laiva ohjusvene2 = new Laiva("ohjusvene",3,4,5,4,3);
//        Laiva emoalus = new Laiva("emoalus",5,2,8,6,8);
//        Laiva miinalautta = new Laiva("miinalautta",4,5,0,8,0);
//        Laiva kokeilu = new Laiva("k",3,5,5,5,7);
//        Laiva vaaralaiva = new Laiva("tiedustelija",2,0,0,1,1);
//        lauta.laivanAsetus(ohjusvene);
//        lauta.laivanAsetus(ohjusvene2);
//        lauta.laivanAsetus(emoalus);
//        lauta.laivanAsetus(miinalautta);
//        lauta.laivanAsetus(kokeilu);
//        lauta.laivanAsetus(vaaralaiva);
//        lauta.tulitus(lauta.getNappula(7, 0));
//        lauta.tulitus(lauta.getNappula(8, 0));
//        lauta.tulitus(lauta.getNappula(5, 0));
//        lauta.tulitus(lauta.getNappula(6, 0));
       
//        lauta.tulosta();
//        System.out.println(ohjusvene.onkoLaivaOikein());       
//        System.out.println(emoalus.onkoLaivaOikein());
//        System.out.println(ohjusvene2.onkoLaivaOikein());
//        System.out.println(miinalautta.onkoLaivaOikein());
//        lauta.tulostaLaivat();
//        ohjusvene.lisaaKoordinaatitListaan();
        
//        Scanner lukija = new Scanner(System.in);
//        while (!lauta.loppuikoPeli()) {
//            int eka = lukija.nextInt();
//            int toka = lukija.nextInt();
//            lauta.tulitus(lauta.getNappula(eka, toka));
//            lauta.tulostaLaivat();
//        }
//        System.out.println(lauta.laukaustenMaara);
//        lauta.laivanAsetus(lauta.satunnainenLaiva(3, "Miinalautta"));
//        lauta.laivanAsetus(lauta.satunnainenLaiva(3, "Miinalautta"));
//        lauta.laivanAsetus(lauta.satunnainenLaiva(3, "Miinalautta"));
//        lauta.laivanAsetus(lauta.satunnainenLaiva(5, "Emoalus"));
//        for (int i = 3; i < 6; i++) {
//            Laiva random;
//            random = lauta.satunnainenLaiva(i, "js");
//            while(lauta.kaykoLaiva(random)==false) {
//                random = lauta.satunnainenLaiva(i, "js");
//            }
//            lauta.laivanAsetus(random);
//        }
//        lauta.tulostaLaivat();
//        System.out.println(lauta.vasenLaitaTarkistus(miinalautta));
    }
}
