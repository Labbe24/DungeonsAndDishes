package com.dungeonsanddishes.game.ChiliRoomLib;

import Framework.BaseActor;

public class Chili extends BaseActor {
    public boolean eaten = false;

    public Chili(float x, float y) {
        super(x, y);
        this.loadTexture("ChiliRoom/chili.png");
    }
}
