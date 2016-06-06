package au.id.hxb.cathedroid.mechanics;

/**
 * Created by Hayden on 3/05/2016.
 */ // move class holds the record of moves made in a linked list. Useful for save/load and piece capture as well as AI processing
public class Move {
    public Piece piece;
    public Orientation orientation;
    public int x, y;
    public Move nextMove;
    public Player player;

    public Move(){
        this.piece = null;
        this.orientation = null;
        this.x = -1;
        this.y = -1;
        this.player = null;
        this.nextMove = null;

    }

    public Move(Piece piece, Orientation orientation, int x, int y, Player player) {
        this.piece = piece;
        this.orientation = orientation;
        this.x = x;
        this.y = y;
        this.player = player;
        this.nextMove = null;
    }

    @Override
    public String toString() {
        return player.toString() + ": " + piece.getCode() + orientation.toLetter() + Character.toString((char) (x + (int) 'A')) + Integer.toString(y + 1);
    }

    public int numMoves() {
        if (nextMove == null)
            return 1;
        else
            return nextMove.numMoves() + 1;
    }

    public void makeFinal() {
        if (nextMove == null)
            return;

        //dispose of other moves?
        nextMove = null;

    }
}
