package board;

public class Moves {
    Square square;
    int attackingCoord;
    public Moves(Square square, int attackingCoord) {
        this.square = square;
        this.attackingCoord = attackingCoord;
    }
    public int getAttackingCoord() {
        return attackingCoord;
    }

    @Override
    public String toString() {
        return square.getPiece().color+ " " + square.getPiece().getPieceType() +" to " + attackingCoord ;
    }

    public Square getSquare() {
        return square;
    }
}
