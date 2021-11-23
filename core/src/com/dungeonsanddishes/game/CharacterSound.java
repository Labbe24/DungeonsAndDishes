package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;



public class CharacterSound {

    private float volume;
    private Music walk;
    private Music hurt;
    private Music hit;
    private Sound talk;
    private Music[] musics;

    public CharacterSound() {
        // walk = Gdx.audio.newSound(Gdx.files.internal(""));
        // walkId = walk.play();
        hurt = Gdx.audio.newMusic(Gdx.files.internal("sounds/mission-complete.wav"));
        // hit = Gdx.audio.newSound(Gdx.files.internal("/sounds/hit-enemy.wav"))
        // hitId = hit.play();
        // talk = Gdx.audio.newSound(Gdx.files.internal("/sounds/character-talking.wav"));
        // talkId = talk.play();
        musics = new Music[1];

        musics[0] = hurt;

    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float number) {
        for (Music music: musics) {
            music.setVolume(number);
        }
    }

    public void Walk() {
        walk.play();
    }

    public void Hurt() {
        Play(hurt);
    }

    public void Hit() {
        hit.play();
    }

    public void Talk() {
        talk.play();
    }

    private void Play(Music music) {
        if (!music.isPlaying()) {
            music.play();
        }
    }

    public void dispose() {
        walk.dispose();
        hurt.dispose();
        hit.dispose();
        talk.dispose();
    }
}
