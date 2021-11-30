package com.dungeonsanddishes.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import Framework.BaseActor;

public class Character extends BaseActor {
    Animation North;
    Animation South;
    Animation East;
    Animation West;
    int CharAngle;
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

    public Character(float x, float y, Stage s) {
        super(x, y, s);
        this.loadTexture("chef_idle/chef_idle_up.png");
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

    public void act(float dt){
        super.act(dt);
        applyPhysics(dt);
        // set direction animation
        float angle = getMotionAngle();

        if(isMoving()==false){
            setAnimationPaused(true);
        }
        else{
            setAnimationPaused(false);
            if(angle >= 45 && angle <= 135)
            {
                CharAngle = 90;
                setAnimation(North);
                setMainItemCoords(itemCoordsNorth);
                setAttackBox(attackBoxNorth);
            }
            else if(angle > 135 && angle < 225)
            {
                CharAngle = 180;
                setAnimation(West);
                setMainItemCoords(itemCoordsWest);
                setAttackBox(attackBoxWest);
            }
            else if(angle >=315 || angle <=45)
            {
                CharAngle = 0;
                setAnimation(East);
                setMainItemCoords(itemCoordsEast);
                setAttackBox(attackBoxEast);
            }
            else
            {
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
}
