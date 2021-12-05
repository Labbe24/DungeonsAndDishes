package com.dungeonsanddishes.game;

import static Framework.TilemapActor.convertMapObjectToRectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
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
    private Music music;

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
        this.music = Gdx.audio.newMusic(Gdx.files.internal("sounds/level-music.ogg"));
        dungeonMap = new DungeonMap(new RandomWalker(new DungeonRoomRepository(1, 7)),mainStage );
        dungeonMap.createDungeon();
        DungeonRoomMeta room = dungeonMap.getCurrentRoom();
        map=(RoomTilemap) room.dungeonRoom.map_layout;
        //map = new RoomTilemap("rooms/start_room.tmx");
        //map.setRoom(mainStage);

        character = new Character(0,0, mainStage,6);
        character.displayHealth(uiStage,30,Gdx.graphics.getHeight() - 50);
        character.displayRecipe(uiStage, 150, Gdx.graphics.getHeight() - 50);
        ArrayList<MapObject> spawn_point = map.getRectangleList("spawn_point");
        character.centerAtPosition((float)spawn_point.get(0).getProperties().get("x"),(float)spawn_point.get(0).getProperties().get("y"));
        character.setWorldBounds(Gdx.graphics.getWidth() - 350, Gdx.graphics.getHeight() - 200); // Hardcoded since they never change.
        music.setVolume(0.05f);
        music.setLooping(true);
        music.play();


    }

    public void update(float dt)
    {
        if(!character.isDead()){
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

            if (Gdx.input.isKeyPressed(Input.Keys.I)) {
                character.incrementChili();
                character.incrementRice();
            }

            dungeonMap.getCurrentRoom().dungeonRoom.update(dt,character);
        }
        else{
            //game over
            Logger.getGlobal().log(Level.WARNING,"GAME OVER!!!!");
            this.dispose();
            music.stop();
            game.setScreen( new GameOverScreen(this.game));
        }
    }
}