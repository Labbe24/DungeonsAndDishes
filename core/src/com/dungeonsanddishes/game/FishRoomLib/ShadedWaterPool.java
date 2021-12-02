package com.dungeonsanddishes.game.FishRoomLib;

import com.badlogic.gdx.scenes.scene2d.Stage;

import Framework.BaseActor;

public class ShadedWaterPool extends BaseActor {
    public ShadedWaterPool(float x, float y, Stage s) {
        super(x, y, s);
        this.loadTexture("FishRoom/shaded_water_pool.png");
    }
}
