package Framework;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dungeonsanddishes.game.DoorDirections;

import java.util.ArrayList;

public interface IRoomRenderer {
    /**
     *
     * @param stage room is added to this stage.
     */
    public void setRoom(Stage stage);

    /**
     *
     * @param stage removes room from this stage. May also remove room from other stages if any exist.
     */
    public void removeRoom(Stage stage);

    /**
     *
     * @param doors list of doors to set in the room
     */
    public void setDoors(ArrayList<DoorDirections> doors);


}
