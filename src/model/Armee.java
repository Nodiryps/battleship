/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import static view.VueConsole.print;
import static view.VueConsole.printLN;

/**
 *
 * @author 2208sptheodorou
 */
public class Armee  {
    
    private String nom;
    private int score;
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

    public int getScore() {
        return score;
    }

    public List<Bateau> getListBat() {
        return ListBat;
    }
    
    public int getSizeListBat(){
        return ListBat.size();
    }
    
    public Bateau getBatFromList(int i){
        return ListBat.get(i);
    }
    
    public void setPosBatFromList(int x, int y, int i){
        getBatFromList(i).setPos(x, y);
    }

    public void setScore(int score) {
        this.score = score;
    }
    
}
