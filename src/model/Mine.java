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
public abstract class Mine implements Positionnable {
    
    protected TypeM type;
    protected Position posM;
    protected boolean debugMode = false;

    public Mine(TypeM type){
        this.type = type;
    }
    
    public TypeM getTypeM() {
        return type;
    }
    
    public TypeM getTypeMAtom() {
        return TypeM.ATOMIQUE;
    }
    
    public TypeM getTypeMNormale(){
        return TypeM.NORMALE;
    }
    
    public Position getXY() {
        return this.posM;
    }
    
    @Override
    public int getX() {
        return this.posM.getPosY();
    }

    @Override
    public int getY() {
        return this.posM.getPosX();
    }
    
       
    
}
