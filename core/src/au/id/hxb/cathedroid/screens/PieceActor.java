package au.id.hxb.cathedroid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

/**
 * Created by Hayden on 23/03/2016.
 */
public class PieceActor extends Image {
    private Rectangle hitBox1, hitBox2, hitBox3;
    private Vector2 v2before, v2after;
    private float deltaTheta;



    public PieceActor(Texture texture, String name,
                      Rectangle hitBox1, Rectangle hitBox2, Rectangle hitBox3,
                      float originX, float originY) {

        super(texture);

        this.hitBox1 = hitBox1;
        this.hitBox2 = hitBox2;
        this.hitBox3 = hitBox3;

        this.setName(name);
        this.setOrigin(originX, originY);
        this.setTouchable(Touchable.enabled);

        //this.addListener(new PieceClickListener());
        this.addListener(new PieceGestureListener());



    }


    @Override
    public Actor hit(float x, float y, boolean touchable) {

        // check touchable and override
        if ( touchable && !this.isTouchable()) {
            return null;
        }

        // check actual collisions with hitboxes
        if (hitBox1.contains(x,y) || hitBox2.contains(x,y) || hitBox3.contains(x,y) ){
                return this;
        }

        // nope out
        return null;
    }


    /*class PieceClickListener extends ClickListener {

        private float dragStartX = 0, dragStartY = 0;
        private float deltaX, deltaY;
        private Vector2 tmpInV2 = new Vector2(), tmpOutV2 = new Vector2();
        private boolean dragged = false;

        public PieceClickListener() {
            this.setTapSquareSize(5 * this.getTapSquareSize());
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            PieceActor.this.setRotation(PieceActor.this.getRotation() - 90);
            Gdx.app.log("Input", "Clicked");
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            dragStartX = x;
            dragStartY = y;
            dragged = false;
            return true;
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            tmpInV2.x = x - dragStartX;
            tmpInV2.y = y - dragStartY;
            tmpInV2.rotate(PieceActor.this.getRotation());
            //PieceActor.this.addAction(Actions.moveBy(tmpOutV2.x, -tmpOutV2.y));
            PieceActor.this.addAction(Actions.moveBy(tmpInV2.x, tmpInV2.y));
            dragged = true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (!dragged)
                PieceActor.this.setRotation(PieceActor.this.getRotation() - 90);
            //PieceActor.this.addAction(Actions.moveTo(0, 0 ));
        }
    }*/

    class PieceGestureListener extends ActorGestureListener {
        private Vector2 tmpInV2 = new Vector2(), tmpOutV2 = new Vector2();

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
        }

        @Override
        public void tap(InputEvent event, float x, float y, int count, int button) {
            PieceActor.this.setRotation(PieceActor.this.getRotation() + 90);
        }

        @Override
        public boolean longPress(Actor actor, float x, float y) {
            //PieceActor.this.setRotation(PieceActor.this.getRotation() + 90);
            return true;
        }
    }
}
