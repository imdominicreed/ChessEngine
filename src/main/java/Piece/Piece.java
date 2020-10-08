package Piece;

import board.Board;
import board.Moves;

import java.util.Collection;

public  abstract class Piece {
    public int PieceCoordinate;
    public Color color;
    public boolean pieceMoved;
    Piece(int PieceCoordinate, Color color, boolean pieceMoved) {
        this.PieceCoordinate = PieceCoordinate;
        this.color = color;
        this.pieceMoved = pieceMoved;
    }
    public abstract Piece clone();
    public abstract void setPieceCoordinate(int coord);
    public abstract PieceType getPieceType();
    public abstract Boolean isPieceMoved();
    public abstract Character returnLetter();
    public abstract Collection<Moves> CalculateLegalMoves (Board board);
    public int getPieceCoordinate(){
        return PieceCoordinate;
    }


}
