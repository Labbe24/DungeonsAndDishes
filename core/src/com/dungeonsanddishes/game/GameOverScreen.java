package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import Framework.BaseScreen;

public class GameOverScreen extends BaseScreen {
    float time_spent=0;
    CustomGame game;
    public GameOverScreen(CustomGame game) {
        this.game=game;
    }

    @Override
    public void initialize() {
        Skin skin = new Skin(Gdx.files.internal("golden-spiral/skin/golden-ui-skin.json"));
        Table uiTable = new Table(skin);
        uiTable.setFillParent(true);
        uiTable.add(new Label("Game Over!!!",skin));
        uiStage.addActor(uiTable);

    }

    @Override
    public void update(float dt) {
        time_spent+=dt;
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            this.dispose();
            game.setScreen(new MainMenuScreen(this.game));

        }

    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
