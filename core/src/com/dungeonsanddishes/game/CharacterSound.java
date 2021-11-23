package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;



public class CharacterSound {

    private float volume;
    private Sound walk;
    private Sound hurt;
    private Sound hit;
    private Sound talk;
    private Pair[] dict;

    public CharacterSound() {
        // walk = Gdx.audio.newSound(Gdx.files.internal(""));
        // walkId = walk.play();
        hurt = Gdx.audio.newSound(Gdx.files.internal("sounds/notification.wav"));
        long hurtId = hurt.play();
        // hit = Gdx.audio.newSound(Gdx.files.internal("/sounds/hit-enemy.wav"))
        // hitId = hit.play();
        // talk = Gdx.audio.newSound(Gdx.files.internal("/sounds/character-talking.wav"));
        // talkId = talk.play();

        dict = new Pair[1];

        dict[0] = new Pair(hurtId, hurt);

    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float number) {
        for (Pair<Long, Sound> pair: dict) {
            pair.GetValue().setVolume(pair.GetKey(), number);
        }
    }

    public void Walk() {
        walk.play();
    }

    public void Hurt() {
        hurt.play();
    }

    public void Hit() {
        hit.play();
    }

    public void Talk() {
        talk.play();
    }

    public void dispose() {
        walk.dispose();
        hurt.dispose();
        hit.dispose();
        talk.dispose();
    }
}
