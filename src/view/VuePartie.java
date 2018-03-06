package view;

import control.ControleurFx;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import model.Gameboard;
import model.NouvPartie;
import model.Position;

public class VuePartie extends GridPane implements Observer {

    private final int SIZE;
    private final ControleurFx control;
    private Gameboard gb = new Gameboard(5);
    private final Set<Position> listPosOcc = gb.getPosOccupados();

    public VuePartie(Stage stage, int size, ControleurFx ctrl) {
        control = ctrl;
        SIZE = size;
        setSizeConstraints();
        stage.setScene(new Scene(this, 600, 400));
        stage.setTitle("Bataille Navale (clic pour déplacer)");
        stage.show();
    }
    
    // Pour que chaque ligne et chaque colonne soit dimensionnée
    private void setSizeConstraints() {
        for (int i = 0; i < SIZE; ++i) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100 / SIZE);
            getColumnConstraints().add(cc);
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(100 / SIZE);
            getRowConstraints().add(rc);
        }        
    }

    @Override
    public void update(Observable o, Object arg) {
        NouvPartie np = (NouvPartie) o;
        Position tmp;
        System.out.println(listPosOcc.size());
        Iterator<Position> it = listPosOcc.iterator();
        getChildren().clear();
            while(it.hasNext()){
                for (int c = 0; c < SIZE; ++c) {
                    for (int l = 0; l < SIZE; ++l) {
                        tmp = it.next();
                        if (tmp.getPosX() == c && tmp.getPosY() == l) {
                            add(new caseBat(c, l), c, l);
                        } 
                        else {
                            add(new caseVide(c, l), c, l);
                        }
                    }
                
                }
            }
    }
    
    // La vue d'une "case"
    private abstract class vueCase extends Pane {
        public vueCase() {
            getStylesheets().add("view/BoxView.css");
        }
    }

    // La vue d'une "case" vide
    private class caseVide extends vueCase {
        public caseVide(int x, int y) {
            getStyleClass().add("empty");
            setOnMouseClicked(e -> control.emptyBoxClicked(x, y));
        }
    }

    // La vue d'un bateau
    private class caseBat extends vueCase {
        public caseBat(int x, int y) {
            getStyleClass().add("boat");
            setOnMouseClicked(e -> control.boatClicked(x, y));
        }
    }
}
