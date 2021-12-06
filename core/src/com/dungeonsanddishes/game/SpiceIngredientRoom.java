package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.dungeonsanddishes.game.ChiliRoomLib.Chili;
import com.dungeonsanddishes.game.ChiliRoomLib.Fire;
import com.dungeonsanddishes.game.ChiliRoomLib.Milk;

import Framework.BaseScreen;
import Framework.IngredientRoomTilemap;

abstract class SpiceIngredientRoomImplementation extends IngredientRoomImplementation{
    public abstract void update(float dt, Character character);
}

class ChiliRoom extends SpiceIngredientRoomImplementation {
    private Stage stage;
    private Music chiliSound;
    private Music milkSound;

    private Chili chili;
    private Chili chiliIngredient;

    private Array<Fire> fireList;
    private int firePartsAdded = 0;

    private Milk milk;
    private float MILK_TIME = 3f;
    private int milksToDrink = 5;
    private int milksDrunk = 0;

    private int mapX = 192, mapY = 128, mapWidth = 1536, mapHeight = 800;

    private static final float MOVE_TIME = 0.1F;
    private static final int CHARACTER_MOVEMENT = 32;
    private static final int RIGHT = 0, LEFT = 1, UP = 2, DOWN = 3;
    private float timer = MOVE_TIME;
    private int characterX = 0, characterY = 0, prevCharacterX, prevCharacterY;
    private int characterDirection = UP;

    private boolean gameOver = false;

    public ChiliRoom() {
        milkSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/milk.ogg"));
        milk = new Milk(0, 0);

        int x;
        int y;

        do {
            x = MathUtils.random(mapWidth / CHARACTER_MOVEMENT - 1) * CHARACTER_MOVEMENT;
            y = MathUtils.random(mapHeight / CHARACTER_MOVEMENT - 1) * CHARACTER_MOVEMENT;
        } while (x < mapX || y < mapY);

        chili = new Chili(MathUtils.random(mapX + 300, mapWidth - 300), MathUtils.random(mapY + 150, mapHeight - 150));
        fireList = new Array<Fire>();
        characterX = x;
        characterY = y;
    }

    @Override
    public void update(float dt, Character character) {
        if(character.boundToWorld) {
            character.boundToWorld = false;
        }

        if(!chili.eaten && character.overlaps(chili)) {
            ChiliStoryScreen screen = new ChiliStoryScreen();
            screen.setNextScreen(CustomGame.getActiveScreen());
            CustomGame.setActiveScreen(screen);

            chili.remove();
            character.setMovementStragety(null);
            chili.eaten = true;
            setRandomMilk();
        }

        if(!gameOver) {
            if(chili.eaten) {

                queryInput(character);
                timer -= dt;

                if(timer <= 0) {
                    timer = MOVE_TIME;

                    moveCharacter(character);
                    checkForMapBoundaries();

                    character.setX(characterX);
                    character.setY(characterY);

                    checkFireCollision(character);
                    checkForNewMilk(MOVE_TIME);
                    updateFire();

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

        if(gameOver) {
            character.incrementChili();
            removeFire();
            removeMilk();
            character.setMovementStragety(new BasicMovement(character));
            character.boundToWorld = true;

            if(chiliIngredient == null) {
                chiliIngredient = new Chili((mapX+mapWidth)/2, (mapY+mapHeight)/2);
                stage.addActor(chiliIngredient);
            }
            else {
                if(character.overlaps(chiliIngredient)) {
                    chiliIngredient.remove();
                    character.incrementChili();
                }
            }
        }
    }

    @Override
    public void setRoom(Stage stage) {
        this.stage = stage;
        stage.addActor(chili);
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
                character.setTexture("chef_idle/chef_idle_right.png");
                break;
            }
            case LEFT: {
                characterX -= CHARACTER_MOVEMENT;
                character.setTexture("chef_idle/chef_idle_left.png");
                break;
            }
            case UP: {
                characterY += CHARACTER_MOVEMENT;
                character.setTexture("chef_idle/chef_idle_up.png");
                break;
            }
            case DOWN: {
                characterY -= CHARACTER_MOVEMENT;
                character.setTexture("chef_idle/chef_idle_down.png");
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

    private void queryInput(Character character) {
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if(characterDirection == RIGHT) {
                character.takeDamage(6);
                gameOver = true;
                return;
            }
            characterDirection = LEFT;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if(characterDirection == LEFT) {
                character.takeDamage(6);
                gameOver = true;
                return;
            }
            characterDirection = RIGHT;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if(characterDirection == DOWN) {
                character.takeDamage(6);
                gameOver = true;
                return;
            }
            characterDirection = UP;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if(characterDirection == UP) {
                character.takeDamage(6);
                gameOver = true;
                return;
            }
            characterDirection = DOWN;
        }
    }

    private void setRandomMilk() {
        if(!milk.available) {
            int milkX, milkY;
            do {
                milkX = MathUtils.random(mapWidth / CHARACTER_MOVEMENT - 1) * CHARACTER_MOVEMENT;
                milkY = MathUtils.random(mapHeight / CHARACTER_MOVEMENT - 1) * CHARACTER_MOVEMENT;
            } while ((milkX < mapX || milkY < mapY)
                    || (milkX == characterX && milkY == characterY));

            milk.setPosition(milkX, milkY);
            stage.addActor(milk);
            milk.available = true;
        }
    }

    private void checkMilkCollision() {
        if(milk.available && characterX == milk.getX() && characterY == milk.getY()) {
            milk.remove();
            milk.available = false;
            milksDrunk++;
            milk.milkTimer = 0;
            milkSound.play();

            Fire firePart = fireList.removeIndex(fireList.size-1);
            firePart.remove();
        }
    }

    private void checkForNewMilk(float tick) {
        milk.milkTimer += tick;
        if(milk.milkTimer >= MILK_TIME){
            milk.remove();
            milk.available = false;
            setRandomMilk();
            milk.milkTimer = 0;
        }
    }

    private void removeMilk() {
        if(milk != null) {
            milk.remove();
        }
    }

    private void checkFireCollision(Character character) {
        for(int i = 0; i < fireList.size; i++) {
            if(fireList.get(i).getX() == characterX
            && fireList.get(i).getY() == characterY) {
                character.takeDamage(6);
                gameOver = true;
                return;
            }
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
        Fire fire = new Fire(prevCharacterX, prevCharacterY);
        fireList.insert(0, fire);
        stage.addActor(fire);
    }

    private void removeFire() {
        //Fire firePart;
        //for(int i = 0; i < fireList.size; i++) {
            //firePart = fireList.removeIndex(i);
            //firePart.remove();
        //}
        if(fireList.notEmpty()) {
            fireList.clear();
        }
    }
}

public class SpiceIngredientRoom extends IngredientRoom {
    public SpiceIngredientRoom(){
        _room_impl = new ChiliRoom();;
        map_layout = new IngredientRoomTilemap("rooms/start_room.tmx", _room_impl);
    }
}
