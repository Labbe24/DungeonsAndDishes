package com.dungeonsanddishes.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.logging.Logger;

import Framework.RoomTilemap;

public class DungeonMap {
    public DungeonRoomMeta[][] dungeon;
    private int x;
    private int y;
    public IDungeonGenerator dungeonGenerator;
    protected DungeonRoomMeta currentRoom;
    protected Stage mainStage;
    public DungeonMap(IDungeonGenerator dungeonGenerator, Stage mainStage){
        this.dungeonGenerator = dungeonGenerator;
        this.mainStage=mainStage;
    }
    private float x_n,y_n,x_s,y_s,x_w,y_w,x_e,y_e;

    public void createDungeon(){
        x=9;y=9;
        dungeon = dungeonGenerator.createDungeonMap(4, 3, 1, new String[]{"Room1", "Room2", "Room3", "Room4"});
        dungeon[9][9].dungeonRoom.map_layout.setRoom(mainStage);
        currentRoom=dungeon[9][9];
        x_n=(float)((RoomTilemap)currentRoom.dungeonRoom.map_layout).getRectangleList("door_spawn_north").get(0).getProperties().get("x");
        y_n=(float)((RoomTilemap)currentRoom.dungeonRoom.map_layout).getRectangleList("door_spawn_north").get(0).getProperties().get("y");
        x_s=(float)((RoomTilemap)currentRoom.dungeonRoom.map_layout).getRectangleList("door_spawn_south").get(0).getProperties().get("x");
        y_s=(float)((RoomTilemap)currentRoom.dungeonRoom.map_layout).getRectangleList("door_spawn_south").get(0).getProperties().get("y");
        x_w=(float)((RoomTilemap)currentRoom.dungeonRoom.map_layout).getRectangleList("door_spawn_west").get(0).getProperties().get("x");
        y_w=(float)((RoomTilemap)currentRoom.dungeonRoom.map_layout).getRectangleList("door_spawn_west").get(0).getProperties().get("y");
        x_e=(float)((RoomTilemap)currentRoom.dungeonRoom.map_layout).getRectangleList("door_spawn_east").get(0).getProperties().get("x");
        y_e=(float)((RoomTilemap)currentRoom.dungeonRoom.map_layout).getRectangleList("door_spawn_east").get(0).getProperties().get("y");
    }
    public DungeonRoomMeta getCurrentRoom(){
        return currentRoom;
    }

    public void doorEntered(DoorDirections direction,Character character) {
        DungeonRoomMeta next_room;
        switch (direction){
            case SOUTH:
                next_room=dungeon[x][--y];
                character.centerAtPosition(x_n,y_n);
                break;
            case NORTH:
                next_room=dungeon[x][++y];
                character.centerAtPosition(x_s,y_s);
                break;
            case WEST:
                next_room=dungeon[--x][y];
                character.centerAtPosition(x_e,y_e);
                break;
            case EAST:
                next_room=dungeon[++x][y];
                character.centerAtPosition(x_w,y_w);
                break;
            default:
                next_room=currentRoom;
                break;
        }
        Logger.getGlobal().info("Entering room: "+x+","+y);
        currentRoom.dungeonRoom.map_layout.removeRoom(mainStage);
        currentRoom=next_room;
        currentRoom.dungeonRoom.map_layout.setRoom(mainStage);
        character.toFront();


    }
}
