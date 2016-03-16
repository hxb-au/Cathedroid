package au.id.hxb.cathedroid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import au.id.hxb.cathedroid.CathedroidGame;

/**
 * Created by hxb on 12/03/2016.
 */
public class GameInputListener extends InputAdapter{
    private CathedroidGame game;
    private OrthographicCamera cam;
    private Screen gameScreen;
    private Rectangle infoRect;
    private final int infox = 8, infoy = 652;
    private final int otherButtonWidth = 65, otherButtonHeight = 65;


    public GameInputListener( CathedroidGame game, OrthographicCamera cam,  Screen gameScreen) {

        this.game = game;
        this.cam = cam;
        this.gameScreen = gameScreen;
        infoRect = new Rectangle(infox,  infoy, otherButtonWidth, otherButtonHeight);
    }
    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        if (button == 0)
        {
            Vector3 nativeClick = cam.unproject(new Vector3(screenX, screenY, 0));
            int nativeX = (int)nativeClick.x;
            int nativeY = (int)nativeClick.y;
            if (infoRect.contains(nativeX, nativeY)){
                game.setInfoScreen(gameScreen);
                Gdx.app.log("GameScreen", "info selected");
                return true;
            }
            else {
                game.setMenuScreen();
                Gdx.app.log("GameScreen", "return to menu");
                return true;
            }
        }
        else
        {
            return false;
        }
    }
}
