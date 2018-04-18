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
    
    public VueConsole(Controleur ctrl){
        this.ctrl = ctrl;
        this.ctrlNouvP = ctrl.getNpVue();
    }
    
    public void affMer() {

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
    
    
    private String etatArmee (){
       String out = "";
           for(Armee a : ctrlNouvP.getListArmees() ){
               for(Bateau bat : a.getListBat()){
                    out += ctrlNouvP.convertPosToStr(bat.getX(), bat.getY());/*(pstr[a.getBatFromList(nbl).getX()]) + (a.getBatFromList(nbl).getY())*/  
                    out += "\t\t\t" + a.getNom();
                    out += "\t\t" + bat.getTypeB();
                    out += "\t\t" + (bat.getPv()/bat.getMaxPv())*100+'%';
                    out += "\n";
               }
           }
            
       return out;
    }

    public void affEtatArmees() {
        List<Armee> list = ctrlNouvP.getListArmees();
        printLN("Etat des Armées");
        printLN("Armée"); 
        printLN("Position\t\t" + "Armée\t\t" + "Type\t\t"+"Intégrité (%)\n");
        afficheArmee();
    }

//    affiche noms, listBat, Pv et pos
    private void afficheArmee() {
            printLN(etatArmee());
    }
   
//    destinations possibles
    private List listDestPoss(){
        List<Position> dest = new LinkedList<>();
        List<Armee> armee = ctrlNouvP.getListArmees();
        for(Armee a : armee){
            for(Bateau b : a.getListBat()){
                for(int i = 1; i <= b.getX() + b.getPm() ;++i){      //les cases à gauche du bateau
                    for(int j = 1; j <= b.getY( )- b.getPm(); ++j){  //les cases à droite
                        Position pos = new Position(i,j);
                        dest.add(pos);
                    }
                }
            }
        } return dest;
    }
    
    private void affDestPoss() {
        List<Position> list = listDestPoss();
        for(Position p : list){
            print(p);
        }
    }
    
    //    vérifie si un bateau fait partie de l'armée courante
    public boolean batAppartientArmee(Armee armeeCou, Position posBatChoisi){//remplacer la fin de tir() par cette méthode
        for(Armee e : ctrlNouvP.getListArmees())
            for(Bateau bat : e.getListBat())
                if(e.getNom().equals(armeeCou.getNom()))
                    return bat.getXY() == posBatChoisi;
        return false;
    }
    
    public boolean partieFinie() {
        for(Armee a : ctrlNouvP.getListArmees())
            return a.getSizeListBat() == 0;
        return false;
    }
    
    public void affTir() {
        if(!partieFinie()){
            List<Armee> joueurList = ctrlNouvP.getListArmees();
            for(int i = 1; i <= joueurList.size(); ++i){
                Armee joueur = joueurList.get(i);
                String batChoisi = "";
                        do{
                            print("Avec quel bateau voulez-vous tirer, " + joueur.getNom() + "? (ex: B5): ");
                            batChoisi = toUpperCase(insert.nextLine());
                                
                        }while(batChoisi.length() != 2 || !ctrlNouvP.posValide(batChoisi) || !batAppartientArmee(joueur,ctrlNouvP.convertStrToPos(batChoisi)));

                        if(batChoisi.length() == 2 && ctrlNouvP.posValide(batChoisi) && batAppartientArmee(joueur,ctrlNouvP.convertStrToPos(batChoisi))){
                            ctrlNouvP.tir(joueur, batChoisi);
                        }
                        ctrlNouvP.setChangedAndNotify();
            }
        }
        else
            print("GAME OVER!");
    }

    public void affMouvBat() {
        List<Armee> joueurList = ctrlNouvP.getListArmees();
        for(int cpt = 1; cpt <= joueurList.size(); ++cpt){
            Armee joueur = joueurList.get(cpt);
            do
            {
                String ouiNon = "";
                do{
                    print("Déplacer un bateau de votre armée? (y/n): ");
                    ouiNon = insert.nextLine();

                }while(!ouiNon.equals("y") || !ouiNon.equals("n"));

                if(ouiNon.equals('y')){
                    String batChoisi = "";
                    do{
                        print("Quel bateau déplacer? (ex: B5): ");
                        batChoisi = toUpperCase(insert.nextLine());

                    }while(batChoisi.length() != 1 || !ctrlNouvP.posValide(batChoisi) || !batAppartientArmee(joueur, ctrlNouvP.convertStrToPos(batChoisi)));

                    if(ctrlNouvP.posValide(batChoisi) && batAppartientArmee(joueur, ctrlNouvP.convertStrToPos(batChoisi))){
                        Position courante = new Position(ctrlNouvP.strToPosX(batChoisi),ctrlNouvP.strToPosY(batChoisi));
                        String destChoisi = "";
                        do{
                            printLN("Sélectionner une des destinations possibles: ");
                            affDestPoss();
                            destChoisi = toUpperCase(insert.nextLine());

                        }while(destChoisi.length() != 1 || !listDestPoss().contains(ctrlNouvP.convertStrToPos(destChoisi)));

                        if(listDestPoss().contains(ctrlNouvP.convertStrToPos(destChoisi)))
                            ctrlNouvP.mouvBat(joueur, courante, destChoisi);
                    }
                }
                if(cpt == ctrlNouvP.getNbJ())
                    cpt = 1;

            }while(cpt <= ctrlNouvP.getNbJ() && !partieFinie());
        }
    }
    
    private String toString(Case c){
        if(c.getBat() != null){
            if(c.getTypeBat() == TypeB.PETIT) return "b";
            else if(c.getTypeBat() == TypeB.GRAND) return "B";
            else return " ";
        }
        else return " ";
    }

    public static void printLN(Object msg) {
        System.out.println(msg);
    }

    public static void print(Object msg) {
        System.out.print(msg);
    }

}
