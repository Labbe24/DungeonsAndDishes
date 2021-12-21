package com.dungeonsanddishes.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import Framework.BaseActor;
import Framework.BaseGame;
import Framework.BaseScreen;
import Framework.Scene;
import Framework.SceneActions;
import Framework.SceneSegment;

public class TutorialMainStoryScreen extends BaseScreen {
    Scene scene;
    BaseScreen nextScreen;
    BaseActor continueKey;

    @Override
    public void initialize() {
        BaseActor background = new BaseActor(0, 0, mainStage);
        background.loadTexture("tuto.png");
        background.setSize(1920, 1080);
        background.setOpacity(0);
        BaseActor.setWorldBounds(background);

        TextBox textBox = new TextBox(0,0, uiStage);
        textBox.setDialogSize(600, 200);
        textBox.setBackgroundColor( new Color(0.6f, 0.6f, 0.8f, 1) );
        textBox.setFontScale(1.5f);
        textBox.setVisible(false);
        textBox.alignCenter();
        uiTable.add(textBox).expandX().expandY().bottom();
        continueKey = new BaseActor(0,0,uiStage);
        continueKey.loadTexture("key-blank.png");
        continueKey.setSize(32,32);
        continueKey.setVisible(false);
        continueKey.setPosition( textBox.getWidth() - continueKey.getWidth(), 0);
        textBox.addActor(continueKey);


        // Scene plot
        scene = new Scene();
        mainStage.addActor(scene);
        scene.addSegment( new SceneSegment( background, Actions.fadeIn(1) ));
        scene.addSegment( new SceneSegment( textBox, Actions.show() ));
        scene.addSegment( new SceneSegment( textBox,
                SceneActions.setText("[PRES C TO CONTINUE]" ) ));
        scene.addSegment( new SceneSegment( continueKey, Actions.show() ));
        scene.addSegment( new SceneSegment( background, SceneActions.pause() ));
        scene.addSegment( new SceneSegment( continueKey, Actions.hide() ));
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
