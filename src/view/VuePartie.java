package view;

import control.ControleurFx;
import java.util.List;
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
import javafx.scene.paint.Color;
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

/**
 *
 * @author Spy
 */
public class VuePartie extends BorderPane implements Observer {
    private final int SIZE;
    private final ControleurFx CTRL;
    private static NouvPartie npVueP;
    private Armee a1;
    private Armee a2;
    private SeaView sv;
    private VBox vboxEA1;
    private VBox vboxEA2;
    private VBox vboxInstr;
    private boolean modeDebug = false;
    private Position posB;
    
    public VuePartie(Stage stage, int size, ControleurFx ctrl) {
        CTRL = ctrl;
        npVueP = CTRL.getNp();
        a1 = CTRL.getArmee1();
        a2 = CTRL.getArmee2();
        SIZE = size +1;
        
        sv = new SeaView();
        settingsSeaview(sv);
        this.setCenter(sv);

        vboxEA1 = new VBox();
        settingsEA(vboxEA1);
        vboxEA1.setPadding(new Insets(10,110,0,10));//HDBG
        this.setLeft(vboxEA1);

        vboxEA2 = new VBox();
        settingsEA(vboxEA2);
        vboxEA2.setPadding(new Insets(10,10,0,0));//HDBG
        this.setRight(vboxEA2);

        vboxInstr = new VBox();
        settingsInstr(vboxInstr);
        this.setBottom(vboxInstr);

        affEtatArmee();

        stage.setScene(new Scene(this, 1120, 600));
        stage.setTitle("BATTLESHIP (cliquez pour déplacer)");
        stage.show();
    }
    
    @Override
    public void update(Observable o, Object arg) {
        affInstrJoueurs();
        affEtatArmee();
        sv.nouvMer();
        //index();
    }
    
    public void affEtatArmee(){
        vboxEA1.getChildren().clear();
        vboxEA2.getChildren().clear();
        styleEtatArmee(npVueP.getArmeeFromList(0), vboxEA1);
        styleEtatArmee(npVueP.getArmeeFromList(1), vboxEA2);
    }
    
    private void styleEtatArmee(Armee a, VBox vb) {
        Text nomA = new Text(a.getNom() + "\n");
        nomA.setTextAlignment(TextAlignment.CENTER);
        nomA.setFont(Font.font("Monospaced",FontWeight.BLACK,25));
        vb.getChildren().add(nomA);
        if(a.equals(a1))
            nomA.setFill(Color.BLUEVIOLET);
        else
            nomA.setFill(Color.GREENYELLOW);

        Text headTab = new Text("Pos\t" + "Type\t" + "Pv(%)");
        headTab.setTextAlignment(TextAlignment.CENTER);
        headTab.setFont(Font.font("Monospaced",FontWeight.BLACK,15));
        vb.getChildren().add(headTab);

        contentEtatArmee(a, vb);
    }       
        
    private void contentEtatArmee(Armee a, VBox vb){
        Text contentTab = new Text();
        
        contentTab.setFont(Font.font("Monospaced",FontWeight.LIGHT,15));
        contentTab.setTextAlignment(TextAlignment.LEFT);
        
        for(Bateau b : a.getListBat()){
            contentTab = new Text(npVueP.convertPosToStr(b.getXY()) + "\t\t"
                                + b.getTypeB() + "\t\t"
                                + (b.getPv()*1.0 / b.getMaxPv())*100 + "%"
                                + "\nPortée du tir: " + porteeTir(b)
                                + "\n");
            vb.getChildren().add(contentTab);
        }
    }
    
    private String porteeTir(Bateau b){
        String s = ". . .   ¯\\_(ツ)_/¯";
        if(b.getPortee() > 0)
            s = b.getPortee() + " case(s).";
        return s;
    }
    
    private void affInstrJoueurs(){
        vboxInstr.getChildren().clear();
        instructionsJContent();
    }
    
    private void instructionsJContent(){
        Text instr = new Text();
        
        if(CTRL.isTourJ1Move() &&  !npVueP.checkBatBonneArmee(a1, posB)) 
            instr = new Text("Veuillez sélectioner un bateau vous appartenant, s.v.p.");
        if (CTRL.isTourJ2Move() && posB != null && !npVueP.checkBatBonneArmee(a2, posB))
            instr = new Text("Veuillez sélectioner un bateau vous appartenant, s.v.p.");
        
        if(CTRL.isTourJ1Tir())
            instr = new Text(a1.getNom() + "\nCliquez sur un bateau vous appartenant pour tirer.");
          
        if(CTRL.isTourJ2Tir())
            instr = new Text(a2.getNom() + "\nCliquez sur un bateau vous appartenant pour tirer.");
        
        if(CTRL.isTourJ1Move())
            instr = new Text(a1.getNom() + "\nVous pouvez maintenant déplacer un de vos bateaux.");
        
        if(CTRL.isTourJ2Move())
            instr = new Text(a2.getNom() + "\nVous pouvez maintenant déplacer un de vos bateaux.");
        
        if(npVueP.partieFinie())
            for(Armee a : npVueP.getListArmees())
                if(a.getSizeListBat() > 0){
                    instr = new Text("GAME OVER\n" + a.getNom() + " A GAGNE!!! ^^");
                }
        
        instr.setFont(Font.font("Monospaced",FontWeight.BOLD,18));
        instr.setTextAlignment(TextAlignment.CENTER);
        vboxInstr.getChildren().add(instr);
    }
    
    private void index(){
        for(int i = 0; i < SIZE -1; ++i){
            char x = npVueP.getAxeXGb()[i];
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
            
            sv.add(desChiffres, 0, i);
            sv.add(desLettres, i, 0);
        }
    }

    protected class SeaView extends GridPane {
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
        
        private void nouvMer() {
            getChildren().clear();
            Bateau b = CTRL.getBatChoisi();
            for (int c = 0; c < npVueP.getTailleGb(); ++c) 
                for (int l = 0; l < npVueP.getTailleGb(); ++l) 
                    if (npVueP.getBatFromCase(l, c) != null) 
                        this.add(new BoatView(c, l), c, l);
                    else 
                        this.add(new EmptyBoxView(c, l), c , l);
            
            affDestPossBonneArmee();
        }
        
        private void affDestPossBonneArmee(){
            if(CTRL.isTourJ1Move() && posB != null)
                if(npVueP.checkBatBonneArmee(a1, posB)){
                    Bateau b = npVueP.getBatFromPos(npVueP.convertPosToStr(posB));
                    affDestPoss(b);
                }
            if(CTRL.isTourJ2Move() && posB != null)
                if(npVueP.checkBatBonneArmee(a2, posB)){
                    Bateau b = npVueP.getBatFromPos(npVueP.convertPosToStr(posB));
                    affDestPoss(b);
                }
        }
        
        private void affDestPoss(Bateau b){
            List<Position> list = npVueP.getListDestPoss(b);
            for (Position p : list) {
                int x = p.getPosY();int y = p.getPosX();
            if(npVueP.caseAccessible(y, x))
                this.add(new SeaView.MoveBoxView(y, x), x, y);
            }
        }
        
        // La vue d'une "case"
        private abstract class BoxView extends Pane {
            public BoxView() {
                getStylesheets().add("view/BoxView.css");
            }
        }

        // La vue d'une "case" vide
        private class EmptyBoxView extends BoxView {
            public EmptyBoxView(int x, int y) {
                if(modeDebug){
                    Case c = npVueP.getCaseGb(y, x);
                    if(c.getMine() != null)
                        if(c.getTypeMine() == TypeM.NORMALE)
                            getStyleClass().add("mineN");
                        else 
                            getStyleClass().add("mineA");
                }else
                    getStyleClass().add("empty");
                setOnMouseClicked(e -> CTRL.moveBoatClicked(x, y));
            }
            
        }
        
        private class BoatView extends BoxView{
            public BoatView(int x, int y){
                Position p = new Position(y,x);
                boatType(p);
                if(!npVueP.partieFinie()){
                    if(CTRL.isTourJ1Move() && npVueP.checkBatBonneArmee(a1, CTRL.getPosBatChoisi()))
                        posB = CTRL.getPosBatChoisi();
                    else if(CTRL.isTourJ2Move() && npVueP.checkBatBonneArmee(a2, CTRL.getPosBatChoisi()))
                        posB = CTRL.getPosBatChoisi();
                    else
                        posB = null;
                    setOnMouseClicked(e -> CTRL.boatClicked(x, y));
                }
            }
        }
            
        private void boatType(Position p){
            Bateau b = npVueP.getBatFromPos(p);
            if(b != null){   
                if(npVueP.checkBatBonneArmee(a1, p)){
                    if(b.getTypeB() == TypeB.GRAND)
                        add(new BatGdA1View(p.getPosX(),p.getPosY(),b), p.getPosY(), p.getPosX());
                    else 
                        add(new BatPtA1View(p.getPosX(),p.getPosY(),b), p.getPosY(), p.getPosX());
                }else if(npVueP.checkBatBonneArmee(a2, p)){
                    if(b.getTypeB() == TypeB.GRAND)
                        add(new BatGdA2View(p.getPosX(),p.getPosY(),b), p.getPosY(), p.getPosX());
                    else 
                        add(new BatPtA2View(p.getPosX(),p.getPosY(),b), p.getPosY(), p.getPosX());
                }
            }
        }
        
        private class MoveBoxView extends BoxView{
            public MoveBoxView(int x, int y){
                if(modeDebug){
                    Case c = npVueP.getCaseGb(y, x);
                    if(c.getMine() != null)
                        if(c.getTypeMine() == TypeM.NORMALE)
                            getStyleClass().add("mineN");
                        else if(c.getTypeMine() == TypeM.ATOMIQUE)
                            getStyleClass().add("mineA");
                        else
                            getStyleClass().add("destPoss");
                }else
                    getStyleClass().add("destPoss");
                setOnMouseClicked(e -> CTRL.moveBoatClicked(y, x));
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
            
        public class MineNormView extends BoxView{
            public MineNormView(int x, int y){
                getStyleClass().add("mineN");
            }
        }
        
        public class MineAtomView extends BoxView{
            public MineAtomView(int x, int y){
                getStyleClass().add("mineA");
            }
        }
        
        public class CaseRadioactiveView extends BoxView{
            public CaseRadioactiveView(int x, int y){
                getStyleClass().add("caseRadio");
            }
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
    
    private void settingsEA(VBox vb){
        vb.setAlignment(Pos.TOP_CENTER);
        vb.setMinSize(200, 250);
        vb.setMaxSize(400, 500);
    }
    
    private void settingsInstr(VBox vb){
        vb.setAlignment(Pos.TOP_CENTER);
        vb.setMinSize(1000, 100);
        vb.setPadding(new Insets(20));
    }
    
    private void settingsFlowpane(FlowPane f){
        f.setAlignment(Pos.TOP_CENTER);
        f.setAlignment(Pos.TOP_CENTER);
        f.setMinSize(200, 250);
        f.setMaxSize(400, 500);
    }
}
