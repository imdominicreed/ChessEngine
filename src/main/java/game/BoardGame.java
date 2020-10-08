package game;

import Piece.Color;
import Player.Player;
import board.Board;
import board.Moves;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class BoardGame {
    public Board board;
    public Color colorTurn;
    public Player whitePlayer;
    public Player  blackPlayer;

    public BoardGame() {
        board = new Board();
        whitePlayer = new Player(board, Color.WHITE);
        blackPlayer = new Player(board, Color.BLACK);
        colorTurn = Color.WHITE;
       /* while (!whitePlayer.isKingCheckmated() && !blackPlayer.isKingCheckmated()) {
            System.out.println(blackPlayer.isKingCheckmated());
            if (colorTurn == Color.WHITE) {
                chooseMove(whitePlayer, blackPlayer);
            } else {
                chooseMove(blackPlayer, blackPlayer);
            }
            colorTurn = colorTurn == Color.WHITE ? Color.BLACK : Color.WHITE;
        }
        System.out.println(!whitePlayer.isKingCheckmated());
        */
    }

    public BoardGame clone() {
        BoardGame bg = new BoardGame();
        bg.board = board.clone();
        bg.colorTurn = colorTurn;
        bg.whitePlayer = whitePlayer.clone(bg.board);
        bg.blackPlayer = blackPlayer.clone(bg.board);
        return  bg;
    }

    public void nextColor() {
        colorTurn = Color.WHITE == colorTurn ? Color.BLACK : Color.WHITE;
    }

    public void printGame() {
        System.out.println(board.toString());
    }

    public Moves moveParser(String line, Collection<Moves> moves) {
            for (Moves move : moves) {
                if (line.equals(move.toString()))
                    return move;
            }
        try{
            throw new IOException();
        } catch (IOException e) {
            System.out.println(line + " is not a legal MOVE!");
        }
        return null;
    }
    public void doEngineMove(Moves move) {
        board.doMove(move);
        nextColor();
    }
    public void doMove(String stringMove) {
        Moves move = moveParser(stringMove, getPlayerTurn().getLegalMoves());
        nextColor();
        System.out.println(move.toString());
        board.doMove(move);
        printGame();
    }

    public Player getPlayerTurn() {
        if (colorTurn == Color.WHITE) {
            return whitePlayer;
        }
        return blackPlayer;
    }
}

