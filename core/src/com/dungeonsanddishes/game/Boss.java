package com.dungeonsanddishes.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import Framework.BaseActor;
class BossHealth extends Health{
    private BossHealthBar bar;
    private  Boss boss;
    //visual representation
    class BossHealthBar extends BaseActor{
        private Texture health;
        private float displacement_x;
        private float displacement_y;

        public BossHealthBar(float x, float y) {
            super(x, y);
            setTexture("golden-spiral/raw/progress-bar.png");
            health = new Texture("golden-spiral/raw/progress-bar-life.png");
            displacement_x= (this.getHeight()-health.getHeight())/2;
            displacement_y= (this.getWidth()-health.getWidth())/2;
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            float ratio;
            if(BossHealth.this.lives!=0)
                ratio=(float)(BossHealth.this.lives)/(float)(BossHealth.this.max_hp);
            else
                ratio=0;
            batch.draw(health,this.getX()+displacement_x,this.getY()+displacement_y,0,0,(int)(health.getWidth()*ratio),health.getHeight());
        }

        @Override
        public void act(float dt) {
            super.act(dt);
            //position bar
            float x_displacement=(BossHealth.this.boss.getWidth()-getWidth())/2;
            setX(BossHealth.this.boss.getX()+x_displacement);
            setY(BossHealth.this.boss.getY()+BossHealth.this.boss.getHeight()+5);
        }
    }

    public BossHealth(int hp, Boss boss) {
        super(hp);
        this.bar = new BossHealthBar(0,0);
        this.boss=boss;
    }
    public void removeHealthBar(){
        bar.remove();
    }
    public void displayHealthBar(Stage stage) {
        stage.addActor(bar);
    }
}
public abstract class Boss extends BaseActor {
    protected BossHealth health;
    protected boolean character_discovered;
    //some basic health impl.?
    protected Character character;
    public Boss(float x, float y) {
        super(x, y);
        health = new BossHealth(5,this);
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

    public void takeDamage(int dmg){
        health.takeDamage(dmg);
    }

    public void displayHealthBar(Stage stage) {
        health.displayHealthBar(stage);
    }

    public void removeHealthBar() {
        health.removeHealthBar();
    }
}
