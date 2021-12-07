package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dungeonsanddishes.game.RiceRoomLib.Puzzle;
import com.dungeonsanddishes.game.RiceRoomLib.PuzzlePiece;

import Framework.IngredientRoomTilemap;

abstract class MainIngredientRoomImplementation extends IngredientRoomImplementation{

    public abstract void update(float dt, Character character);
}
class RiceRoom extends MainIngredientRoomImplementation{
    private int mapX = 192, mapY = 128, mapWidth = 1536, mapHeight = 800;
    private Rice rice;
    private Puzzle puzzle;
    private boolean rice_eaten;
    public RiceRoom(int seed){
        //super(seed);
        rice_eaten=false;
        rice = new Rice((mapX+mapWidth)/2, (mapY+mapHeight)/2);
        rice.setVisible(false);
        //Create puzzle picture
        puzzle= new Puzzle(640,200);
        //Shuffle
        puzzle.shuffle(seed);
    }

    @Override
    public void update(float dt, Character character) {
        //Check for completion
        boolean complete= puzzle.IsComplete();
        if(complete&&!rice_eaten&&!rice.isVisible()) {
            rice.setVisible(true);
        }
        if(complete&&character.overlaps(rice)&&!rice_eaten) {
            rice.setVisible(false);
            rice.remove();
            character.incrementRice();
            rice_eaten=true;
        }

        //Check for interactions
        boolean swapped = false;
        if(Gdx.input.isKeyJustPressed(Input.Keys.E)&&(!complete)){
            for(PuzzlePiece[] piece_arr : puzzle.getPieces()){
                if(swapped){
                    break;
                }
                for(PuzzlePiece piece : piece_arr){
                    if(character.overlaps(piece)){
                        if(puzzle.swap(piece,puzzle.getEmptyPiece())){
                            swapped = true;
                            break;
                        }
                    }
                }
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
            rice.setVisible(false);
            rice.remove();
            character.incrementRice();
            rice_eaten=true;
        }
    }

    @Override
    public void setRoom(Stage stage) {
        stage.addActor(puzzle);
        stage.addActor(rice);
        for(PuzzlePiece[] piece_arr : puzzle.getPieces()){
            for(PuzzlePiece piece : piece_arr){
                stage.addActor(piece);
            }

        }
    }

    @Override
    public void removeRoom(Stage stage) {
        puzzle.remove();
        for(PuzzlePiece[] piece_arr : puzzle.getPieces()){
            for(PuzzlePiece piece : piece_arr){
                piece.remove();
            }

        }
    }
}

public class MainIngredientRoom extends IngredientRoom{
    private static final int impl_count = 1;
    public MainIngredientRoom(int seed){
        switch (seed%impl_count){
            case 0:
                _room_impl = new RiceRoom(seed);
                map_layout = new IngredientRoomTilemap("rooms/start_room.tmx",_room_impl);
            default:
                //throw
        }
    }

}