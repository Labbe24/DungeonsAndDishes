package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

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

    Item mainItem = null;

    public IMovement movement;
    private CharacterSound sound;
    private boolean _boss_slain;
    private CharacterHealth _healthBar;
    private Recipe _recipe;


    public Character(float x, float y, Stage s) {
        super(x, y, s);
        this.loadTexture("chef_idle/chef_idle_up.png");
        this.sound = new CharacterSound();
        mainItemCoords = new Coordinate(x,y);

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
        _healthBar = new CharacterHealth(health);
        this._recipe = new Recipe();
    }

    public void takeDamage(int dmg){
        if(timeSinceDmgTaken>=dmgDelay){
            flicker();
            timeSinceDmgTaken=0;
            _healthBar.takeDamage(dmg);
        }
    }

    public void displayHealth(Stage s,float x,float y){
        _healthBar.displayHealthBar(s,x,y);
    }

    public void incrementChili() {
        this._recipe.incrementChili();
    }

    public void incrementRice() {
        this._recipe.incrementRice();
    }

    public void incrementFish() { this._recipe.incrementFish(); }

    public boolean finishedRecipe() {return this._recipe.Finsihed(); }

    public void displayRecipe(Stage s,float x,float y) {
        this._recipe.display(s, x, y);
    }

    public void act(float dt){
        timeSinceDmgTaken+=dt;
        super.act(dt);
        applyPhysics(dt);
        // set direction animation
        float angle = getMotionAngle();

        if (isMoving() == false) {
            setAnimationPaused(true);
            this.sound.StopWalk();
        }
        else{
            setAnimationPaused(false);
            this.sound.StartWalk();
            if(angle >= 45 && angle <= 135)
            {
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
        return _healthBar.isDead();
    }

    public boolean bossSlain() {
        return _boss_slain;
    }
    public void setBossSlain(boolean val){
        _boss_slain=val;
    }

    public void setMainItem(Item item) {
        mainItem = item;
    }

    public void flicker() {
        SequenceAction flicker = new SequenceAction(Actions.fadeOut(0.1f), Actions.fadeIn(0.1f));
        this.addAction(Actions.repeat(2, flicker));
    }
}

class CharacterHealth extends Health {

    //visual heart class
    enum HeartContainerState{ FULL,HALF,EMPTY};
    private Music damageSound;

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
    protected float x;
    protected float y;
    //ArrayList of heartContainers
    ArrayList<HeartContainer> heartContainers;
    public CharacterHealth(int hp) {
        super(hp);
        heartContainers = new ArrayList<HeartContainer>();
        max_hp=hp;
        damageSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/hit-by-enemy.ogg"));
    }
    public void updateHeartStates(){
        for(int i=0;i<max_hp/2+max_hp%2;i++){
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
        damageSound.play();
        this.updateHeartStates();
    }

    @Override
    public void heal(int hp) {
        super.heal(hp);
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
