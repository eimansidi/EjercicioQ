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

    // Propiedad que representa el tiempo en minutos
    private IntegerProperty tiempo;

    // Propiedad booleana que indica si el temporizador ha terminado
    private BooleanProperty fin;

    // Objeto Timer que maneja la cuenta atras
    private Timer timer;

    // Etiqueta para la primera cifra de los minutos
    @FXML
    private Label labelMin1;

    // Etiqueta para la segunda cifra de los minutos
    @FXML
    private Label labelMin2;

    // Etiqueta para la primera cifra de los segundos
    @FXML
    private Label labelSeg1;

    // Etiqueta para la segunda cifra de los segundos
    @FXML
    private Label labelSeg2;

    /**
     * Constructor del controlador.
     * Inicializa las propiedades del tiempo y estado de la cuenta atras,
     * y carga el archivo FXML correspondiente.
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

    /**
     * Establece el tiempo en minutos para el temporizador.
     * @param minutos el tiempo en minutos (entre 1 y 99)
     * @return true si el tiempo es valido, false en caso contrario
     */
    public boolean setTiempo(int minutos) {
        if (minutos >= 1 && minutos <= 99) {
            this.tiempo.set(minutos);
            return true;
        }
        return false; // Devuelve false si los minutos no estan entre 1 y 99
    }

    /**
     * Inicia el temporizador y comienza la cuenta atras.
     */
    public void iniciar() {
        if (this.tiempo.get() <= 0) {
            System.err.println("Asigna los minutos antes de iniciar el temporizador");
            return;
        }

        timer = new Timer();
        int totalSegundos = this.tiempo.get() * 60;

        // Programa la tarea que actualiza el temporizador cada segundo
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

                // Actualiza la interfaz de usuario en el hilo principal
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

    /**
     * Detiene el temporizador y cancela la tarea programada.
     */
    public void detener() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            estiloParado();
        }
    }

    /**
     * Aplica un estilo visual indicando que el temporizador esta detenido.
     */
    public void estiloParado() {
        Platform.runLater(() -> {
            // Aplica el estilo a las etiquetas si no son nulas
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

    /**
     * Obtiene la propiedad booleana que indica si el temporizador ha terminado.
     * @return la propiedad booleana fin
     */
    public BooleanProperty finProperty() {
        return fin;
    }

    /**
     * Obtiene la propiedad del tiempo en minutos del temporizador.
     * @return la propiedad del tiempo
     */
    public IntegerProperty tiempoProperty() {
        return tiempo;
    }
}
