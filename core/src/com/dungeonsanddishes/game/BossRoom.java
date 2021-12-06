package com.dungeonsanddishes.game;

import static Framework.TilemapActor.convertMapObjectToRectangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Framework.BaseActor;
import Framework.IngredientRoomTilemap;
import Framework.RoomTilemap;

class BossRoomImplementation extends IngredientRoomImplementation{
    protected Boss boss;
    protected Stage stage;
    public BossRoomImplementation(){
        boss = new SushiBoss(800,600);
    }
    @Override
    public void setRoom(Stage stage) {
        //Add range in middle of room
        this.stage=stage;
        stage.addActor(boss);
        boss.displayHealthBar(stage);
        for( BaseActor actor : BaseActor.getList(stage,"com.dungeonsanddishes.game.Character")){
            boss.discoverCharacter((Character)actor);
        }

    }
    public ArrayList<Projectile> getprojectiles(){
        ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
        for( BaseActor actor : BaseActor.getList(stage,"com.dungeonsanddishes.game.Projectile")){
            projectiles.add((Projectile) actor);
        }
        return projectiles;
    }
    public Boss getBoss(){return boss;}

    @Override
    public void removeRoom(Stage stage) {

    }

    @Override
    public void update(float dt, Character character) {
        //First half

        //check for ingredients, if ingredients present, spawn according boss.
        //second half:

    }
}

public class BossRoom extends IngredientRoom{

    public BossRoom(){
        _room_impl = new BossRoomImplementation();
        map_layout = new IngredientRoomTilemap("rooms/start_room.tmx",_room_impl);
    }

    @Override
    public void update(float dt, Character character) {
        super.update(dt,character);
        Boss boss = ((BossRoomImplementation) _room_impl).getBoss();
        RoomTilemap map =(RoomTilemap)map_layout;
        for (MapObject obj:map.getCustomRectangleList("Collidable")){
            if ((boolean)obj.getProperties().get("Collidable")) {
                boss.preventOverlapWithObject( convertMapObjectToRectangle(obj));
            }
        }
        //iterate projectiles
        for(Projectile projectile: ((BossRoomImplementation)_room_impl).getprojectiles()){
            for (MapObject obj:map.getCustomRectangleList("Collidable")) {
                if(projectile.overlaps(convertMapObjectToRectangle(obj))){
                    //destroy if crashed into environment
                    projectile.destroy();
                    //dmg if hitting target
                }
            }

            if(projectile.targetType== Projectile.TargetType.CHARACTER && projectile.overlaps(character)){
                character.takeDamage(projectile.damage());
                projectile.destroy();
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.K))
            boss.takeDamage(1);

        if(boss.health.isDead()){
            character.setBossSlain(true);
        if (character.mainItem.hasActions()) {
            Rectangle rect = character.getAttackBox();
            Rectangle hitbox = new Rectangle(rect.getX() + character.getX(), rect.getY() + character.getY(), rect.width, rect.height);

            if (((BossRoomImplementation)_room_impl).getBoss().overlapsRectangle(hitbox)) {
                ((BossRoomImplementation)_room_impl).getBoss().takeDamage(1);
            }
        }
    }
}
