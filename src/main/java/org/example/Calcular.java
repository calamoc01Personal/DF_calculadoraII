package org.example;

/**
 * Clase con el modelo de la calculadora
 * @author Celia Álamo Calle
 * @version 1.0
 */

public class Calcular {
    /**
     * Atributos
     * */
    double numero1;
    double numero2;

    /**
    *  constructores  con parametros y sin parametros
    * */
    public Calcular(Double numero1, Double numero2){
        this.numero1 =numero1;
        this.numero2 = numero2;
    }
    public Calcular(){};

    /**@param numero1 suma el numero 1 al numero 2
     * @param numero2 suma el numero 2 al numero 1
     * @return devuelve el valor  de la suma
     * */
    public  double sumar(Double numero1, Double numero2){
        return numero1 + numero2;
    }

    /** @param numero1 resta el numero 1 al numero 2
     * @param numero2 resta el numero 2 al numero 1
     * @return devuelve el valor  de la suma
     * */

    public  double restar(double numero1, double numero2){
        return numero1 - numero2;
    }
    /** @param numero1 multiplica el numero 1 al numero 2
     * @param numero2 multiplica  el numero 2 al numero 1
     * @return devuelve el valor  de la suma
     * */
    public  double multiplicar(double numero1, double numero2){
        return numero1 * numero2;
    }

    /**@param numero1 dividendo de la division
     * @param numero2 divisor de la division
     * @return  resutalado de la division
     * @throws   IllegalArgumentException una excepcion de si divide la operacion es cero
     * */
    public  double dividir(double numero1, double numero2){
        try {

            if (numero2 == 0) {
                System.out.println("No se puede dividir por cero.");
            }
            return numero1 / numero2;

        } catch (IllegalArgumentException e) {

            System.out.println("Error: " + e.getMessage());
            return 0;
        }
    }
    /**
     * @param numero2  valor de número de porcentaje
     * @return resultado del valor número 1 divido entre 100
     * @throws  IllegalArgumentException una excepcion de si divide la operacion en cero
     * */
    public double porcentaje(double numero2){
       try{

           return numero2/100;
       }catch(IllegalArgumentException e){
           System.out.println("Error: " + e.getMessage());
           return 0;
       }
    }


}
