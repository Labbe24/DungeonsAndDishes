package com.dungeonsanddishes.game;
class MainIngredientRoomImplementation{

}
class RiceRoom extends MainIngredientRoomImplementation{

}

public class MainIngredientRoom extends IngredientRoom{
    private int impl_count = 1;
    private MainIngredientRoomImplementation _room_impl;
    public MainIngredientRoom(int seed){
        switch (seed%impl_count){
            case 0:
                _room_impl = new RiceRoom();
            default:
                //throw
        }
    }
}