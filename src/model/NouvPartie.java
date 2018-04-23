/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;



import java.util.LinkedList;
import java.util.List;
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
    
    public boolean caseAccessible(int x, int y){
        return gb.getMer()[x][y].caseAccessible();
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

    public String convertPosToStr(Position p) {
        if (gb.mapPositionsContains(p)) 
            for (String cle : gb.mapPositionsKeySet()) 
                if (gb.getElemtMapPositions(cle).equals(p)) 
                    return cle;
        return null;
    }
    
    //    vérifie si la pos est valide et qu'un bateau fait partie de l'armée courante
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
    
    public void moveBat(Bateau b, Position posCour, String destChoisi){
        if(caseAccessible(posCour.getPosX(), posCour.getPosY())){
            posCour.setPosX(convertStrToPos(destChoisi).getPosX());
            posCour.setPosY(convertStrToPos(destChoisi).getPosY());
            b.setPos(posCour.getPosX(), posCour.getPosY());
        }
    }
    
    private Position goLeft(Position p, int pm){
        Position pos = new Position(p.getPosX(),p.getPosY());
        
        for(int i = 0; i < pm; ++i)
            if(pos.getPosX() == 0)
                pos.setPosX(gb.getTAILLE() - 1);
        else
                pos.setPosX(pos.getPosX() - 1);
        return pos;
    }
    
    private Position goRight(Position p, int pm){
        Position pos = new Position(p.getPosX(),p.getPosY());
        
        for(int i = 0; i < pm; ++i)
            if(pos.getPosX() == gb.getTAILLE() - 1)
                pos.setPosX(0);
        else
                pos.setPosX(pos.getPosX() + 1);
        return pos;
    }
    
    private Position goUp(Position p, int pm){
        Position pos = new Position(p.getPosX(),p.getPosY());
        
        for(int i = 0; i < pm; ++i)
            if(pos.getPosY() == 0)
                pos.setPosY(gb.getTAILLE() - 1);
        else
                pos.setPosY(pos.getPosY() - 1);
        return pos;
    }
    
    private Position goDown(Position p, int pm){
        Position pos = new Position(p.getPosX(),p.getPosY());
        
        for(int i = 0; i < pm; ++i)
            if(pos.getPosY() == gb.getTAILLE() - 1)
                pos.setPosY(0);
        else
                pos.setPosY(pos.getPosY() + 1);
        return pos;
    }
    
    //    destinations possibles
    public List<Position> listDestPoss(Bateau b) {
        List<Position> dest = new LinkedList<>();
        Position p = b.getXY();
        Position top = goUp(p,b.getPm());
        Position bot = goDown(p,b.getPm());
        Position left = goLeft(p,b.getPm());
        Position right = goRight(p,b.getPm());
        
        if(posValide(convertPosToStr(p)))
            dest.add(top);
        if(posValide(convertPosToStr(p)))
            dest.add(bot);
        if(posValide(convertPosToStr(p)))
            dest.add(left);
        if(posValide(convertPosToStr(p)))
            dest.add(right);
        return dest;
    }
    
    public void permCircul(Position p){
        if(p.getPosX() > gb.getTAILLE())
            p.setPosX(p.getPosX() - gb.getTAILLE());
        if(p.getPosX() < 0)
            p.setPosX(p.getPosX() + gb.getTAILLE());
        
        if(p.getPosY() > gb.getTAILLE())
            p.setPosY(p.getPosY() - gb.getTAILLE());
        if(p.getPosY() < 0)
            p.setPosY(p.getPosY() + gb.getTAILLE());
    }
    
    public void tir(Armee a, String pos){
        if(checkPosEtArmeeBat(a, pos)){
            Position posBatChoisi = convertStrToPos(pos);
            Bateau b = a.getBatFromPos(posBatChoisi);
            b.randomPortee();                         
            if (!(b.getPortee() == 0)) 
                for(Position p : porteeTir(b)){ 
                    permCircul(p);
                    for(Armee ar : this.listArmee)
                        if(!ar.getNom().equals(a.getNom()))//si bat == ennemi => pewpew!
                            for(Bateau bat : ar.getListBat()) {
                                bat.touché();
                                if(bat.getPv() <= 0)
                                    coulé(ar,bat);
                            }
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
        }return zoneTir;
    }

    public void coulé(Armee a, Bateau b) {
        List<Bateau> listBat = new LinkedList<>();
        for(Bateau bat : a.getListBat())
            listBat.add(bat);
        for(Bateau bat : listBat)
            if(bat.equals(b))
                a.getListBat().remove(b);
        setChangedAndNotify();
    }
    
    public void setChangedAndNotify() {
        setChanged();
        notifyObservers();
    }

    private void setChangedAndNotify(Object o) {
        setChanged();
        notifyObservers(o);
    }
}
