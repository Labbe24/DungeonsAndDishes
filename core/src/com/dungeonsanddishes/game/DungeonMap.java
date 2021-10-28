package com.dungeonsanddishes.game;

public class DungeonMap {
    public DungeonRoomMeta[][] dungeon;
    public IDungeonGenerator dungeonGenerator;

    public DungeonMap(IDungeonGenerator dungeonGenerator){
        this.dungeonGenerator = dungeonGenerator;
    }

    public void createDungeon(){
        dungeon = dungeonGenerator.createDungeonMap(4, 3, 3, new String[]{"Room1", "Room2", "Room3"});
    }
}
