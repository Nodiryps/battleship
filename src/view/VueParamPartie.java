package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import control.ControleurFx;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;

public class VueParamPartie extends VBox {

    private final ControleurFx control;
//    private static NouvPartie np;

    public VueParamPartie(Stage stage, ControleurFx ctrl) {
        control = ctrl;
        setup();
        stage.setTitle("Paramètres Partie");
        stage.setScene(new Scene(this, 300, 250));
        stage.show();
    }

    // Mise en place de la (racine de la) scene
    private void setup() {
        TextField size = new InputNumber();
        TextField j1 = new InputJoueur1();
        TextField j2 = new InputJoueur2();
        Button bt = new Button("OK");
        bt.setOnAction(e -> {
            if (!size.getText().isEmpty())
                switchToMainWindow(j1.getText(), j2.getText(), Integer.valueOf(size.getText()));
            else
                size.requestFocus(); // Laisse le focus au TextField
        });
        setOnKeyPressed(ke -> {
                if (ke.getCode().equals(KeyCode.ENTER) && !j1.getText().isEmpty() && !j2.getText().isEmpty() 
                                                       && Integer.valueOf(size.getText()) >= 5
                                                       && Integer.valueOf(size.getText()) <= 26) {
                    switchToMainWindow(j1.getText(), j2.getText(), Integer.valueOf(size.getText()));
                }else
                size.requestFocus(); // Laisse le focus au TextField
            }); 
        
        CheckBox checkbox = new CheckBox("Disposer les vaisseaux MANUELLEMENT");
        checkbox.setIndeterminate(false);
        
        checkbox.setOnAction(e -> {
            control.setPlacementAuto(false);
        });
        
        getChildren().addAll(j1, j2, size, bt, checkbox);
 
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(20));
        setSpacing(20);
    }

    private void switchToMainWindow(String j1, String j2, int size) {
        control.switchToMainWindow(j1, j2, size);
    }
    
    // Un TextField qui n'accepte que des saisies de nombre entiers naturels
    private class InputNumber extends TextField {
        InputNumber() {
            super("5");
            setAlignment(Pos.CENTER);
            setMaxWidth(50);
            installListeners();
        }
        
        private void installListeners() {
            // N'accepte que les chiffres
            textProperty().addListener((obs, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")){ 
                    setText(oldValue);
                }
            });
        }

    }
    
    private class InputJoueur1 extends TextField {
        InputJoueur1() {
            super("AL1");
            setAlignment(Pos.CENTER_LEFT);
            setMaxWidth(150);
        }

    }
    
    private class InputJoueur2 extends TextField {
        InputJoueur2() {
            super("SPY");
            setAlignment(Pos.CENTER_LEFT);
            setMaxWidth(150);
        }

    }
    
    
}
