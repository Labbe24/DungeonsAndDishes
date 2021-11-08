package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dungeonsanddishes.game.ChiliRoomLib.Chili;
import com.dungeonsanddishes.game.ChiliRoomLib.Fire;
import com.dungeonsanddishes.game.ChiliRoomLib.Milk;

import java.util.ArrayList;

import Framework.IngredientRoomTilemap;

abstract class SpiceIngredientRoomImplementation extends IngredientRoomImplementation{
    public abstract void update(float dt, Character character);
}

class ChiliRoom extends SpiceIngredientRoomImplementation {
    private Chili chili;
    private Milk milk;
    private ArrayList<Fire> fireList;
    private Stage stage;
    private int milksToDrink = 5;
    private int milksDrunk = 0;
    private Boolean chiliEaten = false;
    private int TIME = 60;
    private int DELTA = 5;
    private int timer = 60;
    private float characterX = 0;
    private float characterY = 0;

    public ChiliRoom(){
        chili = new Chili(640, 200);
        fireList = new ArrayList<Fire>();
    }

    @Override
    public void update(float dt, Character character) {

        timer -= DELTA;
        if(timer < 0) {
            timer = TIME;
            if(chiliEaten) {
                renderFire(character);
            }
        }

        if(!chiliEaten && character.overlaps(chili)) {
            chili.remove();
            character.setMovementStragety(new ChiliMovement(character));
            chiliEaten = true;

            float randomX = MathUtils.random(Gdx.graphics.getWidth()-128);
            float randomY = MathUtils.random(Gdx.graphics.getHeight()-128);
            milk = new Milk(randomX, randomY);
            stage.addActor(milk);
            addFire(character);
        }

        if(chiliEaten && character.overlaps(milk)) {
            milk.remove();
            milksDrunk++;

            // remove one fire from chefs tail
            int listSize = fireList.size()-1;
            fireList.get(listSize).remove();
            fireList.remove(listSize);

            if (milksDrunk < milksToDrink) {
                float randomX = MathUtils.random(Gdx.graphics.getWidth()-128);
                float randomY = MathUtils.random(Gdx.graphics.getHeight()-128);
                milk = new Milk(randomX, randomY);
                stage.addActor(milk);
            }
            else if (milksDrunk >= milksToDrink) {
                character.setMovementStragety(new BasicMovement(character));
            }
        }



        // if character collides with chili
        // remove chili
        // check win-conditions
        // -- if win, let the player know and set character movement to default
        // -- if no win, spawn new milk
    }

    @Override
    public void setRoom(Stage stage) {
        this.stage = stage;
        stage.addActor(chili);
    }

    @Override
    public void removeRoom(Stage stage) {
        chili.remove();
    }

    private void renderFire(Character character) {
        int listSize = fireList.size()-1;
        fireList.get(listSize).remove();
        fireList.remove(listSize);
        Fire fire = new Fire(characterX, characterY);
        stage.addActor(fire);

        ArrayList<Fire> tempFireList = new ArrayList<Fire>();
        tempFireList.add(0, fire);

        for(int i = 1; i < listSize+1; i++){

            tempFireList.add(i, fireList.get(i-1));
        }

        fireList = tempFireList;
        characterX = character.getX();
        characterY = character.getY();
    }

    private void addFire(Character character) {
        characterX = character.getX();
        characterY = character.getY();
        Fire firstFire = new Fire(characterX, characterY);
        fireList.add(0, firstFire);
        stage.addActor(firstFire);

        for(int i = 1; i < milksToDrink; i++) {
            Fire restFire = new Fire(fireList.get(i-1).getX()+32, fireList.get(i-1).getY());
            fireList.add(i, restFire);
            stage.addActor(restFire);
        }
    }
}

public class SpiceIngredientRoom extends IngredientRoom {
    public SpiceIngredientRoom(){
        _room_impl = new ChiliRoom();
        map_layout = new IngredientRoomTilemap("rooms/start_room.tmx", _room_impl);
    }
}
