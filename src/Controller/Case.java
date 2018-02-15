/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.Bateau;
import Model.GrandBateau;
import Model.MouvementsBateau;
import Model.PetitBateau;
import Model.Pos;
import Model.TypeBateau;

/**
 *
 * @author 0205frwarens
 */
public class Case {
    Pos position ;
    boolean bloquee;
    Bateau TypeBat;
  
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
    
    public void setAttribute(TypeBateau TBat){
        if (TBat.equals("GRAND")) {
            TypeBat= new GrandBateau();
            
        }
        else if (TBat.equals("PETIT")) {
            TypeBat= new PetitBateau();
        }
    }
    
    public String getAttribute () {
        return TypeBat.getType();
    }
}


