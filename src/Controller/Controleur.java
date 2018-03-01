/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Gameboard;
import View.Vue;

/**
 *
 * @author 2208sptheodorou
 */
public class Controleur {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Controleur ctrl = new Controleur();
        ctrl.lancer();
        
    }
    
    
    
    public void lancer(){
        Vue v = new Vue();
        
        v.afficheMer();
        
        v.AfficheEtatArmees();
        
        v.affBougerBat();
        v.saisirDir();
                
    }

    
    
}
