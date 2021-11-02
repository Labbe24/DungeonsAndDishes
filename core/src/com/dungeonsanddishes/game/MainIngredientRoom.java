package com.dungeonsanddishes.game;
abstract class MainIngredientRoomImplementation extends IngredientRoomImplementation{

    public abstract void update(float dt, Character character);
}
class RiceRoom extends MainIngredientRoomImplementation{

    @Override
    public void update(float dt, Character character) {

    }
}

public class MainIngredientRoom extends IngredientRoom{
    private static final int impl_count = 1;
    public MainIngredientRoom(int seed){
        switch (seed%impl_count){
            case 0:
                _room_impl = new RiceRoom();
            default:
                //throw
        }
    }

}