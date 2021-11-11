package com.dungeonsanddishes.game;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Health {
    protected int lives;
    public Health(int hp){
        lives=hp;
    }

    public boolean isDead(){ return lives==0;}

    public void takeDamage(int dmg){
        lives -=dmg;
        if(lives<0)
            lives=0;
    }
    public void heal(int hp){
        lives+=hp;
    }
}
