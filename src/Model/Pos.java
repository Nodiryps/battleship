package Model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Spy
 */
public class Pos {

    private int posL, posC;

    public Pos(int posL, int posC) {
        this.posL = posL;
        this.posC = posC;
    }

    public int getPosL() {
        return posL;
    }

    public void setPosL(int posL) {
        this.posL = posL;
    }

    public int getPosC() {
        return posC;
    }

    public void setPosC(int posC) {
        this.posC = posC;
    }
    
    //Pour voir si deux bateaux sont sur la meme case 
    //ou si un bateau croise une mine
    @Override
    public boolean equals(Object o){
        return(
                o instanceof Pos &&
                o.equals(posL) && 
                o.equals(posC)
              );
    }

    //pas de equals sans hashCode :)
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.posL;
        hash = 97 * hash + this.posC;
        return hash;
    }
  
    
}
