package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Framework.BaseActor;

public class Character extends BaseActor {
    Animation North;
    Animation South;
    Animation East;
    Animation West;
    int CharAngle;
    private float dmgDelay=0.3f;
    private float timeSinceDmgTaken=0;
    Coordinate itemCoordsNorth;
    Coordinate itemCoordsSouth;
    Coordinate itemCoordsEast;
    Coordinate itemCoordsWest;
    Coordinate mainItemCoords;
    Rectangle attackBoxNorth;
    Rectangle attackBoxSouth;
    Rectangle attackBoxEast;
    Rectangle attackBoxWest;
    Rectangle attackBox;


    public IMovement movement;
    private CharacterSound sound;


    public Character(float x, float y, Stage s) {
        super(x, y, s);
        this.loadTexture("chef_idle/chef_idle_up.png");
        this.sound = new CharacterSound();
        mainItemCoords = new Coordinate(x,y);
        //attackBox = new Rectangle(0,0,64,64);

        //Set Item coordinates:
        itemCoordsNorth = new Coordinate(61,73);
        itemCoordsSouth = new Coordinate(8,68);
        itemCoordsEast = new Coordinate(39,55);
        itemCoordsWest = new Coordinate(40,63);

        //Set Attack Boxes:
        attackBoxNorth = new Rectangle(32, 128, 64, 32);
        attackBoxSouth = new Rectangle(32, -32, 64, 64);
        attackBoxEast = new Rectangle(64, 64, 32, 64);
        attackBoxWest = new Rectangle(-32, 64, 64, 64);

        //Set animations:
        Array<TextureRegion> animation_array= new Array<TextureRegion>();
        animation_array.add(new TextureRegion(new Texture("chef_walk/chef_walk_right1.png")));
        animation_array.add(new TextureRegion(new Texture("chef_walk/chef_walk_right2.png")));

        East = new Animation(0.2f,animation_array,Animation.PlayMode.LOOP);

        animation_array.clear();
        animation_array.add(new TextureRegion(new Texture("chef_walk/chef_walk_right1.png")));
        animation_array.add(new TextureRegion(new Texture("chef_walk/chef_walk_right2.png")));
        for( TextureRegion anim :animation_array){
            anim.flip(true,false);
        }

        West = new Animation(0.2f,animation_array,Animation.PlayMode.LOOP);

        animation_array.clear();
        animation_array.add(new TextureRegion(new Texture("chef_walk/chef_walk_down1.png")));
        animation_array.add(new TextureRegion(new Texture("chef_walk/chef_walk_down2.png")));
        South = new Animation(0.2f,animation_array,Animation.PlayMode.LOOP);

        animation_array.clear();
        animation_array.add(new TextureRegion(new Texture("chef_walk/chef_walk_up1.png")));
        animation_array.add(new TextureRegion(new Texture("chef_walk/chef_walk_up2.png")));
        North = new Animation(0.2f,animation_array,Animation.PlayMode.LOOP);

        setAnimation(South);
        CharAngle = 270;
        setMainItemCoords(itemCoordsSouth);
        setAttackBox(attackBoxSouth);

        //set movement parameter values:
        setAcceleration(10000);
        setMaxSpeed(300);
        setDeceleration(10000);

        setBoundaryRectangle();
    }
    public Character(float x, float y, Stage s, int health){
        this(x,y,s);
        health_bar = new CharacterHealth(health);
    }
    private CharacterHealth health_bar;
    public void takeDamage(int dmg){
        Logger.getGlobal().log(Level.INFO ,"TakeDamage, time since dmg: "+timeSinceDmgTaken);
        if(timeSinceDmgTaken>=dmgDelay){
            timeSinceDmgTaken=0;
            health_bar.takeDamage(dmg);
        }
    }
    public void displayHealth(Stage s,float x,float y){
        health_bar.displayHealthBar(s,x,y);
    }
    public void act(float dt){
        timeSinceDmgTaken+=dt;
        super.act(dt);
        applyPhysics(dt);
        // set direction animation
        float angle = getMotionAngle();

        if (isMoving() == false) {
            setAnimationPaused(true);
        } else {
            setAnimationPaused(false);
            this.sound.Hurt();
            if (angle >= 45 && angle <= 135) {
                CharAngle = 90;
                setAnimation(North);
                setMainItemCoords(itemCoordsNorth);
                setAttackBox(attackBoxNorth);
            } else if (angle > 135 && angle < 225) {
                CharAngle = 180;
                setAnimation(West);
                setMainItemCoords(itemCoordsWest);
                setAttackBox(attackBoxWest);
            } else if (angle >= 315 || angle <= 45) {
                CharAngle = 0;
                setAnimation(East);
                setMainItemCoords(itemCoordsEast);
                setAttackBox(attackBoxEast);
            } else {
                CharAngle = 270;
                setAnimation(South);
                setMainItemCoords(itemCoordsSouth);
                setAttackBox(attackBoxSouth);
            }
        }
    }

    private void setMainItemCoords(Coordinate coords) {
        mainItemCoords = coords;
    }
    public void setAttackBox(Rectangle rectangle) {
       //rectangle.x = getX() + rectangle.x;
       //rectangle.y = getY() + rectangle.y;
        attackBox = rectangle;
    }

    public Coordinate getMainItemCoords() {
        return mainItemCoords;
    }
    public Rectangle getAttackBox() {
        return attackBox;
    }

    public int getCharAngle() {
        return CharAngle;
    }
    
    public void setMovementStragety(IMovement movement){
        this.movement = movement;
    }

    public boolean isDead() {
        return health_bar.isDead();
    }
}

class CharacterHealth extends Health {

    //visual heart class
    enum HeartContainerState{ FULL,HALF,EMPTY};
    class HeartContainer extends BaseActor {
        protected HeartContainerState state;
        public HeartContainer(float x, float y) {
            super(x, y);
            this.scaleBy(2);
            state=HeartContainerState.EMPTY;
        }

        public void setState(HeartContainerState state) {
            this.state = state;
        }

        @Override
         public void act(float dt){
            switch (state){
                case FULL:
                    this.setTexture("hearts/full_heart.png");
                    break;
                case HALF:
                    this.setTexture("hearts/half_heart.png");
                    break;
                case EMPTY:
                    this.setTexture("hearts/empty_heart.png");
                    break;
                default:
                    break;
            }
         }
    }


    //max hp
    int max_hp;
    protected float x;
    protected float y;
    //ArrayList of heartContainers
    ArrayList<HeartContainer> heartContainers;
    public CharacterHealth(int hp) {
        super(hp);
        heartContainers = new ArrayList<HeartContainer>();
        max_hp=hp;
    }
    public void updateHeartStates(){
        for(int i=0;i<max_hp/2+max_hp%2;i++){
            Logger.getGlobal().log(Level.INFO, "hi from update iteration: "+i);
            //problem if i=0
            if((i+1)*2<=lives){
                heartContainers.get(i).setState(HeartContainerState.FULL);
            }
            else if((i+1)*2==lives+1){
                heartContainers.get(i).setState(HeartContainerState.HALF);
            }
            else{
                heartContainers.get(i).setState(HeartContainerState.EMPTY);
            }
        }
    }

    @Override
    public void takeDamage(int dmg) {
        super.takeDamage(dmg);
        this.updateHeartStates();
    }

    @Override
    public void heal(int hp) {
        super.heal(hp);
        if(lives>max_hp)
            lives=max_hp;
        this.updateHeartStates();
    }

    public void displayHealthBar(Stage ui_stage, float x, float y){
        this.x=x;
        this.y=y;
        for(int i=0;i<max_hp/2+max_hp%2;i++){
            if(i>= heartContainers.size()){
                //create new heart
                heartContainers.add(new HeartContainer(x+i*40,y));
            }
            if(ui_stage!= heartContainers.get(i).getStage()){
                //add to stage
                ui_stage.addActor(heartContainers.get(i));
            }
        }
        updateHeartStates();
    }
}
