package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import Framework.BaseActor;

public class ChiliMovement implements IMovement {
    private BaseActor actor;
    private float characterX = 0, characterY = 0;
    private int characterDirection = UP;
    private static final int RIGHT = 0, LEFT = 1, UP = 2, DOWN = 3;
    private static final int CHARACTER_MOVEMENT = 32;
    private static final float MOVE_TIME = 0.2f;
    private float timer = MOVE_TIME;
    public float mapWidth, mapHeight;

    public ChiliMovement(BaseActor actor, float width, float height){
        super();

        this.actor = actor;
        this.actor.setAcceleration(100000);
        this.actor.setMaxSpeed(500);
        this.actor.setDeceleration(100000);
        characterX = actor.getX();
        characterY = actor.getY();
        mapWidth = width;
        mapHeight = height;
    }

    public void handleMovement() {
        queryInput();

        if(timer <= 0) {
            timer = MOVE_TIME;
            moveCharacter();
            checkForMapBoundaries();
        }
    }

    private void moveCharacter() {
        switch (characterDirection) {
            case RIGHT: {
                characterX += CHARACTER_MOVEMENT;
                break;
            }
            case LEFT: {
                characterX -= CHARACTER_MOVEMENT;
                break;
            }
            case UP: {
                characterY += CHARACTER_MOVEMENT;
                break;
            }
            case DOWN: {
                characterY -= CHARACTER_MOVEMENT;
                break;
            }
            default:
                break;
        }
        actor.setX(characterX);
        actor.setY(characterY);
    }

    private void checkForMapBoundaries() {
        if( characterX >= mapWidth) {
            characterX = 0;
        }
        if( characterX < 0) {
            characterX = mapWidth - CHARACTER_MOVEMENT;
        }
        if( characterY >= mapHeight) {
            characterY = 0;
        }
        if( characterY < 0) {
            characterY = mapHeight - CHARACTER_MOVEMENT;
        }
    }

    private void queryInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            characterDirection = LEFT;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            characterDirection = RIGHT;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            characterDirection = UP;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            characterDirection = DOWN;
        }
    }
}
