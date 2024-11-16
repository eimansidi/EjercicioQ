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
    private int segundos;

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
        this.fin = new SimpleBooleanProperty(false);
        this.segundos = -1;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EjQ.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Función que asigna los segundos al temporizador
     *
     * @param segundos del temporizador
     * @return true/false
     */
    public boolean setSegundos(int segundos) {
        if (segundos >= 60) {
            int minutos = (int) (segundos / 60);
            if (minutos < 100) {
                this.segundos = segundos;
                return true;
            }
        } else {
            if (segundos > 0) {
                this.segundos = segundos;
                return true;
            }
        }
        return false;
    }

    /**
     * Inicia el temporizador y comienza la cuenta atras.
     */
    public void iniciar() {
        if (this.segundos == -1) {
            System.err.println("Asigna los segundos antes de iniciar el temporizador");
        } else {
            final int[] restante = {this.segundos};
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (restante[0] < 0) {
                        timer.cancel();
                        estiloParado();
                        Platform.runLater(() -> fin.set(true)); // Actualizar la propiedad fin para indicar que el temporizador ha terminado
                        return;
                    }
                    int mins = restante[0] / 60;
                    int mins1 = mins / 10;
                    int mins2 = mins % 10;
                    int segs = restante[0] % 60;
                    int segs1 = segs / 10;
                    int segs2 = segs % 10;
                    // Usar Platform.runLater para actualizar los labels del temporizador
                    Platform.runLater(() -> {
                        labelMin1.setText(mins1 + "");
                        labelMin2.setText(mins2 + "");
                        labelSeg1.setText(segs1 + "");
                        labelSeg2.setText(segs2 + "");
                    });
                    restante[0] -= 1;
                }
            }, 0, 1000);
        }
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
}
