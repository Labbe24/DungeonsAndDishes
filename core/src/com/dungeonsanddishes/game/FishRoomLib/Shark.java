package com.dungeonsanddishes.game.FishRoomLib;

import com.badlogic.gdx.scenes.scene2d.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

import Framework.BaseActor;

public class Shark extends BaseActor {
    public Shark(float x, float y, Stage s) {
        super(x, y, s);
        loadAnimationFromSheet("FishRoom/shark_jump_up_anim.png", 1, 10, 0.05f, false);
        Logger.getGlobal().log(Level.WARNING, "Shark ctor called");
    }

    public void act(float dt) {
        super.act(dt);
        if(isAnimationFinished()){
            remove();
        }
    }
}
