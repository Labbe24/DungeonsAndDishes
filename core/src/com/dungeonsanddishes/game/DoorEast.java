package com.dungeonsanddishes.game;

public class DoorEast extends Door{
    public DoorEast(float x, float y) {
        super(x, y);
        direction=DoorDirections.EAST;
        this.loadTexture("rooms/tilesets/kitchen_tiles/door_right.png");
    }
}
