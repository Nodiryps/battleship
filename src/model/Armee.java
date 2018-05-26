/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author 2208sptheodorou
 */
public class Armee  {
    
    private String nom;
    private List<Bateau> ListBat = new LinkedList<>();
    private static int nbGBat = 1;
    private static int nbPBat = 2;

    public Armee(String nom) {
        this.nom = nom;
        nouvArmee();
    }
    
    private void nouvArmee(){
        for(int i = 0; i < nbGBat; ++i)
            ListBat.add(new BateauGrand());
        for(int j = 0; j < nbPBat; ++j)
            ListBat.add(new BateauPetit());
    }
    
    public String getNom() {
        return nom;
    }


    public List<Bateau> getListBat() {
        return ListBat;
    }

    public static int getNbGBat() {
        return nbGBat;
    }

    public static int getNbPBat() {
        return nbPBat;
    }
    
    public int getSizeListBat(){
        return ListBat.size();
    }
    
    public Bateau getBatFromList(int i){
        return ListBat.get(i);
    }
    
    public int getBateauFromListPosX(int i){
        return getBatFromList(i).getX();
    }
    
    public int getBateauFromListPosY(int i){
        return getBatFromList(i).getY();
    }
    
    public Position getPosBateauFromList(int i){
        return getBatFromList(i).getXY();
    }
    
    public Bateau getBatFromPos(Position p) {
        for (Bateau b : getListBat()) {
            if (b.getX() == p.getPosX() && b.getY() == p.getPosY()) 
                return b;
        }
        return null;
    }
    
    public void setPosBatFromList(int x, int y, int i){
        getBatFromList(i).setPos(x, y);
    }
    
}
