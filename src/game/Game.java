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
        Player whitePlayer = new Player(board, Color.WHITE);
        Player blackPlayer = new Player(board, Color.BLACK);
        Color colorTurn = Color.WHITE;
        while (!whitePlayer.isKingCheckmated() || blackPlayer.isKingCheckmated()) {
            if (colorTurn == Color.WHITE) {
                chooseMove(whitePlayer, blackPlayer);
            } else {
                chooseMove(blackPlayer, blackPlayer);
            }
            colorTurn = colorTurn == Color.WHITE ? Color.BLACK : Color.WHITE;
        }

    }

    public void printGame() {
        System.out.println(board.toString());
    }

    public void chooseMove(Player player, Player otherPlayer) {
        printGame();
        ArrayList<Moves> whitesMoves = new ArrayList<>(player.getLegalMoves(otherPlayer));
        System.out.println("Choose you move");
        for (int i = 0; i < whitesMoves.size(); i++) {
            System.out.println(i + " " + whitesMoves.get(i));
        }
        Scanner scanner = new Scanner(System.in);
        board.doMove(whitesMoves.get(scanner.nextInt()));
        printGame();
    }
}
