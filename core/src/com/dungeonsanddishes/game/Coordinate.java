package com.dungeonsanddishes.game;

public class Coordinate {
    float X;
    float Y;

    public Coordinate(float x, float y) {
        X = x;
        Y = y;
    }

    public float getX() {
        return X;
    }

    public void setX(float x) {
        X = x;
    }

    public float getY() {
        return Y;
    }

    public void setY(float y) {
        Y = y;
    }

    public void setCoords(float x,float y) {
        Y = y;
        X = x;
    }
}
