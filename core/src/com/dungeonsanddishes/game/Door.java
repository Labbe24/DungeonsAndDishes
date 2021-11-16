package com.dungeonsanddishes.game;

import Framework.BaseActor;

public class Door extends BaseActor {
    protected DoorDirections direction;
    public DoorDirections getDirection(){
        return direction;
    }
    public Door(float x, float y) {
        super(x, y);
    }
}
