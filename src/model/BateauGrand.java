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
public class BateauGrand extends Bateau {

    public BateauGrand() {
        super(TypeB.GRAND,2,2,1);
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
            setPortee(2);
        }
        else if(d > 20 && d < 50){
            setPortee(1);
        }
        else if(d <= 20){
            setPortee(0);
        }
    }
    
//    public void tirBatGrand(Armee ar, Bateau b){
//        for (Position p : porteeTir(b)) 
//            for (Bateau bat : ar.getListBat()) {
//                if (p.equals(bat.getXY())) {
//                        bat.touché();
//                        if (bat.getPv() <= 0) 
//                            coulé(ar, bat);
//                    }
//            }
//    }
}
