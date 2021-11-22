package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import Framework.BaseActor;

public class ChiliMovement implements IMovement{
    private BaseActor actor;
    private String lastKeyPressed = "";

    public ChiliMovement(BaseActor actor){

        this.actor = actor;
        this.actor.setAcceleration(100000);
        this.actor.setMaxSpeed(500);
        this.actor.setDeceleration(100000);
    }

    public void handleMovement(){
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            lastKeyPressed = "LEFT";
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            lastKeyPressed = "RIGHT";
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            lastKeyPressed = "UP";
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            lastKeyPressed = "DOWN";
        }

        switch (lastKeyPressed){
            case "LEFT":
                actor.accelerateAtAngle(180);
                break;
            case "RIGHT":
                actor.accelerateAtAngle(0);
                break;
            case "UP":
                actor.accelerateAtAngle(90);
                break;
            case "DOWN":
                actor.accelerateAtAngle(270);
                break;
            default:
                break;
        }
    }
}
