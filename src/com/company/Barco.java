package com.company;

import java.io.Serializable;
import java.util.Scanner;

public class Barco implements Serializable {

    int numBarcosJ1 = 0;
    int numBarcosJ2 = 0;
//    int tamano = 1;
    Tablero tablero1;
//    Tablero tablero2;
    Scanner sc = new Scanner(System.in);
    boolean derrota = true;

//TODO: CONTROL DE ERRORES (ARREGLAR FALLO ARRAYINDEXOUTOFBOUNDS AL AÑADIR POSICION DEL BARCO Y AL DISPARAR.)


    void imprimeBarco(Tablero tableroJ1) {

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

//        tablero2 = tableroJ2;
//
//        while (numBarcosJ2 < 3) {
//
//            "Añade posición del barco (x, y): (J2)"
//            int posX = (int) (Math.random() * 5);
//            int posY = (int) (Math.random() * 5);
//
//            if (posX < 5 && posX >= 1 && posY < 5 && posY >= 1) {
//                if(tableroJ2.tablero[posX][posY] != 'b') {
//                    tablero2.añadirBarcoJ2(posX, posY);
//
//                    numBarcosJ2++;
//                }
//            }
//        }
    }

    void disparar(Tablero tableroJ1, int x, int y){

        tablero1 = tableroJ1;

//        while (numBarcosJ1 <= 3 && numBarcosJ1 > 0 && derrota) {
            if (x < 5 && x >= 1 || y < 5 && y >= 1) {
                if(tableroJ1.tablero[x][y] == 'b') {
                    tableroJ1.tablero[x][y] = 'x';
                    numBarcosJ1--;
                    System.out.println("Has hundido un barco.");

                    if (numBarcosJ1 == 0){
                        derrota = false;
                        System.out.println("Victoria!");
                    }
                }else{
                    tablero1.actualizaTablero(x, y);
                    System.out.println("Has fallado.");
                }
            } else {
                System.out.println("Valor incorrecto.");
            }
            System.out.println(numBarcosJ1);
//        }

//        tablero2 = tableroJ2;
//
//        while (numBarcosJ2 <= 3 && numBarcosJ2 > 0 && derrota) {
//            System.out.println("Indica donde vas a disparar (x, y): ");
//            int posX = sc.nextInt();
//            int posY = sc.nextInt();
//
//
//            if (posX < 5 && posX >= 1 || posY < 5 && posY >= 1) {
//                if(tableroJ2.tablero[posX][posY] == 'b') {
//                    tableroJ2.tablero[posX][posY] = 'x';
//                    numBarcosJ2--;
//                    System.out.println("Has hundido un barco.");
//
//                    if (numBarcosJ2 == 0){
//                        derrota = false;
//                        System.out.println("Victoria!");
//                    }
//                }else{
//                    tablero2.actualizaTablero(posX, posY);
//                    System.out.println("Has fallado.");
//                }
//            } else {
//                System.out.println("Valor incorrecto.");
//            }
//        }

    }
}

