package com.dungeonsanddishes.game;

import Framework.RoomRenderer;

public class DungeonRoom {
    public String roomName;
    public RoomRenderer map_layout;
    public DungeonRoom(){}

    public DungeonRoom(String name){
        roomName = name;
    }
}

