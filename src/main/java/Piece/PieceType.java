package Piece;

public enum PieceType {
    KNIGHT(30),
    BISHOP(30),
    KING(900),
    PAWN(1),
    QUEEN(90),
    ROOK(50);
    int value;
    PieceType(int value) {
        this.value = value;
    }
    public int getValue(){
        return value;
    }

}
