/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Pelilogiikka;

import java.util.Scanner;

/**
 *Tein syötevirtaa hyväksikäyttävän tekstikäyttöliittymän,
 *kun huomasin omaavani tarpeeksi toimivaa toimintalogiikkaa
 *sen totutusta varten.
 * @author anttkari
 */
public class Tekstisovellus {

    public static void main(String[] args) {
        Pelilauta lauta = new Pelilauta();
        lauta.alustaLauta();
        Scanner lukija = new Scanner(System.in);
        
        for (int i = 2; i < 6; i++) {
            Laiva random;
            random = lauta.satunnainenLaiva(i, "js");
            while(lauta.kaykoLaiva(random)==false) {
                random = lauta.satunnainenLaiva(i, "js");
            }
            lauta.laivanAsetus(random);
        }
        
        System.out.println("Tervetuloa laivanupotukseen!\n"
                + "0 kuvaa ampumatonta aluetta\n"
                + "X ohiammuttua aluetta\n"
                + "* osuttua laivan kohtaa\n"
                + "laudalla on 2:n, 3:n, 4:n ja viiden kokoiset alukset\n"
                + "Onnea peliin!\n");
        
        while (!lauta.loppuikoPeli()) {
            System.out.print("Y-koordinaatti(0-9): ");
            int eka = lukija.nextInt();
            System.out.print("X-koordinaatti(0-9): ");
            int toka = lukija.nextInt();
            System.out.println("");
            if (eka>=0 && eka <=9 && toka >=0 && toka<=9) {
                lauta.tulitus(lauta.getNappula(eka, toka));
                lauta.tulostaOsumat();
            } else {
                System.out.println("Laitoithan koordinaatit oikein?\n");
            }
            
        }
        System.out.println("Vihollisen laivasto tuhottu!\n Pisteesi: " + lauta.pisteet());
    }
}