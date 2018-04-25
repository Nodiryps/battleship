/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import model.Armee;
import model.NouvPartie;
import model.Position;
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
    
    private NouvPartie np = NouvPartie.getNP();
    VueConsole v = new VueConsole(this);
    
    public NouvPartie getNpCtrl() {
         return this.np;
     }
    
    public void lancer() {
        np.addObserver(v);
        np.setChangedAndNotify();
        
        while (!v.partieFinie()){
            for (Armee a : np.getListArmees()) {
                String posBatChoisi = v.affTir(a);
                np.tir(a, posBatChoisi);
                if()

                if(!v.partieFinie()){
                    String destChoisi = v.affMoveBat(a);
                    np.moveBat(destChoisi);
                }else
                    v.partieFinieMsg();
            } 
            
            if(v.partieFinie())
                v.partieFinieMsg();
        }
    }
    

}
