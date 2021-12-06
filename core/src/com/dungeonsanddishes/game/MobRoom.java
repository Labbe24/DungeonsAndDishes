package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.dungeonsanddishes.game.StartRoomLib.Oven;

import java.util.ArrayList;

import Framework.IngredientRoomTilemap;
import Framework.RoomTilemap;

abstract class MobRoomImplementation extends IngredientRoomImplementation{
    protected ArrayList<Enemy> enemies;
    protected Stage stage;

    public MobRoomImplementation(){
        generateEnemies();
    }
    @Override
    public void setRoom(Stage stage) {
        this.stage=stage;
        for (Enemy enemy : enemies ) {
            if (enemy.health > 0) {
                stage.addActor(enemy);
            }
        }
    }

    @Override
    public void removeRoom(Stage stage) {
        for (Enemy enemy : enemies ) {
            enemy.remove();
        }
    }

    @Override
    public void update(float dt, Character character) {   }

    public ArrayList<Enemy> getEnemies(){return enemies;}

    public void generateEnemies() {
        enemies = new ArrayList<Enemy>();
        int x = MathUtils.random(4);
        switch(x) {
            case 1 :
                enemies.add(new Enemy(800,600, 500));
                break;
            case 2 :
                enemies.add(new Enemy(800,600, 500));
                enemies.add(new Enemy(300,300, 500));
                break;
            case 3 :
                enemies.add(new Enemy(800,600, 500));
                enemies.add(new Enemy(300,300, 500));
                enemies.add(new Enemy(600,800, 500));
                break;
            case 4 :
                enemies.add(new Enemy(800,600, 500));
                enemies.add(new Enemy(300,300, 500));
                enemies.add(new Enemy(600,800, 500));
                enemies.add(new Enemy(500,900, 500));
                break;
            default :
                enemies.add(new Enemy(800,600, 500));
        }

    }
}

public class MobRoom extends IngredientRoom {
    public MobRoom(){
        _room_impl = new MobRoomImplementation() {};
        map_layout = new IngredientRoomTilemap("rooms/start_room.tmx", _room_impl);
    }

    @Override
    public void update(float dt, Character character) {
        super.update(dt,character);
        RoomTilemap map =(RoomTilemap)map_layout;
        ArrayList<Enemy> enemies = ((MobRoomImplementation)_room_impl).getEnemies();

        for (Enemy enemy : enemies ) {
            if (enemy.health < 1) {
                enemy.remove();
            } else {
                enemy.seek(character);
                character.preventOverlap(enemy);
                if (character.mainItem.hasActions()) {
                    Rectangle rect = character.getAttackBox();
                    Rectangle hitbox = new Rectangle(rect.getX() + character.getX(), rect.getY() + character.getY(), rect.width, rect.height);
                    if (enemy.overlapsRectangle(hitbox)) {
                        enemy.takeDamage(1);
                    }
                }
            }
        }
    }
}
