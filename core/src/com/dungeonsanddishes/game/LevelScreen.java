package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;

import Framework.BaseScreen;
import Framework.RoomTilemap;

public class LevelScreen extends BaseScreen
{
    Character character;
    RoomTilemap map;
    DungeonMap dungeonMap;
    Item knife;

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
        knife = new Item(0,0,mainStage); // Must be initialized after char to be drawn over
        ArrayList<MapObject> spawn_point = map.getRectangleList("spawn_point");
        character.centerAtPosition((float)spawn_point.get(0).getProperties().get("x"),(float)spawn_point.get(0).getProperties().get("y"));

        character.setWorldBounds(1536, 778); // Hardcoded since they never change.
    }

    public void update(float dt)
    {
        if (!knife.hasActions()) {
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
           if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {

               for(Door door:dungeonMap.currentRoom.dungeonRoom.map_layout.getDoors()){
                   if(character.isWithinDistance(20,door)){
                       dungeonMap.doorEntered(door.getDirection(),character);
                       break;
                   }
               }
           }
           knife.centerAtActorMainItem(character);
       }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            swingKnife();
        }

        dungeonMap.getCurrentRoom().dungeonRoom.update(dt,character);
    }
            //check if interactible nearby
            //if interactible is door
            //call map.DoorEntered(door.getProperty("direction"))


    public Rectangle convertMapObjectToRectangle(MapObject obj) {
        MapProperties props = obj.getProperties();
        return new Rectangle( (float)props.get("x"), (float)props.get("y"), ((float)props.get("width")), ((float)props.get("height")));
    }

    public void swingKnife()
    {
        character.setSpeed(0);
        float facingAngle = character.CharAngle;
        float knifeArc = 180;
        if (facingAngle == 0)
            knife.addAction( Actions.sequence(
                    Actions.rotateBy(-knifeArc, 0.15f),
                    Actions.rotateBy(knifeArc, 0.15f))
            );
        else if (facingAngle == 90)
            knife.addAction( Actions.sequence(
                    Actions.rotateBy(-knifeArc, 0.15f),
                    Actions.rotateBy(knifeArc, 0.15f))
            );
        else if (facingAngle == 180)
            knife.addAction( Actions.sequence(
                    Actions.rotateBy(knifeArc, 0.15f),
                    Actions.rotateBy(-knifeArc, 0.15f))
            );
        else // facingAngle == 270
            knife.addAction( Actions.sequence(
                    Actions.rotateBy(knifeArc, 0.15f),
                    Actions.rotateBy(-knifeArc, 0.15f))
            );
    }
}