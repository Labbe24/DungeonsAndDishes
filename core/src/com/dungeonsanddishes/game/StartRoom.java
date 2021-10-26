package com.dungeonsanddishes.game;

import Framework.TilemapActor;

public class StartRoom extends DungeonRoom{
    StartRoom(){
        super();
        map_layout=new TilemapActor("rooms/start_room.tmx");
    }
}
