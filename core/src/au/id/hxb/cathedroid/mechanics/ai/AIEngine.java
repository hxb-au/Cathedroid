package au.id.hxb.cathedroid.mechanics.ai;

import java.util.EnumMap;

import au.id.hxb.cathedroid.mechanics.GameState;
import au.id.hxb.cathedroid.mechanics.Move;
import au.id.hxb.cathedroid.mechanics.Orientation;
import au.id.hxb.cathedroid.mechanics.Piece;
import au.id.hxb.cathedroid.mechanics.Player;
import au.id.hxb.cathedroid.mechanics.SquareState;

/**
 * Created by Hayden on 3/05/2016.
 */
public class AIEngine {

    //select a move by some means, return the move where the gameScreen applies it to the UI and the main gameState
    public  AIEngine() {
        AIEvaluator evaluator = new SimpleEval();
    }

    public Move selectMove(GameState gameState) {
        return makeRandomMove(gameState);
    }

    private Move makeRandomMove(GameState gameState){
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

        return new Move(piece, dir, x, y, player);
    }

}

class SimpleEval implements AIEvaluator {
    @Override
    public float evaluate(SquareState[][] board, EnumMap<Piece, Boolean> pieceAvailable, Player nextPlayer, Player aiPlayer) {
        int lightPoints = 0, darkPoints = 0;

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
                return darkPoints - lightPoints;
            case DARK:
                return lightPoints - darkPoints;
            default:
                return 0;
        }
    }
}