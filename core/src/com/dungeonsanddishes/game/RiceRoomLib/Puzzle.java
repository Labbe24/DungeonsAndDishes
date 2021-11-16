package com.dungeonsanddishes.game.RiceRoomLib;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;


import Framework.BaseActor;

public class Puzzle extends BaseActor {
    private int no_rows =4;
    private int no_columns=4;
    private boolean is_complete=false;
    private BaseActor picture;
    private PuzzlePiece pieces[][];
    public Puzzle(float x, float y) {
        super(x, y);
        this.loadTexture("RiceRoom/frame_4.png");
        picture = new BaseActor(20,20);
        picture.loadTexture("RiceRoom/temple.png");
        picture.addAction(Actions.fadeOut(0.0f));
        picture.scaleBy((600f/576f)-1);

        this.addActor(picture);

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
                        this.getY()+8*(no_rows-j)+(piece_height*(no_rows-j-1)),tmp[j][i],(i+1)+(3*j),i,j);

            }
        }
        getEmptyPiece().setOpacity(0);


    }
    public PuzzlePiece getEmptyPiece(){
        return pieces[no_rows-1][no_columns-1];
    }
    private boolean isAdjacent(PuzzlePiece p1, PuzzlePiece p2){
        //same row, adjacent
        if(p1.getRow()==p2.getRow() && ((p1.getColumn()-p2.getColumn()==1) || (p1.getColumn()-p2.getColumn()==-1))){
            return true;
        }
        // same column, adjacent
        else if(p1.getColumn()==p2.getColumn() && ((p1.getRow()-p2.getRow()==1) || (p1.getRow()-p2.getRow()==-1))){
            return true;
        }
        return false;
    }
    private PuzzlePiece getRandomAdjacent(PuzzlePiece p,int seed){
        int row = p.getRow();
        int column = p.getColumn();
        PuzzlePiece piece=null;
        int i =0;
        while (piece==null){
            switch ((seed+i)%4){
                case 0:
                    if(pieces.length>row+1)
                        piece=pieces[row+1][column];
                    break;
                case 1:
                    if(row>0)
                        piece=pieces[row-1][column];
                    break;
                case 2:
                    if(pieces[row].length>column+1)
                        piece=pieces[row][column+1];
                    break;
                case 3:
                    if(column>0)
                        piece=pieces[row][column-1];
                    break;
                default:
                    break;

            }
            i++;
        }
        return piece;
    }
    public boolean swap(PuzzlePiece p1,PuzzlePiece p2){
        if(isAdjacent(p1,p2)){
            float tmpX=p1.getX();
            float tmpY=p1.getY();
            int tmpCol=p1.getColumn();
            int tmpRow=p1.getRow();
            p1.setX(p2.getX());
            p1.setY(p2.getY());
            p1.setRow(p2.getRow());
            p1.setColumn(p2.getColumn());
            p2.setX(tmpX);
            p2.setY(tmpY);
            p2.setRow(tmpRow);
            p2.setColumn(tmpCol);
            return true;
        }
        return false;
    }

    public PuzzlePiece[][] getPieces() {
        return pieces;
    }

    public boolean IsComplete(){
        if(!is_complete)
        {
            for(int i = 0 ;i<pieces.length;i++){
                for(int j =0;j<pieces[0].length;j++){
                    if(pieces[i][j].getColumn()!=j||pieces[i][j].getRow()!=i){
                        //not complete
                        return false;
                    }
                }
            }
            for(int i = 0 ;i<pieces.length;i++){
                for(int j =0;j<pieces[0].length;j++){
                    pieces[i][j].setOpacity(0);
                }
            }
            picture.addAction(Actions.fadeIn(0.8f));

        }
        return true;
    }

    public void shuffle(int seed) {
        for(int i =0; i <(seed % 30 + 50);i++){
            swap(getRandomAdjacent(getEmptyPiece(),seed+i/4),getEmptyPiece());
        }

    }
}
