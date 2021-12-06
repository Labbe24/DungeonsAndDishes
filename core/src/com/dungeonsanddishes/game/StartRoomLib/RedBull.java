package com.dungeonsanddishes.game.StartRoomLib;

import com.badlogic.gdx.scenes.scene2d.Action;

import Framework.BaseActor;

public class RedBull extends BaseActor {
    public boolean taken = false;

    public RedBull(float x, float y) {
        super(x, y);
        this.loadTexture("items/redbull/redbull.png");
    }

    public Action completeAction = new Action(){
        public boolean act( float delta ) {
            remove();
            return true;
        }
    };
}
