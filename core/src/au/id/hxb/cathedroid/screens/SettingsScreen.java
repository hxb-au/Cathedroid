package au.id.hxb.cathedroid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import au.id.hxb.cathedroid.CathedroidGame;

/**
 * Created by hxb on 6/03/2016.
 */
public class SettingsScreen implements Screen {
    CathedroidGame game;
    SpriteBatch batch;
    Texture placeholder;
    OrthographicCamera cam;
    SettingsInputListener settingsInputListener;
    private int midPointX, midPointY;
    private Viewport viewport;


    public SettingsScreen(CathedroidGame game) {
        Gdx.app.log("SettingsScreen", "Attached");
        batch = new SpriteBatch();
        placeholder = new Texture(Gdx.files.internal("settings_placeholder.png"));
        this.game = game;
        int nativeWidth = 1280;
        int nativeHeight = 720;
        midPointX = nativeWidth / 2;
        midPointY = nativeHeight / 2;
        cam = new OrthographicCamera(nativeWidth,nativeHeight);
        cam.setToOrtho(false, nativeWidth,nativeHeight);
        viewport = new FitViewport(nativeWidth, nativeHeight, cam);
        settingsInputListener = new SettingsInputListener(game, cam);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(settingsInputListener);
        viewport.apply();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 1f, 0f, 1f); // Sets a Color to Fill the Screen with (RGB = 0, 0, 0), Opacity of 1 (100%)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Fills the screen with the selected color

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.disableBlending();

        batch.draw(placeholder,midPointX-placeholder.getWidth()/2,midPointY-placeholder.getHeight()/2);

        batch.enableBlending();
        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("SettingsScreen", "resizing");
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
    class SettingsInputListener extends InputAdapter {

        private CathedroidGame game;
        private OrthographicCamera cam;

        //button locations?


        public SettingsInputListener( CathedroidGame game, OrthographicCamera cam) {
            this.game = game;
            this.cam = cam;

        }
        @Override
        public boolean touchDown (int screenX, int screenY, int pointer, int button) {
            if (button == 0){
                game.setMenuScreen();
                return true;
            }
            else {
                return false;
            }
        }
    }

}
