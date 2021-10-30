package com.dungeonsanddishes.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class DoorEast extends Door{
    public DoorEast(float x, float y, Stage s) {
        super(x, y, s);
        this.loadTexture("rooms/kitchen_tiles/door_right.png");
    }
}
