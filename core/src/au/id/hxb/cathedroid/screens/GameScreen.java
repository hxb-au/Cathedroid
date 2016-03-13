package au.id.hxb.cathedroid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import au.id.hxb.cathedroid.CathedroidGame;

/**
 * Created by Hayden on 6/03/2016.
 */
public class GameScreen implements Screen {
    CathedroidGame game;
    SpriteBatch batch;
    Texture placeholder;
    GameInputListener gameInputListener;
    private int midPointX, midPointY;
    private Viewport viewport;


    public GameScreen(CathedroidGame game) {
        Gdx.app.log("GameScreen", "Attached");
        batch = new SpriteBatch();
        placeholder = new Texture(Gdx.files.internal("gamescreen_placeholder.png"));
        gameInputListener = new GameInputListener(game);
        this.game = game;
        int nativeWidth = 1280;
        int nativeHeight = 720;
        midPointX = nativeWidth / 2;
        midPointY = nativeHeight / 2;
        viewport = new FitViewport(nativeWidth, nativeHeight);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(gameInputListener);
        viewport.apply();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 1f, 0f, 1f); // Sets a Color to Fill the Screen with (RGB = 0, 0, 0), Opacity of 1 (100%)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Fills the screen with the selected color
        batch.begin();
        batch.disableBlending();

        batch.draw(placeholder,midPointX-placeholder.getWidth()/2,midPointY-placeholder.getHeight()/2);

        batch.enableBlending();
        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen", "resizing");
        Gdx.app.log("Width", Integer.toString(width));
        Gdx.app.log("Height", Integer.toString(height));
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {

    }
}
