package com.dungeonsanddishes.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;

import java.awt.Point;

import Framework.BaseActor;

public class Enemy extends BaseActor {
    Animation<TextureRegion> East;
    Animation<TextureRegion> West;
    int CharAngle;
    private final float _detectRange;
    private float dmgDelay=0.3f;
    private float timeSinceDmgTaken=0;
    public int health = 2;

    public Enemy(float x, float y, float detectRange) {
        super(x, y);
        this._detectRange = detectRange;
    this.loadTexture("Enemies/rottenFish/rotten_fish_west_1.png");

    //Set animations:
    Array<TextureRegion> animation_array= new Array<>();
        animation_array.add(new TextureRegion(new Texture("Enemies/rottenFish/rotten_fish_east_1.png")));
        animation_array.add(new TextureRegion(new Texture("Enemies/rottenFish/rotten_fish_east_2.png")));
    East = new Animation<>(0.2f,animation_array, Animation.PlayMode.LOOP);

        animation_array.clear();
        animation_array.add(new TextureRegion(new Texture("Enemies/rottenFish/rotten_fish_west_1.png")));
        animation_array.add(new TextureRegion(new Texture("Enemies/rottenFish/rotten_fish_west_2.png")));
    West = new Animation<>(0.2f,animation_array,Animation.PlayMode.LOOP);
    setAnimation(East);
    CharAngle=270;

    //set movement parameter values:
    setAcceleration(10000);
    setMaxSpeed(150);
    setDeceleration(10000);
    }

    public void takeDamage(int i) {
        if(timeSinceDmgTaken>=dmgDelay) {
            flicker();
            this.health= this.health - i;
            timeSinceDmgTaken=0;
        }
    }

    public void flicker() {
        SequenceAction flicker = new SequenceAction(Actions.fadeOut(0.1f), Actions.fadeIn(0.1f));
        this.addAction(Actions.repeat(2, flicker));
    }

    public void seek(Character _character) {
        Point c_point =  new Point((int) _character.getX(), (int) _character.getY());
        Point e_point = new Point((int) this.getX(), (int) this.getY());
        float dist = distance(c_point, e_point);
        boolean collided = distance(c_point, e_point) < 70;

        if (dist > _detectRange) {
            return;
        }
        if (this.overlaps(_character)) {
            _character.takeDamage(1);
            return;
        }
        this.setAccelerationVec(new Vector2(c_point.x - e_point.x, c_point.y - e_point.y));
    }

    protected static float distance(Point a, Point b) {
        return (float) Math.sqrt((b.y - a.y) * (b.y - a.y) + (b.x - a.x) * (b.x - a.x));
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        applyPhysics(dt);
        float angle = getMotionAngle();
        timeSinceDmgTaken+=dt;

        if (!isMoving()) {
            setAnimationPaused(true);
        } else {
            setAnimationPaused(false);
            if (angle >= 45 && angle <= 135)
            {
                CharAngle = 90;
                setAnimation(West);
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
                setAnimation(East);
            }
        }
    }
}
