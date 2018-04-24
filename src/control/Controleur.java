/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import model.Armee;
import model.NouvPartie;
import view.VueConsole;
import static view.VueConsole.print;

/**
 *
 * @author 2208sptheodorou
 */
public class Controleur {

    public static void main(String[] args) {
        Controleur ctrl = new Controleur();
        ctrl.lancer();
    }
    
    private NouvPartie npVue = VueConsole.getNpVue();
    
    public void lancer() {
        for (Armee a : npVue.getListArmees()) {
            while (!VueConsole.partieFinie()){
                VueConsole.affMer();
                VueConsole.affEtatArmees();

                    VueConsole.affTir(a);
                    if(!VueConsole.partieFinie()){
                        VueConsole.affMer();
                        VueConsole.affEtatArmees();

                        VueConsole.affMouvBat(a);

                        VueConsole.affMer();
                        VueConsole.affEtatArmees();
                    }
                    else
                        VueConsole.partieFinieMsg();
            } 
            
            if(VueConsole.partieFinie())
                VueConsole.partieFinieMsg();
        }
    }

}
