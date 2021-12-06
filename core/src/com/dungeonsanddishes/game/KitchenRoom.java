package com.dungeonsanddishes.game;

import static Framework.TilemapActor.convertMapObjectToRectangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dungeonsanddishes.game.StartRoomLib.Oven;

import java.util.ArrayList;

import Framework.BaseActor;
import Framework.IngredientRoomTilemap;
import Framework.RoomTilemap;

abstract class KitchenRoomImplementation extends IngredientRoomImplementation{
    protected Boss boss;
    protected Stage stage;
    protected Oven oven;
    boolean boss_spawned;
    public KitchenRoomImplementation(){
        oven = new Oven(800,600);
    }
    @Override
    public void setRoom(Stage stage) {
        this.stage=stage;
        stage.addActor(oven);
    }

    @Override
    public void removeRoom(Stage stage) {
        oven.remove();
        if(boss_spawned) {
            boss.remove();
            boss.removeHealthBar();
        }
    }

    @Override
    public void update(float dt, Character character) {

    }

    public Oven getOven(){return oven;}
    public ArrayList<Projectile> getprojectiles(){
        ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
        for( BaseActor actor : BaseActor.getList(stage,"com.dungeonsanddishes.game.Projectile")){
            projectiles.add((Projectile) actor);
        }
        return projectiles;
    }
    public Boss getBoss(){return boss;}
    public void setBoss(Boss boss){
        this.boss=boss;
        stage.addActor(boss);
        boss.displayHealthBar(stage);
        for( BaseActor actor : BaseActor.getList(stage,"com.dungeonsanddishes.game.Character")){
            boss.discoverCharacter((Character)actor);
        }
        boss_spawned=true;


    };
}

public class KitchenRoom extends IngredientRoom {
    public KitchenRoom(){
        _room_impl = new KitchenRoomImplementation() {};
        map_layout = new IngredientRoomTilemap("rooms/start_room.tmx", _room_impl);
    }
    public void bossUpdate(float dt, Character character) {
        super.update(dt, character);
        Boss boss = ((KitchenRoomImplementation) _room_impl).getBoss();
        RoomTilemap map = (RoomTilemap) map_layout;
        for (MapObject obj : map.getCustomRectangleList("Collidable")) {
            if ((boolean) obj.getProperties().get("Collidable")) {
                boss.preventOverlapWithObject(convertMapObjectToRectangle(obj));
            }
        }
        //iterate projectiles
        for (Projectile projectile : ((KitchenRoomImplementation) _room_impl).getprojectiles()) {
            for (MapObject obj : map.getCustomRectangleList("Collidable")) {
                if (projectile.overlaps(convertMapObjectToRectangle(obj))) {
                    //destroy if crashed into environment
                    projectile.destroy();
                    //dmg if hitting target
                }
            }

            if (projectile.targetType == Projectile.TargetType.CHARACTER && projectile.overlaps(character)) {
                character.takeDamage(projectile.damage());
                projectile.destroy();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.K))
            boss.takeDamage(1);

        if (boss.health.isDead()) {
            character.setBossSlain(true);
            if (character.mainItem.hasActions()) {
                Rectangle rect = character.getAttackBox();
                Rectangle hitbox = new Rectangle(rect.getX() + character.getX(), rect.getY() + character.getY(), rect.width, rect.height);

                if (((KitchenRoomImplementation) _room_impl).getBoss().overlapsRectangle(hitbox)) {
                    ((KitchenRoomImplementation) _room_impl).getBoss().takeDamage(1);
                }
            }
        }
    }
    @Override
    public void update(float dt, Character character) {
        super.update(dt,character);
        RoomTilemap map =(RoomTilemap)map_layout;
        //check for ingredients, if ingredients present, turn on oven

        Oven oven = ((KitchenRoomImplementation)_room_impl).getOven();
        if(!((KitchenRoomImplementation) _room_impl).boss_spawned)
            character.preventOverlap(oven);

        if (character.isWithinDistance(30, oven)) {
            if(!oven.hasActions()) {
                if(Gdx.input.isKeyPressed(Input.Keys.E)){
                    if (oven.isOn) {
                        oven.updateOvenOff();
                        oven.remove();
                        ((KitchenRoomImplementation) _room_impl).setBoss(new SushiBoss(800,600));
                        //if oven is On + Interaction => spawn boss. Current setup is for testing
                    } else {
                        oven.updateOvenOn();
                    }
                }
            }
        }
        if(((KitchenRoomImplementation) _room_impl).boss_spawned){
            bossUpdate(dt,character);
        }
    }
}
