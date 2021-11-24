package com.dungeonsanddishes.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

class BossRoomImplementation extends IngredientRoomImplementation{
    protected Boss boss;

    public BossRoomImplementation(){
        boss = new SushiBoss(800,600);
    }
    @Override
    public void setRoom(Stage stage) {
        //Add range in middle of room
        stage.addActor(boss);

    }

    @Override
    public void removeRoom(Stage stage) {

    }


    @Override
    public void update(float dt, Character character) {
        //First half
        //if player interacts with range
        //check for ingredients, if ingredients present, spawn according boss.
        boss = new SushiBoss(800,600);

        //second half:
        //boss fight.

    }
}

public class BossRoom extends IngredientRoom{

    public BossRoom(){
        _room_impl = new BossRoomImplementation();
    }

    @Override
    public void update(float dt, Character character) {


    }
}
