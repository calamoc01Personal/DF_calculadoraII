package org.calculadoraII;
/**
 * @Test prueba Calculadora
 *Comprobacion de prueba unitarias
 * */
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
*
**/

class CalcularTest {

    @Test
    void sumar() {
      Calcular calculadora = new Calcular();
      double numero1 = 10.0;
      double numero2 = 4.0;
      double resultadoEsperado= 14.00;

      // ejecuto el metodo para ver si me da el resultado esperado
        double resultado = calculadora.sumar(numero1,numero2);
      //compruebo si son iguales los resultado
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    void restar() {
        Calcular calculadora = new Calcular();
        double numero1 = 10.0;
        double numero2 = 4.0;
        double resultadoEsperado= 4.00;
        // ejecuto el metodo para ver si me da el resultado esperado
        double resultado = calculadora.restar(numero1,numero2);
        //compruebo si son iguales los resultado
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    void multiplicar() {
        Calcular calculadora = new Calcular();
        double numero1 = -10.0;
        double numero2 = 4.0;
        double resultadoEsperado= -14.00;
        // ejecuto el metodo para ver si me da el resultado esperado
        double resultado = calculadora.multiplicar(numero1,numero2);
        //compruebo si son iguales los resultado
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    void dividir() {
        Calcular calculadora = new Calcular();
        double numero1 = 10;
        double numero2 = 0;
        double resultadoEsperado= 0.00;
        // ejecuto el metodo para ver si me da el resultado esperado
        double resultado = calculadora.multiplicar(numero1,numero2);
        //compruebo si son iguales los resultado
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    void porcentaje() {
        Calcular calculadora = new Calcular();
        double numero1 = 0.0;
        double resultadoEsperado= -0.00;
        // ejecuto el metodo para ver si me da el resultado esperado
        double resultado = calculadora.porcentaje(numero1);
        //compruebo si son iguales los resultado
        assertEquals(resultadoEsperado, resultado);
    }
}