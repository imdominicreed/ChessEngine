package engine;

import Piece.Color;
import Piece.Piece;
import Piece.PieceType;
import board.Board;
import board.Moves;
import board.Square;
import game.BoardGame;


import javax.print.DocFlavor;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

public class Engine {
    BoardGame game;
    int blackPosition;
    int whitePosition;
    int depth;
    Color color;
    boolean doneCalculating;
    HashMap<String, Integer> numberOfMoves;
    Node root;
    Node current;
    public Engine(BoardGame game, int depth, Color color) {
        this.color = color;
        this.game = game;
        this.depth = depth;
        numberOfMoves = new HashMap<>();
        root = new Node();
        current = root;
        doneCalculating = false;
    }
    public boolean createTree() throws IOException {

        FileReader fileReader = new FileReader("C:\\Users\\domin\\IdeaProjects\\ChessEngine\\src\\main\\java\\board\\openings.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while (true) {
            Node current = root;
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            String[] moves = line.split(" ");
            boolean counter = false;
            for (String move : moves){
                if (counter && color.toString().toLowerCase().equals(moves[0])) {
                    current.putIfAbsent(move, new Node());
                    current = current.get(move);
                }
                counter = true;
            }
        }
        return true;
    }
    class Node extends HashMap<String, Node>{
    }
    public void moveParser() throws IOException {
        FileReader fileReader = new FileReader("C:\\Users\\domin\\IdeaProjects\\ChessEngine\\src\\main\\java\\board\\games.csv");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        bufferedReader.readLine();
        String[] smith = new String[2];
        int counter = 0;
        int mistakes = 0;
        while (true) {
            counter++;
            String[] entireLine = bufferedReader.readLine().split(",");
            BoardGame bg = game.clone();
            boolean didMoves = true;
            String[] lines = entireLine[12].split(" ");
            smith = new String[lines.length];
            String answer = "";
            for (int j = 0; j < lines.length; j++) {
                if (!didMoves) {
                    mistakes++;
                    System.out.println(counter+ " " + mistakes + " " + lines[j-1]);
                    break;
                }
                if (lines[j].contains("+")) {
                    lines[j] = lines[j].substring(0, lines[j].indexOf('+'));
                }
                if (lines[j].contains("#")) {
                    lines[j] = lines[j].substring(0, lines[j].indexOf('#'));
                }
                didMoves = false;
                Collection<Moves> moves = bg.getPlayerTurn().getLegalMoves();
                for (Moves move : moves) {
                    boolean includeLetter = false;
                    if (lines[j].equals("exd3") && move.toString().equals("e4d3")) {
                        int poo = 3;
                    }
                    boolean includeNumber = false;
                    if ((lines[j].length() == 4 && !lines[j].contains("x")) || (lines[j].length() == 5 && lines[j].contains("x")) || (Character.isLowerCase(lines[j].charAt(0)) && lines[j].contains("x"))) {
                        if (Character.isDigit(lines[j].charAt(1))) {
                            includeNumber = true;
                        } else if(lines[j].length() == 6 && lines[j].contains("x")){
                            includeLetter = true;
                            includeNumber = true;
                        } else {
                            includeLetter = true;
                        }
                    }
                    String pgn = move.toPGN(includeLetter, includeNumber);
                    if (lines[j].equals(pgn)) {
                        answer += move.toString() + " ";
                        smith[j] = move.toString();
                        bg.doEngineMove(move);
                        didMoves = true;
                        break;
                    }
                }
            }
            File movetext = new File("C:\\Users\\domin\\IdeaProjects\\ChessEngine\\src\\main\\java\\board\\openings.txt");
            FileWriter fileWriter = new FileWriter(movetext, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(entireLine[6] + " " + answer);
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();

        }
    }
    public Moves checkTree(){
        if (!current.isEmpty() && !doneCalculating){
            int counter = 0;
            int random = (int) (Math.random() * current.size());
            for (String key: current.keySet()) {
                if (counter == random){
                    return game.moveParser(key, game.getPlayerTurn().getLegalMoves());
                }
                counter++;
            }
        }
        doneCalculating = true;
        return null;
    }
    public Moves calculateMove() {
        Moves move = checkTree();
        if (move != null) {
            return move;
        }
        return getBestMove();
    }
    public void doOppenentMove(String move) {
        if (current.containsKey(move)) {
            current = current.get(move);
        }
    }
    public Moves getBestMove() {
        doneCalculating = true;
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
            System.out.println(((float) counter / (float) moves.size()) * 100 + "%");
            BoardGame boardGame = game.clone();
            boardGame.doEngineMove(move);
            int position = getBestMoveLine(boardGame, 1);
            if (bestPosition < position) {
                System.out.println(position);
                bestMoves = move;
                bestPosition = position;
            } else if (bestPosition == position) {
                if (Math.random() > 0.5) {
                    bestMoves = move;
                }
            }
        }
        numberOfMoves.putIfAbsent(moves.toString(), 0);
        numberOfMoves.put(moves.toString(), numberOfMoves.get(moves.toString()) + 1);
        return bestMoves;
    }

    public int getBestMoveLine(BoardGame game, int depthMachine) {
        if (depth == depthMachine) {
            return calculatePosition(game.board);
        }
        int nextDepth = depthMachine + 1;
        int position = Integer.MAX_VALUE;
        Collection<Moves> moves = game.getPlayerTurn().getLegalMoves();
        if (moves.size() == 0) {
            if (game.colorTurn == color) {
                return -9000;
            } else{
                return 9000;
            }
        }
        //test
        for (Moves move : moves) {
            BoardGame bg = game.clone();
            try {
                bg.doEngineMove(move);
            } catch (NullPointerException noKING) {
                System.out.println("THIS CALCULATION DOESNT CONTAIN KING!");
                if (game.colorTurn == color) {
                    position += 900;
                } else {
                    position -= 900;
                }
            }
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
