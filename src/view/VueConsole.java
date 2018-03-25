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

/**
 *
 * @author 2208sptheodorou
 */
public class VueConsole implements Observer {

    private final int SIZE;
    private final Controleur ctrl;
    private final Scanner insert = new Scanner(System.in);
    private final NouvPartie np;
    
    public VueConsole(){
        this.ctrl = new Controleur();
        print("Taille de votre mer (5 à 26): ");
        this.SIZE = insert.nextInt();
        this.np = new NouvPartie(SIZE);
    }
    
    public NouvPartie getNpVue(){
        return np;
    }
    
    public void affNomArmees(){
        for(int i = 1; i <= np.getNbJ(); i++){
            print("J" + i + ": ");
            np.creationArmees(insert.nextLine());
            printLN("");
        }
    }
    
    public void affMer() {

        print("   ");
        for (int i = 0; i < np.getGb().getTAILLE(); i++) {
            print(np.getGb().getAXE_X()[i] + " ");
        }
        printLN("");
        for (int i = 0; i < np.getGb().getTAILLE(); i++) {
            print(np.getGb().getAXE_Y()[i] + " ");
            for (int j = 0; j < np.getGb().getTAILLE(); j++) {
                print("|" + np.getGb().getMer()[i][j]);
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
        List<Armee> list = np.getListArmees();
        printLN("Etat des Armées");
        printLN("Armée"); 
        for(Armee a : list)
            afficheArmee(a);
    }

//    affiche noms, listBat, Pv et pos
    private void afficheArmee(Armee a) {
        
        for (Entry<String, List> entry : a.getMapJoueur().entrySet()) {
            String nomArmee = entry.getKey();
            List val = entry.getValue();
            printLN(nomArmee); 
            printLN(val);
            affichePV(a);
            affichePosBat(a);
        }
    }
    
    private void affichePosBat(Armee a) {
        for (Bateau b : a.getListBat()) {
            printLN(np.convertPosToStr(b.getX(), b.getY()));
        }
    }

    private void affichePV(Armee a) {
        for (Bateau b : a.getListBat()) {
            printLN(b.getPv());
        }
    }
    
//    destinations possibles
    private List listDestPoss(){
        List<Position> dest = new LinkedList<>();
        List<Armee> armee = np.getListArmees();
        for(Armee a : armee){
            for(Bateau b : a.getListBat()){
                for(int i = 1; i <= b.getX()+b.getPm() ;++i){      //les cases à gauche du bateau
                    for(int j = 1; j <= b.getY()-b.getPm(); ++j){  //les cases à droite
                        Position pos = new Position(i,j);
                        dest.add(pos);
                    }
                }
            }
        } return dest;
    }
    
    public void affDestPoss() {
        List<Position> list = listDestPoss();
        for(Position p : list){
            print(p);
        }
    }
    
    //    vérifie si un bateau fait partie de l'armée courante
    public boolean batAppartientArmee(Armee armeeCou, Position p){//remplacer la fin de tir() par cette méthode
        for(Armee e : np.getListArmees())
            for(Bateau bat : e.getListBat())
                if(!e.getNom().equals(armeeCou.getNom()))
                    return bat.getPosBat() == p;
        return false;
    }
    
    public boolean partieFinie() {
        for(Armee a : np.getListArmees())
            return a.getSizeListBat() == 0;
        return false;
    }
    
    public void affTir() {
        if(!partieFinie()){
            List<Armee> joueurList = np.getListArmees();
            for(int i = 1; i <= joueurList.size(); ++i){
                Armee joueur = joueurList.get(i);
                String batChoisi = "";
                        do{
                            print("Avec quel bateau voulez-vous tirer, " + joueur.getNom() + "? (ex: B5): ");
                            batChoisi = toUpperCase(insert.nextLine());

                        }while(batChoisi.length() != 2 || !np.posValide(batChoisi) || !batAppartientArmee(joueur,np.convertStrToPos(batChoisi)));

                        if(batChoisi.length() == 2 && np.posValide(batChoisi) && batAppartientArmee(joueur,np.convertStrToPos(batChoisi))){
                            np.tir(joueur, batChoisi);
                        }
                        np.getGb().setChangedAndNotify();
            }
        }
        else
            print("GAME OVER!");
    }

    public void affMouvBat() {
        List<Armee> joueurList = np.getListArmees();
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

                    }while(batChoisi.length() != 1 || !np.posValide(batChoisi) || !batAppartientArmee(joueur, np.convertStrToPos(batChoisi)));

                    if(np.posValide(batChoisi) && batAppartientArmee(joueur, np.convertStrToPos(batChoisi))){//pas sur
                        Position courante = new Position(np.convertStrToPos(batChoisi).getPosX(),np.convertStrToPos(batChoisi).getPosY());
                        String destChoisi = "";
                        do{
                            printLN("Sélectionner une des destinations possibles: ");
                            affDestPoss();
                            destChoisi = toUpperCase(insert.nextLine());

                        }while(destChoisi.length() != 1 || !listDestPoss().contains(np.convertStrToPos(destChoisi)));

                        if(listDestPoss().contains(np.convertStrToPos(destChoisi)))
                            for(Bateau b : joueur.getListBat())
                                if(b.getX() == courante.getPosX() && b.getY() == courante.getPosY())
                                    b.setPos(np.convertStrToPos(destChoisi).getPosX(), np.convertStrToPos(destChoisi).getPosY());
                    }
                }
                if(cpt == np.getNbJ())
                    cpt = 1;

            }while(cpt <= np.getNbJ() && !partieFinie());
        }
    }
    
    private String toStringBatGd(){
        return "B";
    }
    
    private String toStringBatPt(){
        return "b";
    }
    
    private String toStringCase(){
        return " ";
    }

    public static void printLN(Object msg) {
        System.out.println(msg);
    }

    public static void print(Object msg) {
        System.out.print(msg);
    }

}
