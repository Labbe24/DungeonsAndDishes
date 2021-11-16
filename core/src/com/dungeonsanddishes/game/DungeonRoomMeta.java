package com.dungeonsanddishes.game;

public class DungeonRoomMeta {
    public boolean tunnelEnd;
    public DungeonRoom dungeonRoom;
    public DungeonRoomMeta[] neighbourRooms;
    public String roomName;


    public DungeonRoomMeta(boolean end, DungeonRoom room, DungeonRoomMeta[] neighbours, String name) {
        tunnelEnd = end;
        dungeonRoom = room;
        neighbourRooms = neighbours;
        roomName = name;
    }
}

