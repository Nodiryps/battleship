/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;



import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Scanner;
import static view.VueConsole.print;

/**
 *
 * @author Spy
 */
public class NouvPartie extends Observable {
    private Gameboard gb;
    private final static int nbJ = 2;
    private final List<Armee> listArmee;
    
    public NouvPartie(int size, List<String> noms) {
        this.gb = new Gameboard(size);
        this.listArmee = creationArmees(noms);
        this.gb.nouvMer(listArmee);
    }

    public static NouvPartie getNP(){
        Scanner insert = new Scanner(System.in);
        List<String> noms = new LinkedList<>();
        print("Taille de votre mer (5 à 26): ");
        int size = insert.nextInt();
        for(int i = 0; i < nbJ; ++i){ 
            print("J" + (i+1) + ": ");
            String s = insert.next();
            noms.add(s);
        }
        return new NouvPartie(size, noms);
    }
    
    private static List<Armee> creationArmees(List<String> noms) {
        List<Armee> list = new LinkedList<>();
        for(String nom : noms)
            list.add(new Armee(nom));
        return list;
    }

    public NouvPartie(int size) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Gameboard getGb() {
        return gb;
    }
    
    public int getTailleGb(){
        return this.gb.getTAILLE();
    }
    
    public char[] getAxeXGb(){
        return this.gb.getAXE_X();
    }
    
    public int[] getAxeYGb(){
        return this.gb.getAXE_Y();
    }
    
    public Case[][] getMerGb(){
        return this.gb.getMer();
    }

    public int getNbJ() {
        return nbJ;
    }
    
    public List<Armee> getListArmees() {
        return this.listArmee;
    }
    
    public int getListArmeesSize(){
        return this.getListArmees().size();
    }
    
    public int strToPosX(String s){
        return convertStrToPos(s).getPosX();
    }
    
    public int strToPosY(String s){
        return convertStrToPos(s).getPosY();
    }

    public Position convertStrToPos(String s) {
        return gb.getElemtMapPositions(s);
    }
//Position p = new Position(0,0);
        
//        char[] toCharArray = s.toCharArray();//découpe le String "B5" en tab de chars
//        char x = toCharArray[0];
//        int y = Integer.parseInt(Character.toString(toCharArray[1]));
//        //x = 'B' et y = '5'

////        int i = getNumericValue(y);//converti '5' en 5
//          System.out.println("val lettre " + x);

//          System.out.println("val chiffre " + y);

//        for (int j = 0; j < gb.getMer().length; ++j) 
//            for (int k = 0; k < gb.getMer()[j].length; ++k) 
//                if (gb.getMer()[j][k] == x ) 
//                    p.setPosY(j);
//                
////        for (int j = 0; j < gb.getMer().length; ++j) 
////            for (int k = 0; k < gb.getMer()[j].length; ++k)
////                if(gb.getAXE_Y()[k] == y)
////                    p.setPosX(k);
//        return p;
    

    public String convertPosToStr(Position p) {
        int x = p.getPosX(), y = p.getPosY();
        String res = "";
        for (int i = 0; i < gb.getTAILLE(); ++i) 
            for (int j = 0; j < gb.getTAILLE(); ++j) 
                if (i == x && j == y) 
                    res += gb.getAXE_X()[i] + "" + gb.getAXE_Y()[j];
        return res;
    }
    
    //    vérifie si la pos est valide qu'un bateau fait partie de l'armée courante
    public boolean checkPosEtArmeeBat(Armee armeeCou, String posBatChoisi) {
//        if(posValide(posBatChoisi))
                if (armeeCou.getBatFromPos(convertStrToPos(posBatChoisi))!= null)
                    return true;
        return false;
    }

    public boolean posValide(String s) {
        Position p = convertStrToPos(s);
        return (p.getPosX() >= 0 && p.getPosX() < gb.getTAILLE()) &&
               (p.getPosY() >= 0 && p.getPosY() < gb.getTAILLE());
    }
    
    public void mouvBat(Position posCour, String destChoisi){
        posCour.setPosX(convertStrToPos(destChoisi).getPosX());
        posCour.setPosY(convertStrToPos(destChoisi).getPosX());
    }
    
    public void permCircul(Position p/*, int pm, String direction*/){
        if(p.getPosX() > gb.getTAILLE())
            p.setPosX(p.getPosX() - gb.getTAILLE());
        if(p.getPosX() < 0)
            p.setPosX(p.getPosX() + gb.getTAILLE());
        
        if(p.getPosY() > gb.getTAILLE())
            p.setPosY(p.getPosY() - gb.getTAILLE());
        if(p.getPosY() < 0)
            p.setPosY(p.getPosY() + gb.getTAILLE());
//        for(int i = 0; i < pm; ++i){
//            if(direction.equals("gauche") && p.getPosX() == 0)
//                p.setPosX(gb.getTAILLE() - 1);
//            else if(direction.equals("gauche") && p.getPosX()!=0)
//                p.setPosX(p.getPosX() - 1);
//            else if(direction.equals("droite") && p.getPosX() == gb.getTAILLE() - 1)
//                p.setPosX(0);
//            else if(direction.equals("droite") && p.getPosX() != gb.getTAILLE() - 1)
//                p.setPosX(p.getPosX() + 1);
//            
//            else if(direction.equals("haut") && p.getPosY() == 0)
//                p.setPosY(gb.getTAILLE() - 1);
//            else if(direction.equals("haut") && p.getPosY() != 0)
//                p.setPosY(p.getPosY() - 1);
//            else if(direction.equals("bas") && p.getPosY() == gb.getTAILLE() - 1)
//                p.setPosY(0);
//            else if(direction.equals("bas") && p.getPosY() != gb.getTAILLE() - 1)
//                p.setPosY(p.getPosY() + 1);
//        }
//        return p;
    }
    
    public void tir(Armee a, String pos) {
        if(checkPosEtArmeeBat(a, pos)){
            Position posBatChoisi = convertStrToPos(pos);
            Bateau b = a.getBatFromPos(posBatChoisi);
            b.randomPortee();                         
            if (!(b.getPortee() == 0)) 
                for(Position p : porteeTir(b)) 
                    for(Armee ar : this.listArmee)
                        if(!ar.getNom().equals(a.getNom()))//si bat est ennemi pewpew!
                            for(Bateau bat : ar.getListBat()) {
                                bat.touché();
                                if(bat.getPv() <= 0)
                                    coulé(bat);
                            }
            setChangedAndNotify();
        }
    }
    
    public List<Position> porteeTir(Bateau b){
        List<Position> zoneTir = new LinkedList<>();
        for(int i = b.getX() - b.getPortee(); i <= b.getX() + b.getPortee() ;++i){      //les cases à gauche du bateau
            for(int j = b.getY() - b.getPortee(); j <= b.getY() + b.getPortee(); ++j){  //les cases à droite
                Position pos = new Position(i,j);
                zoneTir.add(pos);
            }
        } return zoneTir;
    }

    public void coulé(Bateau b) {
        List<Armee> list = this.listArmee;
        for(Armee a : list)
            for(Bateau bat : a.getListBat())
                if(bat.equals(b))
                    a.getListBat().remove(b);
        setChangedAndNotify();
    }
    
    public void setChangedAndNotify() {
        setChanged();
        notifyObservers();
    }
}
