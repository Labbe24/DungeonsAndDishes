package com.dungeonsanddishes.game;

import Framework.BaseGame;

public class CustomGame extends BaseGame {
    public void create() {
        super.create();
        setActiveScreen(new LevelScreen());
    }
}
