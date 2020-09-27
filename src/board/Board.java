package board;

import Piece.*;

import java.util.ArrayList;
import java.util.Collection;

import static board.Square.createSquare;

public class Board {
    public Square[] board;

    public Board() {
        this.board = newBoardBuilder();

    }

    public King getKing(Color color) {
        for (int i = 0; i < board.length; i++) {
            if (board[i].isOccupied()) {
                if (board[i].getPiece().color == color && board[i].getPiece().getPieceType() == PieceType.KING) {
                    return (King) board[i].getPiece();
                }
            }
        }
        //TODO: THROW NO KING ERROR
        return null;
    }

    public void doMove(Moves move) {
        Piece piece = move.square.getPiece();
        piece.setPieceCoordinate(move.getAttackingCoord());
        piece.pieceMoved = true;
        board[move.attackingCoord] = Square.createSquare(piece);
        board[piece.PieceCoordinate] = createSquare(null);
    }

    public Square getSquare(int squareCoordinate) {
        return board[squareCoordinate];
    }

    public Collection<Moves> getColorMoves(Board board, Color color) {
        Collection<Moves> allLegalMoves = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            if (board.getSquare(i).isOccupied()) {
                if (board.getSquare(i).getPiece().color == color) {
                    allLegalMoves.addAll(board.getSquare(i).getPiece().CalculateLegalMoves(board));
                }
            }
        }
        return allLegalMoves;
    }

    @Override
    public String toString() {
        String string = "";
        for (int j = 0; j < 64; j++) {

            if (board[j].isOccupied()) {
                string += board[j].getPiece().returnLetter() + " ";
            } else {
                string += "- ";
            }
            if ((j + 1) % 8 == 0) {
                string += "\n";
            }
        }
        return string;
    }

    public Square[] newBoardBuilder() {
        Square[] board = new Square[64];
        board[0] = createSquare(new Rook(0, Color.BLACK, false));
        board[1] = createSquare(new Knight(1, Color.BLACK, false));
        board[2] = createSquare(new Bishop(2, Color.BLACK, false));
        board[3] = createSquare(new Queen(3, Color.BLACK, false));
        board[4] = createSquare(new King(4, Color.BLACK, false));
        board[5] = createSquare(new Bishop(5, Color.BLACK, false));
        board[6] = createSquare(new Knight(6, Color.BLACK, false));
        board[7] = createSquare(new Rook(7, Color.BLACK, false));
        for (int i = 8; i < 16; i++) {
            board[i] = createSquare(new Pawn(i, Color.BLACK, false));
        }
        for (int i = 16; i < 48; i++) {
            board[i] = createSquare(null);
        }

        for (int i = 48; i < 56; i++) {
            board[i] = createSquare(new Pawn(i, Color.WHITE, false));
        }
        board[56] = createSquare(new Rook(56, Color.WHITE, false));
        board[57] = createSquare(new Knight(57, Color.WHITE, false));
        board[58] = createSquare(new Bishop(58, Color.WHITE, false));
        board[59] = createSquare(new Queen(59, Color.WHITE, false));
        board[60] = createSquare(new King(60, Color.WHITE, false));
        board[61] = createSquare(new Bishop(61, Color.WHITE, false));
        board[62] = createSquare(new Knight(62, Color.WHITE, false));
        board[63] = createSquare(new Rook(63, Color.WHITE, false));
        return board;
    }
}

