package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapObject;

import java.util.ArrayList;

import Framework.BaseScreen;
import Framework.RoomTilemap;

public class LevelScreen extends BaseScreen
{
    Character character;
    RoomTilemap map;
    DungeonMap dungeonMap;

    public boolean scrolled(float a, float b){
        return true;
    }

    /**
     *
     * setup the structure of the dungeon and initialize start room and character
     */
    public void initialize() 
    {
        dungeonMap = new DungeonMap(new RandomWalker(new DungeonRoomRepository(1, 7)));
        dungeonMap.createDungeon();
        DungeonRoomMeta room = dungeonMap.getCurrentRoom();
        map=(RoomTilemap) room.dungeonRoom.map_layout;
        //map = new RoomTilemap("rooms/start_room.tmx");
        //map.setRoom(mainStage);

        character = new Character(0,0, mainStage);
        ArrayList<MapObject> spawn_point = map.getRectangleList("spawn_point");
        character.centerAtPosition((float)spawn_point.get(0).getProperties().get("x"),(float)spawn_point.get(0).getProperties().get("y"));
    }

    public void update(float dt)
    {
       if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
           character.accelerateAtAngle(180);
       }
       if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
           character.accelerateAtAngle(0);
       }
       if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)){
            character.accelerateAtAngle(90);
       }
       if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
           character.accelerateAtAngle(270);
       }
        //if isKeyPressed(e)
            //check if interactible nearby
            //if interactible is door
            //call map.DoorEntered(door.getProperty("direction"))
    }
}