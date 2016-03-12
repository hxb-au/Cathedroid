package au.id.hxb.cathedroid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import au.id.hxb.cathedroid.CathedroidGame;

/**
 * Created by Hayden on 6/03/2016.
 */
public class GameScreen implements Screen {
    CathedroidGame game;
    SpriteBatch batch;
    Texture placeholder;
    GameInputListener gameInputListener;


    public GameScreen(CathedroidGame game) {
        Gdx.app.log("GameScreen", "Attached");
        batch = new SpriteBatch();
        placeholder = new Texture(Gdx.files.internal("gamescreen_placeholder.png"));
        gameInputListener = new GameInputListener(game);
        this.game = game;


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(gameInputListener);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f); // Sets a Color to Fill the Screen with (RGB = 0, 0, 0), Opacity of 1 (100%)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Fills the screen with the selected color
        batch.begin();
        batch.disableBlending();

        batch.draw(placeholder,0,0);

        batch.enableBlending();
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

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
