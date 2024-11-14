package com.eiman.ejq;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TemporizadorApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EjQ.fxml"));
        GridPane root = loader.load();

        // Establecer la escena
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TEMPORIZADOR");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
