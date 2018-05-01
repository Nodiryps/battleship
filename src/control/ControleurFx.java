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

/**
 *
 * @author Spy
 */
public class ControleurFx extends Application {
    private Stage stage;
    private NouvPartie np;
    private Position posBatChoisi;
    private Armee a1;
    private Armee a2;
    private int totBatMer;
    private final boolean placementAuto = true;
    private boolean tourTirJ1 = true;
    private boolean tourTirJ2 = false;
    private boolean tourMoveJ1 = false;
    private boolean tourMoveJ2 = false;
    
    // Vrai si on a cliqué sur un bateau pour le déplacer
    private boolean etatDeplacementBateau = false; 
    
    public NouvPartie getNp() {
        return np;
    }
    
    public Armee getArmee1(){
        return a1;
    }
    
    public Armee getArmee2(){
        return a2;
    }

    public boolean isPlacementAuto(){
        return placementAuto;
    }
    
    public boolean isTourTirJ1() {
        return tourTirJ1;
    }

    public boolean isTourTirJ2() {
        return tourTirJ2;
    }

    public boolean isTourMoveJ1() {
        return tourMoveJ1;
    }

    public boolean isTourMoveJ2() {
        return tourMoveJ2;
    }

    public void setTourTirJ1(boolean tourTirJ1) {
        this.tourTirJ1 = tourTirJ1;
    }

    public void setTourTirJ2(boolean tourTirJ2) {
        this.tourTirJ2 = tourTirJ2;
    }

    public void setTourMoveJ1(boolean tourMoveJ1) {
        this.tourMoveJ1 = tourMoveJ1;
    }

    public void setTourMoveJ2(boolean tourMoveJ2) {
        this.tourMoveJ2 = tourMoveJ2;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        new VueParamPartie(stage, this); // Fenêtre initiale (saisie taille)
    }

    // fait apparaître la fenêtre principale de l'application
    public void switchToMainWindow(String j1, String j2, int size) {
        List<String> noms = new LinkedList();
        noms.add(j1);
        noms.add(j2);
        Builder bldr = new Builder(size, noms, placementAuto);
        np = bldr.build();
        a1 = np.getArmeeFromList(0);
        a2 = np.getArmeeFromList(1);
        VuePartie mainWindow = new VuePartie(stage, np.getTailleGb(), this);
        np.addObserver(mainWindow);
        np.setChangedAndNotify(); // Provoque un 1er affichage
    }
    
    // Quand l'utilisateur clique sur une case vide
    public void emptyBoxClicked(int x, int y) {
        Bateau b;
        boolean j1 = true, 
                j2 = false;
        int nbBatJ = 0,
                nbPB = 0;
        if(!placementAuto && totBatMer < 6)
            for(int joueurs = 0; joueurs < 2; ++joueurs)
                if(j1 && nbBatJ <3){
                    List<Bateau> listJ1 = a1.getListBat();
                    if(b != null && b.getTypeB() == b.getTypeGrandBat()){
                        b = listJ1.get(0); 
                        b.setPos(new Position(x, y));
                        Case kaz = np.getCaseGb(x, y);
                        kaz.setBat(b);
                        ++totBatMer;
                        ++nbBatJ;
                    }else if(b != null && b.getTypeB() == b.getTypePetitBat()){
                        if(nbPB == 1){
                            b = listJ1.get(1); 
                            b.setPos(new Position(x, y));
                            Case kaz = np.getCaseGb(x, y);
                            kaz.setBat(b);
                            ++totBatMer;
                        }else{
                            b = listJ1.get(2); 
                            b.setPos(new Position(x, y));
                            Case kaz = np.getCaseGb(x, y);
                            kaz.setBat(b);
                            ++totBatMer;
                        }
                        ++nbBatJ;
                        ++nbPB;
                    }
                    if(joueurs == 1){
                        j1 = false;
                        j2 = true;
                    }
                }
    }
    
    // Quand l'utilisateur clique sur un bateau
    public void boatClicked(NouvPartie np,int x, int y) {
        tirBoatClicked(np, x, y);
    }
    
    public void tirBoatClicked(NouvPartie np, int x, int y){
        Position p = new Position(x, y);
        if(isTourTirJ1())
            if(np.checkBatBonneArmee(a1, np.convertPosToStr(p))){
                np.tir(a1, np.convertPosToStr(p));
                setTourTirJ1(false);
                setTourMoveJ1(true);
            }
        if(isTourTirJ2())
            if(np.checkBatBonneArmee(a2, np.convertPosToStr(p))){
                np.tir(a2, np.convertPosToStr(p));
                setTourTirJ2(false);
                setTourMoveJ2(true);
            }
        if(isTourMoveJ1())
            boatToMoveClicked(a1, p);
        if(isTourMoveJ2())
            boatToMoveClicked(a2, p);
    }
    
    public void boatToMoveClicked(Armee a, Position p){
        if(np.checkBatBonneArmee(a, np.convertPosToStr(p)))
            posBatChoisi = p;
        else
            posBatChoisi = null;
    }
    
    public void moveBoatClicked(NouvPartie np, int x, int y){
        Bateau b;
        Position p = new Position(x, y);
        if(isTourMoveJ1())
            if(np.checkBatBonneArmee(a1, np.convertPosToStr(posBatChoisi))){
                b = a1.getBatFromPos(posBatChoisi);
                if(np.listDestPossContains(b,p)){
                    np.moveBat(a1, b, np.convertPosToStr(p));
                    this.tourMoveJ1 = false;
                    Case c = np.getCaseGb(x,y);
                    if(c.explosionMineN())
                        if(b.getPv() <= 0)
                            np.coulé(a1,b);
                    posBatChoisi = null;
                    setTourTirJ2(true);
                }
            }
        if(isTourMoveJ2())
            if(np.checkBatBonneArmee(a2, np.convertPosToStr(posBatChoisi))){
                b = a2.getBatFromPos(posBatChoisi);
                if(np.listDestPossContains(b,p)){
                    np.moveBat(a2, b, np.convertPosToStr(p));
                    this.tourMoveJ2 = false;
                    Case c = np.getCaseGb(x,y);
                    if(c.explosionMineN())
                        if(b.getPv() <= 0)
                            np.coulé(a2,b);
                    posBatChoisi = null;
                    setTourTirJ1(true);
                }
            }
    }
    
    
    
}
