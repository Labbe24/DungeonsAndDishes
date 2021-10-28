package com.dungeonsanddishes.game;

import Framework.IRoomRenderer;

public class DungeonRoom {
    public String roomName;
    public IRoomRenderer map_layout;
    public DungeonRoom(){}

    public DungeonRoom(String name){
        roomName = name;
    }
}

