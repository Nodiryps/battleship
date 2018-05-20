/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author 2208sptheodorou
 */
public class Case {
    
    private Bateau bat;
    private Mine mine;
    private boolean empty;
    
    public Case() {
        this.empty = true;
    }
    
    public Case(Bateau b){
        this.bat = b;
        this.empty = false;
    }
    
    public Case(Mine m){
        this.mine =  m;
        this.empty = false;
    }
    
    public boolean isEmpty(){
        return this.empty;
    }
    
    public Bateau getBat() {
        return bat;
    }
    
    public Mine getMine() {
        return mine;
    }
    
    public TypeB getTypeBat(){
        return bat.getTypeB();
    }
    
    public TypeM getTypeMine(){
        return mine.getTypeM();
    }

    public void setEmpty(boolean b){
        this.empty = b;
    }
    
    public void setBat(Bateau bat) {
        this.bat = bat;
        this.empty = false;
    }

    public void setMine(Mine mine) {
        this.mine = mine;
    }
    
    public boolean explosionMineA(){
        return bat != null && mine.getTypeMAtom() != null;
    }
    
    public boolean explosionMineN(){
        return bat != null && mine.getTypeMAtom() != null;
    }
    
    public boolean caseAccessible() {
        return bat == null && !explosionMineA();
    }
    
    
    
}
