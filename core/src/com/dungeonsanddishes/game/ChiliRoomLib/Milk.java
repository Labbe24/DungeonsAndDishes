package com.dungeonsanddishes.game.ChiliRoomLib;

import Framework.BaseActor;

public class Milk extends BaseActor {
    public float milkTimer = 0;
    public boolean available = false;

    public Milk(float x, float y){
        super(x, y);
        this.loadTexture("ChiliRoom/milk.png");
    }
}
