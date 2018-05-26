/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import control.Controleur;
import java.util.ArrayList;
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
import model.TypeM;


enum Couleur {
    RESET("\u001B[0m"),
    YELLOW("\u001B[33m"),
    PURPLE("\u001B[35m");

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
        print("\n   ");
        for (int i = 0; i < ctrlNP.getTailleGb(); i++) {
            print(ctrlNP.getAxeXGb()[i] + " " + Couleur.RESET);
        }
        printLN("");
        for (int i = 0; i < ctrlNP.getTailleGb(); i++) {
            print(ctrlNP.getAxeYGb()[i] + " " + Couleur.RESET);
            for (int j = 0; j < ctrlNP.getTailleGb(); j++) {
                print(Couleur.RESET + "|" + toString(ctrlNP.getCaseGb(i,j)));
            }
            printLN(Couleur.RESET + "|");
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
                if(a.equals(ctrlNP.getArmeeFromList(0))){
                    out += Couleur.PURPLE + ctrlNP.convertPosToStr(bat.getXY());
                    out += "\t\t\t" + Couleur.PURPLE + a.getNom();
                    out += "\t\t" + Couleur.PURPLE + bat.getTypeB();
                    out += "\t\t" + Couleur.PURPLE + (bat.getPv()*1.0 / bat.getMaxPv()) * 100 + "%\t";
                    out += "\n";
                }
                else{
                    out += Couleur.YELLOW + ctrlNP.convertPosToStr(bat.getXY());
                    out += "\t\t\t" + Couleur.YELLOW + a.getNom();
                    out += "\t\t" + Couleur.YELLOW + bat.getTypeB();
                    out += "\t\t" + Couleur.YELLOW + (bat.getPv()*1.0 / bat.getMaxPv()) * 100 + "%\t";
                    out += "\n";
                }
            }
        }
        return out;
    }

    private void affDestPoss(Bateau b) {
        List<Position> list = ctrlNP.getListDestPoss(b);
        for (Position p : list) {
            if(ctrlNP.caseAccessible(p.getPosX(), p.getPosY()))
                print(ctrlNP.convertPosToStr(p) + " | ");
        }
    }

    public String affTir(Armee joueur) {
        String batChoisi = "";
        print("Avec quel bateau voulez-vous tirer, " + joueur.getNom() + "? (ex: B5): ");
        batChoisi = toUpperCase(insert.nextLine());
        
        while(!ctrlNP.checkBatBonneArmee(joueur, batChoisi)){
            printLN("Veuillez entrer une position valide ou un bateau vous appartenant, s.v.p.");
            batChoisi = toUpperCase(insert.nextLine());
        }
        return batChoisi;
    }
    
    public void tirMsg(Armee a, String p){
        Position pos = ctrlNP.convertStrToPos(p);
        Bateau b = a.getBatFromPos(pos);
        if(ctrlNP.tir(a, ctrlNP.convertPosToStr(b.getXY())))
            printLN("PEW! PEW!(portée: " + b.getPortee() + ")");
        else
            printLN("Vous avez fait " + b.getPortee() + " de portée... -____-\"");
    }

    public List<String> affMoveBat(Armee joueur) {
        List<String> choix = new ArrayList();
        print("Déplacer un bateau de votre armée? (y/n): ");
        String ouiNon = insert.nextLine();
        
        while(!ouiNon.equals("y") && !ouiNon.equals("n")){
            print("Yes ou No?: ");
            ouiNon = insert.nextLine();
        }
        choix.add(ouiNon);
        
        if (ouiNon.equals("y")) {
            
            print("Quel bateau déplacer? (ex: B5): ");
            String batChoisi = toUpperCase(insert.nextLine());
            while(!ctrlNP.checkBatBonneArmee(joueur, batChoisi)){
                printLN("Veuillez entrer une position valide ou un bateau vous appartenant, s.v.p.");
                batChoisi = toUpperCase(insert.nextLine());
            }
            Position posCour = ctrlNP.convertStrToPos(batChoisi);
            choix.add(batChoisi);
            Bateau b = joueur.getBatFromPos(posCour);

            print("Sélectionner une des destinations suivantes: ");
            affDestPoss(b);
            printLN("");
            String posChoisi = toUpperCase(insert.nextLine());
            choix.add(posChoisi);
            return choix;
        }
        else if(ouiNon.equals("n"))
            return choix;
        return choix;
    }

    private String toString(Case c) {
        Bateau batCase = c.getBat();
        if (batCase != null) {
            if (null == c.getTypeBat()) {
                return " ";
            } else switch (c.getTypeBat()) {
                case PETIT:
                    for(Armee a : ctrlNP.getListArmees())
                        for(Bateau b : a.getListBat())
                            if(batCase.getXY().equals(b.getXY()) && a.equals(ctrlNP.getArmeeFromList(0)))
                                return Couleur.PURPLE + "b";
                            else if(batCase.getXY().equals(b.getXY()) && a.equals(ctrlNP.getArmeeFromList(1)))
                                return Couleur.YELLOW + "b";
                    break;
                case GRAND:
                    for(Armee a : ctrlNP.getListArmees())
                        for(Bateau b : a.getListBat())
                            if(batCase.getXY().equals(b.getXY()) && a.equals(ctrlNP.getArmeeFromList(0)))
                                return Couleur.PURPLE + "B";
                            else if(batCase.getXY().equals(b.getXY()) && a.equals(ctrlNP.getArmeeFromList(1)))
                                return Couleur.YELLOW + "B";
                    break;
                default:
                    return " ";
            }
        }
        return " ";
    }
    
    public void partieFinieMsg(){
        for(Armee a : ctrlNP.getListArmees())
            if(a.getSizeListBat() > 0){
                print("\nGAME OVER\n" + a.getNom() + " a gagné! ^^");
            }
    }
    
    public void mineMsg(Bateau b){
        Case c = ctrlNP.getCaseGb(b.getX(), b.getY());
        if(c.getTypeMine() == TypeM.NORMALE)
            printLN("OUPS! Vous êtes tombé sur une mine...");
        if(c.getTypeMine() == TypeM.NORMALE)
            printLN("HOLLY SHIT! Vous êtes tombé sur une mine ATOMIQUE!!!");
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
