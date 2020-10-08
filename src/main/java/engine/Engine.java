package engine;

import Piece.Color;
import board.Board;
import board.Moves;
import game.BoardGame;

import javax.print.DocFlavor;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Engine {
    BoardGame game;
    int blackPosition;
    int whitePosition;
    int depth;
    Color color;
    HashMap<String, Integer> numberOfMoves;
    public Engine(BoardGame game, int depth, Color color) {
        this.color = color;
        this.game = game;
        this.depth = depth;
        numberOfMoves = new HashMap<>();
    }

    public Moves calculateMove() {
        return getBestMove();
    }

    public Moves getBestMove() {
        int bestPosition = -Integer.MAX_VALUE;
        Moves bestMoves = null;
        Collection<Moves> moves = game.getPlayerTurn().getLegalMoves();
        int counter = 0;
        for (Moves move : moves) {
            if (numberOfMoves.containsKey(moves.toString())) {
                System.out.println(move.toString() + " " + numberOfMoves.get(move.toString()));
                if (numberOfMoves.get(moves.toString()) == 3) {
                    continue;
                }
            }
            counter++;
            System.out.println( ((float)counter / (float)moves.size())*100 + "%");
            BoardGame boardGame = game.clone();
            boardGame.doEngineMove(move);
            int position = getBestMoveLine(boardGame, 1);
            if (bestPosition < position) {
                System.out.println(position);
                bestMoves = move;
                bestPosition = position;
            } else if(bestPosition == position) {
                if (Math.random() > 0.5) {
                    bestMoves = move;
                }
            }
        }
        numberOfMoves.putIfAbsent(moves.toString(), 0);
        numberOfMoves.put(moves.toString(), numberOfMoves.get(moves.toString())+1);
        return bestMoves;
    }

    public int getBestMoveLine(BoardGame game, int depthMachine) {
        if (depth == depthMachine) {
            return calculatePosition(game.board);
        }
        int nextDepth = depthMachine + 1;
        int position = Integer.MAX_VALUE;
        Collection<Moves> moves = game.getPlayerTurn().getLegalMoves();
        for (Moves move : moves) {
            BoardGame bg = game.clone();
            bg.doEngineMove(move);
            //SAME COLOR
            if (game.colorTurn == color) {
                if (position == Integer.MAX_VALUE) {
                    position = getBestMoveLine(bg, nextDepth);
                } else {
                    position = Math.max(getBestMoveLine(bg, nextDepth), position);
                }
            } else {
                if (position == Integer.MAX_VALUE) {
                    position = getBestMoveLine(bg, nextDepth);
                } else {
                    position = Math.min(getBestMoveLine(bg, nextDepth), position);
                    if (position == -1) {
                        position = -1;
                    }
                }
            }
        }
        return position;
    }

    public int calculatePosition(Board board) {
        blackPosition = 0;
        whitePosition = 0;
        for (int i = 0; i < 64; i++) {

            if (board.getSquare(i).isOccupied()) {
                if (board.getSquare(i).getPiece().color == Color.WHITE) {
                    int num = board.getSquare(i).getPiece().getPieceType().getValue();
                    whitePosition += board.getSquare(i).getPiece().getPieceType().getValue();
                } else {
                    int num = board.getSquare(i).getPiece().getPieceType().getValue();
                    blackPosition += board.getSquare(i).getPiece().getPieceType().getValue();
                }
            }
        }
        if (color == Color.BLACK) {
            return blackPosition - whitePosition;
        } else {
            return whitePosition - blackPosition;
        }
    }
}
