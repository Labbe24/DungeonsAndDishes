package com.dungeonsanddishes.game;

import Framework.BaseGame;

public class CustomGame extends BaseGame {
    public void create() {
        super.create();

        FirstMainStoryScreen first = new FirstMainStoryScreen();
        SecondMainStoryScreen second = new SecondMainStoryScreen();
        ThirdMainStoryScreen third = new ThirdMainStoryScreen();
        MainMenuScreen main =  new MainMenuScreen(this);

        first.setNextScreen(second);
        second.setNextScreen(third);
        third.setNextScreen(main);

        setActiveScreen(first);
    }
}
