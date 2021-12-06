package com.dungeonsanddishes.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.dungeonsanddishes.game.FishRoomLib.Fish;
import com.dungeonsanddishes.game.FishRoomLib.ShadedWaterPool;
import com.dungeonsanddishes.game.FishRoomLib.Shark;
import com.dungeonsanddishes.game.FishRoomLib.WaterPool;

import Framework.IngredientRoomTilemap;

abstract class SideIngredientRoomImplementation extends IngredientRoomImplementation {
    public abstract void update(float dt, Character character);
}

class FishRoom extends SideIngredientRoomImplementation {
    private int mapX = 192, mapY = 128, mapWidth = 1536, mapHeight = 800;

    private Stage stage;
    private Shark shark;
    private Fish fish;

    private float TIME = 2F;
    private float SHARK_TIME = 2.3F;
    private float timer = TIME;
    private float sharkTimer = SHARK_TIME;
    private int waterPoolWidth = 307, waterPoolHeight = 160;
    private int survivedSharkAttacks = 0;
    private Array<WaterPool> waterPoolList;
    private Array<ShadedWaterPool> shadedWaterPoolsList;

    private boolean shadedPoolsAdded = false;
    private boolean removeShadedPools = false;
    private boolean attackedByShark = false;

    public FishRoom() {
        // place watter paddles in room
        waterPoolList = new Array<WaterPool>();
        shadedWaterPoolsList = new Array<ShadedWaterPool>();
        addWater();
    }

    @Override
    public void update(float dt, Character character) {
        if(survivedSharkAttacks >= 3 && shark.isAnimationFinished()) {
            removeWater();

            if(fish == null) {
                fish = new Fish((mapX+mapWidth)/2, (mapY+mapHeight)/2);
                fish.preventOverlap(character);
                stage.addActor(fish);
            }
            else if(character.overlaps(fish)) {
                    fish.remove();
                    character.incrementFish();
            }
        }

        else {
            if(!shadedPoolsAdded) {
                timer -= dt;
            }
            else {
                sharkTimer -= dt;
            }

            if(timer <= 0) {
                timer = TIME;
                addShadedPools(character);
                shadedPoolsAdded = true;
            }

            if(sharkTimer <= 0) {
                sharkTimer = SHARK_TIME;
                for (WaterPool pool : waterPoolList) {
                    if(pool.hasShark) {
                        shark = new Shark(0, 0, stage);
                        shark.centerAtActor(pool);
                        shark.setY(pool.getY()+pool.getHeight()/2);
                    }

                    if(character.getX() > pool.getX() && character.getX() < pool.getX() + pool.getWidth()
                    && character.getY() > pool.getY() && character.getY() < pool.getY() + pool.getHeight()
                    && pool.hasShark) {
                        attackedByShark = true;
                    }

                    //if(character.overlaps(pool) && pool.hasShark) {
                        //attackedByShark = true;
                    //}
                }

                if(attackedByShark) {
                    character.takeDamage(2);
                    attackedByShark = false;
                }
                else {
                    survivedSharkAttacks++;
                }
                shufflePoolsThatHaveSharks(character);
                removeShadedPools = true;
                shadedPoolsAdded = false;
            }
        }

        if(removeShadedPools && shark.isAnimationFinished()) {
            removeShadedPools();
            removeShadedPools = false;
        }
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

    private void addWater() {
        int x, y = mapY;

        for(int i = 0; i < 5; i++) {
            x = mapX;

            for(int j = 0; j < 5; j++) {
                WaterPool waterPool = new WaterPool(x, y);
                waterPool.hasShark = MathUtils.random(0, 1) > 0 ? true : false;
                waterPoolList.add(waterPool);
                x += waterPoolWidth;
            }

            y += waterPoolHeight;
        }
    }

    private void removeWater() {
        for (WaterPool pool : waterPoolList) {
            pool.remove();
        }
    }

    private void shufflePoolsThatHaveSharks(Character character) {
        for(WaterPool waterPool : waterPoolList) {
            if(character.overlaps(waterPool)) {
                waterPool.hasShark = true;
            }
            else {
                waterPool.hasShark = MathUtils.random(0, 1) > 0 ? true : false;
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
