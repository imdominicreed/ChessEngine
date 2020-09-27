package Player;

import Piece.Color;
import Piece.King;
import board.Board;
import board.Moves;

import java.util.ArrayList;
import java.util.Collection;


public class Player {
    Board board;
    King playerKing;
    boolean isKingChecked;
    public Color oppositeColor;
    public Collection<Moves> legalMoves;
    Color color;
    public Player(Board board,King king, Color color) {
        this.board = board;
        this.playerKing =  king;
        if (color == color.WHITE)  {
            oppositeColor = Color.BLACK;
        }
        this.color = color;
    }
    public Collection<Moves> getMoves(){
        return board.getColorMoves(board, color);
    }
    public boolean isKingChecked() {
        for (Moves move: board.getColorMoves(board, oppositeColor)) {
            if (move.getAttackingCoord() == playerKing.getKingCoord()) {
                return true;
            }
            if(playerKing.getKingCoord() == move.getAttackingCoord()) {
                return true;
            }

        }
        return false;
    }
    public boolean isKingCheckmated() {
        Collection<Moves> escapeMoves = calculateEscapeMoves();
        if (isKingChecked && escapeMoves.size() == 0) {
            return true;
        }
        legalMoves.clear();
        legalMoves.addAll(escapeMoves);
        return false;
    }
    public Collection<Moves> calculateEscapeMoves() {
        Collection<Moves> escapeMoves = new ArrayList<>();
        Board tempBoard = board;
        for (Moves move: legalMoves) {
            if (move.getAttackingCoord() == playerKing.getKingCoord()) {
                continue;
            }
            escapeMoves.add(move);
        }
        return calculateEscapeMoves();
    }
}
