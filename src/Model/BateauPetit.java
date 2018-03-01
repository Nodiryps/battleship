/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;


/**
 *
 * @author 2208sptheodorou
 */
public class BateauPetit extends Bateau {

    public BateauPetit() {
        this.pm = 2;
        this.pv = 1;
    }
    
    @Override
    public int getX() {
        return this.getX();
    }

    @Override
    public int getY() {
        return this.getY();
    }

    @Override
    public void setX(int x) {
        this.setX(x);
    }

    @Override
    public void setY(int y) {
        this.setY(y);
    }
    
    @Override
    public void touché() {
        --this.pv;
    }
    
}
