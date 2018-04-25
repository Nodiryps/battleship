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
    protected Position posM = new Position(0, 0);
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
        return this.posM.getPosX();
    }

    @Override
    public int getY() {
        return this.posM.getPosY();
    }

    @Override
    public void setX(int x) {
        this.posM.setPosX(x);
    }

    @Override
    public void setY(int y) {
        this.posM.setPosY(y);
    }
    
       
    
}
