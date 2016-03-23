package au.id.hxb.cathedroid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import au.id.hxb.cathedroid.CathedroidGame;

/**
 * Created by hxb on 6/03/2016.
 */
public class GameScreen implements Screen {
    private CathedroidGame game;
    private OrthographicCamera cam;
    private SpriteBatch batch;
    private int midPointX, midPointY;
    private Viewport viewport;
    private Stage stage;


    public GameScreen(CathedroidGame game) {
        Gdx.app.log("GameScreen", "Attached");
        this.game = game;
        int nativeWidth = 1280;
        int nativeHeight = 720;
        midPointX = nativeWidth / 2;
        midPointY = nativeHeight / 2;

        cam = new OrthographicCamera(nativeWidth,nativeHeight);
        cam.setToOrtho(false, nativeWidth,nativeHeight);
        viewport = new FitViewport(nativeWidth, nativeHeight, cam);
        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);

        Image bgImg = new Image(new Texture(Gdx.files.internal("board_on_grey.png")));
        bgImg.setName("bg");
        PieceActor cathedralPiece = new PieceActor(new Texture(Gdx.files.internal("cathedral_piece.png")),"Cathedral",
                new Rectangle(0,100,150,50),new Rectangle(50,0,50,200),new Rectangle(50,0,50,200),75f,125f);

        stage.addActor(bgImg);
        stage.addActor(cathedralPiece);




    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        viewport.apply();
        }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.7f, .7f, .7f, 1f); // Sets a Color to Fill the Screen with (RGB = 0, 0, 0), Opacity of 1 (100%)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Fills the screen with the selected color

        //do i still need this?
        //batch.setProjectionMatrix(viewport.getCamera().combined);

        /*
        batch.begin();
        batch.disableBlending();

        batch.draw(placeholder,midPointX-placeholder.getWidth()/2,midPointY-placeholder.getHeight()/2);

        batch.enableBlending();
        batch.end();*/

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen", "resizing");
        Gdx.app.log("Width", Integer.toString(width));
        Gdx.app.log("Height", Integer.toString(height));
        //shoudl this be a reference via stage?
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
