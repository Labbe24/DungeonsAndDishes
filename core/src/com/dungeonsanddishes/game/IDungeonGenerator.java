package com.dungeonsanddishes.game;

public interface IDungeonGenerator {
    public DungeonRoomMeta[][] createDungeonMap(int numberOfDungeons,
                                                int numberOfDungeonTunnels,
                                                int tunnelLength,
                                                String[] roomNames);
}
