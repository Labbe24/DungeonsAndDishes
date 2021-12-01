package com.dungeonsanddishes.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.dungeonsanddishes.game.ChiliRoomLib.Fire;
import com.dungeonsanddishes.game.FishRoomLib.WaterPool;

import Framework.IngredientRoomTilemap;

abstract class SideIngredientRoomImplementation extends IngredientRoomImplementation {
    public abstract void update(float dt, Character character);
}

class FishRoom extends SideIngredientRoomImplementation {
    public int mapX = 192, mapY = 128, mapWidth = 1536, mapHeight = 800;

    private Stage stage;
    private int waterPaddleWidth = 307, waterPaddleHeight = 160;
    private Array<WaterPool> waterPoolList;

    public FishRoom() {
        // place watter paddles in room
        waterPoolList = new Array<WaterPool>();

        int x, y = mapY;

        // grid dimensions 5*5
        for(int i = 0; i < 5; i++) {

            x = mapX;

            for(int j = 0; j < 5; j++) {
                x += waterPaddleWidth;
                WaterPool waterPool = new WaterPool(mapX, mapHeight);
                waterPoolList.add(waterPool);
                stage.addActor(waterPool);
            }

            y += waterPaddleHeight;
        }
    }

    @Override
    public void update(float dt, Character character) {

    }

    @Override
    public void setRoom(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void removeRoom(Stage stage) {

    }
}

public class SideIngredientRoom extends IngredientRoom {
    public SideIngredientRoom() {
        _room_impl = new FishRoom();
        map_layout = new IngredientRoomTilemap("rooms/start_room.tmx", _room_impl);
    }
}
