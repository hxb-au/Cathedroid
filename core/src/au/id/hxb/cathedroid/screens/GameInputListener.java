package au.id.hxb.cathedroid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

import au.id.hxb.cathedroid.CathedroidGame;

/**
 * Created by hxb on 12/03/2016.
 */
public class GameInputListener extends InputAdapter{
    private CathedroidGame game;


    public GameInputListener( CathedroidGame game) {
        this.game = game;
    }
    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        if (button == 0)
        {
            game.setMenuScreen();
            Gdx.app.log("GameScreen", "return to menu");
            return true;
        }
        else
        {
            return false;
        }
    }
}
