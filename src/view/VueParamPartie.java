package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import control.ControleurFx;
import javafx.scene.input.KeyCode;

public class VueParamPartie extends VBox {

    private final ControleurFx control;
//    private static NouvPartie np;

    public VueParamPartie(Stage stage, ControleurFx ctrl) {
        control = ctrl;
        setup();
        stage.setTitle("Paramètres Partie");
        stage.setScene(new Scene(this, 300, 200));
        stage.show();
    }

    // Mise en place de la (racine de la) scene
    private void setup() {
        TextField tf = new InputNumber();
        TextField j1 = new InputJoueur();
        TextField j2 = new InputJoueur();
        Button bt = new Button("OK");
        bt.setOnAction(e -> {
            if (!tf.getText().isEmpty()) 
                switchToMainWindow(j1.getText(), j2.getText(), Integer.valueOf(tf.getText()));
            else
                tf.requestFocus(); // Laisse le focus au TextField
        });
        getChildren().addAll(j1, j2, tf, bt);
 
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
                if (!newValue.matches("\\d*")){ ////////////////////////&& (newValue >= 5 && newValue <= 26)) {
                    setText(oldValue);
                }
            });
            // Capture du Enter pour valider saisie
            setOnKeyPressed(ke -> {
                if (ke.getCode().equals(KeyCode.ENTER) && !getText().isEmpty()) {
                    switchToMainWindow(getText(), getText(), Integer.valueOf(getText()));
                }
            });            
        }

    }
    
    private class InputJoueur extends TextField {
        InputJoueur() {
            super("AAA");
            setAlignment(Pos.CENTER_LEFT);
            setMaxWidth(150);
            installListeners();
        }
        
        private void installListeners() {
            // Capture du Enter pour valider saisie
            setOnKeyPressed(ke -> {
                if (ke.getCode().equals(KeyCode.ENTER) && !getText().isEmpty()) {
                    switchToMainWindow(getText(), getText(),Integer.valueOf(getText()));
                }
            });            
        }

    }
    
    
}
