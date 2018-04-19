/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;



/**
 *
 * @author 2208sptheodorou
 */
public class Gameboard extends Observable {
    
    private static final char[] AXE_X = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private static final int[] AXE_Y = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26};
    private final int TAILLE;
    private Case[][] mer;
    private Set<Position> setPosOccupados = new HashSet<>(); //enregistre les pos occupées
    
    public Gameboard(int size) {
        this.TAILLE = size;
        this.mer = new Case[TAILLE][TAILLE];
    }

    public char[] getAXE_X() {
        return AXE_X;
    }

    public int[] getAXE_Y() {
        return AXE_Y;
    }
    
    public int getTAILLE() {
        return TAILLE;
    }

    public Case[][] getMer() {
        return mer;
    }

    public Set<Position> getPosOccupados() {
        return setPosOccupados;
    }

    public void setMer(Case[][] m) {
        this.mer = m;
    }
    
//    création tableau de cases
    public void nouvMer(List<Armee> list){
        
        for(int i = 0; i < TAILLE; ++i){
            for(int j = 0; j < TAILLE; ++j){
                mer[i][j] = new Case();
            }
        }
        for(Armee armee : list){
            randPosBat(armee);
        }
        randPosMine();
    }
    
//    disposition random de la flotte
    private void randPosBat(Armee a){
        for(int i = 0; i < a.getSizeListBat(); ++i){
            Position p = randPosFree();
            int x = p.getPosX(), y = p.getPosY();
            this.mer[x][y] = new Case(a.getBatFromList(i));
            a.setPosBatFromList(x, y, i);
            setPosOccupados.add(p);
        }
    }
    
//    disposition random des mines
    private void randPosMine(){
        for(int i = 0; i < TAILLE; ++i){
            for(int j = 0; j < TAILLE; ++j){
                int uneSurDix = ThreadLocalRandom.current().nextInt(1,100);
                int uneSurDeux = ThreadLocalRandom.current().nextInt(1,100);
                if(this.mer[i][j].getBat()== null && 
                   this.mer[i][j].getMineA() == null && 
                   this.mer[i][j].getMineN() == null){//bool caseVide à faire
                    if(uneSurDix <= 10){
                        if(uneSurDeux <= 50){
                            this.mer[i][j] = new Case(new MineNormale());
                        }
                        else{
                            this.mer[i][j] = new Case(new MineAtomique());
                        }
                    }
                }
            }
        }
    }
    
    //    rand de 1 à taille du tab
    private int randomPos(){
        return ThreadLocalRandom.current().nextInt(1,TAILLE);
    }
    
//    rand de deux nb pour pos
    private Position randomPos2(){
        return new Position(randomPos(), randomPos());
    }
    
//    retourne une pos libre par rapport au setPosOccupados
    private Position randPosFree(){
        Position pos = randomPos2();
        while(setPosOccupados.contains(pos)){
            pos = randomPos2();//on rejette les "dés" en somme... 
        }
        return pos;
    }

    public void setChangedAndNotify() {
        setChanged();
        notifyObservers();
    }
    

    
    
}
