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
import static view.VueConsole.printLN;

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
    private List<Position> listePosA1 = new LinkedList<>();
    private List<Position> listePosA2 = new LinkedList<>();
    
    private final static int NB_GD_BAT = Armee.getNbGBat();
    private final static int NB_PT_BAT = Armee.getNbPBat();
    private final static int TOT_BAT_PAR_J = NB_GD_BAT + NB_PT_BAT;
   
    private static int cptTotBatJ1;
    private static int cptTotBatJ2;
    private static int cptPtBatJ1;
    private static int cptPtBatJ2;
    private static int cptBatTotMer;
    
    private boolean placementAuto = true;
    private boolean placementManuBatJ1 = true;
    private boolean placementManuBatJ2 = false;
    
    private boolean tourJ1Tir = true;
    private boolean tourJ1Move = false;
    private boolean tourJ2Tir = false;
    private boolean tourJ2Move = false;
    
    private boolean tirFiniJ1 = false;
    private boolean tirFiniJ2 = false;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        new VueParamPartie(stage, this); // Fenêtre initiale (saisie taille)
    }
    
    public void placementBatManuel(Bateau b){
        if(!this.placementAuto)
            batChoisi = b;
    }

    public void switchToBuildWindow(int size){
        vueBldr = new VueBuilder(size, this, stage);
        np.addObserver(vueBldr);
        np.setChangedAndNotify();
    }
    
    // fait apparaître la fenêtre principale de l'application
    public void switchToMainWindow(String j1, String j2, int size) {
        if(!placementAuto){
            build(size, nomsArmees(j1, j2), false);
            switchToBuildWindow(size);
        }else{
            build(size, nomsArmees(j1, j2), placementAuto);
            a1 = np.getArmeeFromList(0);
            a2 = np.getArmeeFromList(1);
            VuePartie mainWindow = new VuePartie(stage, np.getTailleGb(), this);
            np.addObserver(mainWindow);
            np.setChangedAndNotify(); // Provoque un 1er affichage
        }
    }
    
    private void build(int size, List<String> noms, boolean b){
        bldr = new Builder(size, noms, b);
        np = bldr.build();
    }
    
    // Quand l'utilisateur clique sur un bateau
    public void boatClicked(int x, int y) {
        if(tourJ1Tir || tourJ2Tir)
            tir(y, x);
        else 
            clickOnBoatToMove(y, x);
    }
    
    public void tir(int x, int y){
        if(tourJ1Tir)
            tirA1(x, y);
        if(tourJ2Tir)
            tirA2(x, y);
    }
    
    public void clickOnBoatToMove(int x, int y){
        if(tourJ1Move)
            selectBatA1(x, y);
        if(tourJ2Move)
            selectBatA2(x, y);
    }
    
    public void moveBoatClicked(int x, int y){
        if(tourJ1Move)
            moveBoatClickedA1(y, x);
        if(tourJ2Move)
            moveBoatClickedA2(y, x);
    }
    
    // clique sur une case vide pour placer le bateau sélectionné manuellemt
    public void putBoatClickedManualy(int x, int y) {
        List<Bateau> listBatJ1 = a1.getListBat(), listBatJ2 = a2.getListBat();
        putBoatA1(listBatJ1, x, y);
        putBoatA2(listBatJ2, x, y);
    }
    
    public boolean tirA1(int x, int y){
        Position p = new Position(x, y);
        if(np.checkBatBonneArmee(a1, p)){
            np.tir(a1, np.convertPosToStr(p));
            setTourJ1Tir(false);
            setTirFiniJ1(true);
            setTourJ1Move(true);
            return true;
        } 
        np.setChangedAndNotify();
        return false;
    }
    
    public boolean tirA2(int x, int y){
        Position p = new Position(x, y);
        if(np.checkBatBonneArmee(a2, p)){
            np.tir(a2, np.convertPosToStr(p));
            setTourJ2Tir(false);
            setTirFiniJ2(true);
            setTourJ2Move(true);
            return true;
        } 
        np.setChangedAndNotify();
        return false;
    }
    
    private void selectBatA1(int x, int y){
        Position p = new Position(x, y);
        if(np.checkBatBonneArmee(a1, p))
            posBatChoisi = p;
        else
            posBatChoisi = null;
        np.setChangedAndNotify();
    }
    
    private void selectBatA2(int x, int y){
        Position p = new Position(x, y);
        if(tourJ2Move)
            if(np.checkBatBonneArmee(a2, p))
                posBatChoisi = p;
            else
                posBatChoisi = null;
        np.setChangedAndNotify();
    }
    
    private void moveBoatClickedA1(int x, int y){
        Position p = new Position(x, y);
        Bateau b;
        if(np.checkBatBonneArmee(a1, posBatChoisi)){
            b = a1.getBatFromPos(posBatChoisi);
            np.move(a1,b,p);
            tourJ1Move = false;
            posBatChoisi = null;
            setTourJ2Tir(true);
            
        }
        np.setChangedAndNotify();
    }
    
    private void moveBoatClickedA2(int x, int y){
        Position p = new Position(x, y);
        Bateau b;   
        if(np.checkBatBonneArmee(a2, posBatChoisi)){
            b = a2.getBatFromPos(posBatChoisi);
            np.move(a2,b,p);
            tourJ2Move = false;
            posBatChoisi = null;
            setTourJ1Tir(true);
        }
        np.setChangedAndNotify();
    }
    
    private void putBoatA1(List<Bateau> list, int y, int x){
        if(placementManuBatJ1 && cptTotBatJ1 < 3){
            if(batChoisi != null){
                if(batChoisi.getTypeB() == TypeB.GRAND){
                    setBatInBox(list.get(0), x, y);
                    ++cptBatTotMer;
                    ++cptTotBatJ1;
                }else if (batChoisi.getTypeB() == TypeB.PETIT && cptPtBatJ1 < 2) {
                    if(cptPtBatJ1 == 1){
                        setBatInBox(list.get(1), x, y);
                        ++cptBatTotMer;
                    }else{
                        setBatInBox(list.get(2), x, y);
                        ++cptBatTotMer;
                    }
                    ++cptTotBatJ1;
                    ++cptPtBatJ1;
                }
            }                
            placementManuBatJ1 = false;
            placementManuBatJ2 = true;
            listePosA1.add(new Position(x, y));
        }
    }
    
    private void putBoatA2(List<Bateau> list, int y, int x){
        if(placementManuBatJ2 && cptTotBatJ2 < 3){
            if(batChoisi != null){
                if(batChoisi.getTypeB() == TypeB.GRAND){
                    setBatInBox(list.get(0), x, y);
                    ++cptBatTotMer;
                    ++cptTotBatJ2;
                }else if (batChoisi.getTypeB() == TypeB.PETIT && cptPtBatJ2 < 2) {
                    if(cptPtBatJ2 == 1){
                        setBatInBox(list.get(1), x, y);
                        ++cptBatTotMer;
                    }else{
                        setBatInBox(list.get(2), x, y);
                        ++cptBatTotMer;
                    }
                    ++cptTotBatJ2;
                    ++cptPtBatJ2;
                }
            }  
            placementManuBatJ1 = true;
            placementManuBatJ2 = false;
            listePosA2.add(new Position(x, y));
        }
    }
    
    private void setBatInBox(Bateau b, int x, int y){
        b.setPos(new Position(x, y));
        Case box = np.getCaseGb(x, y);
        box.setBat(b);
    }
    
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

    public boolean listePosA1Contains(Position p) {
        return listePosA1.contains(p);
    }

    public boolean listePosA2Contains(Position p) {
        return listePosA2.contains(p);
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

    public int getCptBatTot() {
        return cptBatTotMer;
    }
    
    public boolean isPlacementJ1() {
        return placementManuBatJ1;
    }

    public boolean isPlacementJ2() {
        return placementManuBatJ2;
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

    public boolean isTirFiniJ1() {
        return tirFiniJ1;
    }

    public void setTirFiniJ1(boolean tirFiniJ1) {
        this.tirFiniJ1 = tirFiniJ1;
    }

    public boolean isTirFiniJ2() {
        return tirFiniJ2;
    }

    public void setTirFiniJ2(boolean tirFiniJ2) {
        this.tirFiniJ2 = tirFiniJ2;
    }

    public void setPlacementAuto(boolean b){
        this.placementAuto = b;
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
    
    private List<String> nomsArmees(String j1, String j2){
        List<String> noms = new LinkedList();
        noms.add(j1);
        noms.add(j2);
        return noms;
    }
}
