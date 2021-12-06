package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dungeonsanddishes.game.StartRoomLib.Oven;
import Framework.IngredientRoomTilemap;
import Framework.RoomTilemap;

abstract class KitchenRoomImplementation extends IngredientRoomImplementation{
    protected Oven oven;
    protected Stage stage;
    public KitchenRoomImplementation(){
        oven = new Oven(800,600);
    }
    @Override
    public void setRoom(Stage stage) {
        this.stage=stage;
        stage.addActor(oven);
    }

    @Override
    public void removeRoom(Stage stage) {
        oven.remove();
    }

    @Override
    public void update(float dt, Character character) {

    }

    public Oven getOven(){return oven;}
}

public class KitchenRoom extends IngredientRoom {
    public KitchenRoom(){
        _room_impl = new KitchenRoomImplementation() {};
        map_layout = new IngredientRoomTilemap("rooms/start_room.tmx", _room_impl);
    }

    @Override
    public void update(float dt, Character character) {
        super.update(dt,character);
        RoomTilemap map =(RoomTilemap)map_layout;
        //check for ingredients, if ingredients present, turn on oven

        Oven oven = ((KitchenRoomImplementation)_room_impl).getOven();

        character.preventOverlap(oven);

        if (character.isWithinDistance(30, oven)) {
            if(!oven.hasActions()) {
                if(Gdx.input.isKeyPressed(Input.Keys.E)){
                    if (oven.isOn) {
                        oven.updateOvenOff();
                        //if oven is On + Interaction => spawn boss. Current setup is for testing
                    } else {
                        oven.updateOvenOn();
                    }
                }
            }
        }
    }
}
