package au.id.hxb.cathedroid.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import au.id.hxb.cathedroid.Mechanics.GameState;
import au.id.hxb.cathedroid.Mechanics.Player;

/**
 * Created by Hayden on 17/04/2016.
 */
public class ClaimActor extends Image {
    private static final int BOARD_ORIGIN_X = 390, BOARD_ORIGIN_Y = 110;
    private static final int SQUARE_DIM = 50;
    static GameState gameState;

    private final int boardX, boardY;
    private static Drawable lightOverlay, darkOverlay;


    static void setGameState(GameState gs)
    {
        gameState = gs;
    }

    static void setDrawables( Drawable light, Drawable dark){
        lightOverlay = light;
        darkOverlay = dark;
    }

    public ClaimActor(int boardX, int boardY){
        //use this constructor to set the image up properly for the both overlay images
        super(lightOverlay);

        this.boardX = boardX;
        this.boardY = boardY;

        this.setPosition(BOARD_ORIGIN_X + SQUARE_DIM * boardX, BOARD_ORIGIN_Y + SQUARE_DIM * (9 - boardY));
        this.setVisible(false);



    }

    public void updateState() {
        Player claimant = gameState.checkClaim(boardX, boardY);

        if (claimant == null){
            this.setVisible(false);
            return;
        }
        if (claimant == Player.DARK){
            this.setVisible(true);
            this.setDrawable(darkOverlay);
            return;
        }
        if (claimant == Player.LIGHT){
            this.setVisible(true);
            this.setDrawable(lightOverlay);
        }

    }

}
