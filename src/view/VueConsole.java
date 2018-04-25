/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import model.Armee;
import model.Bateau;
import model.NouvPartie;
import model.Position;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import static jdk.nashorn.internal.objects.NativeString.toUpperCase;
import model.Case;
import model.TypeB;

enum Couleur {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");

    private final String code;
    private Couleur(String code) { this.code = code; }
    @Override public String toString() { return code; }
}

/**
 *
 * @author 2208sptheodorou
 */
public class VueConsole implements Observer {

    private static final Scanner INSERT = new Scanner(System.in);
    private static final NouvPartie npVue = NouvPartie.getNP();

    public static NouvPartie getNpVue() {
        return npVue;
    }
    
    public static void affMer() {
        printLN("");
        print("   ");
        for (int i = 0; i < npVue.getTailleGb(); i++) {
            print(npVue.getAxeXGb()[i] + " ");
        }
        printLN("");
        for (int i = 0; i < npVue.getTailleGb(); i++) {
            print(npVue.getAxeYGb()[i] + " ");
            for (int j = 0; j < npVue.getTailleGb(); j++) {

                print("|" + toString(npVue.getMerGb()[i][j]));
            }
            printLN("|");
        }
    }

    public static void affEtatArmees() {
        for (int i = 0; i < 69; ++i) {
            print("-");
        }
        printLN("");
        printLN("Position\t\t" + "Armée\t\t" + "Type\t\t" + "Intégrité (%)");
        for (int i = 0; i < 69; ++i) {
            print("-");
        }
        printLN("");
        afficheArmee();
    }

//    affiche noms, listBat, Pv et pos
    private static void afficheArmee() {
        printLN(etatArmee());
    }

    private static String etatArmee() {
        String out = "";
        for (Armee a : npVue.getListArmees()) {
            for (Bateau bat : a.getListBat()) {
                out += npVue.convertPosToStr(bat.getXY());
                out += "\t\t\t" + a.getNom();
                out += "\t\t" + bat.getTypeB();
                out += "\t\t" + (bat.getPv()*1.0 / bat.getMaxPv()) * 100 + "%\t";
                out += "\n";
                
            }
        }
        return out;
    }

    private static void affDestPoss(Bateau b) {
        List<Position> list = npVue.listDestPoss(b);
        for (Position p : list) {
            if(npVue.caseAccessible(p.getPosX(), p.getPosY()))
                print("["+npVue.convertPosToStr(p)+"] ");
        }
        
    }

    public static boolean partieFinie() {
        for (Armee a : npVue.getListArmees()) {
            return a.getSizeListBat() == 0;
        }
        return false;
    }
    
    public static void partieFinieMsg(){
        for(Armee a : npVue.getListArmees())
            if(a.getSizeListBat()>0)
                print("GAME OVER\n" + a.getNom() + "a gagné! ^^");
    }

    public static void affTir(Armee joueur) {
        String batChoisi = "";
        print("Avec quel bateau voulez-vous tirer, " + joueur.getNom() + "? (ex: B5): ");
        batChoisi = toUpperCase(INSERT.nextLine());

//        if (ctrlNouvP.checkPosEtArmeeBat(joueur, batChoisi)) 
        npVue.tir(joueur, batChoisi);
    }

    public static void affMouvBat(Armee joueur) {
        String ouiNon = "";
        print("Déplacer un bateau de votre armée? (y/n): ");
        ouiNon = INSERT.nextLine();

        if (ouiNon.equals("y")) {
            String batChoisi = "";
            print("Quel bateau déplacer? (ex: B5): ");
            batChoisi = toUpperCase(INSERT.nextLine());
            Position posCour = npVue.convertStrToPos(batChoisi);
            
//          print("x: " + posCour.getPosX() + " ");
//          printLN("y: " + posCour.getPosY());
                  
            String destChoisi = "";
            Bateau b = joueur.getBatFromPos(posCour);

            printLN("Sélectionner une des destinations possibles: ");
            affDestPoss(b);
            System.out.print("\n");
            System.out.println("Entrez votre choix");
            destChoisi = toUpperCase(INSERT.nextLine());

            if (npVue.listDestPoss(b).contains(npVue.convertStrToPos(destChoisi))) {
                npVue.moveBat(b, posCour, destChoisi);
            }
        }
    }

    private static String toString(Case c) {
        if (c.getBat() != null) {
            if (c.getTypeBat() == TypeB.PETIT) {
                return "b";
            } else if (c.getTypeBat() == TypeB.GRAND) {
                return "B";
            } else {
                return " ";
            }
        } else {
            return " ";
        }
    }
    
    @Override
    public void update(Observable obs, Object o) {
        affMer();
        affEtatArmees();
//        NouvPartie np = (NouvPartie) obs;
    }

    public static void printLN(Object msg) {
        System.out.println(msg);
    }

    public static void print(Object msg) {
        System.out.print(msg);
    }

}
