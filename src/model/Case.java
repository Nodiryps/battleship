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
    private MineNormale mineN;
    private MineAtomique mineA;
    boolean modeDebug = false;

    public Case() {
    }
    
    public Case(Bateau b){
        this.bat = b;
    }
    
    public Case(MineNormale m){
        this.mineN =  m;
    }
    
    public Case(MineAtomique m){
        this.mineA =  m;
    }

    public Bateau getBat() {
        return bat;
    }
    
    public TypeB getTypeBat(){
        return bat.type;
    }

    public MineNormale getMineN() {
        return mineN;
    }

    public MineAtomique getMineA() {
        return mineA;
    }
    
    public boolean explosionMineA(){
        return bat != null && mineA != null;
    }
    
    public boolean explosionMineN(){
        return bat != null && mineN != null;
    }
    
    public boolean caseAccessible() {
        return bat == null && !explosionMineA();
    }
    
    public String toStringMine(){
        if(!modeDebug){
            return "";
        }else
            return "" + mineN;
    }
    
    
}
