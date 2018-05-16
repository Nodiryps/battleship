/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import view.VuePartie;
import view.VueParamPartie;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import model.Builder;
import model.NouvPartie;
import java.util.LinkedList;
import java.util.List;
import model.Armee;
import model.Bateau;
import model.Case;
import model.Position;
import view.VueBuilder;

/**
 *
 * @author Spy
 */
public class ControleurFx extends Application {
    private Stage stage;
    private NouvPartie np;
    private Builder bldr;
    private VueBuilder vueBldr;
    private Position posBatChoisi;
    private Bateau batChoisi;
    private Armee a1;
    private Armee a2;
    
    private final static int NB_GD_BAT = Armee.getNbGBat();
    private final static int NB_PT_BAT = Armee.getNbPBat();
    private final static int NB_TOT_BAT = NB_GD_BAT + NB_PT_BAT;
   
    private static int cptBatJ1;
    private static int cptBatJ2;
    private static int cptPtBatJ1;
    private static int cptPtBatJ2;
    private static int cptBatTot;
    
    private boolean placementAuto = true;
    private boolean placementJ1 = true;
    private boolean placementJ2 = false;
    
    private boolean tourJ1Tir = true;
    private boolean tourJ1Move = true;
    
    public NouvPartie getNp() {
        return np;
    }
    
    public Builder getBldr(){
        return bldr;
    }
    
    public Armee getArmee1(){
        return a1;
    }
    
    public Armee getArmee2(){
        return a2;
    }

    public Stage getStage() {
        return stage;
    }

    public Position getPosBatChoisi() {
        return posBatChoisi;
    }

    public Bateau getBatChoisi() {
        return batChoisi;
    }

    public static int getNB_GD_BAT() {
        return NB_GD_BAT;
    }

    public static int getNB_PT_BAT() {
        return NB_PT_BAT;
    }

    public static int getNB_TOT_BAT() {
        return NB_TOT_BAT;
    }

    public static int getCptBatJ1() {
        return cptBatJ1;
    }

    public static int getCptBatJ2() {
        return cptBatJ2;
    }

    public static int getCptPtBatJ1() {
        return cptPtBatJ1;
    }

    public static int getCptPtBatJ2() {
        return cptPtBatJ2;
    }

    public static int getCptBatTot() {
        return cptBatTot;
    }

    public boolean isPlacementJ1() {
        return placementJ1;
    }

    public boolean isPlacementJ2() {
        return placementJ2;
    }

    public boolean isPlacementAuto(){
        return placementAuto;
    }
    
    public boolean isTourJ1Tir() {
        return tourJ1Tir;
    }

    public boolean isTourJ2Tir() {
        return !tourJ1Tir;
    }

    public boolean isTourJ1Move() {
        return tourJ1Move;
    }

    public boolean isTourJ2Move() {
        return !tourJ1Move;
    }

    public void setTourJ1Tir(boolean tourTirJ1) {
        this.tourJ1Tir = tourTirJ1;
    }

    public void setTourJ1Move(boolean tourMoveJ1) {
        this.tourJ1Move = tourMoveJ1;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        new VueParamPartie(stage, this); // Fenêtre initiale (saisie taille)
    }

    private void switchToBuildWindow(int size){
        vueBldr = new VueBuilder(size, this, stage);
    }
    
    // fait apparaître la fenêtre principale de l'application
    public void switchToMainWindow(String j1, String j2, int size) {
        List<String> noms = new LinkedList();
        noms.add(j1);
        noms.add(j2);
        bldr = new Builder(size, noms, placementAuto, false);
        np = bldr.build();
        a1 = np.getArmeeFromList(0);
        a2 = np.getArmeeFromList(1);
        VuePartie mainWindow = new VuePartie(stage, np.getTailleGb(), this);
        np.addObserver(mainWindow);
        np.setChangedAndNotify(); // Provoque un 1er affichage
    }
    
    // Quand l'utilisateur clique sur une case vide
    public void emptyBoxClicked(int x, int y) {
        List<Bateau> listBatJ1 = a1.getListBat(), listBatJ2 = a2.getListBat();
        
        if(!placementAuto && cptBatTot < NB_TOT_BAT * 2){
            if(placementJ1 && cptBatJ1 < 3){
                distribBat(listBatJ1, cptPtBatJ1, NB_TOT_BAT, x, y);
                placementJ1 = false;
                placementJ2 = true;
            }
            else if(placementJ2 && cptBatJ2 < 3){
                distribBat(listBatJ2, cptPtBatJ2, NB_TOT_BAT, x, y);
                placementJ1 = true;
                placementJ2 = false;
            }
        }
    }
    
    private void distribBat(List<Bateau> listBat, int cptPtBat, int nbBatJ, int x, int y){
        if(batChoisi != null && batChoisi.getTypeB() == batChoisi.getTypeGrandBat()){
            setBatInBox(listBat.get(0), x, y);
            ++cptBatTot;
            ++cptBatJ1;
            if (batChoisi != null && batChoisi.getTypeB() == batChoisi.getTypePetitBat() && cptPtBat < 2) {
                if(cptPtBat == 1){
                    setBatInBox(listBat.get(1), x, y);
                    ++cptBatTot;
                }else{
                    setBatInBox(listBat.get(2), x, y);
                    ++cptBatTot;
                }
                ++nbBatJ;
                ++cptPtBat;
            }
        }
    }
    
    private void setBatInBox(Bateau b, int x, int y){
        b.setPos(new Position(x, y));
        Case box = np.getCaseGb(x, y);
        box.setBat(b);
    }
    
    // Quand l'utilisateur clique sur un bateau
    public void boatClicked(NouvPartie np,int x, int y) {
        tirMoveBoatClicked(np, x, y);
    }
    
    public void tirMoveBoatClicked(NouvPartie np, int x, int y){
        Position p = new Position(x, y);
        if(isTourJ1Tir())
            if(np.checkBatBonneArmee(a1, np.convertPosToStr(p))){
                np.tir(a1, np.convertPosToStr(p));
                setTourJ1Tir(false);
                setTourJ1Move(true);
            }else
                
        if(isTourJ2Tir())
            if(np.checkBatBonneArmee(a2, np.convertPosToStr(p))){
                np.tir(a2, np.convertPosToStr(p));
                setTourJ1Tir(true);
                setTourJ1Move(false);
            }
        if(isTourJ1Move())
            boatClickedToMove(a1, p);
        if(isTourJ2Move())
            boatClickedToMove(a2, p);
    }
    
    public void boatClickedToMove(Armee a, Position p){
        if(np.checkBatBonneArmee(a, np.convertPosToStr(p)))
            posBatChoisi = p;
        else
            posBatChoisi = null;
    }
    
    public void tirToMove(Armee a, Position p, boolean tir, boolean move){
        np.tir(a,np.convertPosToStr(p));
        tir = false;
        move = true;
    }
    
    public void moveBoatClicked(NouvPartie np, int x, int y){
        Position p = new Position(x, y);
        if(isTourJ1Move())
            if(np.checkBatBonneArmee(a1, np.convertPosToStr(posBatChoisi))){
                batChoisi = a1.getBatFromPos(posBatChoisi);
                move(a1,batChoisi,p,tourJ1Move);
                posBatChoisi = null;
                setTourJ1Tir(false);
            }
        if(isTourJ2Move())
            if(np.checkBatBonneArmee(a2, np.convertPosToStr(posBatChoisi))){
                batChoisi = a2.getBatFromPos(posBatChoisi);
                move(a2,batChoisi,p,!tourJ1Move);
                posBatChoisi = null;
                setTourJ1Tir(true);
            }
    }
    
    private void move(Armee a, Bateau b, Position p, boolean tourMove){
        b = a.getBatFromPos(posBatChoisi);
            if(np.listDestPossContains(b,p)){
                np.moveBat(a, b, np.convertPosToStr(p));
                tourMove = false;
                Case c = np.getCaseGb(p.getPosX(),p.getPosY());
                if(c.explosionMineN())
                    if(b.getPv() <= 0)
                        np.coulé(a,b);
            }
    }

    
}
