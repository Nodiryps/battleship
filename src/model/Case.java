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

    public boolean isBatOnMineA(){
        return bat != null && mine != null && mine.getTypeM() == TypeM.ATOMIQUE;
    }
    
    public boolean isBatOnMineN(){
        return bat != null && mine != null && mine.getTypeM() == TypeM.NORMALE;
    }
    
    public boolean isAccessible() {
        return bat == null && !isBatOnMineA();
    }    
    
    public boolean isRadioactive(){
        return !this.isAccessible();
    }
    
    public boolean isEmpty(){
        return this.empty;
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
}
