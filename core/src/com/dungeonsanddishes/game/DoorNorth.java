package com.dungeonsanddishes.game;

public class DoorNorth extends Door{
    public DoorNorth(float x, float y) {
        super(x, y);
        direction=DoorDirections.NORTH;
        this.loadTexture("rooms/tilesets/kitchen_tiles/door_top.png");
    }
}
