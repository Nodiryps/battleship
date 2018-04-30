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

/**
 *
 * @author Spy
 */
public class ControleurFx extends Application {
    private Stage stage;
    private NouvPartie np;
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
        VuePartie mainWindow = new VuePartie(stage, np.getTailleGb(), this);
        np.addObserver(mainWindow);
        np.setChangedAndNotify(); // Provoque un 1er affichage
    }
    
    // Quand l'utilisateur clique sur une case vide
    public void emptyBoxClicked(int x, int y) {
        if(etatDeplacementBateau) {
//            np.(x, y); // Déplace le bateau
            etatDeplacementBateau = false;
        }
    }
    
    // Quand l'utilisateur clique sur un bateau
    public void boatClicked(int x, int y) {
        etatDeplacementBateau = true;
    }
    
    public static void main(String[] args) {
        launch(args);
        
    }
}
