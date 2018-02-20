/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Model.GameBoard;
import Model.Joueur;
import Vue.AfficheurConsole;

/**
 *
 * @author 0205frwarens
 */
public class BatailleNavalle {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GameBoard gb = new GameBoard();
        AfficheurConsole aff = new AfficheurConsole();
        aff.CreateJoueur();
        Joueur joueur1 = new Joueur(aff.getJoueur(),0);
        aff.CreateJoueur();
        Joueur joueur2 = new Joueur(aff.getJoueur(),0);
        aff.AfficherGrille(gb);//mettre dans un update
        
        
        
    }
    
}
