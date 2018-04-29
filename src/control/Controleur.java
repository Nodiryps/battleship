/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import model.Armee;
import model.NouvPartie;
import view.VueConsole;

/**
 *
 * @author 2208sptheodorou
 */
public class Controleur {
    private final int NBJ = 2;
    private NouvPartie np = NouvPartie.getNP(NBJ);
    private VueConsole v = new VueConsole(this);
    
    private Controleur() {
    }
    
    public NouvPartie getNpCtrl() {
         return this.np;
     }
    
    public void lancer() {
        np.addObserver(v);
        np.setChangedAndNotify();
        
        if (!v.partieFinie()){
            for (Armee a : np.getListArmees()) {
                String posBatChoisi = v.affTir(a);
                np.tir(a, posBatChoisi);
                v.tirMsg(a,posBatChoisi);

                if(!v.partieFinie()){
                    String destChoisi = "";
                    v.affMoveBat(a,destChoisi);
                    np.moveBat(destChoisi);
                }else
                    v.partieFinieMsg();
            } 
        }else
            v.partieFinieMsg();
    }
    
    public static void main(String[] args) {
        Controleur ctrl = new Controleur();
        ctrl.lancer();
    }
    

}
