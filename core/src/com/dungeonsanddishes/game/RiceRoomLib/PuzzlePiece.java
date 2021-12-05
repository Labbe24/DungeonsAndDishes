package com.dungeonsanddishes.game.RiceRoomLib;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


import Framework.BaseActor;

public class PuzzlePiece extends BaseActor {

    private int _no;
    private  int row;
    private  int col;
    public PuzzlePiece(float x, float y, TextureRegion pic,int no, int row, int col) {
        super(x, y);
        this.row=row;
        this.col=col;
        _no=no;
        //set animation
        this.setAnimation(new Animation<TextureRegion>(1,pic));
    }
    public int getNumber(){
        return _no;
    }

    public int getRow(){
        return row;
    }
    public int getColumn(){
        return col;
    }
    public void setRow(int row){
        this.row=row;
    }
    public void setColumn(int column){
        this.col=column;
    }
}
