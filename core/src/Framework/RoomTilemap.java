package Framework;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dungeonsanddishes.game.Door;
import com.dungeonsanddishes.game.DoorDirections;
import com.dungeonsanddishes.game.DoorEast;
import com.dungeonsanddishes.game.DoorNorth;
import com.dungeonsanddishes.game.DoorSouth;
import com.dungeonsanddishes.game.DoorWest;
import java.util.ArrayList;
import java.util.logging.Logger;

public class RoomTilemap extends TilemapActor implements IRoomRenderer{
    /**
     * Initialize Tilemap created with the Tiled Map Editor.
     *
     * @param filename
     */
    Stage mainStage;
    protected ArrayList<Door> _doors;
    public RoomTilemap(String filename) {
        super(filename);
        _doors = new ArrayList<Door>();
    }
    public void setRoom(Stage stage) {
        stage.addActor(this);
        for(Door door: _doors){

            Logger.getGlobal().info("Adding door at:"+door.getDirection().getString());
            stage.addActor(door);
        }
    }

    public void removeRoom(Stage stage) {
        for(Door door: _doors){
            door.remove();
        }
        this.remove();
    }

    @Override
    public void setDoors(ArrayList<DoorDirections> doors) {
        //for(door : doors)
        MapProperties door_props;
        for(DoorDirections door : doors){
            switch (door){
                case EAST:
                    //get east door
                     door_props=getRectangleList("door_spawn_east").get(0).getProperties();
                    _doors.add(new DoorEast((float)door_props.get("x"),(float)door_props.get("y")));
                    break;
                case WEST:
                    //get west door
                    door_props =getRectangleList("door_spawn_west").get(0).getProperties();
                    _doors.add(new DoorWest((float)door_props.get("x"),(float)door_props.get("y")));
                    break;
                case NORTH:
                    door_props =getRectangleList("door_spawn_north").get(0).getProperties();
                    _doors.add(new DoorNorth((float)door_props.get("x"),(float)door_props.get("y")));
                    //get north door
                    break;
                case SOUTH:
                    door_props =getRectangleList("door_spawn_south").get(0).getProperties();
                    _doors.add(new DoorSouth((float)door_props.get("x"),(float)door_props.get("y")));
                    //get south door
                    break;
                default:
                    break;
            }
        }
        for(Door door:_doors){
            door.remove();
        }
    }

    @Override
    public ArrayList<Door> getDoors() {
        return _doors;
    }

}
