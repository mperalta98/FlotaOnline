package com.company;

import java.io.Serializable;

public class Jugada implements Serializable {
    int x, y, numJ;

    public void setNumJ(int numJ) {
        this.numJ = numJ;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
