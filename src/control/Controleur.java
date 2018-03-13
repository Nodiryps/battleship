/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import model.NouvPartie;
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
        NouvPartie npVue = v.getNpVue();
        
        v.affNomArmees();
        
        while(!npVue.getListArmees().isEmpty()){
        v.affMer();
        v.affEtatArmees();
        v.affTir();
        v.affMouvBat();
        }
    }

    
    
}
