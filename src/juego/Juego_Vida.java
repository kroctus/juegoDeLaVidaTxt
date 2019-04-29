/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.util.ArrayList;
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
    private ArrayList<Generacion> generaciones = new ArrayList<>();// lista para guardar las generaciones que se van creando cada vez que el usuario crea la siguiente generación
//    private int opcion; // indica si la generación se ha creado con los valores del usuario(1) o aleatoriamente(2)
    private Integer contador = 1;
    private Integer contadorGenAnterior = contador;

    /*Método principal que se encarga de realizar las llamadas a los diferentes métodos del juego*/
    public void EjecutarJuego() {
        Scanner teclado = new Scanner(System.in);

        menuInicio();
        imprimirGeneracion1();
        String salir = "";// gauarda la opción del usuario dentro del bucle
        do {
            generacionSiguiente();
            System.out.println("¿Quiéres seguir con la ejecución? " + "si " + "/" + " no");
            salir = teclado.nextLine();
        } while (compararGeneraciones() == false || salir.equalsIgnoreCase("si"));

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

    //método que compara las diferentes generaciones que están dentro de la lista de generaciones y devuelve true en caso de que dos generaciones sean iguales
    //en caso de que no sean iguales devuelve falso
    // este valor será la condición de parada del bucle en ejecutarJuego()
    public boolean compararGeneraciones() {
        for (Generacion generacione : generaciones) {
            Generacion tmp;
            tmp = generacione;
            if (generacione.equals(tmp) == true) {
                return true;
            }
        }
        return false;
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
        Celula[][] aux = new Celula[uno.getMatrizCelula().length][uno.getMatrizCelula().length];

        this.copiarMatriz(aux, uno.getMatrizCelula()); // copiamos el estado de la matriz uno a la matriz aux para realizar los cambios sobre esta (aux)
        // posteriormente devolveremos estos cambios a la matriz de la generación uno
//        this.tres= new Generacion(this.uno.getMatrizCelula().length); // instanciamos la generación 3 con el mismo tamaño que la generación 1 para realizar una copia a continuación
        dos = uno;// copiamos el estado de la matriz uno a la matriz de la geenración tres 
        // el objetivo de realizar esta acción es guardar el estado orginal de la generación uno para luego mostrarla por pantalla

        for (int i = 0; i < uno.getMatrizCelula().length; i++) {
            for (int j = 0; j < uno.getMatrizCelula().length; j++) {

                /*Recorremos la matriz y viendo el estado de las posiciones que se encuentran alrededor de i,j que es la celula que estamos analizando
                En caso de que esten vivas se suma un valor al contador de celulas vivas, por otro lado si estan muertas se suma el valor al contadorMuestas*/
                try {

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

                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }

        }
        copiarMatriz(aux, uno.getMatrizCelula());
        generaciones.add(new Generacion(uno));
    }
    //método que copia el valor de dos matrices que se le pasen por parametro
    //los valores de la primera en la segunda
    // usamos este método para copiar los valores de la matriz de la generación uno con los de aux en el método analizarGeneración()
    //equivale a un set

    public void copiarMatriz(Celula[][] matriz1, Celula[][] matriz2) {
        for (int i = 0; i < matriz1.length; i++) {
            for (int j = 0; j < matriz1.length; j++) {
                matriz1[i][j] = matriz2[i][j];
            }
        }
    }

    // método que gestiona los estados de la celula según lo desee el usuario una vez que se genere la matriz de celulas con la opcion de generación por valores
    public void cambiarCelulas() {
        Scanner teclado = new Scanner(System.in);
        int parada;

        do {
            System.out.println("¿Qué quieres hacer?");
            System.out.println("1) Matar célula");
            System.out.println("2) Revivir célula");
            int opcion = teclado.nextInt();

            switch (opcion) {
                case 1:
                    matarCelula();
                    break;
                case 2:
                    resucitarCelula();
                    break;

            }

            imprimirGeneracion1();

            System.out.println("¿Realizar otro cambio?");
            System.out.println("1) si");
            System.out.println("2) no");
            parada = teclado.nextInt();// guarda la opción del usuario para realizar o no otro cambio
            /*En  caso de que la selección sea si se vuelve a ejecutar el método si es no se sale de este*/

        } while (parada != 2);

    }

    //método que mata una celula que quiera el usuario
    public void matarCelula() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Introduce la fila en la que se encuentra la célula:");
        int fila = teclado.nextInt();
        System.out.println("Introduce la columna: ");
        int columna = teclado.nextInt();

        Celula[][] aux = uno.getMatrizCelula();
        aux[fila][columna].matarCelula();
    }

    //Método que resucita una celula que seleccione el usuario
    public void resucitarCelula() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Introduce la fila en la que se encuentra la célula:");
        int fila = teclado.nextInt();
        System.out.println("Introduce la columna: ");
        int columna = teclado.nextInt();

        Celula[][] aux = uno.getMatrizCelula();
        aux[fila][columna].resucitarCelula();
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
        imprimirGeneracion1(); // imprimimos la generación

        System.out.println("¿Quieres gestionar las células?");
        System.out.println("1) si");
        System.out.println("2) no");
        int opcion = teclado.nextInt(); // guarda la seleccion del usuario si gestionar celulas o no  si la opcion es no se sale del método

        switch (opcion) {
            case 1:
                cambiarCelulas(); // si la opcion es que si llamamos al método encargado de cambiar las celulas
                break;
            case 2:
                return; // si es no salimos del método
        }
    }

    public void generacionAleatoria() {

//        opcion = 2;
        int numero = Generacion.tamañoAleatorioMatriz();
        if (numero <= 25) {
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

                if (aux[x][y].isEstado() == true) {
                    System.out.printf(" O ");
                } else if (aux[x][y].isEstado() == false) {
                    System.out.printf(" X ");
                }

            }
            System.out.println();

        }

    }

    //Método que imprime la segunda matriz de celulas
    public void imprimirGeneracion2() {

        Celula[][] aux = dos.copiarMatriz();

        for (int x = 0; x < dos.getMatrizCelula().length; x++) {
            for (int y = 0; y < aux[x].length; y++) {

                if (aux[x][y].isEstado() == true) {
                    System.out.printf(" O ");
                } else if (aux[x][y].isEstado() == false) {
                    System.out.printf(" X ");
                }

            }
            System.out.println();

        }

    }

    // método que se encarga de gestionar los cambios que sufren las generaciones  al seleccionar la opcion de "siguiente generación"
    public void gestionGeneracion() {
        System.out.println("Generación anterior: " + (this.contadorGenAnterior = this.contador - 1));
        System.out.println("");
        imprimirGeneracion1();// imprimos la generación sin cambios
        dos = uno;// pasamos el estado de la generacion 1 a la generación 2 para luego mostrarla por pantallay asi poder ver los cambios
        analizarGeneracion(); // aplicamos la logica del programa sobre la generación 1 y así ver los cambios en la matriz
        System.out.println("Generación Actual: " + this.contador);
        System.out.println("");
        imprimirGeneracion1(); //imprimimos tras los cambios
    }

    //método que hace la función de el boton siguiente generacion
    //realiza las llamadas a los métodos pertinenetes para realizar los cambios en la generación según la lógica del juego
    public void generacionSiguiente() {
        this.contador++;
        Scanner teclado = new Scanner(System.in);
        System.out.println("¿Quieres generar la siguiente generación?");
        System.out.println(" \n S) Si");
        System.out.println(" N) No");

        String opcion = teclado.nextLine(); // guarda la opción seleccioanda por el usuario

        if (opcion.equalsIgnoreCase("S")) {
            gestionGeneracion();
        } else {
            return;
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

    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }

    // método main del juego
    public static void main(String[] args) {

        Juego_Vida juego = new Juego_Vida();

        juego.EjecutarJuego();

    }

}
