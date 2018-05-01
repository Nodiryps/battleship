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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Armee;
import model.Bateau;
import model.NouvPartie;
import javafx.scene.text.TextAlignment;
import model.Case;
import model.Position;
import model.TypeB;
import model.TypeM;
import static view.VueConsole.print;
import static view.VueConsole.printLN;


public class VuePartie extends BorderPane implements Observer {

    private final int SIZE;
    private final ControleurFx control;
    private static NouvPartie np;
    private Armee a1;
    private Armee a2;
    private SeaView grille;
    private VBox vbox1;
    private VBox vbox2;
    private FlowPane flowp1;
    private FlowPane flowp2;
    private boolean modeDebug = true;
    private Position posB;
    
    public VuePartie(Stage stage, int size, ControleurFx ctrl) {
        control = ctrl;
        np = control.getNp();
        a1 = control.getArmee1();
        a2 = control.getArmee2();
        SIZE = size;
        grille = new SeaView();
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
        
        if(!control.isPlacementAuto()){
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
            
            affListBatAPlacer();
        }
        
        affEtatArmee();
        
        stage.setScene(new Scene(this, 1000, 600));
        stage.setTitle("Bataille Navale leFilmAvecRihanna (déplacer avec souris)");
        stage.show();
        
    }
    
    @Override
    public void update(Observable o, Object arg) {
            grille.nouvMer();
        }
    
    public void affListBatAPlacer(){
        flowp1.getChildren().clear();
        flowp2.getChildren().clear();
        
    }
    
    private void listBatAPlacer(Armee a, SeaView.BatGdView BatG, SeaView.BatPtView BatP){
            for(Bateau b : a.getListBat())
                if(b.getTypeB() == TypeB.GRAND)
                    if(a == np.getArmeeFromList(0))
                        flowp1.getChildren().add(BatG);
                    else
                        flowp2.getChildren().add(BatG);
                
                else if(a == np.getArmeeFromList(0))
                        flowp1.getChildren().add(BatP);
                    else
                        flowp2.getChildren().add(BatP);
                    
    }
    
    public void affEtatArmee(){
            vbox1.getChildren().clear();
            vbox2.getChildren().clear();
            etatArmee(np.getArmeeFromList(0), vbox1, np);
            etatArmee(np.getArmeeFromList(1), vbox2, np);
        }
    
    private void etatArmee(Armee a, VBox vb, NouvPartie np) {
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

    protected class SeaView extends GridPane {
    
        
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
        
        private void boatToMove(Armee a){
            if(np.checkBatBonneArmee(a, np.convertPosToStr(posB))){
                Bateau b = np.getBatFromPos(a, np.convertPosToStr(posB));
                for(Position p : np.getListDestPoss(b))
                    if(!p.equals(b.getXY()))
                        this.add(new SeaView.MoveBoatView(p.getPosX(), p.getPosY()), p.getPosX(), p.getPosY());
            }else
                printLN("!! Attention, bateau ennemi !! \\°o°/ ");
        }

        private void nouvMer() {
            getChildren().clear();
            for (int c = 0; c < np.getTailleGb(); ++c) 
                for (int l = 0; l < np.getTailleGb(); ++l) 
                    if (np.getBatFromCase(c, l) != null) 
                        add(new BoatView(c, l), c, l);
                    else 
                        add(new EmptyBoxView(c, l), c, l);
            if(control.isTourMoveJ1() && posB != null)
                boatToMove(a1);
            else if(control.isTourMoveJ2() && posB != null)
                boatToMove(a2);
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
                if(!modeDebug){
                    Case c = np.getCaseGb(x, y);
                    if(c.getMine() != null)
                        if(c.getTypeMine() == TypeM.NORMALE)
                            getStyleClass().add("mineN");
                        else 
                            getStyleClass().add("mineA");
                }else
                    getStyleClass().add("empty");
                setOnMouseClicked(e -> control.emptyBoxClicked(x, y));
            }
        }
        public class BoatView extends BoxView{
            
            public BoatView(int x, int y){
                Position p = new Position(x,y);
                boatType(a1,p);
                boatType(a2,p);
                }
            }
            
            private void boatType(Armee a, Position p){
                if(np.checkBatBonneArmee(a, np.convertPosToStr(p))){
                    if(np.getTypeBatFromMer(p.getPosX(),p.getPosY()) == TypeB.GRAND)
                        add(new BatGdView(p.getPosX(),p.getPosY()), p.getPosX(),p.getPosY());
                    else 
                        add(new BatPtView(p.getPosX(),p.getPosY()), p.getPosX(),p.getPosY());
            }
        }
        
        public class MoveBoatView extends BoxView{
            
            public MoveBoatView(int x, int y){
                if(!modeDebug){
                    Case c = np.getCaseGb(x, y);
                    if(c.getMine() != null)
                        if(c.getTypeMine() == TypeM.NORMALE)
                            getStyleClass().add("mineN");
                        else 
                            getStyleClass().add("mineA");
                }else
                    getStyleClass().add("destPoss");
                setOnMouseClicked(e -> control.moveBoatClicked(np, x, y));
            }
            
        }
        
        public class BatGdView extends BoxView{
                
                public BatGdView(int x, int y){
                    getStyleClass().add("batG");
                    setOnMouseClicked(e -> control.boatClicked(np, x, y));
                }
        }
        
        public class BatPtView extends BoxView{

            public BatPtView(int x, int y){
                getStyleClass().add("batP");
                setOnMouseClicked(e -> control.boatClicked(np, x, y));
            }
        }
            
        public class MineNormView extends BoxView{
            
            public MineNormView(int x, int y){
                getStyleClass().add("mineN");
                setOnMouseClicked(e -> control.emptyBoxClicked(x, y));
            }
        }
        
        public class MineAtomView extends BoxView{
            
            public MineAtomView(int x, int y){
                getStyleClass().add("mineA");
                setOnMouseClicked(e -> control.emptyBoxClicked(x, y));
            }
        }
        
        public class CaseRadioactiveView extends BoxView{
            
            public CaseRadioactiveView(int x, int y){
                getStyleClass().add("caseRadio");
                setOnMouseClicked(e -> control.emptyBoxClicked(x, y));
            }
        }
        
    }
    
    
}
