package Piece;

import board.Board;
import board.Moves;
import board.Square;
import board.Utilities;

import java.util.ArrayList;
import java.util.Collection;

public class King extends Piece{


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
        return null;
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
            if(FirstColumnExceptions(getPieceCoordinate(),possibleMove) || SecondColumnExceptions(getPieceCoordinate(),possibleMove)
                    || possibleMove <= -1 || possibleMove >= 65){
                continue;
            }
            Square moveSquare = board.getSquare(possibleMove);
            if (!moveSquare.isOccupied()) {
                for (int i = 0; i < 64; i++) {

                }
                //TODO NO CLUE WHAT I AM DOING
                legalMoves.add(new Moves(board.getSquare(PieceCoordinate), possibleMove));
            } else {
                if(moveSquare.getPiece().color != this.color) {
                    //TODO THIS IS A CAPUTUE IMPROVE
                    legalMoves.add(new Moves(board.getSquare(PieceCoordinate), possibleMove));
                }
            }

        }
        return legalMoves;
    }
    public int getKingCoord() {
        return PieceCoordinate;
    }
    public static boolean SecondColumnExceptions(int coord, int movement){
        if(Utilities.isSecondColumn(coord) && (movement == 6 || movement == -10 || movement == -17 || movement == 15)) {
            return true;
        }
        return false;
    }
    public static boolean FirstColumnExceptions(int coord, int movement){
        if(Utilities.isFirstColumn(coord) && (movement == 6 || movement == -10)) {
            return true;
        }
        return false;
    }
}
