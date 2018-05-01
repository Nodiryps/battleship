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

    public Case() {
    }
    
    public Case(Bateau b){
        this.bat = b;
    }
    
    public Case(Mine m){
        this.mine =  m;
    }
    

    public Bateau getBat() {
        return bat;
    }
    
    public Mine getMine() {
        return mine;
    }
    
    public TypeB getTypeBat(){
        return bat.TYPE;
    }
    
    public TypeM getTypeMine(){
        return mine.type;
    }

    public void setBat(Bateau bat) {
        this.bat = bat;
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
