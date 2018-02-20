/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.Bateau;
import Model.MouvementsBateau;
import Model.PetitBateau;
import Model.TypeBateau;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Spy
 */
public class Joueur {
    private String nom;
    private int score;
    private List<Bateau> listB = new ArrayList();

    public Joueur(String nom, int score) {
        this.nom = nom;
        this.score = score;
    }
    
    public Joueur(String nom){
        this(nom,0);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Bateau> getListB() {
        return listB;
    }

    public void setListB(List<Bateau> listB) {
        this.listB = listB;
    }

    @Override
    public String toString(){
        String res = "Liste des Bateaux de l'armée de: " + nom + "\n";
        for(Bateau b : listB)
            res += "\t-" + "Bateau "
                         + b.getId() 
                         + "\test un " 
                         + b.getType()
                         + " et est en "
                         + b.getPos()
                         + "\n";
        return res;
    }
    
   /* public static void main(String[] args) {
        Joueur j = new Joueur("Paul");
        
        System.out.println(j);
    }*/
}
