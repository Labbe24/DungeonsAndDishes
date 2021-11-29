package com.dungeonsanddishes.game;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.graphics.Color;

import Framework.BaseActor;
import Framework.BaseGame;
import Framework.BaseScreen;
import Framework.Scene;
import Framework.SceneActions;
import Framework.SceneSegment;

public class ChiliStoryScreen extends BaseScreen {
    Scene scene;
    BaseScreen nextScreen;
    BaseActor continueKey;

    @Override
    public void initialize() {
        BaseActor background = new BaseActor(0, 0, mainStage);
        background.setSize(1536, 800);
        background.setOpacity(0);
        BaseActor.setWorldBounds(background);

        BaseActor actor1 = new BaseActor(0, 0, mainStage);
        actor1.loadTexture("ChiliRoom/chef_chili.png");
        actor1.setSize(128, 256);
        actor1.setPosition(-actor1.getWidth(), 200);

        TextBox textBox = new TextBox(0,0, uiStage);
        textBox.setDialogSize(800, 400);
        textBox.setBackgroundColor( new Color(0.6f, 0.6f, 0.8f, 1) );
        textBox.setFontScale(2f);
        textBox.setVisible(false);
        textBox.alignCenter();
        uiTable.add(textBox).expandX().expandY().bottom();
        continueKey = new BaseActor(0,0,uiStage);
        continueKey.loadTexture("key-blank.png");
        continueKey.setSize(32,32);
        continueKey.setVisible(false);
        textBox.addActor(continueKey);
        continueKey.setPosition( textBox.getWidth() - continueKey.getWidth(), 0 );

        // Scene plot
        scene = new Scene();
        mainStage.addActor(scene);
        scene.addSegment( new SceneSegment( background, Actions.fadeIn(1) ));
        scene.addSegment( new SceneSegment( actor1, SceneActions.moveToScreenPosition( 950,400,1)));
        scene.addSegment( new SceneSegment( textBox, Actions.show() ));
        scene.addSegment( new SceneSegment( textBox,
                SceneActions.setText("The chef ate a chili so hot, that flames are coming out of his butt!" ) ));
        scene.addSegment( new SceneSegment( continueKey, Actions.show() ));
        scene.addSegment( new SceneSegment( background, SceneActions.pause() ));
        scene.addSegment( new SceneSegment( continueKey, Actions.hide() ));
        scene.addSegment( new SceneSegment( textBox,
                SceneActions.setText("Let's quickly drink some milk!" ) ));
        scene.addSegment( new SceneSegment( continueKey, Actions.show() ));
        scene.addSegment( new SceneSegment( background, SceneActions.pause() ));
        scene.addSegment( new SceneSegment( continueKey, Actions.hide() ));
        scene.addSegment( new SceneSegment( textBox, Actions.hide() ) );
        scene.addSegment( new SceneSegment( actor1, SceneActions.moveToScreenPosition(2000, 200, 1)));
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
                BaseGame.setActiveScreen(new LevelScreen());
            }
        }
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    public boolean keyDown(int keyCode) {
        if(keyCode == Keys.C && continueKey.isVisible()) {
            scene.loadNextSegment();
        }
        return false;
    }

    public void setNextScreen(BaseScreen s) {
        nextScreen = s;
    }
}
