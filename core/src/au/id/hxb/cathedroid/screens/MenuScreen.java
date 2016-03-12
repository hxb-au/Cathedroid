package au.id.hxb.cathedroid.screens;

import com.badlogic.gdx.Game;
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
public class MenuScreen implements Screen{
    private SpriteBatch batch;
    private Texture logoTex, new2pTex, load2pTex, new1pTex, load1pTex ;
    private int width, height;
    private int midPointX, midPointY;
    private MenuInputListener menuInputListener;
    private int nativeWidth, nativeHeight;
    private OrthographicCamera cam;
    private Viewport viewport;
    //private CathedroidGame game;


    public MenuScreen(CathedroidGame game) {
        Gdx.app.log("MenuScreen", "Attached");
        batch = new SpriteBatch();
        cam = new OrthographicCamera();
        nativeWidth = 1280;
        nativeHeight = 720;
        midPointX = nativeWidth / 2;
        midPointY = nativeHeight / 2;
        viewport = new FitViewport(nativeWidth, nativeHeight,cam);

        menuInputListener = new MenuInputListener(game);

        //TODO put these in a single texture Atlas
        logoTex = new Texture(Gdx.files.internal("main_logo.png"));
        new2pTex = new Texture(Gdx.files.internal("2p_new.png"));
        load2pTex = new Texture(Gdx.files.internal("2p_load.png"));
        new1pTex = new Texture(Gdx.files.internal("1p_new.png"));
        load1pTex = new Texture(Gdx.files.internal("1p_load.png"));
    }

    @Override
    public void render(float delta) {

        int logo_x, logo_y;
        int new2p_x, new2p_y;
        int load2p_x, load2p_y;
        int new1p_x, new1p_y;
        int load1p_x, load1p_y;
        //Gdx.app.log("MenuScreen", "render called");



        //calculate logo location
        logo_x = (midPointX  - logoTex.getWidth()/2 );
        logo_y = (midPointY + 100);

        //calculate button locations
        new2p_x = midPointX - new2pTex.getWidth() - 10;
        new2p_y = midPointY - new2pTex.getHeight() - 20;

        load2p_x = midPointX + 10;
        load2p_y = midPointY - load2pTex.getHeight() - 20;

        new1p_x = midPointX - new1pTex.getWidth() - 10;
        new1p_y = new2p_y - new1pTex.getHeight() - 20;

        load1p_x = midPointX + 10;
        load1p_y = load2p_y - load1pTex.getHeight() - 20;


        //draw logo
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f); // Sets a Color to Fill the Screen with (RGB = 0, 0, 0), Opacity of 1 (100%)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Fills the screen with the selected color
        batch.begin();
        batch.disableBlending();

        batch.draw(logoTex, logo_x, logo_y);

        batch.draw(new2pTex, new2p_x, new2p_y);
        batch.draw(load2pTex,load2p_x,load2p_y);
        batch.draw(new1pTex,new1p_x,new1p_y);
        batch.draw(load1pTex,load1p_x,load1p_y);

        batch.enableBlending();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        OrthographicCamera cam;

        Gdx.app.log("MenuScreen", "resizing");
        Gdx.app.log("Width", Integer.toString(width));
        Gdx.app.log("Height", Integer.toString(height));

        viewport.update(width, height);

        //TODO fix the camera and update locations for stuff

    }

    @Override
    public void show() {
        Gdx.app.log("MenuScreen", "show called");
        Gdx.input.setInputProcessor(menuInputListener);
    }

    @Override
    public void hide() {
        Gdx.app.log("MenuScreen", "hide called");
        Gdx.input.setInputProcessor(null);
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

    class MenuInputListener extends InputAdapter {

        private CathedroidGame game;

        public MenuInputListener( CathedroidGame game) {
            this.game = game;
        }
        @Override
        public boolean touchUp (int screenX, int screenY, int pointer, int button) {
            if (button == 0){
                game.setPlayScreen(true, false);
                Gdx.app.log("MenuScreen", "new 2p game selected");
                return true;
            }
            else {
                return false;
            }
        }
    }
}

