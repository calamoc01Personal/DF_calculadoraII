package org.calculadoraII;

import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Controlador de la calculadora que conecta el modelo con la vista.
 * Permite usar la calculadora con el ratón y con el teclado.
 *
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
     * Creamos el objeto de Calcular que es el modelo
     */
    private final Calcular calculadora = new Calcular();

    //variables  iniciadas
    private double numero1;
    private double numero2;
    /**
     * Instaciamos la variables de tipo String
     *
     */
    private String operacion;
    /**
     * Instaciamos la variables de tipo boolean
     *
     */
    private boolean limpiar = false;


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
            operadores[i].setOnAction(e -> operar(simbolo));
        }

        /**
         * La acción permite crear el evento al pulsar el botón porcentaje
         */
        btporcentaje.setOnAction(e -> porcentaje());

        /**
         * La acción permite crear el evento al pulsar el botón borrar
         */
        btborrar.setOnAction(e -> borrar());

        /**
         * La acción permite crear el evento al pulsar el botón igual
         */
        btigual.setOnAction(e -> igual());

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
     *
     * @param boton indica el valor tipo botón que pasa
     */
    public void botones(Button boton) {
        boton.setOnAction(e -> {
            // ejecutamos escribir para selecionar con el raton
            escribir(boton.getText());
        }

        );
    }

    /**
     * Metodo que escribe un número o seleciona con e raton la operacion o número
     * @param texto número o punto que se añade
     */
    private void escribir(String texto) {
        if (limpiar) {
            pantalla.clear();
            limpiar = false;
        }
        pantalla.appendText(texto);
    }

    /**
     *  Metodo operar guarda el primer número y la operación elegida
     *
     * @param simbolo símbolo de la operación (+, -, *, /)
     * @exception  NumberFormatException controla que sea formato numero  y no letra y si hay un operador
     */
    private void operar(String simbolo) {
        if (pantalla.getText().isEmpty()) return;
        try {
            numero1 = Double.parseDouble(pantalla.getText());
            operacion = simbolo;
            pantalla.appendText(" " + simbolo + " ");
            limpiar = false;
        } catch (NumberFormatException ex) {
            alertas("operador");
        }
    }

    /**
     * Metodo porcentaje calcula el porcentaje del número que hay en pantalla
     * @exception  NumberFormatException controla que sea formato numero  y no letra
     */
    private void porcentaje() {
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
    }

    /**
     * Metodo Borra la pantalla y la operación guardada
     */
    private void borrar() {
        pantalla.clear();
        operacion = null;
        limpiar = false;
    }

    /**
     * Metodo igual calcula el resultado de la operación y lo muestra en pantalla
     * @exception  NumberFormatException controla que sea formato numero  y no letra
     */
    private void igual() {
        //condicion de si esta vacio o la operacion es nulla
        if (pantalla.getText().isEmpty() || operacion == null){
            return;
        }
        String texto = pantalla.getText();
        int posicion = texto.indexOf(" " + operacion + " ");
        String numerosegundo = texto.substring(posicion+3);
        try {
            numero2 = Double.parseDouble(numerosegundo);
        } catch (NumberFormatException ex) {
          alertas("operadordos");
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
    }

    /**
     * Metodo configurarTeclado registra los eventos de teclado en la escena.
     * con la misma lógica que con el ratón.
     * @param escena escena de la calculadora
     */
    private void configurarTeclado(Scene escena) {

        // evento para detectar si se ha pulsado la combinación "CTRL + L" o se a pulsado enter

        escena.addEventFilter(KeyEvent.KEY_PRESSED, evento -> {
            if (evento.isControlDown() && evento.getCode() == KeyCode.L) {
                borrar();
                evento.consume();
            } else if (evento.getCode() == KeyCode.ENTER) {
                igual();
                evento.consume();
            }
        });
        /*
         *evento que permite escribir cualquier tecla, con la condicion de si es control lo pasa al evento de la convinaciones
         * y si se ha usado entonces no hace anda
         */
        escena.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if (e.isControlDown()) return; // las combinaciones con Ctrl van arriba

            if (teclaPulsada(e.getCharacter())) {
                e.consume();
            }
        });
    }

    /**
     * Metodo teclapulsada guarda la tecla pulsada y hace la siguiente acciones
     * en caso de que sea número muestra en pantalla si es operaciones
     * lo calcula y el igual da el resutlado final
     * @param botonpulsado carácter pulsado
     * @return nos muestra el teclado si se usa o no
     */
    private boolean teclaPulsada(String botonpulsado) {
        if (botonpulsado.isEmpty() || botonpulsado.equals("\r") || botonpulsado.equals("\n")
                || botonpulsado.equals("\b") || botonpulsado.equals("\t")) {
            return false;
        }


        if (!botonpulsado.matches("[0-9+\\-*/.,%=]*")) {
            System.out.println("Esta escribiendo letras");
            alertas("letra");
            return true;
        }

        // Segun que caso se escribe en pantalla
        switch (botonpulsado) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                escribir(botonpulsado);
                return true;
            case ".":
            case ",":
                escribir(".");
                return true;
            case "+":
            case "-":
            case "*":
            case "/":
                operar(botonpulsado);
                return true;
            case "%":
                porcentaje();
                return true;
            case "=":
                igual();
                return true;
            default:
                return false;
        }
    }
/**
 * Metodo alertas muestra las alerta según los casos de las acciones
 * @param  accion pasa la accion del caso  a comprobar
 * */
    private void alertas(String accion) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        switch (accion){
            case "operador":
                alert.setTitle("Operador");
                alert.setHeaderText(null);
                alert.setContentText("Ya se ha elegido un operador.");
                break;

            case "letra":
                alert.setTitle("Dato no válido");
                alert.setHeaderText(null);
                alert.setContentText("Estás escribiendo una letra y no un número.");
                break;
            case "operadordos":
                alert.setTitle("no hay operador2");
                alert.setHeaderText(null);
                alert.setContentText("Falta el segundo numero");
                break;
            default:
        }
        alert.showAndWait();

    }
}