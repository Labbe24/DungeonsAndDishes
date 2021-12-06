package com.dungeonsanddishes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dungeonsanddishes.game.RiceRoomLib.Puzzle;
import com.dungeonsanddishes.game.RiceRoomLib.PuzzlePiece;

import java.util.logging.Level;
import java.util.logging.Logger;

import Framework.IngredientRoomTilemap;

abstract class MainIngredientRoomImplementation extends IngredientRoomImplementation{

    public abstract void update(float dt, Character character);
}
class RiceRoom extends MainIngredientRoomImplementation{

    private Puzzle puzzle;
    public RiceRoom(int seed){
        //super(seed);

        //Create puzzle picture
        puzzle= new Puzzle(640,200);
        //Shuffle
        puzzle.shuffle(seed);
    }

    @Override
    public void update(float dt, Character character) {
        //Check for completion
        boolean complete= puzzle.IsComplete();
        if(complete){
            character.incrementRice();
            Logger.getGlobal().log(Level.INFO,"Puzzle Complete!!");
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
    }

    @Override
    public void setRoom(Stage stage) {
        stage.addActor(puzzle);
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