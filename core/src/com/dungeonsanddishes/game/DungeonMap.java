package com.dungeonsanddishes.game;

import Framework.BaseGame;

public class DungeonMap {
    public DungeonRoomMeta[][] dungeon;
    public IDungeonGenerator dungeonGenerator;
    protected DungeonRoomMeta currentRoom;
    public DungeonMap(IDungeonGenerator dungeonGenerator){
        this.dungeonGenerator = dungeonGenerator;
    }

    public void createDungeon(){
        dungeon = dungeonGenerator.createDungeonMap(4, 3, 3, new String[]{"Room1", "Room2", "Room3", "Room4"});
        dungeon[9][9].dungeonRoom.map_layout.setRoom(BaseGame.getActiveScreen().getMainStage());
        currentRoom=dungeon[9][9];
    }
    public DungeonRoomMeta getCurrentRoom(){
        return currentRoom;
    }
}
