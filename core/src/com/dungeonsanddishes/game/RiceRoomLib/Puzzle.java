package com.dungeonsanddishes.game.RiceRoomLib;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import Framework.BaseActor;

public class Puzzle extends BaseActor {
    private PuzzlePiece pieces[][];
    public Puzzle(float x, float y) {
        super(x, y);
        this.loadTexture("RiceRoom/frame.png");
        int no_rows =3;
        int no_columns=3;
        Texture texture = new Texture("RiceRoom/temple.png");
        int image_width= texture.getWidth();
        int image_height=texture.getHeight();
        int piece_width=image_width/no_columns;
        int piece_height=image_height/no_rows;
        TextureRegion tmp[][]=TextureRegion.split(texture,piece_width,piece_height);
        pieces=new PuzzlePiece[no_rows][no_columns];
        for(int i=0;i<no_rows;i++){
            for(int j=0;j<no_columns;j++){
                //pieces[i][j]=new PuzzlePiece(this.getX()+(8*i)+(image_width*(i-1)),this.getY()+(8*j)+(image_height*(j-1)),tmp[i][j],(i+1)+(3*j));
                pieces[i][j]=new PuzzlePiece(this.getX()+(8*(i+1))+(piece_width*i),
                        this.getY()+8*(no_rows-j)+(piece_height*(no_rows-j-1)),tmp[j][i],(i+1)+(3*j));

            }
        }


    }

    public PuzzlePiece[][] getPieces() {
        return pieces;
    }
}
