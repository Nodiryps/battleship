/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Random;
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
    private final List<Armee> listArmees;
    private List<Mine> listeMines;

    public NouvPartie(Builder bldr) {
        this.bldr = bldr;
        this.gb = bldr.getGb();
        this.listArmees = bldr.getListArmees();
        this.listeMines = bldr.getListeMines();
    }

    public static NouvPartie getNP(int nbj) {
        boolean auto = true;     
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

    public Case getCaseGb(int x, int y) {
        return this.gb.getMer()[x][y];
    }
    
    public Bateau getBatFromCase(int x, int y){
        return getCaseGb(x, y).getBat();
    }
    
    public TypeB getTypeBatFromMer(int x, int y){
        Bateau b = getBatFromCase(x, y);
        return b.getTypeB();
    }
    
    private Mine getMineFromCase(int x, int y) {
        return getCaseGb(x, y).getMine();    
    }

    public TypeM getTypeMineFromMer(int x, int y){
        return getMineFromCase(x, y).getTypeM();
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
        return this.listArmees;
    }

    public int getListArmeesSize() {
        return this.getListArmees().size();
    }
    
    public Armee getArmeeFromList(int i){
        return getListArmees().get(i);
    }
    
    public Bateau getBatFromPos(String pos) {
        Position p = new Position(convertStrToPosX(pos), convertStrToPosY(pos));
            for(Armee a : listArmees)
                for(Bateau b : a.getListBat())
                    if(b.getXY().equals(p))
                        return b;
        return null;
    }
    
    public Bateau getBatFromPos(Position pos) {
            for(Armee a : listArmees)
                for(Bateau b : a.getListBat())
                    if(b.getXY().equals(pos))
                        return b;
        return null;
    }
    
    public int convertStrToPosX(String s) {
        return gb.getPosFromMapPositions(s).getPosX();
    }

    public int convertStrToPosY(String s) {
        return gb.getPosFromMapPositions(s).getPosY();
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
    public boolean checkBatBonneArmee(Armee armeeCou, String posBatChoisi) {
            for(Bateau b : armeeCou.getListBat())
                if(b.getXY().equals((convertStrToPos(posBatChoisi))))
                    return true;
            return false;
    }
    
    public boolean checkBatBonneArmee(Armee armeeCou, Position posBatChoisi) {
            for(Bateau b : armeeCou.getListBat())
                if(b.getXY().equals((posBatChoisi)))
                    return true;
            return false;
    }
    
    public boolean posValide(String s) {
        Position p = convertStrToPos(s);
        return (p.getPosX() >= 0 && p.getPosX() < gb.getTAILLE()) ||
               (p.getPosY() >= 0 && p.getPosY() < gb.getTAILLE());
    }
    
    public void moveBat(Armee a, Bateau b, String destChoisi) {
        Position p = convertStrToPos(destChoisi);
        if (getListDestPoss(b).contains(p)) 
            if (posValide(convertPosToStr(b.getXY())) && caseAccessible(p.getPosX(), p.getPosY())) {
                b.setPos(p);
//                b.getXY().setPosX(p.getPosX());
//                b.getXY().setPosY(p.getPosY());
//                b.setPos(b.getXY().getPosX(), b.getXY().getPosY());
                
                gestionExplosionsMines(a, b);
                updateMer(listArmees);
                setChangedAndNotify(this);
            }
    }
    
    public void move(Armee a, Bateau b, Position pos){
        b.setPos(pos);
        gestionExplosionsMines(a,b);
        setChangedAndNotify(this);
    }
    
    public boolean gestionExplosionsMines(Armee a, Bateau b){
        Case c = this.getCaseGb(b.getX(), b.getY());
        if(c.explosionMineN()){
            b.touché();
            if(b.getPv() <= 0)
                this.coulé(a,b);
            return true;
        }
        if(c.explosionMineA()){
            this.coulé(a,b);
            return true;
        }
        return false;
    }
    
    public void updateMer(List<Armee> list){
        for(int i = 0; i < gb.getTAILLE(); ++i)
            for(int j = 0; j < gb.getTAILLE(); ++j){
                String s = gb.getAXE_X()[j] + "" + gb.getAXE_Y()[i];
                gb.mapPositionsPut(s, new Position(i, j));
                gb.getMer()[i][j] = new Case();
            }
        for(Armee armee : list)
            updatePosBat(armee);
        updatePosMine();
    }
    
    private void updatePosBat(Armee a){
        Set<Position> leSet = new HashSet<>();
        for(int i = 0; i < a.getSizeListBat(); ++i){
            int x = a.getBateauFromListPosX(i);
            int y = a.getBateauFromListPosY(i);
            gb.getMer()[x][y] = new Case();
            gb.getMer()[x][y].setBat(a.getBatFromList(i));
            a.setPosBatFromList(a.getBateauFromListPosX(i), a.getBateauFromListPosY(i), i);
            leSet.add(a.getPosBateauFromListPos(i));
        }
        gb.setPosOccupados(leSet);
    }
    
    private void updatePosMine(){
        for(Mine m : listeMines){
            int x = m.getX();
            int y = m.getY();
            gb.getMer()[x][y] = new Case(m);
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
    
    public boolean listDestPossContains(Bateau b, Position p){
        return getListDestPoss(b).contains(p);
    }

    //    destinations possibles
    public List<Position> getListDestPoss(Bateau b) {
        List<Position> dest = new LinkedList<>();
        Position p = b.getXY();
        for (int i = 1; i <= b.getPm(); ++i) {
            Position top = goUp(p, i);
            Position bot = goDown(p, i);
            Position left = goLeft(p, i);
            Position right = goRight(p, i);

            if (posValide(convertPosToStr(p)) && !dest.contains(top)) {
                dest.add(top);
            }
            if (posValide(convertPosToStr(p)) && !dest.contains(bot)) {
                dest.add(bot);
            }
            if (posValide(convertPosToStr(p)) && !dest.contains(left)) {
                dest.add(left);
            }
            if (posValide(convertPosToStr(p)) && !dest.contains(right)) {
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
        List<Bateau> listB = new LinkedList<>();
        if (checkBatBonneArmee(a, pos)) {
            Position posBatChoisi = convertStrToPos(pos);
            Bateau b = a.getBatFromPos(posBatChoisi);
            b.randomPortee();
            if (!(b.getPortee() == 0)) {
                for (Armee ar : this.listArmees) 
                    if (!ar.getNom().equals(a.getNom()))//si bat == ennemi => pewpew!
                        if(b.getTypeB() == TypeB.GRAND)
                            tirBatGrand(ar,b);
                        else
                            tirBatPetit(ar,b,listB);
                updateMer(listArmees);
                setChangedAndNotify(this);
                return true;
            }
        }return false;
    }
    
    public void tirBatGrand(Armee ar, Bateau b){
        for (Position p : porteeTir(b)) 
            for (Bateau bat : ar.getListBat()) {
                if (p.equals(bat.getXY())) {
                        bat.touché();
                        if (bat.getPv() <= 0) 
                            coulé(ar, bat);
                    }
            }
    }
    
    public void tirBatPetit(Armee ar, Bateau b, List<Bateau> list){
        for (Position p : porteeTir(b))
            for (Bateau adv : ar.getListBat()) {
                if (p.equals(adv.getXY())) 
                    list.add(adv);
            }
        Bateau adv = randBatFromListPortee(list);
        if(adv!=null){
            adv.touché();
            if (adv.getPv() <= 0) 
                coulé(ar, adv);
        }
    }
    
    public Bateau randBatFromListPortee(List<Bateau> list){
        Random rand = new Random();
        int posRand;
        if(list.size()>0){
            posRand = rand.nextInt(list.size());
            return list.get(posRand);
        }  
        return null;
    }

    public List<Position> porteeTir(Bateau b) {
        List<Position> zoneTir = new LinkedList<>();
        for (int i = b.getX() - b.getPortee(); i <= (b.getX() + b.getPortee()); ++i) {      //les cases à gauche du bateau
            for (int j = b.getY() - b.getPortee(); j <= (b.getY() + b.getPortee()); ++j) {  //les cases à droite
                Position pos = new Position(i, j);
                permCircul(pos);
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
    
    public boolean partieFinie() {
        for (Armee a : listArmees) {
            if(a.getSizeListBat() <= 0){
                return true;
            }
        }
        return false;
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
