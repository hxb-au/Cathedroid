package au.id.hxb.cathedroid.mechanics.ai;

import com.badlogic.gdx.Gdx;

import java.util.EnumMap;

import au.id.hxb.cathedroid.mechanics.GameState;
import au.id.hxb.cathedroid.mechanics.Move;
import au.id.hxb.cathedroid.mechanics.Orientation;
import au.id.hxb.cathedroid.mechanics.Piece;
import au.id.hxb.cathedroid.mechanics.Player;
import au.id.hxb.cathedroid.mechanics.SquareState;
import au.id.hxb.cathedroid.screens.GameScreen;

/**
 * Created by Hayden on 3/05/2016.
 */
public class AIEngine {

    AIEvaluator evaluator;
    private final int BOARD_WIDTH = 10, BOARD_HEIGHT = 10;

    //select a move by some means, return the move where the gameScreen applies it to the UI and the main gameState
    public  AIEngine() {
        evaluator = new SimpleEval();
    }

    public Move selectMove(GameState gameState) {
        return selectMoveSimple(gameState);
    }

    private Move selectRandomMove(GameState gameState){
        Boolean moveOk;
        GameState testState = new GameState();
        int x = 0, y = 0;
        Orientation dir = null;
        Piece piece = null;
        Player player = gameState.whoseTurn();

        moveOk = false;
        while (!moveOk){
            testState.revert(gameState);
            x = (int)(Math.random()*9 + 0.5);
            y = (int)(Math.random()*9 + 0.5);
            dir = Orientation.values()[(int)(Math.random()*3 + 0.5)];
            piece = Piece.values()[(int)(Math.random()*(Piece.values().length-1) + 0.5)];
            moveOk = testState.attemptMove(piece,dir,x,y,player);
        }

        Gdx.app.log("AI Engine", "move chosen leads to board score: " + Float.toString(testState.AIevaluate(evaluator, player)));

        return new Move(piece, dir, x, y, player);
    }

    private Move selectMoveSimple(GameState gameState){
        GameState testState = new GameState();
        int x = 0, y = 0;
        //Orientation dir = null;
        Piece piece = null;
        Player player = gameState.whoseTurn();
        float bestMoveScore = -10000000, testMoveScore;
        Move testMove, bestMove = new Move();

        // for each piece
        for (Piece testPiece : Piece.values()) {
            // if that piece belongs to current player
            if (testPiece == Piece.CA || testPiece.getOwner() == player) {
                //check each orientation of that piece
                for (Orientation dir : Orientation.values()) {
                    //in every position
                    for (x = 0; x < BOARD_WIDTH; x++) {
                        for (y = 0; y < BOARD_HEIGHT; y++){
                            testState.revert(gameState);
                            testMove = new Move(testPiece, dir, x, y, player);
                            //if the move is legal, evaluate it
                            if (testState.attemptMove(testMove)){
                                testMoveScore = testState.AIevaluate(evaluator, player);
                                if (testMoveScore > bestMoveScore){
                                    bestMoveScore = testMoveScore;
                                    bestMove = testMove;
                                }
                            }
                        }
                    }
                }
            }
        }

        Gdx.app.log("AI Engine", "move chosen leads to board score: " + Float.toString(bestMoveScore));
        return bestMove;

    }


}

class SimpleEval implements AIEvaluator {
    @Override
    public float evaluate(SquareState[][] board, EnumMap<Piece, Boolean> pieceAvailable, Player nextPlayer, Player aiPlayer) {
        float lightPoints = 0, darkPoints = 0;

        for (SquareState[] line : board) {
            for (SquareState squareState : line) {
                switch (squareState) {
                    case EMPTY:
                        break;
                    case LIGHTCLAIM:
                    case LIGHTPIECE:
                    case LIGHTPIECE_ORIGIN:
                        lightPoints++;
                        break;
                    case DARKCLAIM:
                    case DARKPIECE:
                    case DARKPIECE_ORIGIN:
                        darkPoints++;
                        break;
                    default:
                        break;
                }
            }
        }

        switch (nextPlayer) {
            case LIGHT:
                return darkPoints - lightPoints + (float)(Math.random()*0.01f);
            case DARK:
                return lightPoints - darkPoints + (float)(Math.random()*0.01f);
            default:
                return 0;
        }
    }
}