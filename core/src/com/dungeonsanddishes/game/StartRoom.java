package com.dungeonsanddishes.game;

import Framework.RoomTilemap;

public class StartRoom extends DungeonRoom{
    StartRoom(){
        super();
        map_layout=new RoomTilemap("rooms/start_room.tmx");
    }

    @Override
    public void update(float dt, Character character) {

    }
}
