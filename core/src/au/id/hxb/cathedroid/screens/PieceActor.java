package au.id.hxb.cathedroid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Array;

import au.id.hxb.cathedroid.mechanics.GameState;
import au.id.hxb.cathedroid.mechanics.Orientation;
import au.id.hxb.cathedroid.mechanics.Piece;
import au.id.hxb.cathedroid.mechanics.Player;

/**
 * Created by Hayden on 23/03/2016.
 */
public class PieceActor extends Image {
    private Rectangle hitBox1, hitBox2, hitBox3;


    private final float referenceX, referenceY;
    private static final int BOARD_ORIGIN_X = 390, BOARD_ORIGIN_Y = 110;
    private static final int BOARD_WIDTH = 500, BOARD_HEIGHT = 500;
    private static final int SQUARE_MID = 25, SQUARE_SIZE = 50;
    private static final int XMAX = 9, YMAX = 9;
    static GameState gameState;
    static GameScreen gameScreen;
    private final Piece piece;

    public void setPlayer(Player player) {
        this.player = player;
    }

    private Player player;
    private boolean placed = false;

    public PieceActor(Texture texture, Piece piece,
                      Rectangle hitBox1, Rectangle hitBox2, Rectangle hitBox3,
                      float originX, float originY,
                      float referenceX, float referenceY) {

        super(texture);

        this.hitBox1 = hitBox1;
        this.hitBox2 = hitBox2;
        this.hitBox3 = hitBox3;

        this.setName(piece.getName());
        this.piece = piece;
        this.player = piece.getOwner();
        this.setOrigin(originX, originY);
        this.setTouchable(Touchable.enabled);
        this.referenceX = referenceX;
        this.referenceY = referenceY;

        this.addListener(new PieceGestureListener());

        this.reset();

    }

    static void setGameState(GameState gs)
    {
        gameState = gs;
    }

    static void setGameScreen(GameScreen gs)
    {
        gameScreen = gs;
    }

    // return the piece or just hide the cathedral
    public void capture()
    {
        if (this.piece == Piece.CA) {
            this.setVisible(false);
        }
        else {
            this.reset();
        }

    }

    //set the piece to playable and return to starting position
    public void reset(){
        this.setTouchable(Touchable.enabled);
        this.placed = false;

        //messy starting positions either side of board.
        //TODO clean this crap up and have set places
        if (player == Player.LIGHT)
            this.addAction(Actions.moveTo(MathUtils.random(340f - 150f), MathUtils.random(720f - 150f)));
        else
            this.addAction(Actions.moveTo(MathUtils.random(340f - 150f)+940f, MathUtils.random(720f - 150f)));
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {

        // check touchable and override
        if ( touchable && !this.isTouchable()) {
            return null;
        }

        // check actual collisions with up to 3 hitboxes
        if (hitBox1 != null)
            if (hitBox1.contains(x,y))
                return this;
        if (hitBox2 != null)
            if (hitBox2.contains(x,y))
                return this;
        if (hitBox3 != null)
            if (hitBox3.contains(x,y))
                return this;

        // nope out
        return null;
    }

    //check this piece's rotation and return an orientation
    //TODO if manual rotation happens, this will need an update
    private Orientation getOrientation(){
        Orientation orientation = Orientation.NORTH;
        float rotation = PieceActor.this.getRotation() % 360;
        if (rotation < 0)             //java modulo is crap
            rotation += 360;

        if      (rotation == 90)
            orientation = Orientation.WEST;
        else if (rotation == 180)
            orientation = Orientation.SOUTH;
        else if (rotation == 270)
            orientation = Orientation.EAST;

        return orientation;
    }

    //decrement rotation and bring the piece to the front of the others
    private void rotateCW(){
        this.setRotation(PieceActor.this.getRotation() - 90);
        this.toFront();
    }

    //increment rotation and bring the piece to the front of the others
    private void rotateCCW(){
        this.setRotation(PieceActor.this.getRotation() + 90);
        this.toFront();
    }

    //used to apply an accepted move before moving and locking the piece
    private void setOrientation(Orientation dir){
        switch(dir) {
            case NORTH:
                this.setRotation(0);
                break;
            case EAST:
                this.setRotation(270);
                break;
            case SOUTH:
                this.setRotation(180);
                break;
            case WEST:
                this.setRotation(90);
                break;
        }
    }

    // get board coordinates as floats. 1 square = 1.0
    // note y inversion
    private Vector2 getBoardCoordinates() {

        Vector2 stageVec = getReferenceInStageCoords();
        float boardX = (                (stageVec.x - BOARD_ORIGIN_X))  / SQUARE_SIZE;
        float boardY = ((BOARD_HEIGHT - (stageVec.y - BOARD_ORIGIN_Y))) / SQUARE_SIZE;

        return new Vector2(boardX,boardY);

    }

    private Vector2 getReferenceInStageCoords() {
        return localToStageCoordinates(new Vector2(referenceX,referenceY));
    }
    
    // gamescreen calls this if a move was valid or if the AI delivers a move
    public void placePiece(Orientation dir, int boardX, int boardY){
        Vector2 referenceVec2, stageVec2;

        //mark the piece as palced an disable UI events for it
        this.placed = true;
        this.setTouchable(Touchable.disabled);

        //TODO make this smooth
        this.setOrientation(dir);

        // snap location to board grid by converting board location back to idealised stage location
        //note y inversion
        float idealStageX = (      boardX  * SQUARE_SIZE) + BOARD_ORIGIN_X + SQUARE_MID;
        float idealStageY = ((YMAX-boardY) * SQUARE_SIZE) + BOARD_ORIGIN_Y + SQUARE_MID;

        // convert piece reference point to stage coordinates
        referenceVec2 = new Vector2(referenceX, referenceY);
        stageVec2 = PieceActor.this.localToStageCoordinates(referenceVec2);
        
        float currentStageX = stageVec2.x;
        float currentStageY = stageVec2.y;
        
        float deltaX = idealStageX - currentStageX;
        float deltaY = idealStageY - currentStageY;

        //adjust position
        //TODO make this smooth
        PieceActor.this.setPosition(PieceActor.this.getX() + deltaX, PieceActor.this.getY()+deltaY);

    }


    class PieceGestureListener extends ActorGestureListener {
        private Vector2 tmpInV2 = new Vector2(), tmpOutV2 = new Vector2();
        private Vector2 v2before, v2after;
        private float deltaTheta;

        //no luck with this yet
        @Override
        public void pinch(InputEvent event,
                          Vector2 initialPointer1, Vector2 initialPointer2,
                          Vector2 pointer1,        Vector2 pointer2) {

            v2before = initialPointer2.sub(initialPointer1);
            v2after = pointer2.sub(pointer1);
            deltaTheta = v2after.angle() - v2before.angle();

            if (deltaTheta > 10){
                PieceActor.this.rotateCCW();
            }
            if (deltaTheta < -10){
                PieceActor.this.rotateCW();
            }
        }

        // drag the piece around. jumps a little at the start though.
        @Override
        public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
            tmpInV2.x = x - PieceActor.this.getOriginX();
            tmpInV2.y = y - PieceActor.this.getOriginY();
            tmpInV2.rotate(PieceActor.this.getRotation());
            PieceActor.this.addAction(Actions.moveBy(tmpInV2.x, tmpInV2.y));
            PieceActor.this.toFront();
        }

        // tap to rotate piece
        @Override
        public void tap(InputEvent event, float x, float y, int count, int button) {
            PieceActor.this.rotateCW();
        }

        //long press to attempt a move
        @Override
        public boolean longPress(Actor actor, float x, float y) {
            Vector2 boardVec;
            int boardX, boardY;
            boolean piecePlaced;

            boardVec = getBoardCoordinates();

            boardX = (int)(boardVec.x);
            boardY = (int)(boardVec.y);

            //check it's on the board. Give up if not.
            if (boardX < 0 || boardX > XMAX)
                return true;
            if (boardY < 0|| boardY > YMAX)
                return true;

            //determine orientation
            Orientation orientation = PieceActor.this.getOrientation();

            //might use piecePlaced later
            piecePlaced = gameScreen.attemptMove(PieceActor.this, PieceActor.this.piece, orientation, boardX, boardY, player);

            return true;
        }
    }
}
