package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import Framework.BaseActor;

// TODO: Singleton??
public class BasicMovement implements IMovement {
    private BaseActor actor;

    public BasicMovement(BaseActor actor){

        this.actor = actor;
        this.actor.setAcceleration(10000);
        this.actor.setMaxSpeed(300);
        this.actor.setDeceleration(10000);
    }

    public void handleMovement(){
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            actor.accelerateAtAngle(180);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            actor.accelerateAtAngle(0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)){
            actor.accelerateAtAngle(90);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            actor.accelerateAtAngle(270);
        }
    }
}
