package com.eiman.ejq;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controlador para el temporizador de cuenta atras.
 * Este controlador maneja la logica del temporizador y actualiza las etiquetas
 * con el tiempo restante en formato de minutos y segundos.
 */
public class TemporizadorController {

    private IntegerProperty tiempo;  // Tiempo en minutos
    private BooleanProperty fin;     // Estado de la cuenta atras
    private Timer timer;  // Cronometro de la cuenta atras

    @FXML
    private Label labelMin1;  // Primera cifra de los minutos
    @FXML
    private Label labelMin2;  // Segunda cifra de los minutos
    @FXML
    private Label labelSeg1; // Primera cifra de los segundos
    @FXML
    private Label labelSeg2; // Segunda cifra de los segundos

    /**
     * Constructor del controlador.
     * Inicializa las propiedades del tiempo y estado de la cuenta atras.
     */
    public TemporizadorController() {
        this.fin = new SimpleBooleanProperty(false); // Inicializa la propiedad fin como false
        this.tiempo = new SimpleIntegerProperty(-1); // Inicializa la propiedad tiempo a -1
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EjQ.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean setTiempo(int minutos) {
        if (minutos >= 1 && minutos <= 99) {
            this.tiempo.set(minutos);
            return true;
        }
        return false; // Devuelve false si los minutos no están entre 1 y 99
    }

    public void iniciar() {
        if (this.tiempo.get() <= 0) {
            System.err.println("Asigna los minutos antes de iniciar el temporizador");
            return;
        }

        timer = new Timer();
        int totalSegundos = this.tiempo.get() * 60;

        timer.scheduleAtFixedRate(new TimerTask() {
            private int restante = totalSegundos;

            @Override
            public void run() {
                if (restante < 0) {
                    timer.cancel();
                    estiloParado();
                    Platform.runLater(() -> fin.set(true)); // Indica que el temporizador ha terminado
                    return;
                }

                int minutos = restante / 60;
                int segundos = restante % 60;

                // Actualiza la interfaz usando Platform.runLater
                Platform.runLater(() -> {
                    labelMin1.setText(String.valueOf(minutos / 10));
                    labelMin2.setText(String.valueOf(minutos % 10));
                    labelSeg1.setText(String.valueOf(segundos / 10));
                    labelSeg2.setText(String.valueOf(segundos % 10));
                });

                restante--; // Decrementa los segundos restantes
            }
        }, 0, 1000);
    }

    public void detener() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            estiloParado();
        }
    }

    public void estiloParado() {
        Platform.runLater(() -> {
            // Aplica el estilo a un nodo específico
            if (labelMin1 != null) {
                labelMin1.getStyleClass().add("parado");
            }
            if (labelMin2 != null) {
                labelMin2.getStyleClass().add("parado");
            }
            if (labelSeg1 != null) {
                labelSeg1.getStyleClass().add("parado");
            }
            if (labelSeg2 != null) {
                labelSeg2.getStyleClass().add("parado");
            }
        });
    }

    public BooleanProperty finProperty() {
        return fin;
    }

    public IntegerProperty tiempoProperty() {
        return tiempo;
    }

}
