package com.dungeonsanddishes.game;

import com.badlogic.gdx.math.Vector2;

import java.awt.Point;

import Framework.BaseActor;

public class Seeker {
    protected final BaseActor _character;
    protected final BaseActor _enemy;
    private final float _detectRange;

    public Seeker(BaseActor character, BaseActor enemy, float detectRange) {
        _character = character;
        _enemy = enemy;
        _detectRange = detectRange;
    }

    public void Seek() {
        Point c_point =  new Point((int) _character.getX(), (int) _character.getY());
        Point e_point = new Point((int) _enemy.getX(), (int) _enemy.getY());
        float dist = distance(c_point, e_point);
        boolean collided = distance(c_point, e_point) < 70;

        if (dist > _detectRange || collided) {
            return;
        }

        _enemy.setAccelerationVec(new Vector2(c_point.x - e_point.x, c_point.y - e_point.y));
    }

    protected static float distance(Point a, Point b) {
        return (float) Math.sqrt((b.y - a.y) * (b.y - a.y) + (b.x - a.x) * (b.x - a.x));
    }
}
