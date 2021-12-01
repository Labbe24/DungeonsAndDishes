package com.dungeonsanddishes.game;

import Framework.BaseGame;
import Framework.BaseScreen;

public class CustomGame extends BaseGame {
    public void create() {
        super.create();

        FirstMainStoryScreen first = new FirstMainStoryScreen();
        SecondMainStoryScreen second = new SecondMainStoryScreen();
        ThirdMainStoryScreen third = new ThirdMainStoryScreen();

        LevelScreen lvlScreen = new LevelScreen();

        first.setNextScreen(second);
        second.setNextScreen(third);
        third.setNextScreen(lvlScreen);

        setActiveScreen(first);
    }
}
