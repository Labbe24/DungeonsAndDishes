package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.dungeonsanddishes.game.ChiliRoomLib.Chili;
import com.dungeonsanddishes.game.ChiliRoomLib.Fire;
import com.dungeonsanddishes.game.ChiliRoomLib.Milk;

import Framework.IngredientRoomTilemap;

abstract class SpiceIngredientRoomImplementation extends IngredientRoomImplementation{
    public abstract void update(float dt, Character character);
}

class ChiliRoom extends SpiceIngredientRoomImplementation {
    private TextBox tb;

    private Chili chili;
    private Milk milk;
    private int milkX, milkY;
    private Boolean milkAvailable = false;
    private float milkTimer = 0;
    private float MILK_TIME = 2f;
    private Array<Fire> fireList;
    private Stage stage;
    private int milksToDrink = 5;
    private int milksDrunk = 0;
    private Boolean chiliEaten = false;

    public int mapX = 192, mapY = 128, mapWidth = 1536, mapHeight = 800;
    private Boolean gameOver = false;
    private static final float MOVE_TIME = 0.1F;
    private static final int CHARACTER_MOVEMENT = 32;
    private float timer = MOVE_TIME;
    private int characterX = 0, characterY = 0, prevCharacterX, prevCharacterY;
    private static final int RIGHT = 0, LEFT = 1, UP = 2, DOWN = 3;
    private int characterDirection = UP;
    private int firePartsAdded = 0;

    public ChiliRoom() {
        int x = MathUtils.random(mapWidth / CHARACTER_MOVEMENT - 1) * CHARACTER_MOVEMENT;
        int y = MathUtils.random(mapHeight / CHARACTER_MOVEMENT - 1) * CHARACTER_MOVEMENT;
        chili = new Chili(x, y);
        fireList = new Array<Fire>();
        characterX = x;
        characterY = y;
    }

    @Override
    public void update(float dt, Character character) {
        if(!chiliEaten && character.overlaps(chili)) {
            chili.remove();
            character.setMovementStragety(null);
            chiliEaten = true;
            setRandomMilk();
        }

        if(!gameOver) {
            if(chiliEaten) {

                queryInput();
                timer -= dt;

                if(timer <= 0) {
                    timer = MOVE_TIME;
                    checkForNewMilk(MOVE_TIME);
                    moveCharacter(character);
                    checkForMapBoundaries();
                    updateFire();
                    character.setX(characterX);
                    character.setY(characterY);

                    if(firePartsAdded < milksToDrink) {
                        addFire();
                        firePartsAdded++;
                    }
                }

                checkMilkCollision();

                if (milksDrunk < milksToDrink) {
                    setRandomMilk();
                }
                else if (milksDrunk >= milksToDrink) {
                    character.setMovementStragety(new BasicMovement(character));
                    gameOver = true;
                }
            }
        }
        // if character collides with chili
        // remove chili
        // check win-conditions
        // -- if win, let the player know and set character movement to default
        // -- if no win, spawn new milk
    }

    @Override
    public void setRoom(Stage stage) {
        this.stage = stage;
        stage.addActor(chili);

        // dialog box
        /*
        tb = new TextBox(864, 464, stage);
        tb.setText("Eat the chili!");
        tb.setBackgroundColor(Color.WHITE);
        tb.setFontColor(Color.BLACK);
        tb.setDialogSize(600, 100);
        tb.setFontScale(1f);
        tb.alignCenter();
        stage.addActor(tb);
        */
    }

    @Override
    public void removeRoom(Stage stage) {
        chili.remove();
    }

    private void moveCharacter(Character character) {
        prevCharacterX = characterX;
        prevCharacterY = characterY;

        switch (characterDirection) {
            case RIGHT: {
                characterX += CHARACTER_MOVEMENT;
                character.accelerateAtAngle(0);
                break;
            }
            case LEFT: {
                characterX -= CHARACTER_MOVEMENT;
                character.accelerateAtAngle(180);
                break;
            }
            case UP: {
                characterY += CHARACTER_MOVEMENT;
                character.accelerateAtAngle(90);
                break;
            }
            case DOWN: {
                characterY -= CHARACTER_MOVEMENT;
                character.accelerateAtAngle(270);
                character.toFront();
                break;
            }
            default:
                break;
        }
    }

    private void checkForMapBoundaries() {
        if( characterX >= mapWidth + mapX) {
            characterX = mapX;
        }
        if( characterX < mapX) {
            characterX = mapWidth + mapX - CHARACTER_MOVEMENT;
        }
        if( characterY >= mapHeight + mapY) {
            characterY = mapY;
        }
        if( characterY < mapY) {
            characterY = mapHeight + mapY - CHARACTER_MOVEMENT;
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

    private void setRandomMilk() {
        if(!milkAvailable) {
            do {
                milkX = MathUtils.random(mapWidth / CHARACTER_MOVEMENT - 1) * CHARACTER_MOVEMENT;
                milkY = MathUtils.random(mapHeight / CHARACTER_MOVEMENT - 1) * CHARACTER_MOVEMENT;
            } while ((milkX < mapX || milkY < mapY)
                    || (milkX == characterX && milkY == characterY));

            milk = new Milk(milkX, milkY);
            stage.addActor(milk);
            milkAvailable = true;
        }
    }

    private void checkMilkCollision() {
        if(milkAvailable && characterX == milkX && characterY == milkY) {
            milk.remove();
            milkAvailable = false;
            milksDrunk++;
            milkTimer = 0;

            Fire firePart = fireList.removeIndex(fireList.size-1);
            firePart.remove();
        }
    }

    private void checkForNewMilk(float tick) {
        milkTimer += tick;
        if(milkTimer >= MILK_TIME){
            milk.remove();
            milkAvailable = false;
            setRandomMilk();
            milkTimer = 0;
        }
    }

    private void updateFire() {
        if(fireList.size > 0) {
            Fire firePart = fireList.removeIndex(0);
            firePart.updateFirePosition(prevCharacterX, prevCharacterY);
            fireList.add(firePart);
        }
    }

    private void addFire() {
        Fire fire = new Fire(characterX, characterY);
        fireList.insert(0, fire);
        stage.addActor(fire);
    }
}

public class SpiceIngredientRoom extends IngredientRoom {
    public SpiceIngredientRoom(){
        _room_impl = new ChiliRoom();;
        map_layout = new IngredientRoomTilemap("rooms/start_room.tmx", _room_impl);
    }
}
