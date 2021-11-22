package com.dungeonsanddishes.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import Framework.BaseActor;

public class Enemy extends BaseActor {
    Animation<TextureRegion> North;
    Animation<TextureRegion> South;
    Animation<TextureRegion> East;
    Animation<TextureRegion> West;
    int CharAngle;

    public Enemy(float x, float y, Stage s) {
        super(x, y, s);

    this.loadTexture("chef_idle/chef_idle_up.png");


    //Set animations:
    Array<TextureRegion> animation_array= new Array<>();
        animation_array.add(new TextureRegion(new Texture("chef_walk/chef_walk_right1.png")));
        animation_array.add(new TextureRegion(new Texture("chef_walk/chef_walk_right2.png")));

    East = new Animation<>(0.2f,animation_array, Animation.PlayMode.LOOP);

        animation_array.clear();
        animation_array.add(new TextureRegion(new Texture("chef_walk/chef_walk_right1.png")));
        animation_array.add(new TextureRegion(new Texture("chef_walk/chef_walk_right2.png")));
        for( TextureRegion anim :animation_array){
        anim.flip(true,false);
    }

    West = new Animation<>(0.2f,animation_array,Animation.PlayMode.LOOP);

        animation_array.clear();
        animation_array.add(new TextureRegion(new Texture("chef_walk/chef_walk_down1.png")));
        animation_array.add(new TextureRegion(new Texture("chef_walk/chef_walk_down2.png")));
    South= new Animation<>(0.2f,animation_array,Animation.PlayMode.LOOP);

        animation_array.clear();
        animation_array.add(new TextureRegion(new Texture("chef_walk/chef_walk_up1.png")));
        animation_array.add(new TextureRegion(new Texture("chef_walk/chef_walk_up2.png")));
    North= new Animation<>(0.2f,animation_array,Animation.PlayMode.LOOP);

    setAnimation(South);
    CharAngle=270;



    //set movement parameter values:
    setAcceleration(10000);
    setMaxSpeed(100);
    setDeceleration(10000);

    }

    @Override
    public void act(float dt) {
        super.act(dt);
        applyPhysics(dt);
        float angle = getMotionAngle();

        if (!isMoving()) {
            setAnimationPaused(true);
        } else {
            setAnimationPaused(false);
            if (angle >= 45 && angle <= 135)
            {
                CharAngle = 90;
                setAnimation(North);
            }
            else if (angle > 135 && angle < 225)
            {
                CharAngle = 180;
                setAnimation(West);
            }
            else if(angle >=315 || angle <=45)
            {
                CharAngle = 0;
                setAnimation(East);
            }
            else
            {
                CharAngle = 270;
                setAnimation(South);
            }
        }
    }
}
