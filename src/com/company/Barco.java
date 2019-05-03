package com.company;

import java.io.Serializable;

public class Barco implements Serializable {

    int numBarcosJ1 = 0;
    int numBarcosJ2 = 0;
    Tablero tablero1;
    Tablero tablero2;
    boolean derrota = true;

    void imprimeBarco(Tablero tableroJ1, Tablero tableroJ2) {

        tablero1 = tableroJ1;

        while (numBarcosJ1 < 3) {

//           "Añade posición del barco (x, y)"
            int posX = (int) (Math.random() * 5);
            int posY = (int) (Math.random() * 5);

            if (posX < 5 && posX >= 1 && posY < 5 && posY >= 1) {
                if(tableroJ1.tablero[posX][posY] != 'b') {
                    tablero1.añadirBarcoJ1(posX, posY);

                    numBarcosJ1++;
                }
            }
        }

        tablero2 = tableroJ2;

        while (numBarcosJ2 < 3) {

//            "Añade posición del barco (x, y): (J2)"
            int posX = (int) (Math.random() * 5);
            int posY = (int) (Math.random() * 5);

            if (posX < 5 && posX >= 1 && posY < 5 && posY >= 1) {
                if(tableroJ2.tablero[posX][posY] != 'b') {
                    tablero2.añadirBarcoJ2(posX, posY);

                    numBarcosJ2++;
                }
            }
        }
    }

    void disparar(Tablero tableroJ1, int x, int y){

    tablero1 = tableroJ1;

        if (x < 5 && x >= 1 || y < 5 && y >= 1) {
            if(tableroJ1.tablero[x][y] == 'b') {
                tableroJ1.tablero[x][y] = 'x';
                numBarcosJ1--;
                tablero1.estadoPartida(derrota);
                System.out.println("Has hundido un barco.");

                if (numBarcosJ1 == 0){
                    derrota = false;
                    System.out.println("Victoria!");
                    tablero1.estadoPartida(derrota);
                }
            }else{
                tablero1.actualizaTablero(x, y);
                System.out.println("Has fallado.");
                tablero1.estadoPartida(derrota);
            }
        } else {
            System.out.println("Valor incorrecto.");
        }
        System.out.println(numBarcosJ1);
    }
}

