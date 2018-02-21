/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Spy
 */
public class Joueur {
    private String nom;
    private int score;
    private Map<Bateau,Pos> map = new HashMap(); 

    public Joueur(String n){
        this.nom = n;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String n) {
        this.nom = n;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int s) {
        this.score = s;
    }

    public Map<Bateau,Pos> getMapBat() {
        return map;
    }

    public void setMapBat(Map<Bateau,Pos> m) {
        this.map = m;
    }
    
    

//    @Override
//    public String toString(){
//        String res = "Liste des Bateaux de l'armée de: " + nom + "\n";
//        map.getOrDefault(Bateau.getId(), defaultValue);
//            res += "\t-" + "Bateau "
//                         + b.getId() 
//                         + "\test un " 
//                         + b.getType()
//                         + " et est en "
//                         + b.getPos()
//                         + "\n";
//        return res;
//    }
    

}
