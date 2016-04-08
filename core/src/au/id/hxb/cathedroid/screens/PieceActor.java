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

import au.id.hxb.cathedroid.Mechanics.GameState;
import au.id.hxb.cathedroid.Mechanics.Orientation;
import au.id.hxb.cathedroid.Mechanics.Piece;
import au.id.hxb.cathedroid.Mechanics.Player;

/**
 * Created by Hayden on 23/03/2016.
 */
public class PieceActor extends Image {
    private Rectangle hitBox1, hitBox2, hitBox3;

    private float deltaTheta;
    private final float referenceX, referenceY;
    private static final int BOARD_ORIGIN_X = 390, BOARD_ORIGIN_Y = 110;
    private static final int BOARD_WIDTH = 500, BOARD_HEIGHT = 500;
    private static final int SQUARE_MID = 25;
    static GameState gameState;
    private final Piece piece;
    private final Player player;

    public PieceActor(Texture texture, Piece piece, Player player,
                      Rectangle hitBox1, Rectangle hitBox2, Rectangle hitBox3,
                      float originX, float originY,
                      float referenceX, float referenceY) {

        super(texture);

        this.hitBox1 = hitBox1;
        this.hitBox2 = hitBox2;
        this.hitBox3 = hitBox3;

        this.setName(piece.getName());
        this.piece = piece;
        this.player = player;
        this.setOrigin(originX, originY);
        this.setTouchable(Touchable.enabled);
        this.referenceX = referenceX;
        this.referenceY = referenceY;

        this.addListener(new PieceGestureListener());
        if (player == Player.LIGHT)
            this.addAction(Actions.moveTo(MathUtils.random(340f - 150f), MathUtils.random(720f - 150f)));
        else
            this.addAction(Actions.moveTo(MathUtils.random(340f - 150f)+940f, MathUtils.random(720f - 150f)));

    }

    static void setGameState(GameState gs)
    {
        gameState = gs;
    }

    void capture()
    {
        if (this.piece == Piece.CA) {
            this.setVisible(false);
        }
        else {
            this.setTouchable(Touchable.enabled);

            if (player == Player.LIGHT)
                this.addAction(Actions.moveTo(MathUtils.random(340f - 150f), MathUtils.random(720f - 150f)));
            else
                this.addAction(Actions.moveTo(MathUtils.random(340f - 150f) + 940f, MathUtils.random(720f - 150f)));
        }

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


    class PieceGestureListener extends ActorGestureListener {
        private Vector2 tmpInV2 = new Vector2(), tmpOutV2 = new Vector2();
        private Vector2 v2before, v2after;

        @Override
        public void pinch(InputEvent event,
                          Vector2 initialPointer1, Vector2 initialPointer2,
                          Vector2 pointer1,        Vector2 pointer2) {

            v2before = initialPointer2.sub(initialPointer1);
            v2after = pointer2.sub(pointer1);
            deltaTheta = v2after.angle() - v2before.angle();

            if (deltaTheta > 10){
                PieceActor.this.setRotation(PieceActor.this.getRotation() + 90);

            }
            if (deltaTheta < -10){
                PieceActor.this.setRotation(PieceActor.this.getRotation() - 90);

            }
        }

        @Override
        public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
            tmpInV2.x = x - PieceActor.this.getOriginX();
            tmpInV2.y = y - PieceActor.this.getOriginY();
            tmpInV2.rotate(PieceActor.this.getRotation());
            PieceActor.this.addAction(Actions.moveBy(tmpInV2.x, tmpInV2.y));
            PieceActor.this.toFront();
        }

        @Override
        public void tap(InputEvent event, float x, float y, int count, int button) {
            PieceActor.this.setRotation(PieceActor.this.getRotation() - 90);
            PieceActor.this.toFront();
        }

        @Override
        public boolean longPress(Actor actor, float x, float y) {
            float stageX, stageY;
            float idealStageX, idealStageY;
            float deltaX, deltaY;
            int boardX, boardY;
            boolean piecePlaced;
            Piece capturedPiece;

            // convert piece reference point to screen coordinates
            tmpInV2.x = referenceX;
            tmpInV2.y = referenceY;
            tmpOutV2 = PieceActor.this.localToStageCoordinates(tmpInV2);
            stageX = tmpOutV2.x;
            stageY = tmpOutV2.y;
            //Gdx.app.log("LongPress Stage Coords:", tmpOutV2.toString());

            //check it's on the board. Give up if not.
            if (stageX < BOARD_ORIGIN_X || stageX > BOARD_ORIGIN_X + BOARD_WIDTH)
                return true;
            if (stageY < BOARD_ORIGIN_Y || stageY > BOARD_ORIGIN_Y + BOARD_HEIGHT)
                return true;

            // convert stage coordinate to board coordinates - note y inversion
            boardX =     ((int)(stageX - BOARD_ORIGIN_X)) / 50;
            boardY = 9 - ((int)(stageY - BOARD_ORIGIN_Y)) / 50;


            //determine orientation
            Orientation orientation = Orientation.NORTH;
            float rotation = PieceActor.this.getRotation() % 360;

            //java modulo is crap
            if (rotation < 0)
                rotation += 360;

            if      (rotation == 90)
                orientation = Orientation.WEST;
            else if (rotation == 180)
                orientation = Orientation.SOUTH;
            else if (rotation == 270)
                orientation = Orientation.EAST;
            //Gdx.app.log("Orientation:",Float.toString(rotation));


            piecePlaced = gameState.attemptMove(piece, orientation, boardX, boardY, player);
            //log it?
            //Gdx.app.log("Piece placed:",piece.toString() + " " + orientation.toString() + " "+ Integer.toString(boardX+1) + ", " + Character.toString((char)(boardY + (int)'A'))+ (piecePlaced ? " Success" : " Failure") );

            if (piecePlaced)
            {


                // fix the piece in place
                PieceActor.this.setTouchable(Touchable.disabled);

                // snap location to board grid by converting board location back to idealised stage location
                //note y inversion
                idealStageX =    boardX  * 50f + BOARD_ORIGIN_X + SQUARE_MID;
                idealStageY = (9-boardY) * 50f + BOARD_ORIGIN_Y + SQUARE_MID;

                //compare idealised location to original (in stage coordinates)
                deltaX = idealStageX - stageX;
                deltaY = idealStageY - stageY;

                PieceActor.this.setPosition(PieceActor.this.getX() + deltaX, PieceActor.this.getY()+deltaY);

                //check for captured pieces and remove them
                capturedPiece = gameState.getCaptureRef();
                while (capturedPiece != null)
                {
                    //find the actor and tell it it's been captured.
                    PieceActor capturedActor = PieceActor.this.getParent().findActor(capturedPiece.getName());
                    if (capturedActor != null)
                        capturedActor.capture();

                    //get next capture if it exists, or null
                    capturedPiece = gameState.getCaptureRef();
                }

            }

            return true;
        }
    }
}
