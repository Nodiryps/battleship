/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.Scanner;
import model.NouvPartie;
import view.VueConsole;
import static view.VueConsole.print;

/**
 *
 * @author 2208sptheodorou
 */
public class Controleur {
   private NouvPartie np = NouvPartie.getNP();
   
   public NouvPartie getNpVue(){
       return this.np;
   }
   
    public void lancer(){
        VueConsole v = new VueConsole(this);
        
        v.affMer();
        v.affEtatArmees();
        do{
            v.affTir();
            v.affMouvBat();
        }while(!v.partieFinie());
    }
    
    
    public static void main(String[] args) {
        Controleur ctrl = new Controleur();
        ctrl.lancer();
    }
}
