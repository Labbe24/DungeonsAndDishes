package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import Framework.BaseScreen;
import Framework.TilemapActor;

public class LevelScreen extends BaseScreen
{
    Character character;
    TilemapActor map;

    public boolean scrolled(float a, float b){
        return true;
    }
    public void initialize() 
    {
        character = new Character(0,0, mainStage);
        map=new TilemapActor("rooms/start_room.tmx");
        map.setRoom(mainStage);

    }

    public void update(float dt)
    {
       if(Gdx.input.isKeyPressed(Input.Keys.A)){
           character.accelerateAtAngle(180);
       }
       if(Gdx.input.isKeyPressed(Input.Keys.D)){
           character.accelerateAtAngle(0);
       }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            character.accelerateAtAngle(90);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            character.accelerateAtAngle(270);
        }
        //if isKeyPressed(e)
            //check if interactible nearby
            //if interactible is door
            //call map.DoorEntered(door.getProperty("direction"))
    }
}