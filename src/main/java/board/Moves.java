package board;

import Piece.Piece;
import Piece.PieceType;

public class Moves {
    Square square;
    int attackingCoord;
    boolean capture;

    public Moves(Square square, int attackingCoord, boolean capture) {
        this.square = square.clone();
        this.attackingCoord = attackingCoord;
        this.capture = capture;
    }

    public int getAttackingCoord() {
        return attackingCoord;
    }

    @Override
    public String toString() {
        char letter = (char) ((char) attackingCoord % 8 + 97);
        char squareLetter = (char) ((char) square.getPiece().getPieceCoordinate() % 8 + 97);
        return (squareLetter) + "" + (8 - (square.getPiece().getPieceCoordinate() / 8) % 8) + "" + (letter) + (8 - (attackingCoord / 8) % 8);
    }


    public boolean isPromotion() {
        return false;
    }

    public boolean isCastle() {
        return false;
    }

    public String toPGN(boolean includeLetter, boolean includeNumber) {
        char squareLetter = (char) ((char) attackingCoord % 8 + 97);
        char fromLetter = (char) ((char) square.getPiece().getPieceCoordinate() % 8 + 97);
        String squarelet = squareLetter + "" + (8 - (attackingCoord / 8) % 8);
        String answer = "";
        if (square.getPiece().getPieceType() == PieceType.PAWN) {
            if (capture) {
                answer += fromLetter + "x";
            }
            answer += squarelet;
            return answer;
        }
        answer += square.getPiece().returnLetter().toString().toUpperCase();
        if (includeLetter && !includeNumber) {
            answer += fromLetter;
        } else if (includeNumber && !includeLetter) {
            answer += (8 - (square.getPiece().getPieceCoordinate() / 8) % 8);
        } else if(includeLetter) {
            answer += fromLetter;
            answer +=  + (8 - (square.getPiece().getPieceCoordinate()/ 8) % 8);
        }
        if (capture) {
            answer += "x";
        }
        answer += squarelet;
        return answer;
    }

    public boolean isJump() {
        return false;
    }

    public boolean isPassant() {
        return false;
    }

    public Square getSquare() {
        return square;
    }

    public static class Promotion extends Moves {
        Piece piece;

        public Promotion(Square square, int attackingCoord, Piece piece, boolean capture) {
            super(square, attackingCoord, capture);
            this.piece = piece;
        }

        public boolean isJump() {
            return false;
        }

        public boolean isPromotion() {
            return true;
        }

        public Piece promotingPiece() {
            return piece;
        }
        public String toPGN(boolean includeLetter, boolean includeNumber) {
            char squareLetter = (char) ((char) attackingCoord % 8 + 97);
            char fromSquare =  (char) ((char) square.getPiece().getPieceCoordinate() % 8 + 97);
            String squarelet = squareLetter + "" + (8 - (attackingCoord / 8) % 8);
            if (!capture) {
                return squarelet + "=" + piece.returnLetter().toString().toUpperCase();
            } else {
                String x = squareLetter + "x" + squarelet + "=" + piece.returnLetter().toString().toUpperCase();
                return fromSquare + "x" + squarelet+ "="+ piece.returnLetter().toString().toUpperCase();
            }
        }
        @Override
        public String toString() {
            char letter = (char) (attackingCoord % 8 + 97);
            char squareLetter = (char) ((char) square.getPiece().getPieceCoordinate() % 8 + 97);
            return (squareLetter) + "" + (8 - (square.getPiece().getPieceCoordinate() / 8) % 8) + "" + (letter) + (8 - (attackingCoord / 8) % 8 + "" + (piece.returnLetter().toString().toLowerCase()));
        }

    }

    public static class Castling extends Moves {
        Piece rook;
        public int rookCoord;

        public Castling(Square square, int attackingCoord, Piece rook, int rookCoord) {
            super(square, attackingCoord, false);
            this.rook = rook;
            this.rookCoord = rookCoord;
        }

        public String toPGN(boolean includeLetter, boolean x) {
            if (rook.getPieceCoordinate() == 56 || rook.getPieceCoordinate() == 0) {
                return "O-O-O";
            }

            return "O-O";
        }

        public boolean isJump() {
            return false;
        }

        public boolean isCastle() {
            return true;
        }

        @Override
        public String toString() {
            char letter = (char) ((char) attackingCoord % 8 + 97);
            char squareLetter = (char) ((char) square.getPiece().getPieceCoordinate() % 8 + 97);
            return (squareLetter) + "" + (8 - (square.getPiece().getPieceCoordinate() / 8) % 8) + "" + (letter) + (8 - (attackingCoord / 8) % 8);
        }

        public boolean isPassant() {
            return false;
        }
    }

    public static class Jump extends Moves {

        public Jump(Square square, int attackingCoord) {
            super(square, attackingCoord, false);
        }


        public boolean isPassant() {
            return false;
        }

        public String toPGN(boolean includeLetter, boolean x) {
            char squareLetter = (char) ((char) attackingCoord % 8 + 97);
            String squarelet = squareLetter + "" + (8 - (attackingCoord / 8) % 8);
            return squarelet;
        }

        public int getAttackingCoord() {
            return attackingCoord;
        }

        @Override
        public String toString() {
            char letter = (char) ((char) attackingCoord % 8 + 97);
            char squareLetter = (char) ((char) square.getPiece().getPieceCoordinate() % 8 + 97);
            return (squareLetter) + "" + (8 - (square.getPiece().getPieceCoordinate() / 8) % 8) + "" + (letter) + (8 - (attackingCoord / 8) % 8);
        }

        public boolean isPromotion() {
            return false;
        }

        public boolean isCastle() {
            return false;
        }

        public boolean isJump() {
            return true;
        }

        public Square getSquare() {
            return square;
        }

    }

    public static class Passant extends Moves {
        public int pawn;

        public Passant(Square square, int attackingCoord, int pawn) {
            super(square, attackingCoord, false);
            this.pawn = pawn;
        }

        public int getAttackingCoord() {
            return attackingCoord;
        }

        @Override
        public String toString() {
            char letter = (char) ((char) attackingCoord % 8 + 97);
            char squareLetter = (char) ((char) square.getPiece().getPieceCoordinate() % 8 + 97);
            return (squareLetter) + "" + (8 - (square.getPiece().getPieceCoordinate() / 8) % 8) + "" + (letter) + (8 - (attackingCoord / 8) % 8);
        }
        public boolean isPromotion() {
            return false;
        }

        public boolean isCastle() {
            return false;
        }

        public String toPGN(boolean includeLetter, boolean x) {
            char squareLetter = (char) ((char) attackingCoord % 8 + 97);
            char fromLetter = (char) ((char) square.getPiece().getPieceCoordinate() % 8 + 97);
            String squarelet = squareLetter + "" + (8 - (attackingCoord / 8) % 8);
            return fromLetter + "x" + squarelet;
        }

        public boolean isJump() {
            return false;
        }

        public boolean isPassant() {
            return true;
        }
    }
}
