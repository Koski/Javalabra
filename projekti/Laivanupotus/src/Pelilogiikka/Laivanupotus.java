package Pelilogiikka;

public class Laivanupotus {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Pelilauta lauta = new Pelilauta();
        lauta.alustaLauta();
        Laiva ohjusvene = new Laiva("ohjusvene",3,1,2,1,4);
        Laiva ohjusvene2 = new Laiva("ohjusvene",3,4,5,4,3);
        Laiva emoalus = new Laiva("emoalus",5,2,8,7,8);
        Laiva miinalautta = new Laiva("miinalautta",4,8,0,5,0);
        Laiva vaaralaiva = new Laiva("tiedustelija",2,0,0,1,1);
        lauta.laivanAsetus(ohjusvene);
        lauta.laivanAsetus(ohjusvene2);
        lauta.laivanAsetus(emoalus);
        lauta.laivanAsetus(miinalautta);
        lauta.laivanAsetus(vaaralaiva);
//        lauta.tulosta();
//        System.out.println(ohjusvene.onkoLaivaOikein());       
//        System.out.println(emoalus.onkoLaivaOikein());
//        System.out.println(ohjusvene2.onkoLaivaOikein());
//        System.out.println(miinalautta.onkoLaivaOikein());
//        lauta.tulosta();
//        ohjusvene.lisaaKoordinaatitListaan();
        
        lauta.tulostaLaivat();
    }
}
