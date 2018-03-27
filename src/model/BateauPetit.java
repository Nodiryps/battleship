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
public class BateauPetit extends Bateau {

    public BateauPetit() {
        this.type= TypeB.PETIT;
        this.pm = 2;
        this.pv = 1;
        
        
    }
    
    @Override
    public int getX() {
        return this.posBat.getPosX();
    }

    @Override
    public int getY() {
        return this.posBat.getPosY();
    }

    @Override
    public void setX(int x) {
        this.posBat.setPosX(x);
    }

    @Override
    public void setY(int y) {
        this.posBat.setPosY(y);
    }
    
    @Override
    public void touché() {
        --this.pv;
    }
    
}
