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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import model.Armee;
import model.Bateau;
import model.Builder;
import model.Case;
import model.NouvPartie;
import model.Position;
import model.TypeB;
import model.TypeM;

/**
 *
 * @author Spy
 */
public class VueBuilder extends BorderPane implements Observer{

    private final ControleurFx CTRL;
    private NouvPartie np;
    private final int SIZE;
    private Builder bldr;
    private FlowPane flowp1;
    private FlowPane flowp2;
    private SeaView merVide = new SeaView();
    Armee a1;
    Armee a2;
    private boolean modeDebug = false;
    private boolean placementManual;
    
    public VueBuilder(int size, ControleurFx ctrl, Stage stage) {
        this.SIZE = size +1;
        this.CTRL = ctrl;
        this.np = CTRL.getNp();
        this.bldr = CTRL.getBldr();
        a1 = np.getArmeeFromList(0);
        a2 = np.getArmeeFromList(1);
        placementManual = !CTRL.isPlacementAuto();
        
        flowp1 = new FlowPane();
        settingsFlowpane(flowp1);
        this.setLeft(flowp1);
        
        flowp2 = new FlowPane();
        settingsFlowpane(flowp2);
        this.setRight(flowp2);
        
        stage.setScene(new Scene(this, 1000, 600));
        stage.setTitle("BATTLESHIP (déplacer avec souris)");
        stage.show();
    }
    
    private void settingsFlowpane(FlowPane f){
        f.setAlignment(Pos.TOP_CENTER);
        f.setAlignment(Pos.TOP_CENTER);
        f.setMinSize(200, 250);
        f.setMaxSize(400, 500);
        f.setPadding(new Insets(20));
    }

    @Override
    public void update(Observable o, Object arg) {
            merVide.nouvMer();
            affListesBatAPlacer();
    }
    
    public void affListesBatAPlacer(){
        flowp1.getChildren().clear();
        flowp2.getChildren().clear();
        distribBatFlowpane();
    }
    
//    public class listesBatAPlacer extends FlowPane{
        public void distribBatFlowpane(){
            getChildren().clear();
            for(Armee a : np.getListArmees())
                for(Bateau b : a.getListBat()){
                    if(b.getTypeB() == TypeB.GRAND)
                        if(a == a1)
                            flowp1.getStyleClass().add("batG1");
                        else
                            flowp2.getStyleClass().add("batG2");

                    else if(a == a1)
                            flowp1.getStyleClass().add("batP1");
                        else
                            flowp2.getStyleClass().add("batP2");
                }
//        }
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
                if (placementManual && CTRL.getCptBatTot() < 6)
                    setOnMouseClicked(e -> CTRL.putBoatClickedManualy(x, y));
                if(modeDebug){
                    if (placementManual) 
                        getStyleClass().add("empty");
                    else {
                        Case c = np.getCaseGb(y, x);
                        if(c.getMine() != null)
                            if(c.getTypeMine() == TypeM.NORMALE)
                                getStyleClass().add("mineN");
                            else 
                                getStyleClass().add("mineA");
                    }
                }else
                    getStyleClass().add("empty");
            }
        }
        
        private class BoatView extends BoxView{
            public BoatView(int x, int y){
                Bateau b = np.getBatFromCase(y, x);
                for(Armee a : np.getListArmees()){
                    if(a == a1)
                        if(b.getTypeB() == TypeB.GRAND)
                            getStyleClass().add("batG1");
                        else 
                            getStyleClass().add("batP1");
                    if(a == a2)
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
            for (int c = 0; c < np.getTailleGb() -1; ++c) 
                for (int l = 0; l < np.getTailleGb() -1; ++l){
                    if(placementManual)
                        for(Armee a : np.getListArmees())
                            for(Bateau b : a.getListBat())
                                if(b.getXY() != null)
                                    add(new BoatView(c, l), c + 1, l + 1);
                add(new EmptyBoxView(c, l), c + 1, l + 1);
                }
        }

        private void nouvMer() {
            getChildren().clear();
            for (int c = 0; c < np.getTailleGb(); ++c) 
                for (int l = 0; l < np.getTailleGb(); ++l) 
                    if (np.getBatFromCase(l, c) != null) 
                        add(new BoatView(c, l), c, l);
                    else 
                        add(new EmptyBoxView(c, l), c , l);
        }

        private class MoveBoatView extends BoxView{
            public MoveBoatView(int x, int y){
                if(modeDebug){
                    Case c = np.getCaseGb(y, x);
                    if(c.getMine() != null)
                        if(c.getTypeMine() == TypeM.NORMALE)
                            getStyleClass().add("mineN");
                        else if(c.getTypeMine() == TypeM.ATOMIQUE)
                            getStyleClass().add("mineA");
                        else
                            getStyleClass().add("destPoss");
                }else
                    getStyleClass().add("destPoss");
                setOnMouseClicked(e -> CTRL.moveBoatClicked(x, y));
            }
            
        }
    }
}


