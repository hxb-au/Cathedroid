package au.id.hxb.cathedroid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
    private int midPointX, midPointY;
    private Viewport viewport;
    private Stage stage;


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
        stage = new Stage(viewport, batch);

        //initUI();

    }

    private void initUI(){
        //load textures
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        //test button
        TextButton button = new TextButton("Back", skin, "default");
        button.setWidth(200f);
        button.setHeight(20f);
        button.setPosition(Gdx.graphics.getWidth() / 2 - 100f, Gdx.graphics.getHeight() / 2 - 10f);
        stage.addActor(button);

        //table
        //Table table = new Table();
        //table.setFillParent(true);
        //stage.addActor(table);




    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        viewport.apply();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f); // Sets a Color to Fill the Screen with (RGB = 0, 0, 0), Opacity of 1 (100%)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Fills the screen with the selected color
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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
        stage.dispose();
    }

}
