package Framework;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dungeonsanddishes.game.IngredientRoomImplementation;

public class IngredientRoomTilemap extends RoomTilemap{
    IngredientRoomImplementation impl_;
    public IngredientRoomTilemap(String filename, IngredientRoomImplementation impl) {
        super(filename);
        impl_=impl;
    }

    @Override
    public void setRoom(Stage stage) {
        super.setRoom(stage);
        impl_.setRoom(stage);
    }
    @Override
    public void removeRoom(Stage stage) {
        super.removeRoom(stage);
        impl_.removeRoom(stage);
    }
}
