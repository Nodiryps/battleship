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
public class BateauGrand extends Bateau {

    public BateauGrand() {
        this.pm = 1;
        this.pv = 2;
        this.type= TypeB.GRAND;
        
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
