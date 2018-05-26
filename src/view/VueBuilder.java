/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.ControleurFx;
import java.util.Observable;
import java.util.Observer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Armee;
import model.Bateau;
import model.NouvPartie;
import model.Position;
import model.TypeB;

/**
 *
 * @author Spy
 */
public class VueBuilder extends BorderPane implements Observer{
    private final int SIZE;
    private final ControleurFx CTRL;
    private NouvPartie np;
    Armee a1;
    Armee a2;
    private SeaView gbVide;
    private BorderPane bpA1 = new BorderPane();
    private BorderPane bpA2 = new BorderPane();
    private ListeBateaux gpA1;
    private ListeBateaux gpA2;
    private VBox vbInstr;
    private boolean placementManual;
    
    public VueBuilder(int size, ControleurFx ctrl, Stage stage) {
        this.CTRL = ctrl;
        this.np = CTRL.getNp();
        a1 = np.getArmeeFromList(0);
        a2 = np.getArmeeFromList(1);
        this.SIZE = size +1;
        placementManual = !CTRL.isPlacementAuto();
        
        gbVide = new SeaView();
        gbVide.nouvMerVide();
        settingsSeaview(gbVide);
        this.setCenter(gbVide);
        
        gpA1 = new ListeBateaux();
        settingsListesBat(gpA1);
        gpA1.putBoatInListes(a1);
        
        gpA2 = new ListeBateaux();
        settingsListesBat(gpA2);
        gpA2.putBoatInListes(a2);
        
        bpA1 = new BorderPane();
        settingsBorderpane(bpA1);
        bpA1.setCenter(gpA1);
        this.setLeft(bpA1);

        bpA2 = new BorderPane();
        settingsBorderpane(bpA2);
        bpA2.setCenter(gpA2);
        this.setRight(bpA2);
        
//        affListesBatAPlacer();

        vbInstr = new VBox();
        settingsInstr(vbInstr);
        this.setBottom(vbInstr);

        stage.setScene(new Scene(this, 1120, 600));
        stage.setTitle("BATTLESHIP (placer avec souris)");
        stage.show();
    }
    
    @Override
    public void update(Observable o, Object arg) {
        placementTerminé();    
        gbVide.nouvMer();
        affListesBatAPlacer();
        affInstrJoueurs();
//        index();
    }
    
    public void affListesBatAPlacer(){
        gpA1.putBoatInListes(a1);
        gpA2.putBoatInListes(a2);
    }
    
    private void affInstrJoueurs(){
        vbInstr.getChildren().clear();
        instructionsJContent();
    }
    
    public class SeaView extends GridPane {
        // La vue d'une "case"
        private abstract class BoxView extends Pane {
            public BoxView() {
                getStylesheets().add("view/BoxView.css");
            }
        }
        
        // La vue d'une "case" vide
        private class EmptyBoxView extends BoxView {
            public EmptyBoxView(int x, int y) {
                 getStyleClass().add("empty");
                 setOnMouseClicked(e -> CTRL.putBoatClickedManualy(x, y));
                 
            }
        }
        
        private class BoatView extends BoxView{
            public BoatView(int x, int y){
                Position p = new Position(x, y);
                if(CTRL.listePosA1Contains(p)){
                    Bateau b = np.getBatFromCase(y, x);
                    if(b.getTypeB() == TypeB.GRAND)
                        getStyleClass().add("batG1");
                    else 
                        getStyleClass().add("batP1");
                } else if(CTRL.listePosA2Contains(p)){
                    Bateau b = np.getBatFromCase(y, x);
                    if(b.getTypeB() == TypeB.GRAND)
                        getStyleClass().add("batG2");
                    else 
                        getStyleClass().add("batP2");
                }
            }
        }
        
        private void setSizeConstraints() {
            for (int i = 1; i < SIZE; ++i) {
                ColumnConstraints cc = new ColumnConstraints();
                cc.setPercentWidth(100 / SIZE);
                getColumnConstraints().add(cc);
                RowConstraints rc = new RowConstraints();
                rc.setPercentHeight(100 / SIZE);
                getRowConstraints().add(rc);
            }        
        }

        private void nouvMerVide(){
            for (int c = 0; c < SIZE -1; ++c) 
                for (int l = 0; l < SIZE -1; ++l){
                    if(placementManual)
                        for(Armee a : np.getListArmees())
                            for(Bateau b : a.getListBat())
                                if(b.getXY() != null)
                                    add(new BoatView(c, l), c, l);
                add(new EmptyBoxView(c, l), c, l);
                }
        }

        private void nouvMer() {
            getChildren().clear();
            for (int c = 0; c < np.getTailleGb(); ++c) 
                for (int l = 0; l < np.getTailleGb(); ++l) 
                    if (np.getBatFromCase(l, c) != null) 
                        this.add(new BoatView(c, l), c, l);
                    else 
                        this.add(new EmptyBoxView(c, l), c , l);
        }
    }
    
    private class ListeBateaux extends GridPane{
        private abstract class BoxView extends Pane {
            public BoxView() {
                getStylesheets().add("view/BoxView.css");
            }
        }
        
        public class BatGdA1View extends BoxView{
            private Bateau b;
            
            public BatGdA1View(int x, int y, Bateau b){
                this.b = b;
                getStyleClass().add("batG1");
                setOnMouseClicked(e->CTRL.placementBatManuel(b));
            }
        }
        
        public class BatPtA1View extends BoxView{
            private Bateau b;
            
            public BatPtA1View(int x, int y, Bateau b){
                this.b = b;
                getStyleClass().add("batP1");
                setOnMouseClicked(e->CTRL.placementBatManuel(b));
            }
        }
        public class BatGdA2View extends BoxView{
            private Bateau b;
            
            public BatGdA2View(int x, int y, Bateau b){
                this.b = b;
                getStyleClass().add("batG2");
                setOnMouseClicked(e->CTRL.placementBatManuel(b));
            }
        }
        
        public class BatPtA2View extends BoxView{
            private Bateau b;
            
            public BatPtA2View(int x, int y, Bateau b){
                this.b = b;
                getStyleClass().add("batP2");
                setOnMouseClicked(e->CTRL.placementBatManuel(b));
            }
        }
        
//        public class BatGdA1View extends BoxView{
//            public BatGdA1View(int x, int y){
//                getStyleClass().add("batG1");
//            }
//        }
//        
//        public class BatPtA1View extends BoxView{
//            public BatPtA1View(int x, int y){
//                getStyleClass().add("batP1");
//            }
//        }
//        public class BatGdA2View extends BoxView{
//            public BatGdA2View(int x, int y){
//                getStyleClass().add("batG2");
//            }
//        }
//        
//        public class BatPtA2View extends BoxView{
//            public BatPtA2View(int x, int y){
//                getStyleClass().add("batP2");
//            }
//        }
        
        
        private void putBoatInListes(Armee a){
            getChildren().clear();
            int cpt = 0;
            Bateau b = a.getBatFromList(cpt);
            if(cpt == 0 && b.getXY() == null){
                this.add(new ListeBateaux.BatGdA1View(0, 0, b), 0 + 0, 0 + 1); 
                ++cpt;
            }
            if (cpt == 1 && b.getXY() == null) {
                this.add(new ListeBateaux.BatPtA1View(1, 0, b), 1 + 0, 0 + 1);
                ++cpt;
            }
            if (cpt == 2 && b.getXY() == null) {
                this.add(new ListeBateaux.BatPtA1View(1, 0, b), 2 + 0, 0 + 1);
                ++cpt;
            }
        }
        
//        private void putBoatInListesA2(){
//            getChildren().clear();
//            int cpt = 0;
//            Bateau b = a2.getBatFromList(cpt);
//            if(cpt == 0 && b.getXY() == null){
//                this.add(new ListeBateaux.BatGdA2View(0, 0, b), 0 + 0, 0 + 1); 
//                ++cpt;
//            }
//            if (cpt == 1 && b.getXY() == null) {
//                this.add(new ListeBateaux.BatPtA2View(1, 0, b), 1 + 0, 0 + 1);
//                ++cpt;
//            }
//            if (cpt == 2 && b.getXY() == null) {
//                this.add(new ListeBateaux.BatPtA2View(1, 0, b), 2 + 0, 0 + 1);
//                ++cpt;
//            }
//        }
        
    }
    
    private void index(){
        for(int i = 0; i < SIZE-1; ++i){
            char x = np.getAxeXGb()[i];
            String y = Integer.toString(i + 1);
            Text desChiffres;
            Text desLettres;
                desChiffres = new Text("\n\n\n" + y + " ");
                desLettres = new Text("      " + x + "\n\n\n");
                desChiffres.setFont(Font.font("Monospaced",18));
                desChiffres.setTextAlignment(TextAlignment.LEFT);
                desChiffres.setFill(Color.CYAN);
                desLettres.setFont(Font.font("Monospaced",18));
                desLettres.setTextAlignment(TextAlignment.RIGHT);
                desLettres.setFill(Color.CYAN);
                gbVide.add(desChiffres, 0, i);
                gbVide.add(desLettres, i, 0);
        }
    }
    
    private void instructionsJContent(){
        Text instr = new Text();
        
        if(CTRL.isPlacementJ1()){
            instr = new Text("A vous de placez un de vos bateaux, " + a1.getNom());
        }
        
        if(CTRL.isPlacementJ2()){
            instr = new Text("A vous de placez un de vos bateaux, " + a2.getNom());
        }
        
        instr.setFont(Font.font("Monospaced",FontWeight.BOLD,18));
        instr.setTextAlignment(TextAlignment.CENTER);
            
        vbInstr.getChildren().add(instr);
    }
    
    private void placementTerminé(){
        if(CTRL.getCptBatTot() == 6)
            CTRL.switchToMainWindow(a1.getNom(), a2.getNom(), SIZE);
    }
    
    public void setSizeConstraints(GridPane gp) {
            for (int i = 0; i < 3; ++i) {
                ColumnConstraints cc = new ColumnConstraints();
                cc.setPercentWidth(100/6);
                gp.getColumnConstraints().add(cc);
                RowConstraints rc = new RowConstraints();
                rc.setPercentHeight(100/6);
                gp.getRowConstraints().add(rc);
            }
        }
    
    private void settingsSeaview(SeaView sv){
        sv.setSizeConstraints();
        sv.setMinSize(500, 500);
        sv.setMaxSize(500, 500);
        sv.scaleXProperty();
        sv.scaleYProperty();
        sv.setPadding(new Insets(10,0,0,0));//H D B G
    }
    
    private void settingsListesBat(GridPane gp){
        setSizeConstraints(gp);
        gp.setAlignment(Pos.TOP_CENTER);
        gp.setMinSize(200, 250);
        gp.setMaxSize(400, 500);
    }
    
    private void settingsBorderpane(BorderPane b){
        b.setMinSize(200, 250);
        b.setMaxSize(400, 500);
    }
    
    private void settingsInstr(VBox vb){
        vb.setAlignment(Pos.TOP_CENTER);
        vb.setMinSize(1000, 100);
        vb.setPadding(new Insets(20));
    }
    
}


