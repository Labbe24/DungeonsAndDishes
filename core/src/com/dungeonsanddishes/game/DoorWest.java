package com.dungeonsanddishes.game;

public class DoorWest extends Door{
    public DoorWest(float x, float y) {
        super(x, y);
        direction=DoorDirections.WEST;
        this.loadTexture("rooms/tilesets/kitchen_tiles/door_left.png");
    }
}
