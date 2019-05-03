package com.company;

import java.io.Serializable;
import java.lang.reflect.Array;

public class Tablero implements Serializable {
    int dimension;
    char caracterSinDestapar;
    char caracterDestapado;
    char tablero[][];
    int numJugadores;

    Tablero(int dimension, char caracterSinDestapar, char caracterDestapado) {
        this.dimension = dimension;
        this.caracterSinDestapar = caracterSinDestapar;
        this.caracterDestapado = caracterDestapado;

        tablero = new char[dimension][dimension];

        for (int i = 1; i < dimension; i++)
            for (int j = 1; j < dimension; j++)
                tablero[i][j] = caracterSinDestapar;
        for (int i = 1; i < dimension; i++)
            tablero[i][0] = String.valueOf(i).charAt(0); //TODO: REVISAR, TAL VEZ CAMBIAR ESTO HA HECHO QUE EL BUCLE NO TERMINE AL MATAR TODOS LOS BARCOS.
        for (int j = 1; j < dimension; j++)
            tablero[0][j] = String.valueOf(j).charAt(0);

    }

    void imprimeTablero() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                System.out.print(tablero[i][j] + "\t");
            }
            System.out.println("\n");
        }
    }

    void actualizaTablero(int x, int y) {
        tablero[x][y] = caracterDestapado;
    }

    void añadirBarcoJ1( int x, int y){
        tablero[x][y] = 'b';
    }

    void añadirBarcoJ2( int x, int y){
        tablero[x][y] = 'b';
    }


    public static void main(String[] args) {

        Barco barco = new Barco();

        Tablero tableroJ1 = new Tablero(5, '-', 'O');
        Tablero tableroJ2 = new Tablero(5, '-', 'x');

//        barco.imprimeBarco(tableroJ1);


        tableroJ1.imprimeTablero();
//        tableroJ2.imprimeTablero();


//        barco.disparar(tableroJ1, tableroJ2);
//        tableroJ1.imprimeTablero(); //Imprimir tablero cada vez que se dispara.

//        tableroJ1.actualizaTablero(1, 2);
//        tableroJ2.actualizaTablero(0, 0);

        tableroJ1.imprimeTablero();
//        tableroJ2.imprimeTablero();
    }
}