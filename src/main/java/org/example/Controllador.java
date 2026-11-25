package org.example;

import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

/**
 * Controlador de la calculadora que conecta el modelo con la vista.
 * Permite usar la calculadora con el ratón y con el teclado.
 * @author Celia Alamo Calle
 */
public class Controllador {

    /**
     * Llamamos a los id del archivo calculadora.fxml
     */
    @FXML
    private TextField pantalla;
    @FXML
    private Button bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt0, btsumar, btrestar, btdividir, btigual, btmulti, btborrar, btporcentaje, btpunto;

    /**
     * inicializamos el objeto calcular para vincular con el modelo
     */
    private final Calcular calculadora = new Calcular();

    /**
     * Variables  de tipo double y String
     */
    private double numero1;
    private double numero2;

   private String operacion;

   private boolean limpiar = false;

    /**
     * Combinación de teclas para el botón borrar: Ctrl + L
     */
    private final KeyCombination combinacionBorrar =
            new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN);

    /**
     * Metodo initialize para arrancar el controlador con FXML
     */
    @FXML
    public void initialize() {
        // array de los botones numéricos
        Button boton[] = {bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt0, btpunto};

        for (int i = 0; i < boton.length; i++) {
            botones(boton[i]);
        }

        /**
         * Array de operadores guardando los botones operadores y los símbolos
         * (el orden de los símbolos debe coincidir con el de los botones)w
         */
        Button operadores[] = {btsumar, btrestar, btdividir, btmulti};
        String operador[] = {"+", "-", "/", "*"};

        for (int i = 0; i < operadores.length; i++) {
            String simbolo = operador[i];

            operadores[i].setOnAction(e -> {
                if (pantalla.getText().isEmpty()) return;
                try {
                    numero1 = Double.parseDouble(pantalla.getText());
                    operacion = simbolo;
                    pantalla.appendText(" " + simbolo + " ");
                    limpiar = true;
                } catch (NumberFormatException ex) {
                    // ya hay un operador en pantalla: se ignora la pulsación
                }
            });
        }

        /**
         * La acción permite crear el evento al pulsar el botón porcentaje
         */
        btporcentaje.setOnAction(e -> {
            if (!pantalla.getText().isEmpty()) {
                try {
                    double valorActual = Double.parseDouble(pantalla.getText());
                    double resultado = calculadora.porcentaje(valorActual);
                    pantalla.setText(String.valueOf(resultado));
                    limpiar = true;
                } catch (NumberFormatException ex) {
                    pantalla.setText("Error");
                }
            }
        });

        /**
         * La acción permite crear el evento al pulsar el botón borrar
         */
        btborrar.setOnAction(e -> {
            pantalla.clear();
            operacion = null;
            limpiar = false;
        });

        /**
         * La acción permite crear el evento al pulsar el botón igual
         */
        btigual.setOnAction(e -> {
            if (pantalla.getText().isEmpty() || operacion == null) return;
            try {
                numero2 = Double.parseDouble(pantalla.getText());
            } catch (NumberFormatException ex) {
                return;
            }
            double resultado = 0;
            switch (operacion) {
                case "+":
                    resultado = calculadora.sumar(numero1, numero2);
                    break;
                case "-":
                    resultado = calculadora.restar(numero1, numero2);
                    break;
                case "*":
                    resultado = calculadora.multiplicar(numero1, numero2);
                    break;
                case "/":
                    resultado = calculadora.dividir(numero1, numero2);
                    break;
            }

            pantalla.setText(String.valueOf(resultado));
            operacion = null;
            limpiar = true;
        });

        /**
         * La pantalla solo muestra resultados: no se escribe en ella directamente
         */
        pantalla.setEditable(false);
        pantalla.setFocusTraversable(false);

        /**
         * En initialize() la escena todavía no existe, así que esperamos
         * a que la pantalla se añada a una escena para registrar el teclado
         */
        pantalla.sceneProperty().addListener((obs, escenaVieja, escenaNueva) -> {
            if (escenaNueva != null) {
                configurarTeclado(escenaNueva);
            }
        });
    } // fin de initialize

    /**
     * Metodo que guarda la acción de los botones numéricos
     * @param boton indica el valor tipo botón que pasa
     */
    public void botones(Button boton) {
        boton.setOnAction(e -> {
            if (limpiar) {
                pantalla.clear();
                limpiar = false;
            }
            pantalla.appendText(boton.getText());
        });
    }

    /**
     * Registra los eventos de teclado en la escena.
     * Cada tecla "pulsa" el botón correspondiente con fire(),
     * así se reutiliza la misma lógica que con el ratón.
     * @param escena escena de la calculadora
     */
    private void configurarTeclado(Scene escena) {

        // KEY_PRESSED: teclas especiales (Enter y la combinación de borrar)
        escena.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (combinacionBorrar.match(e)) {
                btborrar.fire();
                e.consume();
            } else if (e.getCode() == KeyCode.ENTER) {
                btigual.fire();
                e.consume();
            }
        });

        // KEY_TYPED: caracteres escritos (números, operadores, punto...)
        // Se usa el carácter y no el código de tecla para que funcione
        // con cualquier distribución de teclado y con el teclado numérico
        escena.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if (e.isControlDown()) return; // las combinaciones con Ctrl van arriba

            Button boton = botonDeCaracter(e.getCharacter());
            if (boton != null) {
                boton.fire();
                e.consume();
            }
        });
    }

    /**
     * Devuelve el botón asociado a un carácter del teclado
     * @param c carácter pulsado
     * @return el botón correspondiente o null si la tecla no se usa
     */
    private Button botonDeCaracter(String c) {
        switch (c) {
            case "0": return bt0;
            case "1": return bt1;
            case "2": return bt2;
            case "3": return bt3;
            case "4": return bt4;
            case "5": return bt5;
            case "6": return bt6;
            case "7": return bt7;
            case "8": return bt8;
            case "9": return bt9;
            case ".":
            case ",": return btpunto;
            case "+": return btsumar;
            case "-": return btrestar;
            case "*": return btmulti;
            case "/": return btdividir;
            case "%": return btporcentaje;
            case "=": return btigual;
            default:  return null;
        }
    }
}