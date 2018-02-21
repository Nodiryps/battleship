/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;


/**
 *
 * @author 0205frwarens
 */
public class Case {
    Pos position ;
    boolean bloquee;
    Bateau TBat;
    Mine TMine;
  
    public Case(Pos position, boolean bloquee) {
        this.position = position;
        this.bloquee = bloquee;
    }

    public Pos getPosition() {
        return position;
    }

    public void setPosition(Pos position) {
        this.position = position;
    }

    public boolean getBloquee() {
        return bloquee;
    }

    public void setBloquee(boolean bloquee) {
        this.bloquee = bloquee;
    }
    
  
    public void SetMine (Mine element ){
        if (element instanceof MineNormale){
          TMine= element;
        }
        else if (element instanceof MineAtomique){
           TMine= element;
       }

    }
    public void SetBateau (Bateau element ){
        if (element instanceof PetitBateau){
          TBat= element;
        }
        else if (element instanceof GrandBateau){
           TBat= element;
        }

    }
    public Bateau getBateau(){
        return TBat;
    }
    public Mine getMine(){
        return TMine;
    }
}


