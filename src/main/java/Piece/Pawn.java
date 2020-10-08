package Piece;

import board.*;

import java.util.ArrayList;
import java.util.Collection;

public final class Pawn extends Piece {

    @Override
    public Piece clone() {
        Pawn holder = new Pawn(PieceCoordinate, color, pieceMoved);
        return holder;
    }

    public Pawn(int PieceCooridnate, Color color, boolean pieceMoved) {
        super(PieceCooridnate, color, pieceMoved);
    }
    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }
    @Override
    public void setPieceCoordinate(int coord) {
        PieceCoordinate = coord;
    }
    @Override
    public Boolean isPieceMoved() {
        return pieceMoved;
    }

    @Override
    public Character returnLetter() {
        return color == Color.WHITE ? 'P' : 'p';
    }

    public static final int[] possibleMovements = {9,7, 8, 16};
    @Override
    public Collection<Moves> CalculateLegalMoves(Board board) {
        Collection<Moves> legalMoves= new ArrayList<>();
        for (int possibleMovement: possibleMovements) {
            int colorNumber =1;
            if (color != Color.BLACK){
                possibleMovement *= -1;
                colorNumber = -1;
            }
            int possibleMove = possibleMovement + PieceCoordinate;
            if(possibleMove <= -1 || possibleMove >= 64) {
                continue;
            }
            Square square = board.getSquare(possibleMove);
            if(Math.abs(possibleMovement) == 8) {
                if (!square.isOccupied()) {
                    if (whitePawnPromotion(PieceCoordinate, possibleMovement) || blackPawnPromotion(PieceCoordinate, possibleMovement)) {
                        legalMoves.add(new Moves.Promotion(board.getSquare(PieceCoordinate), possibleMove, new Queen(possibleMove, color, true)));
                        legalMoves.add(new Moves.Promotion(board.getSquare(PieceCoordinate), possibleMove, new Bishop(possibleMove, color, true)));
                        legalMoves.add(new Moves.Promotion(board.getSquare(PieceCoordinate), possibleMove, new Knight(possibleMove, color, true)));
                        legalMoves.add(new Moves.Promotion(board.getSquare(PieceCoordinate), possibleMove, new Rook(possibleMove, color, true)));
                    } else {
                        legalMoves.add(new Moves(board.getSquare(PieceCoordinate), possibleMove));
                    }
                }
                continue;
            }
            //JUMP over 2
            if(SecondRowException(PieceCoordinate, possibleMovement) || SeventhRowException(PieceCoordinate, possibleMovement) ) {
                if (!square.isOccupied() && !board.getSquare(colorNumber*8+PieceCoordinate).isOccupied()){
                    legalMoves.add(new Moves(board.getSquare(PieceCoordinate), possibleMove));
                }  else {
                    continue;
                }
            } else if (Math.abs(possibleMovement) == 16) {
                continue;
            }
            //idk should doc
            if(EigthColumnExceptions(PieceCoordinate, possibleMovement) ||
                    FirstColumnExceptions(PieceCoordinate, possibleMovement)) {
                continue;
            }
            if (square.isOccupied() && (this.color != square.getPiece().color) ) {
                if (whitePawnPromotion(PieceCoordinate, possibleMovement) || blackPawnPromotion(PieceCoordinate, possibleMovement)) {
                    legalMoves.add(new Moves.Promotion(board.getSquare(PieceCoordinate), possibleMove, new Queen(possibleMove, color, true)));
                    legalMoves.add(new Moves.Promotion(board.getSquare(PieceCoordinate), possibleMove, new Bishop(possibleMove, color, true)));
                    legalMoves.add(new Moves.Promotion(board.getSquare(PieceCoordinate), possibleMove, new Knight(possibleMove, color, true)));
                    legalMoves.add(new Moves.Promotion(board.getSquare(PieceCoordinate), possibleMove, new Rook(possibleMove, color, true)));
                } else {
                    legalMoves.add(new Moves(board.getSquare(PieceCoordinate), possibleMove));
                }
            }
        }
        return legalMoves;
    }
    public static boolean whitePawnPromotion(int coord, int direction) {
        if(Utilities.isSecondRow(coord) && (direction == -8 || direction == -7 || direction == -9))  {
            return true;
        }
        return false;
    }
    public static boolean blackPawnPromotion(int coord, int direction) {
        if(Utilities.isSeventhRow(coord) && (direction == 8 || direction == 7 || direction == 9)) {
            return true;
        }
        return false;
    }
    public static boolean SecondRowException(int coord, int direction) {
        if(Utilities.isSecondRow(coord) && (direction == 16)) {
            return true;
        }
        return false;
    }
    public static boolean SeventhRowException(int coord, int direction) {
        if(Utilities.isSeventhRow(coord) && (direction == -16)){
            return true;
        }
        return false;
    }
    public static boolean EigthColumnExceptions(int coord, int direction){
        if(Utilities.isEigthColumn(coord) && (direction == 9 || direction == -7)) {
            return true;
        }
        return false;
    }
    public static boolean FirstColumnExceptions(int coord, int direction) {
        if(Utilities.isFirstColumn(coord) && (direction == -9 || direction == 7)) {
            return true;
        } else {
            return false;
        }
    }
}
