/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import control.Controleur;
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

    private static final Scanner insert = new Scanner(System.in);
    private final Controleur ctrl;
    private NouvPartie ctrlNP;
    
    public VueConsole(Controleur c){
        this.ctrl = c;
        this.ctrlNP = ctrl.getNpCtrl(); 
    }
    
    public void affMer() {
        printLN("");
        print("   ");
        for (int i = 0; i < ctrlNP.getTailleGb(); i++) {
            print(ctrlNP.getAxeXGb()[i] + " ");
        }
        printLN("");
        for (int i = 0; i < ctrlNP.getTailleGb(); i++) {
            print(ctrlNP.getAxeYGb()[i] + " ");
            for (int j = 0; j < ctrlNP.getTailleGb(); j++) {

                print("|" + toString(ctrlNP.getMerGb()[i][j]));
            }
            printLN("|");
        }
    }

    public void affEtatArmees() {
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
    private void afficheArmee() {
        printLN(etatArmee());
    }

    private String etatArmee() {
        String out = "";
        for (Armee a : ctrlNP.getListArmees()) {
            for (Bateau bat : a.getListBat()) {
                out += ctrlNP.convertPosToStr(bat.getXY());
                out += "\t\t\t" + a.getNom();
                out += "\t\t" + bat.getTypeB();
                out += "\t\t" + (bat.getPv()*1.0 / bat.getMaxPv()) * 100 + "%\t";
                out += "\n";
                
            }
        }
        return out;
    }

    private void affDestPoss(Bateau b) {
        List<Position> list = ctrlNP.listDestPoss(b);
        for (Position p : list) {
            if(ctrlNP.caseAccessible(p.getPosX(), p.getPosY()))
                print(ctrlNP.convertPosToStr(p) + " | ");
        }
    }

    public boolean partieFinie() {
        for (Armee a : ctrlNP.getListArmees()) {
            return a.getSizeListBat() == 0;
        }
        return false;
    }
    
    public void partieFinieMsg(){
        for(Armee a : ctrlNP.getListArmees())
            if(a.getSizeListBat()>0)
                print("GAME OVER\n" + a.getNom() + " a gagné! ^^");
    }

    public String affTir(Armee joueur) {
        String batChoisi = "";
        print("Avec quel bateau voulez-vous tirer, " + joueur.getNom() + "? (ex: B5): ");
        batChoisi = toUpperCase(insert.nextLine());

        return batChoisi;
    }

    public String affMoveBat(Armee joueur) {
        String ouiNon = "";
        print("Déplacer un bateau de votre armée? (y/n): ");
        ouiNon = insert.nextLine();
        
        String destChoisi = "";
        
        if (ouiNon.equals("y")) {
            String batChoisi = "";
            print("Quel bateau déplacer? (ex: B5): ");
            batChoisi = toUpperCase(insert.nextLine());
            Position posCour = ctrlNP.convertStrToPos(batChoisi);
            
            Bateau b = joueur.getBatFromPos(posCour);

            print("Sélectionner une des destinations suivantes: ");
            affDestPoss(b);
            printLN("");
            destChoisi = toUpperCase(insert.nextLine());
        }
        return destChoisi;
    }

    private String toString(Case c) {
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
    }

    public static void printLN(Object msg) {
        System.out.println(msg);
    }

    public static void print(Object msg) {
        System.out.print(msg);
    }

}
