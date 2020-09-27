package Piece;

import board.Board;
import board.Moves;
import board.Square;
import board.Utilities;

import java.util.ArrayList;
import java.util.Collection;

public class Queen extends Piece{
    int[] POSSIBLE_DIRECTIONS = {-7,-9,7,9,-1,1,-8,8};

    public Queen(int PieceCooridnate, Color color, boolean pieceMoved) {
        super(PieceCooridnate, color, pieceMoved);
    }
    @Override
    public void setPieceCoordinate(int coord) {
        PieceCoordinate = coord;
    }
    @Override
    public PieceType getPieceType() {
        return PieceType.QUEEN;
    }

    @Override
    public Boolean isPieceMoved() {
        return null;
    }

    @Override
    public Character returnLetter() {
        return color == Color.WHITE ? 'Q' : 'q';
    }

    @Override
    //TODO   REVIEW FOR BUGS OR ANYTHING MISSING
    public Collection<Moves> CalculateLegalMoves(Board board) {
        Collection<Moves> legalMoves= new ArrayList<>();
        for (int direction : POSSIBLE_DIRECTIONS) {
            int possibleMove = getPieceCoordinate() + direction;
            while(possibleMove <= 65 && possibleMove >= 0 ) {
                Square square = board.getSquare(possibleMove);
                if(EigthColumnExceptions(possibleMove, direction) || FirstColumnExceptions(possibleMove, direction)) {
                    break;
                }
                if(square.isOccupied()) {
                    if(square.getPiece().color == this.color) {
                        break;
                    } else {
                        legalMoves.add(new Moves(board.getSquare(PieceCoordinate), possibleMove));
                    }
                } else {
                    legalMoves.add(new Moves(board.getSquare(PieceCoordinate), possibleMove));
                }
                possibleMove = possibleMove + direction;
            }
        }
        return legalMoves;
    }
    public static boolean EigthColumnExceptions(int coord, int direction){
        if(Utilities.isEigthColumn(coord) && (direction ==-7 || direction == 9 || direction == 8)) {
            return true;
        }
        return false;
    }
    public static boolean FirstColumnExceptions(int coord, int direction) {
        if (Utilities.isFirstColumn(coord) && (direction == 7 || direction == -9 || direction == -8)) {
            return true;
        } else {
            return false;
        }
    }
}
