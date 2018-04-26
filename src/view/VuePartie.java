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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Armee;
import model.Bateau;
import model.NouvPartie;
import static view.VueConsole.print;
import static view.VueConsole.printLN;

public class VuePartie extends BorderPane implements Observer {

    private final int SIZE;
    private final ControleurFx control;
    private static NouvPartie npVueParam = VueParamPartie.getNpVueParam();
    private final GrilleView grille;
    private final VBox vbox1;
    private final VBox vbox2;

    public VuePartie(Stage stage, int size, ControleurFx ctrl) {
        control = ctrl;
        SIZE = size;
        grille = new GrilleView();
        grille.setSizeConstraints();
        this.setCenter(grille);
        
        vbox1 = new VBox();
        vbox1.setAlignment(Pos.TOP_LEFT);
        vbox1.setMaxSize(250, 200);
        this.setLeft(vbox1);
        
        vbox2 = new VBox();
        vbox2.setAlignment(Pos.BOTTOM_LEFT);
        vbox2.setMaxSize(250, 200);
        this.setLeft(vbox2);
        
        stage.setScene(new Scene(this, 500, 500));
        stage.setTitle("Bataille Navale leFilmAvecRihanna (déplacer avec souris)");
        stage.show();
        
    }
    
    @Override
    public void update(Observable o, Object arg) {
            grille.nouvMer();
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

        public void nouvMer() {
            getChildren().clear();
                for (int c = 0; c < npVueParam.getTailleGb(); ++c) {
                    for (int l = 0; l < npVueParam.getTailleGb(); ++l) {
                        if (npVueParam.getMerGb()[c][l].getBat() != null) {
                            add(new BoatView(c, l), c, l);
                        } 
                        else {
                            add(new EmptyBoxView(c, l), c, l);
                        }
                    }
                }
        }
        
        public void etatArmee(Armee a, VBox vb, NouvPartie np) {
            Text nomA = new Text("\t" + a.getNom() + "\t\n");
            nomA.setFont(Font.font("Impact", 25));
            vb.getChildren().add(nomA);
            
            Text headTab = new Text("Position\t\t" + "Armée\t\t" + "Type\t\t" + "Intégrité (%)");
            headTab.setFont(Font.font("Impact",11));
            vb.getChildren().add(headTab);
            
            printLN("");
            afficheArmee(a, vb, np);
        }       
        
        private void afficheArmee(Armee a, VBox vb, NouvPartie np){
            printLN(etatArmeeContent(a, vb, np));
        }
        
        private Text etatArmeeContent(Armee a, VBox vb, NouvPartie np){
            Text contentTab = new Text();
            for(Bateau b : a.getListBat()){
                contentTab = new Text
                                    ( 
                                    np.convertPosToStr(b.getXY())
                                    + "\t\t\t" + a.getNom()
                                    + "\t\t" + b.getTypeB()
                                    + "\t\t" + (b.getPv()*1.0 / b.getMaxPv()) * 100 + "%\t"
                                    + "\n"
                                    );
                vb.getChildren().add(contentTab);
            }return contentTab;
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
