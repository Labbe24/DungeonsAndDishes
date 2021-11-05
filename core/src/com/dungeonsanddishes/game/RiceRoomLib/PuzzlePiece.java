package com.dungeonsanddishes.game.RiceRoomLib;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


import Framework.BaseActor;

public class PuzzlePiece extends BaseActor {

    private int _no;
    public PuzzlePiece(float x, float y, TextureRegion pic,int no) {
        super(x, y);
        _no=no;
        //set animation
        this.setAnimation(new Animation<TextureRegion>(1,pic));
    }
    public int getNumber(){
        return _no;
    }
}
