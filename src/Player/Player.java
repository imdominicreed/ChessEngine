package Player;

import Piece.Color;
import Piece.King;
import board.Board;
import board.CloneBoard;
import board.Moves;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class Player {
    Board board;

    King playerKing;
    boolean isKingChecked;
    public Color oppositeColor;
    public Collection<Moves> legalMoves;
    Color color;
    CloneBoard copyBoard;

    public Player(Board board, Color color) {

        this.board = board;
        copyBoard = new CloneBoard(board);
        this.playerKing = board.getKing(color);
        if (color == color.WHITE) {
            oppositeColor = Color.BLACK;
        }
        this.color = color;
        legalMoves = getMoves();
    }
    public Collection<Moves> getMoves() {
        return board.getColorMoves(board, color);
    }
    public Collection<Moves> getLegalMoves(Player otherPlayer) {
        Collection<Moves> possibleMoves = board.getColorMoves(board, color);
        Iterator<Moves> iterator = possibleMoves.iterator();
      while(iterator.hasNext()) {
            Moves move = iterator.next();
            Board tempBoard = copyBoard.getBoard();
            tempBoard.doMove(move);
            if(isKingChecked())  {
                possibleMoves.remove(move);
            }
        }
        return possibleMoves;

    }

    public boolean isKingChecked() {
        for (Moves move : board.getColorMoves(board, oppositeColor)) {
            if (move.getAttackingCoord() == playerKing.getKingCoord()) {
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
        for (Moves move : legalMoves) {
            if (move.getAttackingCoord() == playerKing.getKingCoord()) {
                continue;
            }
            escapeMoves.add(move);
        }
        return escapeMoves;
    }
}
