/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Pelilogiikka;

import java.util.Scanner;

/**
 *
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
            System.out.println("Y-koordinaatti: ");
            int eka = lukija.nextInt();
            System.out.println("X-koordinaatti: ");
            int toka = lukija.nextInt();
            System.out.println("");
            lauta.tulitus(lauta.getNappula(eka, toka));
            lauta.tulostaOsumat();
        }
    }
}