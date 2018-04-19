/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static java.lang.Character.getNumericValue;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;
import static view.VueConsole.print;
import static view.VueConsole.printLN;

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
//            printLN("");
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
        return stringToPos(s).getPosX();
    }
    
    public int strToPosY(String s){
        return stringToPos(s).getPosY();
    }
    
    //    récupéation pos inséré par le user ("B5" -> pos)
    public Position convertStrToPos(String s) {
        return stringToPos(s);
    }
    
    //    convert pos en string pour pouvoir afficher la position (pos -> "B5")
    public String convertPosToStr(int x, int y) {
        return posToString(x, y);
    }

    private Position stringToPos(String s) {
        Position p = new Position(0, 0);
        
        char[] toCharArray = s.toCharArray();//découpe le String "B5" en tab de chars
        char x = toCharArray[0];
        char y = toCharArray[1];
        //x = 'B' et y = '5'
        int i = getNumericValue(y);//converti '5' en 5

        for (int j = 0; j < gb.getTAILLE(); ++j) 
            for (int k = 0; k < gb.getTAILLE(); ++k) 
                if (gb.getAXE_X()[j] == x && gb.getAXE_Y()[k] == i) {
                    p.setPosX(j);
                    p.setPosY(k);
                }
        return p;
    }

    private String posToString(int x, int y) {
        String res = "";
        for (int i = 0; i < gb.getTAILLE(); ++i) 
            for (int j = 0; j < gb.getTAILLE(); ++j) 
                if (i == x && j == y) 
                    res += gb.getAXE_X()[i] + "" + gb.getAXE_Y()[j];
        return res;
    }

    public boolean posValide(String s) {
        Position p = stringToPos(s);
        return (p.getPosX() >= 0 && p.getPosX() < gb.getTAILLE()) &&
               (p.getPosY() >= 0 && p.getPosY() < gb.getTAILLE());
    }
    
    public void mouvBat(Armee joueur, Position courante, String destChoisi){
        for(Bateau b : joueur.getListBat())
            if(b.getX() == courante.getPosX() && b.getY() == courante.getPosY())
                b.setPos(convertStrToPos(destChoisi).getPosX(), convertStrToPos(destChoisi).getPosY());
    }
    
    public void tir(Armee a, String pos) {
        
        Position batChoisi = convertStrToPos(pos);

        for (Bateau b : a.getListBat()) {
            Position p = new Position(b.getX(), b.getY());//choppe la pos des bat de la liste
            if (batChoisi.equals(p)) {                    //choppe la pos du bateau choisi
                b.randomPortee();                         //set la portée
                if (b.getPortee() != 0) {
                    List<Position> zoneTir = b.porteeTir();
                    for(Position p2 : zoneTir) {
////////////////////////////////////////////////////////////////////////////////p2 -> circulaire
                        for(Armee ar : this.listArmee)
                            if(!ar.getNom().equals(a.getNom()))
                                for(Bateau bat : ar.getListBat()){
                                    bat.touché();
                                    if(bat.getPv() <= 0)
                                        coulé(bat);
                                }
                    }
                }
            }
        }
    }

    public void coulé(Bateau b) {
        List<Armee> list = this.listArmee;
        for(Armee a : list)
            for(Bateau bat : a.getListBat())
                if(bat.equals(b))
                    a.getListBat().remove(b);
    }
    
    public void setChangedAndNotify() {
        setChanged();
        notifyObservers();
    }

    

    
}
