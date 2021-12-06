package com.dungeonsanddishes.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

import Framework.BaseActor;

public abstract class Projectile extends BaseActor {
    enum TargetType{
        CHARACTER,
    }
    public TargetType targetType=TargetType.CHARACTER;
    protected int dmg;
    public Projectile(float x, float y, Stage s,int direction,int speed,int damage) {
        super(x, y, s);
        dmg=damage;
        setSpeed(speed);
        setMotionAngle(direction);
        setBoundaryRectangle();
    }
    public void act(float dt){
        applyPhysics(dt);
        if(!this.overlaps(getWorldBounds())){
            this.destroy();
        }
    }
    public int damage(){
        return dmg;
    }


    public void destroy(){
        this.remove();
    }
}
