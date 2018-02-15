package Model;


import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Spy
 */
public class PetitBateau extends Bateau {

    public PetitBateau() {
    }


    @Override
    public void randomPortee(){
        Random rand = new Random();
        double d = rand.nextDouble();
        if(d <= 0.5){
            setPortee(0);
        }
        else if(d <= 0.3){
            setPortee(1);
        }
        else if(d <= 0.2){
            setPortee(2);
        }
    }

    @Override
    public void touche() {
        this.setPv(this.getPv() - 1);
    }

    @Override
    public boolean coule() {
        return this.getPv() <= 0;
    }

    
    
    
    
}
