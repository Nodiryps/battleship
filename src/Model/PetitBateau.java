package Model;


import Model.MouvementsBateau;
import Model.Bateau;

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

    public PetitBateau(int portee, int vie, int id, Pos pos, TypeBateau type, MouvementsBateau m) {
        super(portee, vie, id, pos, TypeBateau.PETIT, m);
    }
    

    
    
    
}
