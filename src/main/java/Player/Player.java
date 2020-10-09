package Player;

import Piece.Color;
import Piece.King;
import Piece.Rook;
import board.Board;
import board.CloneBoard;
import board.Moves;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class Player {
    public Board board;
    public Color oppositeColor;
    Color color;

    public Player clone(Board newboard) {
        return new Player(newboard, color);
    }

    public Player(Board board, Color color) {

        this.board = board;
        if (color == color.WHITE) {
            oppositeColor = Color.BLACK;
        } else {
            oppositeColor = Color.WHITE;
        }
        this.color = color;
    }

    public Collection<Moves> getMoves() {
        Board tempBoard = board.clone();
        return tempBoard.getColorMoves(color);
    }

    public Collection<Moves> getCastling() {
        King king = board.getKing(this.color);
        Collection<Moves> moves = new ArrayList<>();
        if (king.isPieceMoved()) {
            return moves;
        }
        if (color == Color.WHITE) {
            if (board.getSquare(56).isOccupied()) {
                try {
                    Rook rook = (Rook) board.getSquare(56).getPiece();
                    if (isSquareNotCheckedOccupied(58) && isSquareNotCheckedOccupied(59) && !rook.isPieceMoved()) {
                        rook = (Rook) rook.clone();
                        moves.add(new Moves.Castling(board.getSquare(60).clone(), 58, rook, 59));
                    }
                } catch (ClassCastException ignored) {
                }
            }
            if (board.getSquare(63).isOccupied()) {
                //TODO: THROW CATCH
                try {
                    Rook rook = (Rook) board.getSquare(63).getPiece();
                    if (isSquareNotCheckedOccupied(62) && isSquareNotCheckedOccupied(61) && !rook.isPieceMoved()) {
                        rook = (Rook) rook.clone();
                        moves.add(new Moves.Castling(board.getSquare(60).clone(), 62, rook, 61));

                    }
                } catch (ClassCastException ignored) {
                }
            }
        } else {

            if (board.getSquare(0).isOccupied()) {
                try {
                    Rook rook = (Rook) board.getSquare(0).getPiece();
                    if (isSquareNotCheckedOccupied(3) && isSquareNotCheckedOccupied(2) && !rook.isPieceMoved()) {
                        rook = (Rook) rook.clone();
                        moves.add(new Moves.Castling(board.getSquare(4).clone(), 2, rook, 3));
                    }
                } catch (ClassCastException ignored) {
                    ;
                }
            }
            if (board.getSquare(7).isOccupied()) {
                try {
                    Rook rook = (Rook) board.getSquare(7).getPiece();
                    if (isSquareNotCheckedOccupied(5) && isSquareNotCheckedOccupied(6) && !rook.isPieceMoved()) {
                        rook = (Rook) rook.clone();
                        moves.add(new Moves.Castling(board.getSquare(4).clone(), 6, rook, 5));
                    }
                } catch (ClassCastException ignored) {
                }

            }
        }

        return moves;
    }

    public boolean isSquareNotCheckedOccupied(int coord) {
        Boolean squareChecked = !isSquareChecked(coord, board);
        Boolean isOccupied = board.getSquare(coord).isOccupied();
        return !isSquareChecked(coord, board) && !board.getSquare(coord).isOccupied();

    }

    public Collection<Moves> getLegalMoves() {
        if (isKingChecked(board)) {
            return calculateEscapeMoves();
        }
        Collection<Moves> possibleMoves = board.getColorMoves(color);
        Iterator<Moves> iterator = possibleMoves.iterator();
        Collection<Moves> toRemove = new ArrayList<>();
        while (iterator.hasNext()) {
            Moves move = iterator.next();
            Board tempBoard = board.clone();
            tempBoard.doMove(move);
            if (isKingChecked(tempBoard)) {
                toRemove.add(move);
            }
        }
        possibleMoves.removeAll(toRemove);
        possibleMoves.addAll(getCastling());
        return possibleMoves;

    }

    public boolean isSquareChecked(int coord, Board board) {
        for (Moves move : board.getColorMoves(oppositeColor)) {
            if (move.getAttackingCoord() == coord) {
                return true;
            }
        }
        return false;
    }

    public boolean isKingChecked(Board board) {
        try {
            King king = board.getKing(this.color);
            return isSquareChecked(king.getKingCoord(), board);
        } catch (NullPointerException e){
            System.out.println("ERROR");
            return false;
        }
    }


    public Collection<Moves> calculateEscapeMoves() {
        Collection<Moves> escapeMoves = new ArrayList<>();
        Collection<Moves> currentLegalMoves = getMoves();
        for (Moves move : currentLegalMoves) {
            Board tempBoard = board.clone();
            tempBoard.doMove(move);
            if (!isKingChecked(tempBoard)) {
                escapeMoves.add(move);
            }
        }
        return escapeMoves;
    }
}
