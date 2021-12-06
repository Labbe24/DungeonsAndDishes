package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.dungeonsanddishes.game.StartRoomLib.Oven;
import com.dungeonsanddishes.game.StartRoomLib.RedBull;

import Framework.IngredientRoomTilemap;
import Framework.RoomTilemap;

abstract class HealRoomImplementation extends IngredientRoomImplementation{
    protected RedBull redBull;
    protected Stage stage;
    public HealRoomImplementation(){
        redBull = new RedBull(900,500);
    }
    @Override
    public void setRoom(Stage stage) {
        this.stage=stage;
        if (!redBull.taken) {
            stage.addActor(redBull);
        }
    }

    @Override
    public void removeRoom(Stage stage) {
        redBull.remove();
    }

    @Override
    public void update(float dt, Character character) {

    }

    public RedBull getRedbull(){return redBull;}
}

public class HealRoom extends IngredientRoom {
    public HealRoom(){
        _room_impl = new HealRoomImplementation() {};
        map_layout = new IngredientRoomTilemap("rooms/start_room.tmx", _room_impl);
    }

    @Override
    public void update(float dt, Character character) {
        super.update(dt,character);
        RoomTilemap map =(RoomTilemap)map_layout;

        RedBull redbull = ((HealRoomImplementation)_room_impl).getRedbull();
        if(Gdx.input.isKeyPressed(Input.Keys.T)){
            character.takeDamage(1);
        }
        if (character.overlaps(redbull)){
            if (!redbull.taken) {
                redbull.taken = true;
                character.heal(2);
                redbull.addAction(Actions.sequence(
                        Actions.parallel(Actions.rotateBy(360, 0.2f),Actions.scaleBy(0.5f, 0.2f)),
                        redbull.completeAction
                ));
            }
        }
    }
}
