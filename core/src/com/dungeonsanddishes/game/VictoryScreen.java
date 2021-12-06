package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import Framework.BaseScreen;

public class VictoryScreen extends BaseScreen {
    private CustomGame game;
    public VictoryScreen(CustomGame game) {
        this.game = game;
    }

    @Override
    public void initialize() {
        Skin skin = new Skin(Gdx.files.internal("golden-spiral/skin/golden-ui-skin.json"));
        Table uiTable = new Table(skin);
        uiTable.setFillParent(true);
        uiTable.add(new Label("Congratulations On Beating The Game",skin));
        uiTable.row();
        uiTable.add(new Label("You Are A Brilliant Chef!!!",skin));
        uiStage.addActor(uiTable);
    }

    @Override
    public void update(float dt) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Gdx.app.exit();
        }
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
