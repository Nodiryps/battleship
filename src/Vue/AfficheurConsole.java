/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Model.GameBoard;
import Model.GrandBateau;
import Model.MineAtomique;
import Model.MineNormale;
import Model.PetitBateau;
import Model.Pos;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

enum Couleur {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");

    private final String code;
    private Couleur(String code) { this.code = code; }
    @Override public String toString() { return code; }
}
/**
 *
 * @author frederic
 */
public class AfficheurConsole implements Observer {
    private String joueur;
    private Pos posBat;
    private boolean modeDebug;
    Scanner sc = new Scanner(System.in);

    
    public String getJoueur() {
        return joueur;
    }

    public void setJoueur(String joueur) {
        this.joueur = joueur;
    }

    public Pos getPositionB() {
        return posBat;
    }

    public void setPositionB(Pos positionB) {
        this.posBat = positionB;
    }

    public boolean isModeDebug() {
        return modeDebug;
    }

    public void setModeDebug(boolean modeDebug) {
        this.modeDebug = modeDebug;
    }
    
    public void CreateJoueur(){
       System.out.println(Couleur.BLACK+"Nom de l'armée : ");
       setJoueur(sc.nextLine());
    }  

   private static final AfficheurConsole AFFICHEURCONSOLE = new AfficheurConsole();
   private static final char FIRSTLINE[]= {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
   private static final String FIRSTCOL[]= {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26"};
   
   public AfficheurConsole() {
    }
   
    //Renvoie une instance de la vue 
    public static AfficheurConsole getInstance() {
        return AFFICHEURCONSOLE;
    } 
    
    public void affGrille(GameBoard gB){
        AfficherGrille(gB);
    }
    
    public static void determinationTypeAff(GameBoard gB, int i, int j){
        if (gB.isBat(gB.getMer()[i][j])){
            if(gB.getBat(i, j) instanceof GrandBateau){
                System.out.print(Couleur.BLACK + "|" + "GB");
            }
            else{
                System.out.print(Couleur.BLACK + "|" + "pb");
            }
        }
        else if (gB.isMine(gB.getMer()[i][j])){
            if(gB.getMine(i, j) instanceof MineAtomique){
                System.out.print(Couleur.BLACK + "|" + "MA");
            }
            else
                System.out.print(Couleur.BLACK + "|" + "mn");
        }
    }
   
    private static void AfficherGrille(GameBoard gB){
        //Afficher la première ligne      
        for (int i =  0 ; i < gB.getLIGNES(); i++) {
               System.out.print(Couleur.BLACK + " " + FIRSTLINE[i]);
        }
        System.out.println();
        //Afficher les autres lignes
        for (int i =  0 ; i < gB.getLIGNES(); i++) {
            System.out.print(FIRSTCOL[i] + " "); 
            for (int j = 0; j < gB.getCOLONNES(); j++){
                if(gB.getMer()[i][j] == null){
                System.out.print(Couleur.BLACK + "|" + " ");
                }
                else
                    determinationTypeAff(gB,i,j);
            }
            System.out.println("|");
        }
        System.out.println(""); 
    }                 
    
    public static void TableauEtat() {
        //A compléter
    }
 @Override
    public void update(Observable obs, Object obj) {
        GameBoard gB = (GameBoard) obs;
        AfficherGrille(gB);
    }
    
}
