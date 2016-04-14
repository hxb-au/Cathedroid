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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
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

    TextButton lightStartButton, darkStartButton, randomStartButton;
    TextButton alternateYesButton, alternateNoButton;



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

        initUI();

    }

    private void initUI(){
        //load textures
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        //build buttonGroups

        // starting player group
        lightStartButton = new TextButton("Light", skin, "toggle");
        darkStartButton = new TextButton("Dark", skin, "toggle");
        randomStartButton = new TextButton("Random", skin, "toggle");
        ButtonGroup startPlayerGroup = new ButtonGroup(lightStartButton, darkStartButton, randomStartButton);
        startPlayerGroup.setMinCheckCount(1);
        startPlayerGroup.setMaxCheckCount(1);


        // alternate starting player group
        alternateYesButton = new TextButton("Yes", skin, "toggle");
        alternateNoButton = new TextButton("No", skin, "toggle");
        ButtonGroup alternateStartGroup = new ButtonGroup(alternateYesButton, alternateNoButton);

        // back button
        TextButton backButton = new TextButton("Back", skin, "default");
        backButton.addListener(new ClickListener(0) {
            @Override
            public void clicked(InputEvent e, float x, float y)
            {
                game.setMenuScreen();
            }
        });

        //table - ugly as
        //TODO make this nicer
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.add(new Label("Starting Player:  ", skin)).align(Align.right).height(30);
        table.add(lightStartButton).width(150).height(30);
        table.add(darkStartButton).width(150).height(30);
        table.add(randomStartButton).width(150).height(30);
        table.add(new Label(" ", skin)).height(30).width(150);
        table.row();
        table.add(new Label(" ", skin)).height(30);
        table.row();
        table.add(new Label("Alternate starting players from now on? ", skin)).align(Align.right).height(30);
        table.add(alternateYesButton).width(150).height(30);
        table.add(alternateNoButton).width(150).height(30);
        table.add(new Label(" ", skin)).height(30);
        table.row();
        table.add(new Label(" ", skin)).height(30);
        table.row();
        table.add(new Label(" ", skin)).height(30).width(150);
        table.add(new Label(" ", skin)).height(30).width(150);
        table.add(backButton).height(30).width(150);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        viewport.apply();

        //update checked buttons to match current settings
        lightStartButton.setChecked(game.isStartingPlayerLight());
        darkStartButton.setChecked(game.isStartingPlayerDark());
        randomStartButton.setChecked(game.isStartingPlayerRandom());

        alternateYesButton.setChecked(game.getAlternateStarts());
        alternateNoButton.setChecked(!game.getAlternateStarts());
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

        // save settings
        game.setAlternateStarts(alternateYesButton.isChecked());
        if (lightStartButton.isChecked())
            game.setStartingPlayerLight();
        if (darkStartButton.isChecked())
            game.setStartingPlayerDark();
        if (randomStartButton.isChecked())
            game.setStartingPlayerRandom();



    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}


