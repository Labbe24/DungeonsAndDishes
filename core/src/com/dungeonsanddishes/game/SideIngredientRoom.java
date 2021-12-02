package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.dungeonsanddishes.game.ChiliRoomLib.Fire;
import com.dungeonsanddishes.game.FishRoomLib.ShadedWaterPool;
import com.dungeonsanddishes.game.FishRoomLib.Shark;
import com.dungeonsanddishes.game.FishRoomLib.WaterPool;

import java.util.Random;

import Framework.IngredientRoomTilemap;

abstract class SideIngredientRoomImplementation extends IngredientRoomImplementation {
    public abstract void update(float dt, Character character);
}

class FishRoom extends SideIngredientRoomImplementation {
    public int mapX = 192, mapY = 128, mapWidth = 1536, mapHeight = 800;

    private Stage stage;
    private float TIME = 2F;
    private float SHARK_TIME = 3F;
    private float timer = TIME;
    private float sharkTimer = SHARK_TIME;
    private int waterPaddleWidth = 307, waterPaddleHeight = 160;
    private Array<WaterPool> waterPoolList;
    private Array<ShadedWaterPool> shadedWaterPoolsList;

    private boolean shadedPoolsAdded = false;

    public FishRoom() {
        // place watter paddles in room
        waterPoolList = new Array<WaterPool>();
        shadedWaterPoolsList = new Array<ShadedWaterPool>();

        int x, y = mapY;

        // grid dimensions 5*5
        for(int i = 0; i < 5; i++) {

            x = mapX;

            for(int j = 0; j < 5; j++) {
                WaterPool waterPool = new WaterPool(x, y);
                waterPool.hasShark = MathUtils.random(0, 1) == 1 ? true : false;
                waterPoolList.add(waterPool);
                x += waterPaddleWidth;
            }

            y += waterPaddleHeight;
        }
    }

    @Override
    public void update(float dt, Character character) {
        if(!shadedPoolsAdded) {
            timer -= dt;
        }

        if(timer <= 0) {
            timer = TIME;
            addShadedPools(character);
            shadedPoolsAdded = true;
        }

        if(shadedPoolsAdded) {
            sharkTimer -= dt;
        }

        if(sharkTimer <= 0) {
            sharkTimer = SHARK_TIME;
            for (WaterPool pool : waterPoolList) {
                if(pool.hasShark) {
                    Shark shark = new Shark(pool.getX(), pool.getY(), stage);
                    shark.centerAtActor(pool);
                }

                if(character.overlaps(pool) && pool.hasShark) {
                    character.remove();
                }
            }
            shufflePoolsThatHaveSharks(character);
            removeShadedPools();
            shadedPoolsAdded = false;
        }

        // call addShadedPools and reset shadedPoolsTimer

        // when shadedPoolsTimer expires
        // make sharks jump
        // if character overlaps with waterPool that has shark
            // character gets eaten and player looses
        // else
            // update water pool that have sharks
    }

    @Override
    public void setRoom(Stage stage) {
        this.stage = stage;
        for (WaterPool pool: waterPoolList) {
            stage.addActor(pool);
        }
    }

    @Override
    public void removeRoom(Stage stage) {

    }

    private void shufflePoolsThatHaveSharks(Character character) {
        for(WaterPool waterPool : waterPoolList) {
            if(character.overlaps(waterPool)) {
                waterPool.hasShark = true;
            }
            else {
                waterPool.hasShark = MathUtils.random(0, 1) == 1 ? true : false;
            }
        }
    }

    private void addShadedPools(Character character) {
        for(WaterPool pool : waterPoolList) {
            if(pool.hasShark) {
                ShadedWaterPool shadedWaterPool = new ShadedWaterPool(pool.getX(), pool.getY(), stage);
                shadedWaterPoolsList.add(shadedWaterPool);
            }
        }
        character.toFront();
    }

    private void removeShadedPools() {
        for(ShadedWaterPool pool : shadedWaterPoolsList) {
            pool.remove();
        }
        shadedWaterPoolsList.clear();
    }
}

public class SideIngredientRoom extends IngredientRoom {
    public SideIngredientRoom() {
        _room_impl = new FishRoom();
        map_layout = new IngredientRoomTilemap("rooms/start_room.tmx", _room_impl);
    }
}
