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
import model.TypeB;
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
    private final static int TOT_BAT_PAR_J = NB_GD_BAT + NB_PT_BAT;
   
    private static int cptTotBatJ1;
    private static int cptTotBatJ2;
    private static int cptPtBatJ1;
    private static int cptPtBatJ2;
    private static int cptBatTotMer;
    
    private boolean placementAuto = true;
    private boolean placementJ1 = true;
    private boolean placementJ2 = false;
    
    private boolean tourJ1Tir = true;
    private boolean tourJ1Move = false;
    private boolean tourJ2Tir = false;
    private boolean tourJ2Move = false;
    
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
        return TOT_BAT_PAR_J;
    }

    public static int getCptBatJ1() {
        return cptTotBatJ1;
    }

    public static int getCptBatJ2() {
        return cptTotBatJ2;
    }

    public static int getCptPtBatJ1() {
        return cptPtBatJ1;
    }

    public static int getCptPtBatJ2() {
        return cptPtBatJ2;
    }

    public static int getCptBatTot() {
        return cptBatTotMer;
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
        return tourJ2Tir;
    }

    public boolean isTourJ1Move() {
        return tourJ1Move;
    }

    public boolean isTourJ2Move() {
        return tourJ2Move;
    }

    public void setTourJ1Tir(boolean b) {
        this.tourJ1Tir = b;
    }
    
    public void setTourJ2Tir(boolean b) {
        tourJ2Tir = b;
    }

    public void setTourJ1Move(boolean b) {
        this.tourJ1Move = b;
    }
    
    public void setTourJ2Move(boolean b) {
        this.tourJ2Move = b;
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
        np.addObserver(vueBldr);
        np.setChangedAndNotify();
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
        List<Bateau> listBatJ1 = a1.getListBat(), 
                     listBatJ2 = a2.getListBat();
        if(!placementAuto && cptBatTotMer < (TOT_BAT_PAR_J * 2)){
            if(placementJ1 && cptTotBatJ1 < 3){
                if(batChoisi != null){
                    if(batChoisi.getTypeB() == TypeB.GRAND){
                        setBatInBox(listBatJ1.get(0), x, y);
                        ++cptBatTotMer;
                        ++cptTotBatJ1;
                    }else if (batChoisi.getTypeB() == TypeB.PETIT && cptPtBatJ1 < 2) {
                        if(cptPtBatJ1 == 1){
                            setBatInBox(listBatJ1.get(1), x, y);
                            ++cptBatTotMer;
                        }else{
                            setBatInBox(listBatJ1.get(2), x, y);
                            ++cptBatTotMer;
                        }
                        ++cptTotBatJ1;
                        ++cptPtBatJ1;
                    }
                }                
                placementJ1 = false;
                placementJ2 = true;
            }
            else if(placementJ2 && cptTotBatJ2 < 3){
                if(batChoisi != null){
                    if(batChoisi.getTypeB() == TypeB.GRAND){
                        setBatInBox(listBatJ2.get(0), x, y);
                        ++cptBatTotMer;
                        ++cptTotBatJ2;
                    }else if (batChoisi.getTypeB() == TypeB.PETIT && cptPtBatJ2 < 2) {
                        if(cptPtBatJ2 == 1){
                            setBatInBox(listBatJ2.get(1), x, y);
                            ++cptBatTotMer;
                        }else{
                            setBatInBox(listBatJ2.get(2), x, y);
                            ++cptBatTotMer;
                        }
                        ++cptTotBatJ2;
                        ++cptPtBatJ2;
                    }
                }  
                placementJ1 = true;
                placementJ2 = false;
            }
        }
    }
    
    private void setBatInBox(Bateau b, int x, int y){
        b.setPos(new Position(x, y));
        Case box = np.getCaseGb(x, y);
        box.setBat(b);
    }
    
    // Quand l'utilisateur clique sur un bateau
    public void boatClicked(int x, int y) {
        Position p = new Position(y, x);
        
        tir(x, y);
        move(p);
    }
    
    public void tir(int x, int y){
        Position p = new Position(x, y);
        if(isTourJ1Tir())
            if(np.checkBatBonneArmee(a1, np.convertPosToStr(p))){
                np.tir(a1, np.convertPosToStr(p));
                setTourJ1Tir(false);
                setTourJ1Move(true);
            }

        if(isTourJ2Tir())
            if(np.checkBatBonneArmee(a2, np.convertPosToStr(p))){
                np.tir(a2, np.convertPosToStr(p));
                setTourJ2Tir(false);
                setTourJ2Move(true);
            }
    }
    
    public void move(Position p){
        if(isTourJ1Move())
            if(np.checkBatBonneArmee(a1, p))
                posBatChoisi = p;
            else
                posBatChoisi = null;
        
        if(isTourJ2Move())
            if(np.checkBatBonneArmee(a2, p))
                posBatChoisi = p;
            else
                posBatChoisi = null;
    }
    
    public void moveBoatClicked(int x, int y){
        Position p = new Position(x, y);
        Bateau b;
        if(isTourJ1Move())
            if(np.checkBatBonneArmee(a1, posBatChoisi)){
                b = a1.getBatFromPos(posBatChoisi);
                if(np.listDestPossContains(b,p)){
                    np.moveBat(a1, b, np.convertPosToStr(p));
                    tourJ1Move = false;
                    Case c = np.getCaseGb(p.getPosX(), p.getPosY());
                    if(c.explosionMineN()){
                        b.touché();
                        if(b.getPv() <= 0)
                            np.coulé(a1,b);
                    }
                    if(c.explosionMineA()){
                        np.coulé(a1,b);
                    }
                }
                posBatChoisi = null;
                setTourJ2Tir(true);
            }
        if(isTourJ2Move())
            if(np.checkBatBonneArmee(a2, posBatChoisi)){
                b = a2.getBatFromPos(posBatChoisi);
                if(np.listDestPossContains(b,p)){
                    np.moveBat(a2, b, np.convertPosToStr(p));
                    tourJ2Move = false;
                    Case c = np.getCaseGb(p.getPosX(), p.getPosY());
                    if(c.explosionMineN()){
                        b.touché();
                        if(b.getPv() <= 0)
                            np.coulé(a2,b);
                    }
                    if(c.explosionMineA()){
                        np.coulé(a2,b);
                    }
                }
                posBatChoisi = null;
                setTourJ1Tir(true);
            }
    }
    
}
