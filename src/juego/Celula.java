/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.awt.Color;
import java.util.Random;
import javax.swing.JButton;

/**
 *
 * @author gabriel
 */
public class Celula extends JButton {

    private boolean estado;

    public Celula() {
        super();
        this.estado = false;
        this.setBackground(Color.black);//Ponemos el color del botón negro
    }

    public Celula(boolean estado) {
        this.estado = estado;
    }

    public boolean isEstado() {
        return estado;
    }

    public void matarCelula() {
        this.estado = false;
        this.setBackground(Color.black);//Ponemos el color del botón negro
    }

    public void resucitarCelula() {
        this.estado = true;
        this.setBackground(Color.white);
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
