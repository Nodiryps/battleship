/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Random;


/**
 *
 * @author 2208sptheodorou
 */
public class BateauPetit extends Bateau {

    public BateauPetit() {
        super(TypeB.PETIT,1,1,2);
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
    public void randomPortee(){
        Random rand = new Random();
        double d = rand.nextInt(100);
        if(d >= 50){
            setPortee(0);
        }
        else if(d > 20 && d < 50){
            setPortee(1);
        }
        else if(d <= 20){
            setPortee(2);
        }
    }
}
