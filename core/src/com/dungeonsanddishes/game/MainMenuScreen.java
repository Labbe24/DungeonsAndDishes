package com.dungeonsanddishes.game;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import Framework.BaseScreen;

public class MainMenuScreen extends BaseScreen {
    private CustomGame game;
    public MainMenuScreen(CustomGame game){
        super();
        this.game=game;
    }
    @Override
    public void initialize() {
        TextButton playButton = new TextButton("Play!", new Skin());
        playButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.setScreen(new LevelScreen(game));
                return true;
            }
        });
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
