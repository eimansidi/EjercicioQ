package com.eiman.ejq;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Controlador para el temporizador de cuenta atras.
 * Este controlador maneja la logica del temporizador y actualiza las etiquetas
 * con el tiempo restante en formato de minutos y segundos.
 */
public class TemporizadorController {

    private IntegerProperty tiempo;  // Tiempo en minutos
    private BooleanProperty fin;     // Estado de la cuenta atras

    @FXML
    private Label labelMinutos1;  // Primera cifra de los minutos
    @FXML
    private Label labelMinutos2;  // Segunda cifra de los minutos
    @FXML
    private Label labelSegundos1; // Primera cifra de los segundos
    @FXML
    private Label labelSegundos2; // Segunda cifra de los segundos

    private Timeline timeline;  // Cronometro de la cuenta atras

    /**
     * Constructor del controlador.
     * Inicializa las propiedades del tiempo y estado de la cuenta atras.
     */
    public TemporizadorController() {
        this.tiempo = new SimpleIntegerProperty();
        this.fin = new SimpleBooleanProperty(false);
        setTiempo(80); // Establecer un tiempo inicial valido (entre 1 y 99)
    }

    /**
     * Metodo que se ejecuta cuando la interfaz grafica se inicializa.
     * Configura los listeners y el cronometro.
     */
    @FXML
    private void initialize() {
        // Establecer los valores iniciales en las etiquetas
        tiempo.addListener((observable, oldValue, newValue) -> actualizarEtiquetas(newValue.intValue()));

        // Crear un timeline para la cuenta atras
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> contar()));
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Iniciar la cuenta atras cuando la aplicacion se inicie
        timeline.play();
    }

    /**
     * Metodo que se llama cada segundo para decrementar el tiempo restante.
     * Si el tiempo llega a cero, detiene la cuenta atras.
     */
    private void contar() {
        int tiempoRestante = tiempo.get();

        if (tiempoRestante > 0) {
            tiempo.set(tiempoRestante - 1); // Decrementar el tiempo
        } else {
            fin.set(true); // Cambiar el estado a 'terminado'
            timeline.stop(); // Detener el cronometro
        }
    }

    /**
     * Metodo que actualiza las etiquetas con el tiempo restante.
     * Convierte el tiempo restante en minutos y segundos y actualiza las etiquetas
     * correspondientes.
     *
     * @param tiempoRestante El tiempo restante en segundos
     */
    private void actualizarEtiquetas(int tiempoRestante) {
        int minutos = tiempoRestante / 60;  // Calcular los minutos
        int segundos = tiempoRestante % 60; // Calcular los segundos

        // Actualizar las etiquetas con las cifras de los minutos y segundos
        labelMinutos1.setText(String.valueOf(minutos / 10));
        labelMinutos2.setText(String.valueOf(minutos % 10));
        labelSegundos1.setText(String.valueOf(segundos / 10));
        labelSegundos2.setText(String.valueOf(segundos % 10));
    }

    /**
     * Metodo para establecer el tiempo de manera segura entre 1 y 99 minutos.
     * Si el valor es menor que 1, lo ajusta a 1. Si es mayor que 99, lo ajusta a 99.
     * De lo contrario, asigna el valor proporcionado.
     *
     * @param minutos El tiempo a establecer en minutos
     */
    public void setTiempo(int minutos) {
        if (minutos < 1) {
            this.tiempo.set(1);  // Ajusta a 1 si el valor es menor
        } else if (minutos > 99) {
            this.tiempo.set(99); // Ajusta a 99 si el valor es mayor
        } else {
            this.tiempo.set(minutos);  // Si esta en el rango, asigna el valor
        }
    }

    /**
     * Metodo para obtener el tiempo restante en minutos.
     *
     * @return El tiempo restante en minutos
     */
    public int getTiempo() {
        return tiempo.get();
    }
}
