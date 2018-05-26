/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.List;
import model.Armee;
import model.Bateau;
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

        while (!np.partieFinie()) {
            for (Armee a : np.getListArmees()) 
                if(!np.partieFinie()){
                    String posBatChoisi = v.affTir(a);
                    v.tirMsg(a, posBatChoisi);
                    if (np.partieFinie()) 
                        v.partieFinieMsg();
                    else{
                        List<String> choix = v.affMoveBat(a);
                        if (choix.get(0).equals("y")) {
                            String batChoisi = choix.get(1);
                            String destChoisi = choix.get(2);
                            if (!destChoisi.equals("")){ 
                                Bateau b = np.getBatFromPos(batChoisi);
                                np.moveBat(a, b, destChoisi);
                                if(np.gestionExplosionsMines(a, b))
                                    v.mineMsg(b);
                            }
                        }
                    }
                }
        }
    }

    public static void main(String[] args) {
        Controleur ctrl = new Controleur();
        ctrl.lancer();
    }

}
