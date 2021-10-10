package com.dungeonsanddishes.game;

public class DungeonRoomMeta {
    public boolean tunnelEnd;
    public DungeonRoom dungeonRoom;

    public DungeonRoomMeta(boolean end, DungeonRoom room){
        tunnelEnd = end;
        dungeonRoom = room;
    }
}

