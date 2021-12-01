package com.dungeonsanddishes.game;

import Framework.BaseActor;
import Framework.BaseScreen;
import Framework.Scene;

public class ThirdMainStoryScreen extends BaseScreen {
    Scene scene;
    BaseScreen nextScreen;
    BaseActor continueKey;

    @Override
    public void initialize() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    public void setNextScreen(BaseScreen s) { nextScreen = s; }
}
