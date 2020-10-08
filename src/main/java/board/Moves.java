package board;

import Piece.Piece;
import Piece.PieceType;

public class Moves {
    Square square;
    int attackingCoord;
    public Moves(Square square, int attackingCoord) {
        this.square = square.clone();
        this.attackingCoord = attackingCoord;
    }
    public int getAttackingCoord() {
        return attackingCoord;
    }
    @Override
    public String toString() {
        char letter = (char) ((char) attackingCoord %8 + 97);
        char squareLetter = (char) ((char) square.getPiece().getPieceCoordinate() %8 + 97);
        return (squareLetter) + ""+ (8-(square.getPiece().getPieceCoordinate()/8)%8)+""+ (letter) + (8-(attackingCoord/8)%8);
    }
    public boolean isPromotion() {
        return false;
    }
    public boolean isCastle() {
        return false;
    }

    public Square getSquare() {
        return square;
    }
    public static class Promotion extends Moves {
        Piece piece;
        public Promotion(Square square, int attackingCoord, Piece piece) {
            super(square, attackingCoord);
            this.piece = piece;
        }

        public boolean isPromotion() {
            return true;
        }
        public Piece promotingPiece() {
            return piece;
        }
        public boolean isCastle() {
            return false;
        }
        @Override
        public String toString() {
            char letter = (char) ((char) attackingCoord %8 + 97);
            char squareLetter = (char) ((char) square.getPiece().getPieceCoordinate() %8 + 97);
            return (squareLetter) + ""+ (8-(square.getPiece().getPieceCoordinate()/8)%8)+""+ (letter) + (8-(attackingCoord/8)%8 +"" + (piece.returnLetter().toString().toLowerCase()));
        }

    }
    public static class Castling extends Moves {
        Piece rook;
        public Castling(Square square, int attackingCoord, Piece rook) {
            super(square, attackingCoord);
            this.rook = rook;
        }
        public boolean isCastle() {
            return true;
        }
        @Override
        public String toString() {
            char letter = (char) ((char) attackingCoord %8 + 97);
            char squareLetter = (char) ((char) square.getPiece().getPieceCoordinate() %8 + 97);
            return (squareLetter) + "" + (8 - (square.getPiece().getPieceCoordinate() / 8) % 8) + "" + (letter) + (8 - (attackingCoord / 8) % 8);
        }
    }
}
