package board;

import Piece.Piece;

public abstract class Square {
    Square() {

    }
    public abstract boolean isOccupied();

    public abstract Piece getPiece();
    public static Square createSquare(Piece piece) {
        if(piece == null) {
            return new EmptySquare();
        }
        return new OccupiedSquare(piece);
    }

    public static final class EmptySquare extends Square {
        private EmptySquare(){

        }
        @Override
        public  boolean isOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    public static final class OccupiedSquare extends Square {
        Piece piece;
        private OccupiedSquare(Piece piece) {

            this.piece = piece;
        }
        public void setPiece(Piece setpiece) {
            this.piece = setpiece;
        }
        @Override
        public boolean isOccupied() {
            return true;
        }
        @Override
        public Piece getPiece() {
            return this.piece;
        }
    }
}


