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
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import static jdk.nashorn.internal.objects.NativeString.toUpperCase;
import model.BateauGrand;
import model.Case;
import model.TypeB;

/**
 *
 * @author 2208sptheodorou
 */
public class VueConsole implements Observer {

    private final Scanner insert = new Scanner(System.in);
    private final Controleur ctrl;
    private final NouvPartie ctrlNouvP;

    public VueConsole(Controleur ctrl) {
        this.ctrl = ctrl;
        this.ctrlNouvP = ctrl.getNpVue();
    }

    public void affMer() {
        printLN("");
        print("   ");
        for (int i = 0; i < ctrlNouvP.getTailleGb(); i++) {
            print(ctrlNouvP.getAxeXGb()[i] + " ");
        }
        printLN("");
        for (int i = 0; i < ctrlNouvP.getTailleGb(); i++) {
            print(ctrlNouvP.getAxeYGb()[i] + " ");
            for (int j = 0; j < ctrlNouvP.getTailleGb(); j++) {

                print("|" + toString(ctrlNouvP.getMerGb()[i][j]));
            }
            printLN("|");
        }
    }

    @Override
    public void update(Observable obs, Object o) {
        affMer();
        affEtatArmees();
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
        ctrlNouvP.setChangedAndNotify();
    }

//    affiche noms, listBat, Pv et pos
    private void afficheArmee() {
        printLN(etatArmee());
    }

    private String etatArmee() {
        String out = "";
        for (Armee a : ctrlNouvP.getListArmees()) {
            for (Bateau bat : a.getListBat()) {
                out += ctrlNouvP.convertPosToStr(bat.getXY());
                out += "\t\t\t" + a.getNom();
                out += "\t\t" + bat.getTypeB();
                out += "\t\t" + (bat.getPv() / bat.getMaxPv()) * 100 + '%'
                        + "\t     |";
                out += "\n";
            }
        }
        return out;
    }

//    destinations possibles
    private List<Position> listDestPoss(Bateau b) {
        List<Position> dest = new LinkedList<>();
        int pm;
        if(b.getTypeB()==TypeB.GRAND)
            pm = 1;
        else
            pm = 2;
            
        for(int i = 0; i < pm; ++i){
            for(int j = 0; j < pm; ++j)
                if(i >= pm && i < pm * 2){
                    Position p = new Position(b.getX(), b.getY());
                    dest.add(p);
                }
            printLN("");
        }
        return dest;
    }
//        Position p = new Position(b.getX(),b.getY());
//        Position top = ctrlNouvP.permCircul(p, b.getPm(), "haut"*/);
//        Position bot = ctrlNouvP.permCircul(p, b.getPm(), "bas");
//        Position left = ctrlNouvP.permCircul(p, b.getPm(), "gauche");
//        Position right = ctrlNouvP.permCircul(p, b.getPm(), "droite");
//        
//        if(ctrlNouvP.posValide(ctrlNouvP.convertPosToStr(top)))
//            dest.add(top);
//        if(ctrlNouvP.posValide(ctrlNouvP.convertPosToStr(bot)))
//            dest.add(bot);
//        if(ctrlNouvP.posValide(ctrlNouvP.convertPosToStr(left)))
//            dest.add(left);
//        if(ctrlNouvP.posValide(ctrlNouvP.convertPosToStr(right)))
//            dest.add(right);
    

    private void affDestPoss(Bateau b) {
        List<Position> list = listDestPoss(b);
        for (Position p : list) {
            print(ctrlNouvP.convertPosToStr(p));
        }
    }

    public boolean partieFinie() {
        for (Armee a : ctrlNouvP.getListArmees()) {
            return a.getSizeListBat() == 0;
        }
        return false;
    }

    public void affTir(Armee joueur) {
        String batChoisi = "";
        print("Avec quel bateau voulez-vous tirer, " + joueur.getNom() + "? (ex: B5): ");
        batChoisi = toUpperCase(insert.nextLine());

//        if (ctrlNouvP.checkPosEtArmeeBat(joueur, batChoisi)) 
        ctrlNouvP.tir(joueur, batChoisi);
        ctrlNouvP.setChangedAndNotify();
    }

    public void affMouvBat(Armee joueur) {
        String ouiNon = "";
        print("Déplacer un bateau de votre armée? (y/n): ");
        ouiNon = insert.nextLine();

        if (ouiNon.equals("y")) {
            String batChoisi = "";
            print("Quel bateau déplacer? (ex: B5): ");
            batChoisi = toUpperCase(insert.nextLine());
            //Position courante = new Position(ctrlNouvP.strToPosX(batChoisi), ctrlNouvP.strToPosY(batChoisi));
            Position courante = ctrlNouvP.convertStrToPos(batChoisi);
            
            print("x: " + courante.getPosX() + " ");
            printLN("y: " + courante.getPosY());
                  
            String destChoisi = "";
            Bateau b = joueur.getBatFromPos(courante);

            printLN("Sélectionner une des destinations possibles: ");
            affDestPoss(b);
            destChoisi = toUpperCase(insert.nextLine());

            if (listDestPoss(b).contains(ctrlNouvP.convertStrToPos(destChoisi))) {
                ctrlNouvP.mouvBat(courante, destChoisi);
            }
        }
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

    public static void printLN(Object msg) {
        System.out.println(msg);
    }

    public static void print(Object msg) {
        System.out.print(msg);
    }

}
