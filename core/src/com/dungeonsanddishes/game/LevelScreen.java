package com.dungeonsanddishes.game;

import Framework.BaseActor;
import Framework.BaseScreen;

public class LevelScreen extends BaseScreen
{
    public boolean scrolled(float a, float b){
        return true;
    }
    public void initialize() 
    {
        BaseActor pic = new BaseActor(0,0, mainStage);
        pic.loadTexture( "badlogic.jpg" );
    }

    public void update(float dt)
    {
       
    }
}