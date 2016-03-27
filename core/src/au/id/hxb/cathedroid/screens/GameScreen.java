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
        stage.addActor(bgImg);
        initPieceActors();







    }
    private void initPieceActors() {
        PieceActor tmpPiece;
        //Cathedral Piece
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/CA.png")),
                "Cathedral", "CA", //Name, abbreviation
                new Rectangle(0,100,150,50),new Rectangle(50,0,50,200),new Rectangle(50,0,50,200), //hitboxes
                75f, 125f, //rotational centre
                75f, 175f  //reference point for game rules
        );
        stage.addActor(tmpPiece);

        //Light Pieces
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-TA.png")),
                "Light Tavern 1", "L-TA", //Name, abbreviation
                new Rectangle(0,0,50,50),new Rectangle(0,0,50,50),new Rectangle(0,0,50,50), //hitboxes
                25f, 25f, //rotational centre
                25f, 25f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-TA.png")),
                "Light Tavern 2", "L-TA", //Name, abbreviation
                new Rectangle(0,0,50,50),new Rectangle(0,0,50,50),new Rectangle(0,0,50,50), //hitboxes
                25f, 25f, //rotational centre
                25f, 25f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-ST.png")),
                "Light Stable 1", "L-ST", //Name, abbreviation
                new Rectangle(0,0,50,100),new Rectangle(0,0,50,100),new Rectangle(0,0,50,100), //hitboxes
                25f, 50f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-ST.png")),
                "Light Stable 2", "L-ST", //Name, abbreviation
                new Rectangle(0,0,50,100),new Rectangle(0,0,50,100),new Rectangle(0,0,50,100), //hitboxes
                25f, 50f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-IN.png")),
                "Light Inn 1", "L-IN", //Name, abbreviation
                new Rectangle(0,0,100,50),new Rectangle(0,0,50,100),new Rectangle(0,0,50,100), //hitboxes
                25f, 25f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-IN.png")),
                "Light Inn 2", "L-IN", //Name, abbreviation
                new Rectangle(0,0,100,50),new Rectangle(0,0,50,100),new Rectangle(0,0,50,100), //hitboxes
                25f, 25f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-BR.png")),
                "Light Bridge", "L-BR", //Name, abbreviation
                new Rectangle(0,0,50,150),new Rectangle(0,0,50,150),new Rectangle(0,0,50,150), //hitboxes
                25f, 75f, //rotational centre
                25f, 125f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-SQ.png")),
                "Light Square", "L-SQ", //Name, abbreviation
                new Rectangle(0,0,100,100),new Rectangle(0,0,100,100),new Rectangle(0,0,100,100), //hitboxes
                50f, 50f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-AB.png")),
                "Light Abbey", "L-AB", //Name, abbreviation
                new Rectangle(0,50,50,100),new Rectangle(50,0,50,100),new Rectangle(0,50,50,100), //hitboxes
                50f, 75f, //rotational centre
                25f, 125f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-MA.png")),
                "Light Manor", "L-MA", //Name, abbreviation
                new Rectangle(0,0,150,50),new Rectangle(50,0,50,100),new Rectangle(50,0,50,100), //hitboxes
                75f, 25f, //rotational centre
                75f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-TO.png")),
                "Light Tower", "L-TO", //Name, abbreviation
                new Rectangle(0,0,100,50),new Rectangle(50,50,100,50),new Rectangle(100,100,50,50), //hitboxes
                75f, 75f, //rotational centre
                125f, 125f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-IF.png")),
                "Light Infirmary", "L-IF", //Name, abbreviation
                new Rectangle(0,50,150,50),new Rectangle(50,0,50,150),new Rectangle(50,0,50,150), //hitboxes
                75f, 75f, //rotational centre
                75f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-CS.png")),
                "Light Castle", "L-CS", //Name, abbreviation
                new Rectangle(0,0,100,50),new Rectangle(0,0,50,150),new Rectangle(0,100,100,50), //hitboxes
                25f, 75f, //rotational centre
                75f, 125f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-AC.png")),
                "Light Academy", "L-AC", //Name, abbreviation
                new Rectangle(50,0,100,50),new Rectangle(0,50,100,50),new Rectangle(50,0,50,150), //hitboxes
                75f, 75f, //rotational centre
                75f, 125f  //reference point for game rules
        );
        stage.addActor(tmpPiece);

        //Dark Pieces
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-TA.png")),
                "Dark Tavern 1", "D-TA", //Name, abbreviation
                new Rectangle(0,0,50,50),new Rectangle(0,0,50,50),new Rectangle(0,0,50,50), //hitboxes
                25f, 25f, //rotational centre
                25f, 25f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-TA.png")),
                "Dark Tavern 2", "D-TA", //Name, abbreviation
                new Rectangle(0,0,50,50),new Rectangle(0,0,50,50),new Rectangle(0,0,50,50), //hitboxes
                25f, 25f, //rotational centre
                25f, 25f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-ST.png")),
                "Dark Stable 1", "D-ST", //Name, abbreviation
                new Rectangle(0,0,50,100),new Rectangle(0,0,50,100),new Rectangle(0,0,50,100), //hitboxes
                25f, 50f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-ST.png")),
                "Dark Stable 2", "D-ST", //Name, abbreviation
                new Rectangle(0,0,50,100),new Rectangle(0,0,50,100),new Rectangle(0,0,50,100), //hitboxes
                25f, 50f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-IN.png")),
                "Dark Inn 1", "D-IN", //Name, abbreviation
                new Rectangle(0,0,100,50),new Rectangle(0,0,50,100),new Rectangle(0,0,50,100), //hitboxes
                25f, 25f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-IN.png")),
                "Dark Inn 2", "D-IN", //Name, abbreviation
                new Rectangle(0,0,100,50),new Rectangle(0,0,50,100),new Rectangle(0,0,50,100), //hitboxes
                25f, 25f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-BR.png")),
                "Dark Bridge", "D-BR", //Name, abbreviation
                new Rectangle(0,0,50,150),new Rectangle(0,0,50,150),new Rectangle(0,0,50,150), //hitboxes
                25f, 75f, //rotational centre
                25f, 125f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-SQ.png")),
                "Dark Square", "D-SQ", //Name, abbreviation
                new Rectangle(0,0,100,100),new Rectangle(0,0,100,100),new Rectangle(0,0,100,100), //hitboxes
                50f, 50f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-AB.png")),
                "Dark Abbey", "D-AB", //Name, abbreviation
                new Rectangle(0,0,50,100),new Rectangle(50,50,50,100),new Rectangle(50,50,50,100), //hitboxes
                50f, 75f, //rotational centre
                25f, 125f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-MA.png")),
                "Dark Manor", "D-MA", //Name, abbreviation
                new Rectangle(0,0,150,50),new Rectangle(50,0,50,100),new Rectangle(50,0,50,100), //hitboxes
                75f, 25f, //rotational centre
                75f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-TO.png")),
                "Dark Tower", "D-TO", //Name, abbreviation
                new Rectangle(0,0,100,50),new Rectangle(50,50,100,50),new Rectangle(100,100,50,50), //hitboxes
                75f, 75f, //rotational centre
                125f, 125f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-IF.png")),
                "Dark Infirmary", "D-IF", //Name, abbreviation
                new Rectangle(0,50,150,50),new Rectangle(50,0,50,150),new Rectangle(50,0,50,150), //hitboxes
                75f, 75f, //rotational centre
                75f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-CS.png")),
                "Dark Castle", "D-CS", //Name, abbreviation
                new Rectangle(0,0,100,50),new Rectangle(0,0,50,150),new Rectangle(0,100,100,50), //hitboxes
                25f, 75f, //rotational centre
                75f, 125f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-AC.png")),
                "Dark Academy", "D-AC", //Name, abbreviation
                new Rectangle(0,0,100,50),new Rectangle(50,50,100,50),new Rectangle(50,0,50,150), //hitboxes
                75f, 75f, //rotational centre
                75f, 125f  //reference point for game rules
        );
        stage.addActor(tmpPiece);


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
