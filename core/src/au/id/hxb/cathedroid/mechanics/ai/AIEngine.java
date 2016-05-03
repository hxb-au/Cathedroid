package au.id.hxb.cathedroid.mechanics.ai;

import au.id.hxb.cathedroid.mechanics.GameState;
import au.id.hxb.cathedroid.mechanics.Move;
import au.id.hxb.cathedroid.mechanics.Orientation;
import au.id.hxb.cathedroid.mechanics.Piece;
import au.id.hxb.cathedroid.mechanics.Player;

/**
 * Created by Hayden on 3/05/2016.
 */
public class AIEngine {

    public Move makeMove(GameState gameState) {
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
            if (piece == Piece.CA || piece.getOwner()==player)
                moveOk = testState.attemptMove(piece,dir,x,y,player);
        }

        gameState.attemptMove(piece,dir,x,y,player);
        return new Move(piece, dir, x, y, player);
    }

}