package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import Framework.BaseScreen;

public class MainMenuScreen extends BaseScreen {
    private CustomGame game;
    public MainMenuScreen(CustomGame game){
        super();
        this.game=game;
    }
    @Override
    public void initialize() {
        Skin skin = new Skin(Gdx.files.internal("golden-spiral/skin/golden-ui-skin.json"));
        Table menuTable = new Table(skin);
        menuTable.setFillParent(true);
        TextButton playButton = new TextButton("Play!",skin );
        menuTable.add(playButton);
        playButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.setScreen(new LevelScreen(game));
                MainMenuScreen.this.dispose();
                return true;
            }
        });
        menuTable.setBackground(pngToDrawable("backgrounds/MMBG.png"));
        this.uiStage.addActor(menuTable);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    public TextureRegionDrawable pngToDrawable(String filepath) {
        Texture text = new Texture(Gdx.files.internal(filepath));
        TextureRegion textReg = new TextureRegion(text);
        return new TextureRegionDrawable(textReg);
    }
}
