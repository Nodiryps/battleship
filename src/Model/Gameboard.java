/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Random;
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
    private Set<Position> setPosOccupados = new HashSet(); //enregistre les pos occupées
    
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
    
//    rand de 1 à taille du tab
    private int randomPos(){
        return ThreadLocalRandom.current().nextInt(1,TAILLE);
    }
    
//    rand de deux nb pour pos
    private Position randomPos2(){
        return new Position(randomPos(), randomPos());
    }
    
//    rand qui sauve les pos ds un Set
    private Position randomPosSet(Set<Position> set){
        Position pos = randomPos2();
        while(set.contains(pos)){
            pos = randomPos2();//on rejette les "dés" en somme... 
        }
        return pos;
    }
    
//    disposition random de la flotte
    private void randPosBat(Armee a){
        for(int i = 0; i < a.getSizeListBat(); ++i){
            Position p = randomPosSet(setPosOccupados);
            if(mer[p.getPosX()][p.getPosY()].caseAccessible()){
                mer[p.getPosX()][p.getPosY()] = new Case(a.getBatList(i));
                a.getBatList(i).setPos(p.getPosX(),p.getPosY());
                setPosOccupados.add(p);
            }
            
        }
    }
    
//    disposition random des mines
    private void randPosMine(){
        for(int i = 0; i < TAILLE; ++i){
            for(int j = 0; j < TAILLE; ++j){
                Random rand = new Random();
                double d = rand.nextDouble();
                Case c = new Case();
                if(c.caseAccessible()){
                    if(d <= 0.1){
                        if(d <= 0.5){
                            Mine mn = new MineNormale();
                            mer[i][j] = new Case((MineNormale) mn);
                        }
                        else{
                            Mine ma = new MineAtomique();
                            mer[i][j] = new Case((MineAtomique) ma);
                        }
                    }
                }
            }
        }
    }
    

    
    
}
