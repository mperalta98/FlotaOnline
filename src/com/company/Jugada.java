package com.company;

import java.io.Serializable;

public class Jugada implements Serializable {
    String Nom;
    int x, y, numJ;

    public void setNumJ(int numJ) {
        this.numJ = numJ;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
