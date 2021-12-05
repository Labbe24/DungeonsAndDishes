package com.dungeonsanddishes.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class RiceProjectile extends Projectile {

    public RiceProjectile(float x, float y, Stage s, int direction, int speed, int damage) {
        super(x, y, s, direction, speed, damage);
        String[] files = new String[]{"sushi_boss/rice.png", "sushi_boss/rice_1.png", "sushi_boss/rice_2.png"};
        loadAnimationFromFiles(files, 3, true);
    }
}
