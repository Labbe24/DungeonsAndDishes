package com.dungeonsanddishes.game;

import Framework.IRoomRenderer;

public abstract class DungeonRoom {
    public String roomName;
    public IRoomRenderer map_layout;
    public DungeonRoom(){}
    public abstract void update(float dt, Character character);
    public DungeonRoom(String name){
        roomName = name;
    }
}

