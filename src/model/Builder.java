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
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Spy
 */
public class Builder extends Observable {
    private Gameboard gb;
    private final int NBJ = 2;
    private List<Armee> listArmee = new LinkedList<>();
    private List<Mine> listeMines = new LinkedList<>();
            
    public Builder(int size, List<String> noms, boolean placementAuto) {
        this.gb = new Gameboard(size);
        this.listArmee = creationArmees(noms);
        
        this.nouvMer();
        if(true){
            for(Armee armee : listArmee)
                randPosBat(armee);
            randPosMine();
        }
    }
    
    public NouvPartie build(){
        return new NouvPartie(this);
    }

    public Gameboard getGb() {
        return this.gb;
    }

    public int getNBJ() {
        return NBJ;
    }

    public List<Armee> getListArmee() {
        return listArmee;
    }
    
    public Armee getArmeeFromList(int i){
        return listArmee.get(i);
    }

    private static List<Armee> creationArmees(List<String> noms) {
        List<Armee> list = new LinkedList<>();
        for (String nom : noms) {
            list.add(new Armee(nom));
        }
        return list;
    }
    
    //    création tableau de cases
    private void nouvMer(){
        for(int i = 0; i < gb.getTAILLE(); ++i)
            for(int j = 0; j < gb.getTAILLE(); ++j){
                String s = gb.getAXE_X()[j] + "" + gb.getAXE_Y()[i];
                gb.mapPositionsPut(s, new Position(i, j));
                gb.getMer()[i][j] = new Case();
            }
    }
        
    
    //    disposition random de la flotte
    private void randPosBat(Armee a){
        for(int i = 0; i < a.getSizeListBat(); ++i){
            Position p = randPosFree();
            int x = p.getPosX(), y = p.getPosY();
            gb.getMer()[x][y] = new Case(a.getBatFromList(i));
            a.setPosBatFromList(x, y, i);
            gb.setPosOccupadosAdd(p);
        }
    }
    
//    disposition random des mines
    private void randPosMine(){
        for(int i = 0; i < gb.getTAILLE(); ++i)
            for(int j = 0; j < gb.getTAILLE(); ++j){
                int uneSurDix = ThreadLocalRandom.current().nextInt(1,100);
                int uneSurDeux = ThreadLocalRandom.current().nextInt(1,100);
                if(gb.getMer()[i][j].getBat() == null && gb.getMer()[i][j].getMine() == null) 
                    if(uneSurDix <= 10)
                        if(uneSurDeux <= 50){
                            Mine MineA = new MineAtomique();
                            gb.getMer()[i][j] = new Case(MineA);
                            listeMines.add(MineA);
                        }else{
                            Mine MineN = new MineNormale();
                            gb.getMer()[i][j] = new Case(MineN);
                            listeMines.add(MineN);
                        }
            }
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
            leSet.add(a.getBateauFromListPos(i));
        }
    }
    
    private void updatePosMine(){
        for(Mine m : listeMines){
            int x = m.getX();
            int y = m.getY();
            gb.getMer()[x][y] = new Case(m);
        }
    }
    
    //    rand de 1 à taille du tab
    private int randomPos(){
        return ThreadLocalRandom.current().nextInt(0,gb.getTAILLE());
    }
    
//    rand de deux nb pour pos
    private Position randomPos2(){
        return new Position(randomPos(), randomPos());
    }
    
//    retourne une pos libre par rapport au setPosOccupados
    private Position randPosFree(){
        Position pos = randomPos2();
        while(gb.setPosOccupadosContains(pos))
            pos = randomPos2();//on rejette les "dés" en somme... 
        return pos;
    }
    
}
