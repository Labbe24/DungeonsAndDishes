package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;



public class CharacterSound {

    private float volume;
    private Music walk;
    private Music hurt;
    private Music hit;
    private Music talk;
    private Music[] all;

    public CharacterSound() {

        walk = Gdx.audio.newMusic(Gdx.files.internal("sounds/walk.ogg"));
        hurt = Gdx.audio.newMusic(Gdx.files.internal("sounds/hit-by-enemy.ogg"));
        hit = Gdx.audio.newMusic(Gdx.files.internal("sounds/mission-complete.wav"));
        talk = Gdx.audio.newMusic(Gdx.files.internal("sounds/mission-complete.wav"));
        hurt = Gdx.audio.newMusic(Gdx.files.internal("sounds/mission-complete.wav"));

        all = new Music[4];

        all[0] = walk;
        all[1] = hurt;
        all[2] = hit;
        all[3] = talk;

        init();
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float number) {
        for (Music music: all) {
            music.setVolume(number);
        }
    }

    public void StartWalk() { Play(walk); }

    public void StopWalk() { Stop(walk); }

    public void StartHurt() {
        Play(hurt);
    }

    public void StopHurt() { Stop(hurt); }

    public void Hit() {
        Play(hit);
    }

    public void Talk() {
        Play(talk);
    }

    private void Play(Music music) {
        if (!music.isPlaying()) {
            music.play();
        }
    }

    private void Stop(Music music) {
        if (music.isPlaying()) {
            music.stop();
        }
    }

    public void dispose() {
        walk.dispose();
        hurt.dispose();
        hit.dispose();
        talk.dispose();
    }

    private void init() {
        setVolume(0.8f);
    }
}
