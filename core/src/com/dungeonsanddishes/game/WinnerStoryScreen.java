package com.dungeonsanddishes.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;

import Framework.BaseActor;
import Framework.BaseGame;
import Framework.BaseScreen;
import Framework.Scene;
import Framework.SceneActions;
import Framework.SceneSegment;

public class WinnerStoryScreen extends BaseScreen {
    Scene scene;
    BaseScreen nextScreen;
    BaseActor continueKey;

    WinnerStoryScreen(BaseScreen nextScreen){
        this.nextScreen=nextScreen;
    }
    @Override
    public void initialize() {
        BaseActor background = new BaseActor(0, 0, mainStage);
        background.setSize(1920, 1080);
        background.setOpacity(0);
        BaseActor.setWorldBounds(background);

        BaseActor star = new BaseActor(BaseActor.getWorldBounds().width/1, -1000, mainStage);
        star.loadTexture("michelinstar.png");

        // Scene plot
        scene = new Scene();
        mainStage.addActor(scene);
        scene.addSegment( new SceneSegment( background, Actions.fadeIn(1) ));

        scene.addSegment( new SceneSegment( star, SceneActions.moveToScreenCenter(0.8f)));
        scene.addSegment( new SceneSegment( star, SceneActions.scale(3, 3, 2)));

        scene.addSegment( new SceneSegment( background, Actions.fadeOut(1) ));
        scene.start();
    }

    @Override
    public void update(float dt) {
        if(scene.isSceneFinished()) {
            if(nextScreen != null) {
                BaseGame.setActiveScreen(nextScreen);
            }
            else {
                BaseGame.setActiveScreen(new LevelScreen(null)); // TODO: argument to LevelScreen?
            }
        }
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    public boolean keyDown(int keyCode) {
        if(keyCode == Input.Keys.C && continueKey.isVisible()) {
            scene.loadNextSegment();
        }
        return false;
    }

    public void setNextScreen(BaseScreen s) { nextScreen = s; }
}
