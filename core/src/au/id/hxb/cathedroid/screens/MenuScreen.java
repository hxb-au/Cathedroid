package au.id.hxb.cathedroid.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Vector;

import au.id.hxb.cathedroid.CathedroidGame;

/**
 * Created by hxb on 6/03/2016.
 */
public class MenuScreen implements Screen{
    private SpriteBatch batch;
    private Texture menuTexture;
    private int width, height;
    private int midPointX, midPointY;
    private MenuInputListener menuInputListener;
    private FitViewport viewport;
    //private CathedroidGame game;


    public MenuScreen(CathedroidGame game) {
        OrthographicCamera cam;
        Gdx.app.log("MenuScreen", "Attached");
        batch = new SpriteBatch();
        final int nativeWidth = 1280;
        final int nativeHeight = 720;
        midPointX = nativeWidth / 2;
        midPointY = nativeHeight / 2;
        cam = new OrthographicCamera(nativeWidth,nativeHeight);
        cam.setToOrtho(false, nativeWidth,nativeHeight);
        viewport = new FitViewport(nativeWidth, nativeHeight, cam);

        menuInputListener = new MenuInputListener(game, cam);

        menuTexture = new Texture(Gdx.files.internal("menu.png"));
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f); // Sets a Color to Fill the Screen with (RGB = 0, 0, 0), Opacity of 1 (100%)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Fills the screen with the selected color
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.disableBlending();

        batch.draw(menuTexture, 0, 0);

        batch.enableBlending();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

        Gdx.app.log("MenuScreen", "resizing");
        Gdx.app.log("Width", Integer.toString(width));
        Gdx.app.log("Height", Integer.toString(height));

        float aspect = (float)height / (float)width;

        viewport.update(width,height);

    }

    @Override
    public void show() {
        Gdx.app.log("MenuScreen", "show called");
        Gdx.input.setInputProcessor(menuInputListener);
        viewport.apply();
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
        private OrthographicCamera cam;

        //button locations
        private Rectangle new2pRect, load2pRect, new1pRect, load1pRect;
        private Rectangle settingsRect, infoRect;
        private final int gameButtonWidth = 186, gameButtonHeight = 84;
        private final int otherButtonWidth = 65, otherButtonHeight = 65;
        private final int new2px = 436, new2py = 220;
        private final int load2px = 655, load2py = 220;
        private final int new1px = 436, new1py = 104;
        private final int load1px = 655, load1py = 104;
        private final int settingsx = 1203, settingsy = 644;
        private final int infox = 8, infoy = 652;

        public MenuInputListener( CathedroidGame game, OrthographicCamera cam) {
            this.game = game;
            this.cam = cam;

            new2pRect = new Rectangle(new2px,  new2py, gameButtonWidth, gameButtonHeight);
            load2pRect = new Rectangle(load2px,  load2py, gameButtonWidth, gameButtonHeight);
            new1pRect = new Rectangle(new1px,  new1py, gameButtonWidth, gameButtonHeight);
            load1pRect = new Rectangle(load1px,  load1py, gameButtonWidth, gameButtonHeight);

            settingsRect = new Rectangle(settingsx,  settingsy, otherButtonWidth, otherButtonHeight);
            infoRect = new Rectangle(infox,  infoy, otherButtonWidth, otherButtonHeight);

        }
        @Override
        public boolean touchDown (int screenX, int screenY, int pointer, int button) {
            if (button == 0){
                Vector3 nativeClick = cam.unproject(new Vector3(screenX, screenY, 0));
                int nativeX = (int)nativeClick.x;
                int nativeY = (int)nativeClick.y;
                if (new2pRect.contains(nativeX, nativeY)){
                    game.setPlayScreen(true, false);
                    Gdx.app.log("MenuScreen", "new 2p game selected");
                    return true;
                }
                if (load2pRect.contains(nativeX, nativeY)){
                    game.setPlayScreen(false, false);
                    Gdx.app.log("MenuScreen", "load 2p game selected");
                    return true;
                }
                if (new1pRect.contains(nativeX, nativeY)){
                    game.setPlayScreen(true, true);
                    Gdx.app.log("MenuScreen", "new 1p game selected");
                    return true;
                }
                if (load1pRect.contains(nativeX, nativeY)){
                    game.setPlayScreen(false, true);
                    Gdx.app.log("MenuScreen", "load 1p game selected");
                    return true;
                }
                if (settingsRect.contains(nativeX, nativeY)){
                    game.setPlayScreen(false, true);
                    Gdx.app.log("MenuScreen", "settings selected");
                    return true;
                }
                if (infoRect.contains(nativeX, nativeY)){
                    game.setPlayScreen(false, true);
                    Gdx.app.log("MenuScreen", "info selected");
                    return true;
                }

                return false;


            }
            else {
                return false;
            }
        }
    }
}


