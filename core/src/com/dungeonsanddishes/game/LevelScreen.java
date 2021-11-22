package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Framework.BaseScreen;
import Framework.RoomTilemap;

public class LevelScreen extends BaseScreen
{
    Character character;
    RoomTilemap map;
    DungeonMap dungeonMap;
    CustomGame game;
    ArrayList<Rectangle> collisionRectangles;

    public LevelScreen(CustomGame game){
        super();
        this.game=game;
    }

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

        character = new Character(0,0, mainStage,6);
        character.displayHealth(uiStage,30,1000);
        ArrayList<MapObject> spawn_point = map.getRectangleList("spawn_point");
        character.centerAtPosition((float)spawn_point.get(0).getProperties().get("x"),(float)spawn_point.get(0).getProperties().get("y"));
        character.setWorldBounds(1550, 765); // Hardcoded since they never change.
    }

    public void update(float dt)
    {
        if(!character.health_bar.isDead()){
            character.boundToWorld();

            for (MapObject obj:map.getCustomRectangleList("Collidable")){
                if ((boolean)obj.getProperties().get("Collidable")) {
                    character.preventOverlapWithObject( convertMapObjectToRectangle(obj));
                }
            }

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
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                character.health_bar.takeDamage(1);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.H)){
                character.health_bar.heal(1);
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
        else{
            //game over
            Logger.getGlobal().log(Level.WARNING,"GAME OVER!!!!");
        }
    }
            //check if interactible nearby
            //if interactible is door
            //call map.DoorEntered(door.getProperty("direction"))


    public Rectangle convertMapObjectToRectangle(MapObject obj) {
        MapProperties props = obj.getProperties();
        return new Rectangle( (float)props.get("x"), (float)props.get("y"), ((float)props.get("width")), ((float)props.get("height")));
    }
}