package com.dungeonsanddishes.game;

public abstract class IngredientRoom extends DungeonRoom {
    @Override
    public void update(float dt, Character character) {
        _room_impl.update(dt,character);
    }
    protected IngredientRoomImplementation _room_impl;
}
