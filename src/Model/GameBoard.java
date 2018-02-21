/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Observable;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author 0205frwarens
 */
public class GameBoard extends Observable {
    private int LIGNES  = 5;
    private int COLONNES  = 5;
    private int nbBat;
    private boolean flagOktoSet= false;
    private int MIN = 0;
    Case[][] mer = new Case[LIGNES][COLONNES];

    public int getLIGNES() {
        return LIGNES;
    }

    public int getCOLONNES() {
        return COLONNES;
    }
    
    public int getNbBat(){
        return nbBat;
    }

    public Case[][] getMer() {
        return mer;
    }

    public Mine getMine(int l, int c){
        return mer[l][c].TMine;
    }
    
    public Bateau getBat(int l, int c){
        return mer[l][c].TBat;
    }
    
    public boolean isBat(Object e){
        return e instanceof Bateau;
    }
    
    public boolean isMine(Object e){
        return e instanceof Mine;
    }
    
    private int randLigne, randCol;
    
    public GameBoard() {}

    public void setMer(Case[][] mer) {
        this.mer = mer;
    }
    
//    pour placer les mines et bateaux aléatoirement
    private int randomPos(){
        return ThreadLocalRandom.current().nextInt(0,5);
    }

    public void CreationMer (){
        for (int i=0 ; i<LIGNES; ++i ){
            for (int j=0; j<COLONNES;++j){
                mer[i][j] = new Case(new Pos(LIGNES,COLONNES) ,false);
            }
        }
    }

    public void Initialisation(Bateau type){
        while (flagOktoSet && nbBat >= 0){
            randLigne = MIN + (int)(Math.random() * ((LIGNES - MIN) + 0));
            randCol = MIN + (int)(Math.random() * ((COLONNES - MIN) + 0));
            if  (!mer[randLigne][randCol].getBloquee()) {
                mer[randLigne][randCol].setBloquee(true);
                mer[randLigne][randCol].TBat.setId(1);
                mer[randLigne][randCol].SetBateau(type);
                flagOktoSet = true;
            }
            --nbBat;
        }
    }
    
//    public void Initialisation () {
////      placement des bateaux et des mines: +++++ Peut-etre optimisé 
////      position grand bateau 
////      Choix de la position 
//        while (flagOktoSet){
//            randLigne = MIN + (int)(Math.random() * ((LIGNES - MIN) + 0));
//            randCol = MIN + (int)(Math.random() * ((COLONNES - MIN) + 0));
//            if  (!mer[randLigne][randCol].getBloquee()) {
//                mer[randLigne][randCol].setBloquee(true);
//                mer[randLigne][randCol].TypeBat.setId(1);//à revoir pour l'id
//                mer[randLigne][randCol].SetBateau(new GrandBateau());
//                flagOktoSet = true;
//
//            }
//        }
////        Petit bateau 
//        
//        for (int a = 0; a<2; ++a){
//            flagOktoSet =false;
//           while (flagOktoSet){
//               randLigne = MIN + (int)(Math.random() * ((LIGNES - MIN) + 0));
//                randCol = MIN + (int)(Math.random() * ((COLONNES - MIN) + 0));
//                if  (!mer[randLigne][randCol].getBloquee()) {
//                    mer[randLigne][randCol].setBloquee(true);
//                    mer[randLigne][randCol].TypeBat.setId(2);
//                    mer[randLigne][randCol].SetBateau(new PetitBateau());
//                    /*mer[randLigne][randCol].setAttribute(TypeBateau.PETIT)*/;
//                    flagOktoSet = true;
//
//                }
//            }  
//        }
//    }
    
    public Case GetCase(int l, int c){//Attribut du bateau devrait être J1 ou J2 et type de bateau : à discuter
        return  mer[l][c] /*return "G"*/;
        /*return "l " + l + "c "+c;*/
             
                
                }
   public Object RetObj (int l , int c){
        /*return mer[l][c].GetObject();*/
        return "X";
     } 
   /* public static void main(String[] args) {
        GameBoard g = new GameBoard();
        g.CreationMer();
        System.out.println(g);
    }*/
}
