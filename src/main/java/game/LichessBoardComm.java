package game;

import Piece.Color;
import Piece.PieceType;
import au.com.anthonybruno.lichessclient.LichessClient;
import au.com.anthonybruno.lichessclient.handler.GameEventHandler;
import au.com.anthonybruno.lichessclient.handler.UserEventHandler;
import au.com.anthonybruno.lichessclient.model.event.GameStart;
import com.fasterxml.jackson.databind.node.ObjectNode;
import engine.Engine;

import java.util.ArrayList;
import java.util.HashMap;

public class LichessBoardComm extends Thread {
    Engine engine;
    BoardGame clientGame;
    static LichessClient lichessClient;
    eventHandler incoming;
    gameEvent game;
    String id;
    LichessBoardComm() {
        game = new gameEvent();
        incoming = new eventHandler();
        lichessClient = new LichessClient("18G9F64gkzkUZBKQ");
        clientGame = new BoardGame();
    }

    public static void main(String[] args) {
        System.out.println(PieceType.KING.getValue());
        while (true) {
            LichessBoardComm api = new LichessBoardComm();
            lichessClient.streamIncomingEvents(api.incoming);
            if (api.incoming.startedGames.size() != 0) {
                api.id = api.incoming.startedGames.get(0).getId();
            } else {
                api.id = api.incoming.getID();
                lichessClient.acceptChallenge(api.id);
            }
            System.out.println("Game Found: " + api.id);
            lichessClient.streamGameState(api.id, api.game);
        }
    }

    class gameEvent implements GameEventHandler {
        boolean gameInBoard;
        String lastMove;

        gameEvent() {
            gameInBoard = false;
        }

        public boolean isGameInBoard() {
            return gameInBoard;
        }

        @Override
        public void chatReceived(ObjectNode chat) {

        }

        @Override
        public void fullGameState(ObjectNode gameState) {
            //    try {
            String[] moveList = gameState.get("state").get("moves").asText().trim().split(" ");
            if (gameState.get("white").get("id").asText().equals("dominicreedbot")) {
                engine = new Engine(clientGame, 4, Color.WHITE);
            } else  {
                engine = new Engine(clientGame, 4, Color.BLACK);
            }
            if (!moveList[0].equals("")) {
                for (String s : moveList) {
                    clientGame.doMove(s);
                    System.out.println(s);
                }
                lastMove = engine.calculateMove().toString();
                System.out.println(lastMove);
                clientGame.doMove(lastMove);
                lichessClient.makeMove(id, lastMove);
                gameInBoard = true;
            } else if (gameState.get("white").get("id").asText().equals("dominicreedbot")) {
                lastMove = engine.calculateMove().toString();
                System.out.println(lastMove);
                clientGame.doMove(lastMove);
                lichessClient.makeMove(id, lastMove);
                gameInBoard = true;
            }
        }

        @Override
        public void gameStateUpdate(ObjectNode gameState) {
            String[] moveList = gameState.get("moves").asText().split(" ");
            if (!moveList[moveList.length - 1].equals(lastMove)) {
                try {
                    clientGame.doMove(moveList[moveList.length - 1]);
                    lastMove = engine.calculateMove().toString();
                    clientGame.doMove(lastMove);
                    lichessClient.makeMove(id, lastMove);
                } catch (Error e) {
                    lichessClient.httpClient.exit = true;
                    return;
                }
            }
            if (!gameState.get("status").asText().equals("started")) {

                lichessClient.httpClient.exit = true;
            }
        }
    }
}

class eventHandler implements UserEventHandler {
    ArrayList<ObjectNode> gameIDs;
    ArrayList<GameStart> startedGames;

    eventHandler() {
        gameIDs = new ArrayList<>();
        startedGames = new ArrayList<>();
    }

    @Override
    public void incomingChallenge(ObjectNode challengeEvent) {
        gameIDs.add(challengeEvent);
    }

    public String getID() {
        return gameIDs.get(0).get("challenge").get("id").asText();
    }

    @Override
    public void gameStart(GameStart gameStartEvent) {
        startedGames.add(gameStartEvent);
    }
}


