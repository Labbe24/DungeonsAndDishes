package com.dungeonsanddishes.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class DungeonMap {
    public DungeonRoomMeta[][] dungeon;
    public IDungeonGenerator dungeonGenerator;
    protected DungeonRoomMeta currentRoom;
    protected Stage mainStage;
    public DungeonMap(IDungeonGenerator dungeonGenerator, Stage mainStage){
        this.dungeonGenerator = dungeonGenerator;
        this.mainStage=mainStage;
    }

    public void createDungeon(){
        dungeon = dungeonGenerator.createDungeonMap(4, 3, 3, new String[]{"Room1", "Room2", "Room3", "Room4"});
        dungeon[9][9].dungeonRoom.map_layout.setRoom(mainStage);
        currentRoom=dungeon[9][9];
    }
    public DungeonRoomMeta getCurrentRoom(){
        return currentRoom;
    }
}
