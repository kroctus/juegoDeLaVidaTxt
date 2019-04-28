/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.util.Random;


/**
 *
 * @author gabriel
 */
public class Celula {

    private boolean estado;
    
    

    public Celula() {
        super();
        this.estado = false;
    }

    /*GETTERS AND SETTERS*/

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
 
    public Celula(boolean estado) {
        this.estado = estado;
    }

    public boolean isEstado() {
        return estado;
    }

    //Método que simula la muerte de la célula pasando su estado a false
    public void matarCelula() {
       setEstado(false);
    }
//método que simula la resurección de la célula pasando su estado a true
    public void resucitarCelula() {
        setEstado(true);
    }


    //Crea una celula viva o muerta dependiendo del valor aleatorio que se genere y la devuelve para agregarla a una generación.
    // 1 = viva.
    // 2= muerta.
    public static Celula generarCelula(int numero) {
        switch (numero) {

            case 1:
                return new Celula(true);
            case 2:
                return new Celula(true);

        }
        return new Celula();
    }

    @Override
    public String toString() {
        return "(" + estado + ')';
    }

    
    
}
