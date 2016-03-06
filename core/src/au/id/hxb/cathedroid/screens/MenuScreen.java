package au.id.hxb.cathedroid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by hxb on 6/03/2016.
 */
public class MenuScreen implements Screen{

    public MenuScreen() {
        Gdx.app.log("MenuScreen", "Attached");
    }

    @Override
    public void render(float delta) {
        // Sets a Color to Fill the Screen with (RGB = 0, 0, 0), Opacity of 1 (100%)
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        // Fills the screen with the selected color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("MenuScreen", "resizing");
    }

    @Override
    public void show() {
        Gdx.app.log("MenuScreen", "show called");
    }

    @Override
    public void hide() {
        Gdx.app.log("MenuScreen", "hide called");
    }

    @Override
    public void pause() {
        Gdx.app.log("MenuScreen", "pause called");
    }

    @Override
    public void resume() {
        Gdx.app.log("MenuScreen", "resume called");
    }

    @Override
    public void dispose() {
        // Leave blank
    }
}
