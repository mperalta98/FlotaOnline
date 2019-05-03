package com.company;

import java.io.Serializable;

public class Tablero implements Serializable {
    int dimension;
    char caracterSinDestapar;
    char caracterDestapado;
    char tablero[][];
    boolean estado = true;
    boolean turnoValido;

    Tablero(int dimension, char caracterSinDestapar, char caracterDestapado) {
        this.dimension = dimension;
        this.caracterSinDestapar = caracterSinDestapar;
        this.caracterDestapado = caracterDestapado;

        tablero = new char[dimension][dimension];

        for (int i = 1; i < dimension; i++)
            for (int j = 1; j < dimension; j++)
                tablero[i][j] = caracterSinDestapar;
        for (int i = 1; i < dimension; i++)
            tablero[i][0] = String.valueOf(i).charAt(0);
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
    //

    public void estadoPartida(boolean estado) {
       this.estado = estado;
    }
    public boolean estadoJuego() {
        return estado;
    }
}