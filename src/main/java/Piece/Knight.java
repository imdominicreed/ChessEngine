package Piece;

import board.Moves;
import board.Square;
import board.Board;
import board.Utilities;
import java.util.ArrayList;
import java.util.Collection;

public class Knight extends Piece {


    public Knight(int PieceCooridnate, Color color, boolean pieceMoved) {
        super(PieceCooridnate, color, pieceMoved);
    }
    @Override
    public Piece clone() {
        Knight holder = new Knight(PieceCoordinate, color, pieceMoved);
        return holder;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }
    @Override
    public void setPieceCoordinate(int coord) {
        PieceCoordinate = coord;
    }
    @Override
    public Boolean isPieceMoved() {
        return null;
    }

    @Override
    public Character returnLetter() {
        return color == Color.WHITE ? 'N' : 'n';
    }

    public static final int[] possibleMovements = {17, 15, 10, 6, -6, -10, -15, -17};

    @Override
    public Collection<Moves> CalculateLegalMoves (Board board) {
        Collection<Moves> legalMoves = new ArrayList<Moves>();
        for (int possibleMovement : possibleMovements) {
            int possibleMove = getPieceCoordinate() + possibleMovement;
            if(FirstColumnExceptions(getPieceCoordinate(),possibleMovement) || SecondColumnExceptions(getPieceCoordinate(),possibleMovement)
                    || SeventhColumnExceptions(getPieceCoordinate(),possibleMovement) || EigthColumnExceptions(getPieceCoordinate(), possibleMovement)
                    || (possibleMove <= -1 || possibleMove >= 64)){
                continue;
            }

            Square moveSquare = board.getSquare(possibleMove);
            if (!moveSquare.isOccupied()) {
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
    public static boolean EigthColumnExceptions(int coord, int movement){
        if(Utilities.isEigthColumn(coord) && (movement == -6 || movement == 10 || movement == 17 || movement == -15)) {
            return true;
        }
        return false;
    }
    public static boolean SecondColumnExceptions(int coord, int movement){
        if(Utilities.isSecondColumn(coord) && (movement == 6 || movement == -10)) {
            return true;
        }
        return false;
    }
    public static boolean FirstColumnExceptions(int coord, int movement){
        if(Utilities.isFirstColumn(coord) && (movement == 6 || movement == -10 || movement == -17 || movement == 15) ) {
            return true;
        }
        return false;
   }
    public static boolean SeventhColumnExceptions(int coord, int movement){
        if(Utilities.isSeventhColumn(coord) && (movement == -6 || movement == 10)) {
            return true;
        }
        return false;
    }
}