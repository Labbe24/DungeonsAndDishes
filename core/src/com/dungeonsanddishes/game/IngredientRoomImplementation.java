package com.dungeonsanddishes.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class IngredientRoomImplementation {
    public abstract void update(float dt, Character character);
    public abstract void setRoom(Stage stage);
    public abstract void removeRoom(Stage stage);
}
