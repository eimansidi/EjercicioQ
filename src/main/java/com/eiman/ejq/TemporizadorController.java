package com.eiman.ejq;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TemporizadorController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}