package com.dungeonsanddishes.game;

import com.badlogic.gdx.math.Vector2;

import java.awt.Point;

import Framework.BaseActor;

public class Seeker {
    private final BaseActor _character;
    private final BaseActor _enemy;
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

        // int angle = (int) calculateAngle(c_point, e_point);

        _enemy.setAccelerationVec(new Vector2(c_point.x - e_point.x, c_point.y - e_point.y));
    }

    private static float distance(Point a, Point b) {
        return (float) Math.sqrt((b.y - a.y) * (b.y - a.y) + (b.x - a.x) * (b.x - a.x));
    }

    private static int calculateAngle(Point a, Point b)
    {
        double angle = Math.toDegrees(Math.atan2(a.x - b.x, a.y - b.y) + Math.PI/2);
        System.out.println(angle);
        if (angle < 0) {
            angle += 360;
        }

        if (angle > 0 && angle < 90) {
            angle += 90;
        } else if (angle < 180 && angle > 90) {
            angle -= 90;
        } else if (angle > 180 && angle < 270) {
            angle += 90;
        } else {
            angle -= 90;
        }

        System.out.print(" ");
        System.out.print(angle);

        return (int) angle;
    }
}
