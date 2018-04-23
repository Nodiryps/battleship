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
public class MineNormale extends Mine {

    public MineNormale(){
        super(TypeM.NORMALE);
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
