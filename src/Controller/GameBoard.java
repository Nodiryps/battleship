/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Pos;
import Model.TypeBateau;
import java.util.Observable;


/**
 *
 * @author 0205frwarens
 */
public class GameBoard extends Observable {
    private int LIGNES  = 5;
    private int COLONNES  = 5;
    private boolean fagOktoSet= false;
    private int MIN = 0;
    Case mer[][] = new Case[LIGNES][COLONNES];
    private int randLigne, randCol;
    
    public GameBoard() {
    }

    public void CreationMer (){
        for (int i=0 ; i<LIGNES; ++i ){
            for (int j=0; j<COLONNES;++j){
                mer[i][j] = new Case(new Pos(LIGNES,COLONNES) ,false);
            }
        }
    }

    public void Initialisation () {
        /*placement des bateaux et des mines : +++++ Peut-etre optimisé */
        /*position grand bateau */
        /*Choix de la position */
        while (fagOktoSet){
            randLigne = MIN + (int)(Math.random() * ((LIGNES - MIN) + 0));
            randCol = MIN + (int)(Math.random() * ((COLONNES - MIN) + 0));
            if  (!mer[randLigne][randCol].isBloquee()) {
                mer[randLigne][randCol].setBloquee(true);
                mer[randLigne][randCol].TypeBat.setId(1);
                mer[randLigne][randCol].Setattribute(TypeBateau.GRAND);
                fagOktoSet = true;

            }
        }
        /*Petit bateau */
        for (int a = 0; a<2; ++a){
           while (fagOktoSet){
            randLigne = MIN + (int)(Math.random() * ((LIGNES - MIN) + 0));
            randCol = MIN + (int)(Math.random() * ((COLONNES - MIN) + 0));
            if  (!mer[randLigne][randCol].isBloquee()) {
                mer[randLigne][randCol].setBloquee(true);
                mer[randLigne][randCol].TypeBat.setId(1);
                mer[randLigne][randCol].Setattribute(TypeBateau.PETIT);
                fagOktoSet = true;

                }
            }  
        }
    }
}
