/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author kroctus
 */
public class Juego_Vida {

    private int numeroCelulas; //Representa el número de celulas en x e y
    private Generacion uno;
    private Generacion dos;
    private Generacion tres;
//    private int opcion; // indica si la generación se ha creado con los valores del usuario(1) o aleatoriamente(2)
    private Integer contador = 0;
    private final Integer contadorGenAnterior = contador;

    /*Método principal que se encarga de realizar las llamadas a los diferentes métodos del juego*/
    public void EjecutarJuego() {
        menuInicio();
        imprimirGeneracion1();
        generacionSiguiente();

    }

    //método que se encarga de dar la bienvenida al juego al usuario y que además sirve para seleccionar las primeras opciones de creación de la matriz de celulas
    public void menuInicio() {
        Scanner teclado = new Scanner(System.in);
        JOptionPane.showMessageDialog(null, "Bienvenido al juego de la vida :D");
        System.out.println("¿Cómo quieres generar tus ganeraciones?");
        System.out.println("\n 1) por valor");
        System.out.println("\n 2) Aleatoriamente");
        int numero = teclado.nextInt();

        switch (numero) {
            case 1:
                generacionPorValor();
                break;
            case 2:
                generacionAleatoria();
                break;
        }

    }

    /*ANALIZAR_GENERACION*/
 /*Método que aplica la lógica del juego revisando la matriz de celulas y cambiando el estado de la celula analizada en base a la cantidad de celulas vivas o muertas
    que se encuentren a su alrededor*/
 /*POSICIONES*/
    //  aux[i][j].isEstado() == true // ij célula analizada
    //  aux[i][j-1].isEstado() == true // IZQUIERDA dela célula analizada 
    //  aux[i][j+2].isEstado() == true //DERECHA dela célula analizada 
    //  aux[i-1][j].isEstado() == true // ARRIBA dela célula analizada 
    //  aux[i+1][j].isEstado() == true // ABAJO dela célula analizada 
    //  aux[i-1][j-1].isEstado() == true // ESQUINA SUPERIOR IZQUIERDA 
    //  aux[i-1][j+1].isEstado() == true // ESQUINA SUPERIOR DERECHA
    //  aux[i+1][j-1].isEstado() == true // ESQUINA INFERIOR IZQUIERDA
    // aux[i+1][j+1].isEstado() == true // ESQUINA INFERIOR DERECHA
    public void analizarGeneracion() {
        int contadorVivas = 0; // cuenta el número de celulas vivas alrededor de una celula en concreto
        int contadorMuertas = 0; // cuenta el número de celulas muertas alrededor de una celula en concreto
        Celula[][] aux = uno.getMatrizCelula();

        try {

            for (int i = 0; i < uno.getMatrizCelula().length; i++) {
                for (int j = 0; j < uno.getMatrizCelula().length; j++) {

                    /*Recorremos la matriz y viendo el estado de las posiciones que se encuentran alrededor de i,j que es la celula que estamos analizando
                En caso de que esten vivas se suma un valor al contador de celulas vivas, por otro lado si estan muertas se suma el valor al contadorMuestas*/
 /*CONTADORES*/
                    //izquierda esta viva
                    if (aux[i][j - 1].isEstado() == true) {
                        contadorVivas++;
                        //Izquierda muerta
                    } else if (aux[i][j - 1].isEstado() == false) {
                        contadorMuertas++;
                    }
                    // derecha esta viva
                    if (aux[i][j + 2].isEstado() == true) {
                        contadorVivas++;
                        //derecha mmuerta
                    } else if (aux[i][j + 2].isEstado() == false) {
                        contadorMuertas++;
                    }
                    // si arriba esta viva
                    if (aux[i - 1][j].isEstado() == true) {
                        contadorVivas++;
                        //arriba muerta
                    } else if (aux[i - 1][j].isEstado() == false) {
                        contadorMuertas++;
                    }
                    // si abajo esta viva
                    if (aux[i + 1][j].isEstado() == true) {
                        contadorVivas++;
                        //abajo esta muerta
                    } else if (aux[i + 1][j].isEstado() == false) {
                        contadorMuertas++;
                    }

                    // si la esquina superior izquierda esta viva
                    if (aux[i - 1][j - 1].isEstado() == true) {
                        contadorVivas++;
                        // muerta
                    } else if (aux[i - 1][j - 1].isEstado() == false) {
                        contadorMuertas++;
                    }
                    //Si la esquina superior derecha esta viva
                    if (aux[i - 1][j + 1].isEstado() == true) {
                        contadorVivas++;
                    }
                    // si la esquina inferior izquierda esta viva
                    if (aux[i + 1][j - 1].isEstado() == true) {
                        contadorVivas++;
                        //muerta
                    } else if (aux[i + 1][j - 1].isEstado() == false) {
                        contadorMuertas++;
                    }
                    // si la esquina inferior derecha esta viva
                    if (aux[i + 1][j + 1].isEstado() == true) {
                        contadorVivas++;
                        //muerta
                    } else if (aux[i + 1][j + 1].isEstado() == false) {
                        contadorMuertas++;
                    }

                    System.out.println("Contador vivas: " + contadorVivas);
                    System.out.println("Contador muertas: " + contadorMuertas);
                    /*CONDICIONES*/
 /*si el contador de celulas vivas es mayor o igual que 3 y la celula esta viva, entonces la celula muere por sobrepoblación pero si esta esta muerta resucita*/
                    if (contadorVivas >= 3 && aux[i][j].isEstado() == true) {
                        aux[i][j].matarCelula();
                    } else if (contadorVivas >= 3 && aux[i][j].isEstado() == false) {
                        aux[i][j].resucitarCelula();
                    }
                    /*si el contador de celulas vivas es igual que 3 y la celula esta muerta entonces la celula vuelve a vivir (estado =true) */
                    if (contadorVivas == 3 && aux[i][j].isEstado() == false) {
                        aux[i][j].resucitarCelula();
                    }

                    /*si la celula esta viva y tiene 1 o 0 celulas vivas a su alrededor muere por soledad*/
                    if (contadorVivas <= 1 && aux[i][j].isEstado() == true) {
                        aux[i][j].matarCelula();
                    }

                    /*Una célula viva con 2 ó 3 células vecinas vivas sigue viva.*/
                    if (contadorVivas == 2 || contadorVivas == 3 && aux[i][j].isEstado() == true) {
                        aux[i][j].resucitarCelula();
                    }

                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("");
        }

        uno.setMatrizCelula(aux); // aplicamos los cambios realizados sobre la matriz auxiliar y en la generación 1
    }

    // método que genera la matriz de celula en base al número entero que introduce el usuario por teclado
    // el máximo valor que puede tomar este número es 25 y el minimo 7
    public void generacionPorValor() {
        Scanner teclado = new Scanner(System.in);
        System.out.println(" \n Introduce el número entero para generar la matriz cuadrada: ");
        int numero = teclado.nextInt();
        if (numero > 25 || numero < 5) {
            System.out.println("El número es incorrecto");
            System.out.println("el rango es de 5-25");
            generacionPorValor();
        } else {
//            opcion = 1;

            // Creo la generación
            numeroCelulas = numero;
            uno = new Generacion(numeroCelulas);
            for (int i = 0; i < numeroCelulas; i++) {
                for (int j = 0; j < numeroCelulas; j++) {
                    Celula tmp = new Celula();
                    uno.ponerCelula(i, j, tmp);
//                    copiarGeneracion();
                }
            }
        }

    }

    public void generacionAleatoria() {

//        opcion = 2;
        int numero = Generacion.tamañoAleatorioMatriz();
        if (numero <= 70) {
            System.out.println("valor de numero aleatorio: " + numero);
            // Creo la generación
            uno = new Generacion(numero);
            for (int i = 0; i < numero; i++) {
                for (int j = 0; j < numero; j++) {
                    int estado = Generacion.generarEstado(); //hago una llamda al método generarEstado() para establecer si la celula estará viva o muerta
                    Celula tmp = Celula.generarCelula(estado);
                    uno.ponerCelula(i, j, tmp);

                }
            }
        }
    }

    //Método que imprime la primera matriz de celulas
    public void imprimirGeneracion1() {
        Celula[][] aux = uno.copiarMatriz();

        for (int x = 0; x < uno.getMatrizCelula().length; x++) {
            for (int y = 0; y < aux[x].length; y++) {

                System.out.print(" | ");
                System.out.print(aux[x][y]);
                System.out.print(" | ");

            }
            System.out.println();

        }

    }

    //Método que imprime la segunda matriz de celulas
    public void imprimirGeneracion2() {

        Celula[][] aux = dos.copiarMatriz();

        for (int x = 0; x < dos.getMatrizCelula().length; x++) {
            for (int y = 0; y < aux[x].length; y++) {

                System.out.print(" | ");
                System.out.print(aux[x][y]);
                System.out.print(" | ");

            }
            System.out.println();

        }

    }

    // método que se encarga de gestionar los cambios que sufren las generaciones  al seleccionar la opcion de "siguiente generación"
    public void gestionGeneracion() {
        dos = uno;// pasamos el estado de la generacion 1 a la generación 2 para luego mostrarla por pantallay asi poder ver los cambios
        analizarGeneracion(); // aplicamos la logica del programa sobre la generación 1 y así ver los cambios en la matriz

    }

    public void generacionSiguiente() {
        this.contador++;
        Scanner teclado = new Scanner(System.in);
        System.out.println("¿Quieres generar la siguiente generación?");
        System.out.println(" \n S) Si");
        System.out.println(" N) No");

        String opcion = teclado.nextLine(); // guarda la opción seleccioanda por el usuario

        if (opcion.equalsIgnoreCase("S")) {
            gestionGeneracion();
            System.out.println("\n Generación actual: " + this.contador);
            imprimirGeneracion1();
            System.out.println(" \n Generación anterior" + this.contadorGenAnterior);
            imprimirGeneracion2();
        } else {
            JOptionPane.showMessageDialog(null, "El programa ha finalizado");
        }

    }


    /*GETTERS AND SETTERS*/
    public int getNumeroCelulas() {
        return numeroCelulas;
    }

    public void setNumeroCelulas(int numeroCelulas) {
        this.numeroCelulas = numeroCelulas;
    }

    public Generacion getUno() {
        return uno;
    }

    public void setUno(Generacion uno) {
        this.uno = uno;
    }

    public Generacion getDos() {
        return dos;
    }

    public void setDos(Generacion dos) {
        this.dos = dos;
    }

    public Generacion getTres() {
        return tres;
    }

    public void setTres(Generacion tres) {
        this.tres = tres;
    }

//    public int getOpcion() {
//        return opcion;
//    }
//
//    public void setOpcion(int opcion) {
//        this.opcion = opcion;
//    }
    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }

}
