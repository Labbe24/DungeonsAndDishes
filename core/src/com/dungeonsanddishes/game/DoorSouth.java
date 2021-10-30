package com.dungeonsanddishes.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class DoorSouth extends Door{
    public DoorSouth(float x, float y, Stage s) {
        super(x, y, s);
        this.loadTexture("rooms/kitchen_tiles/door_bottom.png");
    }
}
