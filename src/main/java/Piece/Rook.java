package Piece;

import board.Board;
import board.Moves;
import board.Square;
import board.Utilities;

import java.util.ArrayList;
import java.util.Collection;

public class Rook extends Piece {

    @Override
    public Piece clone() {
        Rook holder = new Rook(PieceCoordinate, color, pieceMoved);
        return holder;
    }
    public Rook(int PieceCooridnate, Color color, boolean pieceMoved) {
        super(PieceCooridnate, color, pieceMoved);
    }
    @Override
    public void setPieceCoordinate(int coord) {
        PieceCoordinate = coord;
    }
    @Override
    public PieceType getPieceType() {
        return PieceType.ROOK;
    }
    @Override
    public Boolean isPieceMoved() {
        return pieceMoved;
    }

    @Override
    public Character returnLetter() {
        return color == Color.WHITE ? 'R' :'r';
    }


    int[] POSSIBLE_DIRECTIONS = {-1,1,-8,8};
    @Override
    //TODO   REVIEW FOR BUGS OR ANYTHING MISSING
    public Collection<Moves> CalculateLegalMoves(Board board) {
        Collection<Moves> legalMoves= new ArrayList<Moves>();
        for (int direction : POSSIBLE_DIRECTIONS) {
            int possibleMove = getPieceCoordinate() + direction;
            while(possibleMove <= 63 && possibleMove >= 0 ) {
                Square square = board.getSquare(possibleMove);
                if(EigthColumnExceptions(possibleMove, direction) || FirstColumnExceptions(possibleMove, direction)) {
                    break;
                }
                if(square.isOccupied()) {
                    if (square.getPiece().color != this.color) {
                        legalMoves.add(new Moves(board.getSquare(PieceCoordinate), possibleMove,true));
                    }
                    break;
                } else {
                    legalMoves.add(new Moves(board.getSquare(PieceCoordinate), possibleMove,false));
                }
                possibleMove = possibleMove + direction;
            }
        }
        return legalMoves;
    }
    public static boolean EigthColumnExceptions(int coord, int direction){
        if(Utilities.isEigthColumn(coord) && (direction ==-1)) {
            return true;
        }
        return false;
    }
    public static boolean FirstColumnExceptions(int coord, int direction) {
        if(Utilities.isFirstColumn(coord) && direction == 1) {
            return true;
        } else {
            return false;
        }
}
}
