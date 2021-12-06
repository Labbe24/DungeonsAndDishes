package com.dungeonsanddishes.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import Framework.BaseActor;

public class Item extends BaseActor {
    Animation North;
    Animation South;
    Animation East;
    Animation West;
    Animation Swing;
    int CharAngle;

    public Item(float x, float y, Stage s) {
        super(x, y, s);
        this.loadTexture("items/knife/knife_left.png");

        //Set animations:
        Array<TextureRegion> animation_array= new Array<TextureRegion>();
        animation_array.add(new TextureRegion(new Texture("items/knife/knife_right.png")));

        East = new Animation(0.2f,animation_array,Animation.PlayMode.LOOP);

        animation_array.clear();
        animation_array.add(new TextureRegion(new Texture("items/knife/knife_right.png")));
        for( TextureRegion anim :animation_array){
            anim.flip(true,false);
        }

        West = new Animation(0.2f,animation_array,Animation.PlayMode.LOOP);

        animation_array.clear();
        animation_array.add(new TextureRegion(new Texture("items/knife/knife_left.png")));
        South = new Animation(0.2f,animation_array,Animation.PlayMode.LOOP);

        animation_array.clear();
        animation_array.add(new TextureRegion(new Texture("items/knife/knife_right.png")));
        North = new Animation(0.2f,animation_array,Animation.PlayMode.LOOP);

        animation_array.clear();
        animation_array.add(new TextureRegion(new Texture("items/knife/knife_right.png")));
        Swing = new Animation(0.2f,animation_array,Animation.PlayMode.LOOP);

        setAnimation(South);
        CharAngle = 270;

    }

    public void act(float dt){
        super.act(dt);
        applyPhysics(dt);
    }

    public void centerAtActorMainItem(Character other)
    {
        centerAtPosition( other.getX() + other.mainItemCoords.X, other.getY() + other.mainItemCoords.Y );
        int angle = other.getCharAngle();
        if (angle >= 45 && angle <= 135)
        {
            setOpacity(100);
            CharAngle = 90;
            setAnimation(North);
        }
        else if (angle > 135 && angle < 225)
        {
            setOpacity(0);
            CharAngle = 180;
            setAnimation(West);
        }
        else if(angle >=315 || angle <=45)
        {
            setOpacity(100);
            CharAngle = 0;
            setAnimation(East);
        }
        else
        {
            setOpacity(100);
            CharAngle = 270;
            setAnimation(South);
        }
    }

}
