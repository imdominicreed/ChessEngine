package Piece;

import board.Board;
import board.Moves;
import board.Square;
import board.Utilities;
import com.fasterxml.jackson.databind.util.ISO8601Utils;

import java.util.ArrayList;
import java.util.Collection;

public class King extends Piece{

    @Override
    public Piece clone() {
        King holder = new King(PieceCoordinate, color, pieceMoved);
        return holder;
    }

    public King(int PieceCooridnate, Color color, boolean pieceMoved) {
        super(PieceCooridnate, color, pieceMoved);
    }
    @Override
    public void setPieceCoordinate(int coord) {
        PieceCoordinate = coord;
    }
    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    public Boolean isPieceMoved() {
        return pieceMoved;
    }

    @Override
    public Character returnLetter() {
        return color == Color.WHITE ? 'K' : 'k';
    }

    public static final int[] possibleMovements = {-1,-7, -8, -9, 1, 7, 8, 9};
    @Override

    public Collection<Moves> CalculateLegalMoves (Board board) {
        Collection<Moves> legalMoves = new ArrayList<>();
        for (int possibleMovement : possibleMovements) {
            int possibleMove = getPieceCoordinate() + possibleMovement;
            if(FirstColumnExceptions(getPieceCoordinate(),possibleMovement) || eightColumnExceptions(getPieceCoordinate(),possibleMovement)
                    || possibleMove <= -1 || possibleMove >= 64){
                continue;
            }
            Square moveSquare = board.getSquare(possibleMove);
            if (!moveSquare.isOccupied()) {
                //TODO NO CLUE WHAT I AM DOING
                legalMoves.add(new Moves(board.getSquare(PieceCoordinate), possibleMove,false));
            } else {
                if(moveSquare.getPiece().color != this.color) {
                    //TODO THIS IS A CAPUTUE IMPROVE
                    legalMoves.add(new Moves(board.getSquare(PieceCoordinate), possibleMove, true));
                }
            }

        }

        return legalMoves;
    }
    public int getKingCoord() {
        return PieceCoordinate;
    }
    public static boolean eightColumnExceptions(int coord, int movement){
        if(Utilities.isEigthColumn(coord) && (movement == -7 || movement == 1 || movement == 9)) {
            return true;
        }
        return false;
    }
    public static boolean FirstColumnExceptions(int coord, int movement){
        if(Utilities.isFirstColumn(coord) && (movement == 7 || movement == -1 || movement == -9)) {
            return true;
        }
        return false;
    }

}
