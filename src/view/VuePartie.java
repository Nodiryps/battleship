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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Armee;
import model.Bateau;
import model.NouvPartie;
import javafx.scene.text.TextAlignment;
import model.Mine;
import model.TypeB;
import model.TypeM;
import static view.VueConsole.print;
import static view.VueConsole.printLN;


public class VuePartie extends BorderPane implements Observer {

    private final int SIZE;
    private final ControleurFx control;
    private static NouvPartie np;
    private final GrilleView grille;
    private final VBox vbox1;
    private final VBox vbox2;

    public VuePartie(Stage stage, int size, ControleurFx ctrl) {
        control = ctrl;
        np = control.getNp();
        SIZE = size;
        grille = new GrilleView();
        grille.setSizeConstraints();
        grille.setMinSize(500, 500);
        grille.setMaxSize(500, 500);
        grille.scaleXProperty();
        grille.scaleYProperty();
        this.setCenter(grille);
        
        vbox1 = new VBox();
        vbox1.setAlignment(Pos.TOP_CENTER);
        vbox1.setMinSize(200, 250);
        vbox1.setMaxSize(400, 500);
        vbox1.setPadding(new Insets(20));
        this.setLeft(vbox1);
        
        vbox2 = new VBox();
        vbox2.setAlignment(Pos.TOP_CENTER);
        vbox2.setMinSize(200, 250);
        vbox2.setMaxSize(400, 500);
        vbox2.setPadding(new Insets(20));
        this.setRight(vbox2);
        
        affEtatArmee();
        
        stage.setScene(new Scene(this, 1000, 600));
        stage.setTitle("Bataille Navale leFilmAvecRihanna (déplacer avec souris)");
        stage.show();
        
        getStylesheets().add("view/BoxView.css");
        getStyleClass().add("bckgrnd");
        
    }
    
    @Override
    public void update(Observable o, Object arg) {
            grille.nouvMer();
        }
    
    public void affEtatArmee(){
            vbox1.getChildren().clear();
            vbox2.getChildren().clear();
            etatArmee(np.getArmeeFromList(0), vbox1, np);
            etatArmee(np.getArmeeFromList(1), vbox2, np);
        }
    
    public void etatArmee(Armee a, VBox vb, NouvPartie np) {
            Text nomA = new Text(a.getNom() + "\n");
            nomA.setTextAlignment(TextAlignment.CENTER);
            nomA.setFont(Font.font("Arial",FontWeight.BLACK,25));
            vb.getChildren().add(nomA);
            
            Text headTab = new Text("Pos\t\t" + "Type\t\t" + "Pv(%)");
            headTab.setTextAlignment(TextAlignment.CENTER);
            headTab.setFont(Font.font("Arial",FontWeight.BLACK,15));
            vb.getChildren().add(headTab);
            
            printLN("");
            afficheEtatArmeeContent(a, vb, np);
    }       
        
    private void afficheEtatArmeeContent(Armee a, VBox vb, NouvPartie np){
        printLN(etatArmeeContent(a, vb, np));
    }

    private Text etatArmeeContent(Armee a, VBox vb, NouvPartie np){
        Text contentTab = new Text();
        contentTab.setFont(Font.font("Arial",FontWeight.LIGHT,11));
        contentTab.setTextAlignment(TextAlignment.CENTER);
        for(Bateau b : a.getListBat()){
            contentTab = new Text
                                ( 
                                np.convertPosToStr(b.getXY()) + "\t\t"
                                + b.getTypeB() + "\t\t"
                                + (b.getPv()*1.0 / b.getMaxPv()) * 100 + "%"
                                + "\n"
                                );
            vb.getChildren().add(contentTab);
        }return contentTab;
    }
    
    protected class GrilleView extends GridPane {
    
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
            for (int c = 0; c < np.getTailleGb(); ++c) 
                for (int l = 0; l < np.getTailleGb(); ++l) 
                    if (np.getMerGb()[c][l].getBat() != null) 
                        addBoatView(c, l);
                    else 
                        add(new EmptyBoxView(c, l), c, l);
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
        
        private class BatGdView extends BoxView{
                private Bateau b;
                private TypeB type;
                
                public BatGdView(int x, int y){
                    type = b.getTypeGrandBat();
                    getStyleClass().add("batG");
                    setOnMouseClicked(e -> control.boatClicked(x, y));
                }
            }
        
        private class BatPtView extends BoxView{
            private Bateau b;
            private TypeB type;

            public BatPtView(int x, int y){
                type = b.getTypePetitBat();
                getStyleClass().add("batP");
                setOnMouseClicked(e -> control.boatClicked(x, y));
            }
        }
            
        private void addBoatView(int col, int ligne){
            for(int i = 0; i < 1; ++i)
                add(new BatGdView(col, ligne), col, ligne);
            for(int j = 0; j < 2; ++j)
                add(new BatPtView(col, ligne), col, ligne);
        }

        private class MineNormView extends BoxView{
            private Mine m;
            private TypeM type;
            
            public MineNormView(int x, int y){
                type = m.getTypeMNormale();
                getStyleClass().add("empty");
                setOnMouseClicked(e -> control.emptyBoxClicked(x, y));
            }
        }
        
        private class MineAtomView extends BoxView{
            private Mine m;
            private TypeM type;
            
            public MineAtomView(int x, int y){
                type = m.getTypeMAtom();
                getStyleClass().add("empty");
                setOnMouseClicked(e -> control.emptyBoxClicked(x, y));
            }
        }

        // La vue d'un bateau
//        public class BoatView extends BoxView {
//            protected Bateau b;
//            protected TypeB type;
            
            
//            public BoatView(int x, int y) {
//                getStyleClass().add("batG");
//                setOnMouseClicked(e -> control.boatClicked(x, y));
//            }
//        }
        
        
        // La vue d'une mine
    
    }
    
    
}
