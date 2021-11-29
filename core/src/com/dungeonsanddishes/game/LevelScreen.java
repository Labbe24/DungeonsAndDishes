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
        dungeonMap = new DungeonMap(new RandomWalker(new DungeonRoomRepository(1, 7)),mainStage );
        dungeonMap.createDungeon();
        DungeonRoomMeta room = dungeonMap.getCurrentRoom();
        map=(RoomTilemap) room.dungeonRoom.map_layout;
        //map = new RoomTilemap("rooms/start_room.tmx");
        //map.setRoom(mainStage);

        character = new Character(0,0, mainStage);
        character.setMovementStragety(new BasicMovement(character));
        ArrayList<MapObject> spawn_point = map.getRectangleList("spawn_point");
        character.centerAtPosition((float)spawn_point.get(0).getProperties().get("x"),(float)spawn_point.get(0).getProperties().get("y"));
    }

    public void update(float dt)
    {
        if(character.movement != null) {
            character.movement.handleMovement();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {

            for(Door door:dungeonMap.currentRoom.dungeonRoom.map_layout.getDoors()){
                if(character.isWithinDistance(20,door)){
                    dungeonMap.doorEntered(door.getDirection(),character);
                    break;
                }
            }
        }

        dungeonMap.getCurrentRoom().dungeonRoom.update(dt,character);
    }
}