package com.dungeonsanddishes.game;

public class Health {
    protected int max_hp;
    protected int lives;
    public Health(int hp){
        max_hp = hp;
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
        if(lives>max_hp){
            lives=max_hp;
        }
    }
}
