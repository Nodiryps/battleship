package view;

import control.ControleurFx;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
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
import javafx.stage.Stage;
import model.Armee;
import model.Bateau;
import model.NouvPartie;
import javafx.scene.text.TextAlignment;
import model.Case;
import model.Position;
import model.TypeB;
import model.TypeM;
import static view.VueConsole.printLN;


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
        SIZE = size + 1;
        
        sv = new SeaView();
        sv.setSizeConstraints();
        sv.setMinSize(500, 500);
        sv.setMaxSize(500, 500);
        sv.scaleXProperty();
        sv.scaleYProperty();
        sv.setPadding(new Insets(10,0,0,0));
        this.setCenter(sv);
        
        vboxEA1 = new VBox();
        vboxEA1.setAlignment(Pos.TOP_CENTER);
        vboxEA1.setMinSize(200, 250);
        vboxEA1.setMaxSize(400, 500);
        vboxEA1.setPadding(new Insets(10,110,0,10));//H D B G
        this.setLeft(vboxEA1);
        
        vboxEA2 = new VBox();
        vboxEA2.setAlignment(Pos.TOP_CENTER);
        vboxEA2.setMinSize(200, 250);
        vboxEA2.setMaxSize(400, 500);
        vboxEA2.setPadding(new Insets(10,10,0,0));
        this.setRight(vboxEA2);
        
        vboxInstr = new VBox();
        vboxInstr.setAlignment(Pos.TOP_CENTER);
        vboxInstr.setMinSize(1000, 100);
        vboxInstr.setPadding(new Insets(20));
//        instructionsJContent();
        this.setBottom(vboxInstr);
        
        
        affEtatArmee();
        
        stage.setScene(new Scene(this, 1120, 600));
        stage.setTitle("BATTLESHIP (déplacer avec souris)");
        stage.show();
        
    }
    
    @Override
    public void update(Observable o, Object arg) {
        sv.nouvMer();
        affEtatArmee();
        affInstrJoueurs();
        index();
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
            nomA.setFont(Font.font("Arial",FontWeight.BLACK,25));
            vb.getChildren().add(nomA);
            if(a.equals(a1))
                nomA.setFill(Color.BLUEVIOLET);
            else
                nomA.setFill(Color.GREENYELLOW);
            
            Text headTab = new Text("Pos\t\t" + "Type\t\t" + "Pv(%)");
            headTab.setTextAlignment(TextAlignment.CENTER);
            headTab.setFont(Font.font("Arial",FontWeight.BLACK,15));
            vb.getChildren().add(headTab);
            
            printLN("\n" + contentEtatArmee(a, vb));
    }       
        
    private Text contentEtatArmee(Armee a, VBox vb){
        Text contentTab = new Text();
        contentTab.setFont(Font.font("Arial",FontWeight.LIGHT,15));
        contentTab.setTextAlignment(TextAlignment.LEFT);
        for(Bateau b : a.getListBat()){
            contentTab = new Text
                                ( 
                                npVueP.convertPosToStr(b.getXY()) + "\t\t"
                                + b.getTypeB() + "\t\t"
                                + (b.getPv()*1.0 / b.getMaxPv()) * 100 + "%"
                                + "\n"
                                );
            vb.getChildren().add(contentTab);
        }return contentTab;
    }
    
    private void affInstrJoueurs(){
        vboxInstr.getChildren().clear();
        instructionsJContent();
    }
    
    private void instructionsJContent(){
        Text instr = new Text();
        
        if(!(CTRL.isTourJ1Move() && posB != null && npVueP.checkBatBonneArmee(a1, posB)) 
        || !(CTRL.isTourJ2Move() && posB != null && npVueP.checkBatBonneArmee(a2, posB)))
            instr = new Text("Veuillez sélectioner un bateau vous appartenant, s.v.p.");
        
//        if(!(CTRL.isTourJ1Move() && npVueP.checkBatBonneArmee(a1, CTRL.getPosBatChoisi()))
//        || !(CTRL.isTourJ2Move() && npVueP.checkBatBonneArmee(a2, CTRL.getPosBatChoisi())))
        
        if(CTRL.isTourJ1Tir())
            instr = new Text("Avec quel bateau voulez-vous tirer, " + a1.getNom() + "?");
//        if(!CTRL.isTourJ1Tir() && !CTRL.isTourJ1Move())
//            instr = new Text("PEW! PEW!(portée: " + b.getPortee() + ")");
//        else
//            instr = new Text("Vous avez fait " + b.getPortee() + " de portée... -____-\"");
            instr = new Text("Avec quel bateau voulez-vous tirer, " + a1.getNom() + "?");
        if(CTRL.isTourJ1Move())
            instr = new Text("Vous pouvez déplacer un de vos bateaux, " + Couleur.PURPLE + a1.getNom());
        
        if(CTRL.isTourJ2Tir())
            instr = new Text("Avec quel bateau voulez-vous tirer, " + a2.getNom() + "?");
//        if(!CTRL.isTourJ2Tir() && !CTRL.isTourJ2Move())
//            instr = new Text("PEW! PEW!(portée: " + b.getPortee() + ")");
//        else
//            instr = new Text("Vous avez fait " + b.getPortee() + " de portée... -____-\"");
        if(CTRL.isTourJ2Move())
            instr = new Text("Vous pouvez déplacer un de vos bateaux, " + a2.getNom());
        
        instr.setFont(Font.font("Arial",FontWeight.BOLD,18));
        instr.setTextAlignment(TextAlignment.CENTER);
        
        if(npVueP.partieFinie())
            for(Armee a : npVueP.getListArmees())
                if(a.getSizeListBat() > 0){
                    instr = new Text("\nGAME OVER\n" + a.getNom() + " a gagné! ^^");
                }
            
        vboxInstr.getChildren().add(instr);
    }
    
    private void index(){
        for(int i = 0; i < SIZE-1; ++i){
            char x = npVueP.getAxeXGb()[i];
            String y = Integer.toString(i + 1);
           
            Text desChiffres = new Text(" " + y + "\n\n");
            Text desLettres = new Text("\t    " + x + "\n\n");
            
            desChiffres.setFont(Font.font("Arial",FontWeight.BLACK,18));
            desChiffres.setTextAlignment(TextAlignment.LEFT);
            desChiffres.setFill(Color.CYAN);
            desLettres.setFont(Font.font("Arial",FontWeight.BLACK,18));
            desLettres.setTextAlignment(TextAlignment.RIGHT);
            desLettres.setFill(Color.CYAN);
            
            sv.add(desChiffres, 0, i);
            sv.add(desLettres, i, 0);
        }
    }

    protected class SeaView extends GridPane {
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
        
//        private void nouvMerVide(){
//            for (int c = 0; c < npVueP.getTailleGb(); ++c) 
//                for (int l = 0; l < npVueP.getTailleGb(); ++l){
//                    if(CTRL.isPlacementAuto())
//                        for(Armee a : npVueP.getListArmees())
//                            for(Bateau b : a.getListBat())
//                                if(b.getXY() != null)
//                                    this.add(new SeaView.BoatView(c, l), c + 1, l + 1);
//                    this.add(new SeaView.EmptyBoxView(c, l), c + 1, l + 1);
//                }
//        }

        private void nouvMer() {
            getChildren().clear();
            for (int c = 0; c < npVueP.getTailleGb(); ++c) 
                for (int l = 0; l < npVueP.getTailleGb(); ++l) 
                    if (npVueP.getBatFromCase(l, c) != null) 
                        add(new BoatView(c, l), c, l);
                    else 
                        add(new EmptyBoxView(c, l), c , l);
            
            checkArmeeForAffDestPoss();
        }
        
        private void checkArmeeForAffDestPoss(){
            if(CTRL.isTourJ1Move() && posB != null)
                if(npVueP.checkBatBonneArmee(a1, posB)){
                    Bateau b = npVueP.getBatFromPos(npVueP.convertPosToStr(posB));
                    for (Position p : npVueP.getListDestPoss(b))
                        affDestPoss(p);
                }
            else if(CTRL.isTourJ2Move() && posB != null)
                if(npVueP.checkBatBonneArmee(a2, posB)){
                    Bateau b = npVueP.getBatFromPos(npVueP.convertPosToStr(posB));
                    for (Position p : npVueP.getListDestPoss(b))
                        affDestPoss(p);
                }
        }
        
        private void affDestPoss(Position p){
            if(npVueP.caseAccessible(p.getPosX(), p.getPosY()))
                this.add(new SeaView.MoveBoatView(p.getPosX(),p.getPosY()), 
                         p.getPosX()+1, p.getPosY()+1);
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
                if (!CTRL.isPlacementAuto())
                    setOnMouseClicked(e -> CTRL.emptyBoxClicked(x, y));
                if(modeDebug){
                    if (!CTRL.isPlacementAuto()) 
                        getStyleClass().add("empty");
                    else {
                        Case c = npVueP.getCaseGb(y, x);
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
                        add(new BatGdA1View(p.getPosX(),p.getPosY()), p.getPosY(), p.getPosX());
                    else 
                        add(new BatPtA1View(p.getPosX(),p.getPosY()), p.getPosY(), p.getPosX());
                }else if(npVueP.checkBatBonneArmee(a2, p)){
                    if(b.getTypeB() == TypeB.GRAND)
                        add(new BatGdA2View(p.getPosX(),p.getPosY()), p.getPosY(), p.getPosX());
                    else 
                        add(new BatPtA2View(p.getPosX(),p.getPosY()), p.getPosY(), p.getPosX());
                }
            }
        }
        
        private class MoveBoatView extends BoxView{
            public MoveBoatView(int x, int y){
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
                setOnMouseClicked(e -> CTRL.moveBoatClicked(x, y));
            }
            
        }
        
        public class BatGdA1View extends BoxView{
            public BatGdA1View(int x, int y){
                getStyleClass().add("batG1");
            }
        }
        
        public class BatPtA1View extends BoxView{
            public BatPtA1View(int x, int y){
                getStyleClass().add("batP1");
            }
        }
        public class BatGdA2View extends BoxView{
            public BatGdA2View(int x, int y){
                getStyleClass().add("batG2");
            }
        }
        
        public class BatPtA2View extends BoxView{
            public BatPtA2View(int x, int y){
                getStyleClass().add("batP2");
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
    
}
