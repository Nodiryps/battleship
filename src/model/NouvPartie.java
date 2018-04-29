/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Scanner;
import java.util.Set;
import static view.VueConsole.print;

/**
 *
 * @author Spy
 */
public class NouvPartie extends Observable {
    private Builder bldr;
    private Gameboard gb;
    private final List<Armee> listArmee;

    public NouvPartie(Builder bldr) {
        this.bldr = bldr;
        this.gb = bldr.getGbBuilder();
        this.listArmee = bldr.getListArmee();
    }

    public static NouvPartie getNP(int nbj) {
        boolean auto = true;                    //pcq console
        Scanner insert = new Scanner(System.in);
        List<String> noms = new LinkedList<>();
        print("Taille de votre mer (5 à 26): ");
        int size = insert.nextInt();
        for (int i = 0; i < nbj; ++i) {
            print("J" + (i + 1) + ": ");
            String s = insert.next();
            noms.add(s);
        }
        return new NouvPartie(new Builder(size, noms, auto));
    }
   
    public int getTailleGb() {
        return this.gb.getTAILLE();
    }

    public char[] getAxeXGb() {
        return this.gb.getAXE_X();
    }

    public int[] getAxeYGb() {
        return this.gb.getAXE_Y();
    }

    public Case[][] getMerGb() {
        return this.gb.getMer();
    }

    public boolean caseAccessible(int x, int y) {
        return gb.getMer()[x][y].caseAccessible();
    }
    
    public boolean caseMineeN(Position p){
        return gb.getMer()[p.getPosX()][p.getPosY()].explosionMineN();
    }

    public boolean caseMineeA(Position p){
        return gb.getMer()[p.getPosX()][p.getPosY()].explosionMineA();
    }
    
    public List<Armee> getListArmees() {
        return this.listArmee;
    }

    public int getListArmeesSize() {
        return this.getListArmees().size();
    }
    
    public Armee getArmeeFromList(int i){
        return getListArmees().get(i);
    }

    public int strToPosX(String s) {
        return convertStrToPos(s).getPosX();
    }

    public int strToPosY(String s) {
        return convertStrToPos(s).getPosY();
    }

    public Position convertStrToPos(String s) {
        return gb.getPosFromMapPositions(s);
    }

    public String convertPosToStr(Position p) {
        if (gb.mapPositionsContains(p)) {
            for (String cle : gb.mapPositionsKeySet()) {
                if (gb.getPosFromMapPositions(cle).equals(p)) {
                    return cle;
                }
            }
        }
        return null;
    }

    //    vérifie si la pos est valide et qu'un bateau fait partie de l'armée courante
    public boolean checkPosEtArmeeBat(Armee armeeCou, String posBatChoisi) {
//        if(posValide(posBatChoisi))    
            for(Bateau b : armeeCou.getListBat())
                if(b.getXY().equals((convertStrToPos(posBatChoisi))))
                    return true;
            return false;
    }

    public boolean posValide(String s) {
        Position p = convertStrToPos(s);
        return (p.getPosX() >= 0 && p.getPosX() < gb.getTAILLE()) ||
               (p.getPosY() >= 0 && p.getPosY() < gb.getTAILLE());
    }

    public void moveBat(String destChoisi) {
        for(Armee a : getListArmees())
            for(Bateau b : a.getListBat())
                if (listDestPoss(b).contains(convertStrToPos(destChoisi))) 
                    if (posValide(convertPosToStr(b.getXY())) && 
                            caseAccessible(convertStrToPos(destChoisi).getPosX(), convertStrToPos(destChoisi).getPosY())) {
                        b.getXY().setPosX(convertStrToPos(destChoisi).getPosX());
                        b.getXY().setPosY(convertStrToPos(destChoisi).getPosY());
                        b.setPos(b.getXY().getPosX(), b.getXY().getPosY());
                        if(caseMineeN(convertStrToPos(destChoisi)))
                            b.touché();
                        else if(caseMineeA(convertStrToPos(destChoisi)))
                            coulé(a,b); 
                        
                        bldr.updateMer(listArmee);
                        setChangedAndNotify(this);
                    }
    }

    private Position goLeft(Position p, int pm) {
        Position pos = new Position(p.getPosX(), p.getPosY());

        for (int i = 0; i < pm; ++i) {
            if (pos.getPosX() == 0) {
                pos.setPosX(gb.getTAILLE() - 1);
            } else {
                pos.setPosX(pos.getPosX() - 1);
            }
        }
        return pos;
    }

    private Position goRight(Position p, int pm) {
        Position pos = new Position(p.getPosX(), p.getPosY());

        for (int i = 0; i < pm; ++i) {
            if (pos.getPosX() == gb.getTAILLE() - 1) {
                pos.setPosX(0);
            } else {
                pos.setPosX(pos.getPosX() + 1);
            }
        }
        return pos;
    }

    private Position goUp(Position p, int pm) {
        Position pos = new Position(p.getPosX(), p.getPosY());

        for (int i = 0; i < pm; ++i) {
            if (pos.getPosY() == 0) {
                pos.setPosY(gb.getTAILLE() - 1);
            } else {
                pos.setPosY(pos.getPosY() - 1);
            }
        }
        return pos;
    }

    private Position goDown(Position p, int pm) {
        Position pos = new Position(p.getPosX(), p.getPosY());

        for (int i = 0; i < pm; ++i) {
            if (pos.getPosY() == gb.getTAILLE() - 1) {
                pos.setPosY(0);
            } else {
                pos.setPosY(pos.getPosY() + 1);
            }
        }
        return pos;
    }

    //    destinations possibles
    public List<Position> listDestPoss(Bateau b) {
        List<Position> dest = new LinkedList<>();
        Position p = b.getXY();
        for (int i = 0; i < b.getPm(); ++i) {
            Position top = goUp(p, b.getPm());
            Position bot = goDown(p, b.getPm());
            Position left = goLeft(p, b.getPm());
            Position right = goRight(p, b.getPm());

            if (posValide(convertPosToStr(p))) {
                dest.add(top);
            }
            if (posValide(convertPosToStr(p))) {
                dest.add(bot);
            }
            if (posValide(convertPosToStr(p))) {
                dest.add(left);
            }
            if (posValide(convertPosToStr(p))) {
                dest.add(right);
            }
        }
        return dest;
    }

    public void permCircul(Position p) {
        if (p.getPosX() > gb.getTAILLE()) {
            p.setPosX(p.getPosX() - gb.getTAILLE());
        }
        if (p.getPosX() < 0) {
            p.setPosX(p.getPosX() + gb.getTAILLE());
        }

        if (p.getPosY() > gb.getTAILLE()) {
            p.setPosY(p.getPosY() - gb.getTAILLE());
        }
        if (p.getPosY() < 0) {
            p.setPosY(p.getPosY() + gb.getTAILLE());
        }
    }

    public boolean tir(Armee a, String pos) {
        if (checkPosEtArmeeBat(a, pos)) {
            Position posBatChoisi = convertStrToPos(pos);
            Bateau b = a.getBatFromPos(posBatChoisi);
            b.randomPortee();
            if (!(b.getPortee() == 0)) {
                for (Armee ar : this.listArmee) 
                    if (!ar.getNom().equals(a.getNom()))//si bat == ennemi => pewpew!
                        for (Position p : porteeTir(b)) 
                            for (Bateau bat : ar.getListBat()) {
                                permCircul(p);
                                if (p.equals(bat.getXY())) {
                                    bat.touché();
                                    if (bat.getPv() <= 0) 
                                        coulé(ar, bat);
                                }
                            }
                bldr.updateMer(listArmee);
                setChangedAndNotify(this);
                return true;
            }
        }return false;
    }

    public List<Position> porteeTir(Bateau b) {
        List<Position> zoneTir = new LinkedList<>();
        for (int i = b.getX() - b.getPortee(); i <= b.getX() + b.getPortee(); ++i) {      //les cases à gauche du bateau
            for (int j = b.getY() - b.getPortee(); j <= b.getY() + b.getPortee(); ++j) {  //les cases à droite
                Position pos = new Position(i, j);
                zoneTir.add(pos);
            }
        }
        return zoneTir;
    }

    public void coulé(Armee a, Bateau b) {
        List<Bateau> listBat = new LinkedList<>();
        for (Bateau bat : a.getListBat()) {
            listBat.add(bat);
        }
        for (Bateau bat : listBat) {
            if (bat.equals(b)) {
                a.getListBat().remove(b);
            }
        }
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
