package com.dungeonsanddishes.game;

import static java.lang.Math.abs;

import com.badlogic.gdx.math.Vector2;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import Framework.BaseActor;

class SushiMovement extends Seeker{
    enum MovementAxis{
        X,Y,IDLE,
    }
    private MovementAxis movementAxis=MovementAxis.IDLE;
    float speed=200;
    float roll_speed=600;
    public SushiMovement(BaseActor character, BaseActor enemy, float detectRange) {
        super(character, enemy, detectRange);
    }
    public float playerAngle(){
        Vector2 char_vec = new Vector2(_character.getX(),_character.getY());
        Vector2 enemy_vec = new Vector2(_enemy.getX(),_enemy.getY());

        Vector2 vec = char_vec.sub(enemy_vec);
        float angle = vec.angleDeg();
        Logger.getGlobal().log(Level.INFO, "rolling at angle: "+angle+"Char vec is : "+char_vec.x+","+char_vec.y+" Enemy vec is: "+enemy_vec.x+","+enemy_vec.y);
        return angle;
    }

    public void setMovementAxis(MovementAxis movementAxis) {
        this.movementAxis = movementAxis;
    }
    public void roll(){
        float angle= playerAngle();
        _enemy.setMotionAngle(angle);
        _enemy.setSpeed(600);
    }
    @Override
    public void Seek() {

        _enemy.setSpeed(speed);
        if(movementAxis==MovementAxis.X)
        {
            Logger.getGlobal().log(Level.INFO, "moving in x direction");
            if(_character.getX()>_enemy.getX()){
                _enemy.setMotionAngle(0);
            }
            else {
                _enemy.setMotionAngle(180);
            }

        }
        else if(movementAxis==MovementAxis.Y){
            if(_character.getY()>_enemy.getY()){
                _enemy.setMotionAngle(90);
            }
            else {
                _enemy.setMotionAngle(270);
            }

        }else if(movementAxis==MovementAxis.IDLE){
            _enemy.setSpeed(0);
        }
    }
}
public class SushiBoss extends Boss{
    boolean isInAction;
    float remainingActionTime;
    Random seeder;
    private  SushiMovement sushiMovement;
    public SushiBoss(float x, float y) {
        super(x, y);
        seeder=new Random();
        remainingActionTime=0;
        isInAction=false;
        this.loadTexture("sushi_boss/sushi_idle1.png");
        setBoundaryRectangle();
    }

    @Override
    public void discoverCharacter(Character actor) {
        super.discoverCharacter(actor);
        sushiMovement=new SushiMovement(character,this,2000);
    }

    public void act(float dt){
        super.act(dt);
        remainingActionTime-=dt;
        if(remainingActionTime<=0){
            isInAction=false;
        }
        if(!isInAction){
            //reset rotation here?
            setRotation(0);
            isInAction=true;
            int randomNumber = seeder.nextInt();
            switch(randomNumber%3){
                case 0:
                    remainingActionTime=(randomNumber%10)/10f;
                    move(seeder.nextInt());
                    break;
                case 1:
                    remainingActionTime=(randomNumber%10)/10f;
                    shoot(seeder.nextInt());
                    break;
                case 2:
                    remainingActionTime=1f;
                    roll(seeder.nextInt());
                    break;

            }
        }
    }
    private void move(int seed){
        //set animation
        setTexture("sushi_boss/sushi_idle1.png");
        switch (seed%3){
            case 0:
                sushiMovement.setMovementAxis(SushiMovement.MovementAxis.IDLE);
                break;
            case 1:
                sushiMovement.setMovementAxis(SushiMovement.MovementAxis.X);
                break;
            case 2:
                sushiMovement.setMovementAxis(SushiMovement.MovementAxis.Y);
                break;

        }
        sushiMovement.Seek();
    }

    private void shoot(int seed){
        String[] files = new String[]{"sushi_boss/sushi_shoot1.png","sushi_boss/sushi_idle1.png"};
        this.setAnimationFromFiles(files,0.3f,false);
        float angle_to_shoot = sushiMovement.playerAngle();
        new RiceProjectile(this.getX(),this.getY(),this.getStage(),(int)angle_to_shoot,400,1);
    }
    private void roll(int seed){
        //set animation
        setTexture("sushi_boss/sushi_roll1.png");
        //calculate angle
        rotateBy(abs(sushiMovement.playerAngle()-getRotation())+90);
        sushiMovement.roll();
    }
}
