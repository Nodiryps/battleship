/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.Armee;
import model.Bateau;
import model.Gameboard;
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

    private final Scanner insert = new Scanner(System.in);
    private final NouvPartie np = new NouvPartie(insert.nextInt());
    

    public static void printLN(Object msg) {
        System.out.println(msg);
    }

    public static void print(Object msg) {
        System.out.print(msg);
    }

    public void afficheMer() {
        affMer();
    }

    private void affMer() {

        print("   ");
        for (int i = 0; i < np.getGb().getTAILLE(); i++) {
            print(np.getGb().getAXE_X()[i] + " ");
        }
        printLN("");
        for (int i = 0; i < np.getGb().getTAILLE(); i++) {
            print(np.getGb().getAXE_Y()[i] + " ");
            for (int j = 0; j < np.getGb().getTAILLE(); j++) {
                System.out.println(" " + np.getGb().getMer()[i][j]);
            }
            printLN("");
        }
        printLN("");
    }

    public void AfficheEtatArmees() {
        List<Armee> list = np.getListArmees();
        printLN("Etat des Armées");
        printLN("Armée"); 
        for(Armee a : list)
            afficheArmee(a);
    }

//    affiche noms, listBat, Pv et pos
    private void afficheArmee(Armee a) {
        
        for (Entry<String, List> entry : a.getMapJoueur().entrySet()) {
            String key = entry.getKey();
            List val = entry.getValue();
            printLN(key); 
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
            System.out.println(b.getPv());
        }
    }
    
//    destinations possibles
    private List destPoss(){
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
        List<Position> list = destPoss();
        for(Position p : list){
            print(p);
        }
    }

    public void affBougerBat() {
        String ouiNon = "";
        do{
            print("Déplacer un bateau de votre armée? (y/n): ");
            ouiNon = insert.nextLine();
            
        }while(ouiNon.compareTo("y") <=0 || ouiNon.compareTo("n") <=0);
        
        if(ouiNon.equals('y')){
            do{
                print("Quel bateau déplacer? (ex: B5): ");
                Position batChoisi = np.selectBat(toUpperCase(insert.nextLine()));
            }while(!np.posValide(insert.nextLine()));
            if(np.posValide(insert.nextLine())){
                do{
                    printLN("Sélectionner une des destinations possibles: ");
                    affDestPoss();
                    
                }
                put les pos ds une list
                        si pos entrée est ds la list, on bouge le bat
                                sinon do while
            }
        }
        else{
            c est a l adevrsaire de jouer
        }
        
    }

    @Override
    public void update(Observable obs, Object o) {
        
    }

    

}
