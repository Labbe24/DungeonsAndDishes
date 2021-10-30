package com.dungeonsanddishes.game;

public class DoorSouth extends Door{
    public DoorSouth(float x, float y) {
        super(x, y);
        direction=DoorDirections.SOUTH;
        this.loadTexture("rooms/tilesets/kitchen_tiles/door_bottom.png");
    }
}
