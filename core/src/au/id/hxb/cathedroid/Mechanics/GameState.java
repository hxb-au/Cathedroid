package au.id.hxb.cathedroid.Mechanics;

/**
 * Created by hxb on 4/04/2016.
 */
public class GameState {


    enum SquareState {
        EMPTY,
        DARKPIECE,
        LIGHTPIECE,
        DARKCLAIM,
        LIGHTCLAIM
    }

    class Move {
        public Piece piece;
        public Orientation orientation;
        public int x,y;
        public Move nextMove;
        public Player player;

        public int numMoves(){
            if (nextMove == null)
                return 1;
            else
                return nextMove.numMoves() + 1;
        }

    }
}
