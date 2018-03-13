package view;

import control.ControleurFx;
import java.util.Observable;
import java.util.Observer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.NouvPartie;

public class VuePartie extends BorderPane implements Observer {

    private final int SIZE;
    private final ControleurFx control;
    private static NouvPartie np = VueParamPartie.getNouvPartie();
    private GrilleView grille;
    private Vbox vbox1;
    private Vbox vbox2;

    public VuePartie(Stage stage, int size, ControleurFx ctrl) {
        control = ctrl;
        SIZE = size;
        grille = new GrilleView();
        vbox1 = new Vbox();
        vbox2 = new Vbox();
        vbox1.setAlignment();
        vbox2.setAlignment();
        grille.setSizeConstraints();
        this.setCenter(grille);
        stage.setScene(new Scene(this, 500, 500));
        stage.setTitle("Bataille Navale (clic pour déplacer)");
        stage.show();
    }
    
    @Override
    public void update(Observable o, Object arg) {
            grille.nouvGridPane();
        }
    private class Vbox extends VBox{
        
        public void setAlignment(){
            this.setAlignment(Pos.TOP_LEFT);
            this.setMaxSize(250, 200);
        }
        
    
    }
    private class GrilleView extends GridPane {
    
        // Pour que chaque ligne et chaque colonne soit dimensionnée
        public void setSizeConstraints() {
            for (int i = 0; i < SIZE; ++i) {
                ColumnConstraints cc = new ColumnConstraints();
                cc.setPercentWidth(100 / SIZE);
                getColumnConstraints().add(cc);
                RowConstraints rc = new RowConstraints();
                rc.setPercentHeight(100 / SIZE);
                getRowConstraints().add(rc);
            }        
        }

        public void nouvGridPane() {
            getChildren().clear();
                for (int c = 0; c < np.getGb().getTAILLE(); ++c) {
                    for (int l = 0; l < np.getGb().getTAILLE(); ++l) {
                        if (np.getGb().getMer()[c][l].getBat() != null) {
                            add(new BoatView(c, l), c, l);
                        } 
                        else {
                            add(new EmptyBoxView(c, l), c, l);
                        }
                    }
                }
        }

        // La vue d'une "case"
        public abstract class BoxView extends Pane {

            public BoxView() {
                getStylesheets().add("view/BoxView.css");
            }
        }

        // La vue d'une "case" vide
        public class EmptyBoxView extends BoxView {

            public EmptyBoxView(int x, int y) {
                getStyleClass().add("empty");
                setOnMouseClicked(e -> control.emptyBoxClicked(x, y));
            }
        }

        // La vue d'un bateau
        public class BoatView extends BoxView {

            public BoatView(int x, int y) {
                getStyleClass().add("batG");
                setOnMouseClicked(e -> control.boatClicked(x, y));
            }
        }

        // La vue d'une mine
        private class MineView extends BoxView{

            public MineView(int x, int y){
                    getStyleClass().add("empty");
                    setOnMouseClicked(e -> control.emptyBoxClicked(x, y));
            }
        }
        
    
    }
    
    
    /*
    private class BoatView extends BoxView {
        
        public BoatView(Bateau b, int x, int y) {
            if(b instanceof BateauGrand){
                getStyleClass().add("batG");
                setOnMouseClicked(e -> control.boatClicked(x, y));
        }
            else if(b instanceof BateauPetit){
                getStyleClass().add("batP");
                setOnMouseClicked(e -> control.boatClicked(x, y));            
            }
        }
    }
    
    private class MineView extends BoxView{
        
        public MineView(Mine m, int x, int y){
            if(m instanceof MineNormale){
                getStyleClass().add("empty");
                setOnMouseClicked(e -> control.emptyBoxClicked(x, y));
            }
            else if(m instanceof MineAtomique){
                getStyleClass().add("empty");
                setOnMouseClicked(e -> control.emptyBoxClicked(x, y));
            }
        }
    }
    */
}
