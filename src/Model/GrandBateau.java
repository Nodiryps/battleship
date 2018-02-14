package Model;


import Model.Bateau;
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
public class GrandBateau extends Bateau {
    
    public GrandBateau(int portee, int vie, int id, Pos pos, TypeBateau type, MouvementsBateau m) {
        super(portee, vie, id, pos, TypeBateau.GRAND, m);
    }
    
    private int random(){
        Random generateur = new Random();
        for(int k = 0; k < ; ++k)
        System.out.println(generateur.nextInt(100));
    
    }
    
}
