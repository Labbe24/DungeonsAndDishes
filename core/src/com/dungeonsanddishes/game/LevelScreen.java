package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import java.util.Random;

import Framework.BaseScreen;
import Framework.TilemapActor;

public class LevelScreen extends BaseScreen
{
    Character character;
    Enemy enemy;
    TilemapActor map;
    Random random;
    Seeker seeker;

    public boolean scrolled(float a, float b){
        return true;
    }
    public void initialize() 
    {
        random = new Random();
        map = new TilemapActor("rooms/start_room.tmx",mainStage);
        character = new Character(0,0, mainStage);
        enemy = new Enemy(random.nextInt(100), random.nextInt(100), mainStage);
        seeker = new Seeker(character, enemy, 400);
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

        // Reset on 'R'  key
        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            Reset();
        }

        seeker.Seek();
    }

    private void Reset() {
        character.setPosition(500, 500);
        enemy.setPosition(300, 300);
    }


}