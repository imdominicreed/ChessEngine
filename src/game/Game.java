package game;

import Piece.Color;
import Player.Player;
import board.Board;
import board.Moves;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Game {
    Board board;
    public Game() {
        board = new Board();
        Player whitePlayer = new Player(board, board.getKing(Color.WHITE), Color.WHITE);
        printGame();
        Collection<Moves> whiteMove = whitePlayer.getMoves();
        ArrayList<Moves> whitesMoves = new ArrayList<>(whiteMove);
        System.out.println("Choose you move");
        for (int i = 0; i < whitesMoves.size(); i++) {
            System.out.println(whitesMoves.get(i));
        }
        Scanner scanner = new Scanner(System.in);
        board.doMove(whitesMoves.get(scanner.nextInt()));
        printGame();
    }
    public void printGame(){
        System.out.println(board.toString());
    }

}
