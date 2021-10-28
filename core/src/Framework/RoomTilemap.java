package Framework;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dungeonsanddishes.game.DoorDirections;

import java.util.ArrayList;

public class RoomTilemap extends TilemapActor implements IRoomRenderer{
    /**
     * Initialize Tilemap created with the Tiled Map Editor.
     *
     * @param filename
     */
    public RoomTilemap(String filename) {
        super(filename);
    }
    public void setRoom(Stage stage) {
        stage.addActor(this);
    }

    public void removeRoom(Stage stage) {
        this.remove();
    }

    @Override
    public void setDoors(ArrayList<DoorDirections> doors) {
        //for(door : doors)
        for(DoorDirections door : doors){
            switch (door){
                case EAST:
                    //get east door
                    break;
                case WEST:
                    //get west door
                    break;
                case NORTH:
                    //get north door
                    break;
                case SOUTH:
                    //get south door
                    break;
                default:
                    break;
            }
        }
    }

}
