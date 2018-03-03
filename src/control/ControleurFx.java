/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import model.Gameboard;
import view.VuePartie;
import view.VueParamPartie;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

/**
 *
 * @author Spy
 */
public class ControleurFx extends Application {
    private Stage stage;
    private Gameboard gb;
    
    // Vrai si on a cliqué sur un bateau pour le déplacer
    private boolean etatDeplacementBateau = false; 
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        new VueParamPartie(stage, this); // Fenêtre initiale (saisie taille)
    }

    // fait apparaître la fenêtre principale de l'application
    public void switchToMainWindow(int size) {
        VuePartie mainWindow = new VuePartie(stage, gb.getTAILLE(), this);
        gb = new Gameboard(gb.getTAILLE());
        gb.addObserver(mainWindow);
        gb.setChangedAndNotify(); // Provoque un 1er affichage
    }
    
    // Quand l'utilisateur clique sur une case vide
    public void emptyBoxClicked(int x, int y) {
        if(etatDeplacementBateau) {
//            np.setXY(x, y); // Déplace le bateau
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
