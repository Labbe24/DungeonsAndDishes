package com.dungeonsanddishes.game;

public enum DoorDirections {
    EAST("East"), SOUTH("South"), WEST("West"), NORTH("North");
    DoorDirections(String string){str=string;}
    private String str;
    public String getString(){
        return str;
    }
}
