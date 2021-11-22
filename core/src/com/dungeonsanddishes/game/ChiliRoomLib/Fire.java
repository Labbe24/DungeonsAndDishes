package com.dungeonsanddishes.game.ChiliRoomLib;

import Framework.BaseActor;

public class Fire extends BaseActor {

    public Fire(float x, float y) {
        super(x, y);
        this.loadTexture("ChiliRoom/fire.png");
    }

    public void updateFirePosition(int x, int y) {
        this.setX(x);
        this.setY(y);
    }
}
