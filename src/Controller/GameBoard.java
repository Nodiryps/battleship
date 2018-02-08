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

    public GameBoard() {
    }



}
