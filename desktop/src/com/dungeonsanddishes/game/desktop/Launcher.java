package com.dungeonsanddishes.game.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.dungeonsanddishes.game.CustomGame;

public class Launcher
{
    public static void main (String[] args)
    {
        Game dungeonsAndDishes = new CustomGame();
        LwjglApplication launcher = new LwjglApplication( dungeonsAndDishes, "Dungeons & Dishes", 800, 600 );
    }
}