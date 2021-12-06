package com.dungeonsanddishes.game.StartRoomLib;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;

import Framework.BaseActor;

public class Oven extends BaseActor{
    Animation On;
    Animation Off;
    public boolean isOn;

        public Oven(float x, float y) {
            super(x, y);
            this.loadTexture("rooms/tilesets/kitchen_objects/oven_off.png");

            Array<TextureRegion> animation_array= new Array<TextureRegion>();
            animation_array.add(new TextureRegion(new Texture("rooms/tilesets/kitchen_objects/oven_on_1.png")));
            animation_array.add(new TextureRegion(new Texture("rooms/tilesets/kitchen_objects/oven_on_2.png")));
            animation_array.add(new TextureRegion(new Texture("rooms/tilesets/kitchen_objects/oven_on_3.png")));
            animation_array.add(new TextureRegion(new Texture("rooms/tilesets/kitchen_objects/oven_on_4.png")));
            On = new Animation(0.2f,animation_array,Animation.PlayMode.LOOP);

            animation_array.clear();
            animation_array.add(new TextureRegion(new Texture("rooms/tilesets/kitchen_objects/oven_off.png")));
            Off = new Animation(0.2f,animation_array,Animation.PlayMode.LOOP);

            isOn = false;
        }

        public void updateOvenOn() {
            //make interactable
            this.addAction( Actions.sequence(
                    Actions.scaleBy(0.2f, 0.2f, 0.1f),
                    Actions.scaleBy(-0.2f, -0.2f, 0.1f))
            );
            isOn = true;
        }
    public void updateOvenOff() {
        //make not interactable
        this.addAction( Actions.sequence(
                Actions.scaleBy(0.2f, 0.2f, 0.1f),
                Actions.scaleBy(-0.2f, -0.2f, 0.1f)
            ));
        isOn = false;
    }

    public void act(float dt){
        super.act(dt);
        if(!this.hasActions()) {
            if (isOn) {
                setAnimation(On);
            } else {
                setAnimation(Off);
            }
        }
    }

}
