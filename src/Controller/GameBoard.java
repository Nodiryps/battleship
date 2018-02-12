/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.Observable;


/**
 *
 * @author 0205frwarens
 */
public class GameBoard extends Observable {
    private int LIGNES  = 5;
    private int COLONNES  = 5;
    Case mer[][] = new Case[LIGNES][COLONNES];
    
    private int randLigne, randCol, min, max;
    
    public GameBoard() {
    }

    public void CreationMer (){
        for (int i=0 ; i<LIGNES; ++i ){
            for (int j=0; j<COLONNES;++j){
                mer[i][j] = new Case();
            }
        }
    }

    public void Initialisation () {
        /*position grand bateau */
        
        randLigne = min + (int)(Math.random() * ((max - min) + 1));
        randCol = min + (int)(Math.random() * ((max - min) + 1));
        if mer[randLigne][randCol].
    }
}
