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
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import model.Armee;
import model.Bateau;
import model.Builder;
import model.NouvPartie;
import model.TypeB;

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
    Armee a1;
    Armee a2;
    public VueBuilder(int size, ControleurFx ctrl, Stage stage) {
        this.SIZE = size;
        this.CTRL = ctrl;
        this.np = CTRL.getNp();
        this.bldr = CTRL.getBldr();
        a1 = np.getArmeeFromList(0);
        a2 = np.getArmeeFromList(1);
        
        flowp1 = new FlowPane();
        flowp1.setAlignment(Pos.TOP_CENTER);
        flowp1.setAlignment(Pos.TOP_CENTER);
        flowp1.setMinSize(200, 250);
        flowp1.setMaxSize(400, 500);
        flowp1.setPadding(new Insets(20));
        this.setLeft(flowp1);
        flowp2 = new FlowPane();
        flowp2.setAlignment(Pos.TOP_CENTER);
        flowp2.setAlignment(Pos.TOP_CENTER);
        flowp2.setMinSize(200, 250);
        flowp2.setMaxSize(400, 500);
        flowp2.setPadding(new Insets(20));
        this.setRight(flowp2);
        
        stage.setScene(new Scene(this, 1000, 600));
        stage.setTitle("BATTLESHIP (déplacer avec souris)");
        stage.show();
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void affListBatAPlacer(){
        flowp1.getChildren().clear();
        flowp2.getChildren().clear();
        
    }
    
    private void listBatAPlacer(Armee a, VuePartie.SeaView.BatGdA1View BatG1, VuePartie.SeaView.BatPtA1View BatP1, VuePartie.SeaView.BatGdA2View BatG2, VuePartie.SeaView.BatPtA2View BatP2){
        for(Bateau b : a.getListBat())
            if(b.getTypeB() == TypeB.GRAND)
                if(a == a1)
                    flowp1.getChildren().add(BatG1);
                else
                    flowp2.getChildren().add(BatG2);

            else if(a == a1)
                    flowp1.getChildren().add(BatP1);
                else
                    flowp2.getChildren().add(BatP2);
                    
    }
    
}
