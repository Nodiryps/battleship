package view;

import control.ControleurFx;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import model.NouvPartie;

public class VuePartie extends GridPane implements Observer {

    private final int SIZE;
    private final ControleurFx control;
    private static NouvPartie np = VueParamPartie.getNouvPartie();

    public VuePartie(Stage stage, int size, ControleurFx ctrl) {
        control = ctrl;
        SIZE = size;
        setSizeConstraints();
        stage.setScene(new Scene(this, 650, 650));
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
        np = (NouvPartie) o;
        getChildren().clear();
            for (int c = 0; c < np.getGb().getTAILLE(); ++c) {
                for (int l = 0; l < np.getGb().getTAILLE(); ++l) {
                    if (!np.getGb().getMer()[c][l].caseAccessible()) {
                        add(new boatView(c, l), c, l);
                    } 
                    else {
                        add(new emptyBoxView(c, l), c, l);
                    }
                }
            }
    }
    
    // La vue d'une "case"
    private abstract class boxView extends Pane {
        public boxView() {
            getStylesheets().add("view/BoxView.css");
        }
    }

    // La vue d'une "case" vide
    private class emptyBoxView extends boxView {
        public emptyBoxView(int x, int y) {
            getStyleClass().add("empty");
            setOnMouseClicked(e -> control.emptyBoxClicked(x, y));
        }
    }

    // La vue d'un bateau
    private class boatView extends boxView {
        public boatView(int x, int y) {
            getStyleClass().add("batP");
            setOnMouseClicked(e -> control.boatClicked(x, y));
        }
    }
}
