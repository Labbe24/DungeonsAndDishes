package com.dungeonsanddishes.game;

import Framework.BaseActor;

public abstract class Boss extends BaseActor {
    protected boolean character_discovered;
    //some basic health impl.?
    protected Character character;
    public Boss(float x, float y) {
        super(x, y);
    }
    public void act(float dt){
        super.act(dt);
        applyPhysics(dt);
        boundToWorld();

        if(this.overlaps(character)){
            character.takeDamage(1);
            character.preventOverlap(this);
        }

    }

    public void discoverCharacter(Character actor) {
        character=actor;
        character_discovered = true;
    }

    abstract public void takeDamage(int dmg);

}
