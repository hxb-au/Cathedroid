package au.id.hxb.cathedroid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import au.id.hxb.cathedroid.CathedroidGame;

/**
 * Created by hxb on 6/03/2016.
 */
public class InfoScreen implements Screen {
    CathedroidGame game;
    SpriteBatch batch;
    Texture placeholder;
    OrthographicCamera cam;
    InfoInputListener infoInputListener;
    private int midPointX, midPointY;
    private Viewport viewport;


    public InfoScreen(CathedroidGame game) {
        Gdx.app.log("InfoScreen", "Attached");
        batch = new SpriteBatch();
        placeholder = new Texture(Gdx.files.internal("info_placeholder.png"));
        this.game = game;
        int nativeWidth = 1280;
        int nativeHeight = 720;
        midPointX = nativeWidth / 2;
        midPointY = nativeHeight / 2;
        cam = new OrthographicCamera(nativeWidth,nativeHeight);
        cam.setToOrtho(false, nativeWidth,nativeHeight);
        viewport = new FitViewport(nativeWidth, nativeHeight, cam);
        infoInputListener = new InfoInputListener(game, cam);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(infoInputListener);
        viewport.apply();
        Gdx.input.setCatchBackKey(true);

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
        Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void dispose() {

    }
    class InfoInputListener extends InputAdapter {

        private CathedroidGame game;
        private OrthographicCamera cam;

        //button locations?


        public InfoInputListener( CathedroidGame game, OrthographicCamera cam) {
            this.game = game;
            this.cam = cam;

        }
        @Override
        public boolean touchDown (int screenX, int screenY, int pointer, int button) {
            if (button == 0){
                game.returnFromInfoScreen();
                return true;
            }
            else {
                return false;
            }

        }

        @Override
        public boolean keyDown(int keycode) {
            if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
                game.returnFromInfoScreen();
                return true;
            }
            else {
                return false;
            }
        }
    }

}
