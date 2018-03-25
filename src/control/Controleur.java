/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import view.VueConsole;

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
        VueConsole v = new VueConsole();
        
        v.affNomArmees();
        v.affMer();
        v.affEtatArmees();
        do{
            v.affTir();
            v.affMouvBat();
        }while(!v.partieFinie());
    }

    
    
}
