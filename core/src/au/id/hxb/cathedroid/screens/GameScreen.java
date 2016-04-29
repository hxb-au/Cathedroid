package au.id.hxb.cathedroid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import au.id.hxb.cathedroid.CathedroidGame;
import au.id.hxb.cathedroid.mechanics.GameState;
import au.id.hxb.cathedroid.mechanics.Piece;
import au.id.hxb.cathedroid.mechanics.Player;

/**
 * Created by hxb on 6/03/2016.
 */
public class GameScreen implements Screen {
    private CathedroidGame game;
    private OrthographicCamera cam;
    private SpriteBatch batch;
    private Viewport viewport;
    private Stage stage;
    private GameState gameState;
    private Group claimGroup;


    public GameScreen(CathedroidGame game) {
        Gdx.app.log("GameScreen", "Attached");
        this.game = game;
        gameState = new GameState();
        int nativeWidth = 1280;
        int nativeHeight = 720;

        cam = new OrthographicCamera(nativeWidth,nativeHeight);
        cam.setToOrtho(false, nativeWidth,nativeHeight);
        viewport = new FitViewport(nativeWidth, nativeHeight, cam);
        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);

        Image bgImg = new Image(new Texture(Gdx.files.internal("board_on_grey.png")));
        bgImg.setName("bg");
        stage.addActor(bgImg);
        initClaimActors();
        initPieceActors();
    }

    public void startNewGame(Player startingPlayer) {

        Gdx.app.log("GameScreen", "new game" + startingPlayer.toString());

        //reset game logic
        gameState.newGame(startingPlayer);

        // give cathedral piece to starting player
        PieceActor cathedralpiece = stage.getRoot().findActor(Piece.CA.getName());
        cathedralpiece.setPlayer(startingPlayer);

        //reset pieces
        Array<Actor> pieceActors = stage.getActors();
        for(Actor pieceActor : pieceActors)
        {
            //TODO this casting feels ugly - is there a better way?
            if(pieceActor instanceof PieceActor)
                ((PieceActor) pieceActor).reset();
        }

        updateClaimActors();

        //put cathedral on top
        cathedralpiece.toFront();
    }

    public void updateClaimActors(){
        Array<Actor> claimActors = claimGroup.getChildren();
        for(Actor claimActor : claimActors)
        {
            //TODO this casting feels ugly - is there a better way?
            if(claimActor instanceof ClaimActor)
                ((ClaimActor) claimActor).updateState();
        }
    }

    private void initPieceActors() {
        PieceActor.setGameState(gameState);
        PieceActor.setGameScreen(this);
        PieceActor tmpPiece;

        //Light Pieces
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-TA.png")),
                Piece.L_TA1,  
                new Rectangle(0,0,50,50),null,null, //hitboxes
                25f, 25f, //rotational centre
                25f, 25f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-TA.png")),
                Piece.L_TA2,  
                new Rectangle(0,0,50,50),null,null, //hitboxes
                25f, 25f, //rotational centre
                25f, 25f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-ST.png")),
                Piece.L_ST1,  
                new Rectangle(0,0,50,100),null,null, //hitboxes
                25f, 50f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-ST.png")),
                Piece.L_ST2,  
                new Rectangle(0,0,50,100),null,null, //hitboxes
                25f, 50f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-IN.png")),
                Piece.L_IN1,  
                new Rectangle(0,0,100,50),new Rectangle(0,0,50,100),null, //hitboxes
                25f, 25f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-IN.png")),
                Piece.L_IN2,  
                new Rectangle(0,0,100,50),new Rectangle(0,0,50,100),null, //hitboxes
                25f, 25f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-BR.png")),
                Piece.L_BR,  
                new Rectangle(0,0,50,150),null,null, //hitboxes
                25f, 75f, //rotational centre
                25f, 125f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-SQ.png")),
                Piece.L_SQ,  
                new Rectangle(0,0,100,100),null,null, //hitboxes
                50f, 50f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-AB.png")),
                Piece.L_AB,  
                new Rectangle(0,50,50,100),new Rectangle(50,0,50,100),null, //hitboxes
                50f, 75f, //rotational centre
                25f, 125f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-MA.png")),
                Piece.L_MA,  
                new Rectangle(0,0,150,50),new Rectangle(50,0,50,100),null, //hitboxes
                75f, 25f, //rotational centre
                75f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-TO.png")),
                Piece.L_TO,  
                new Rectangle(0,0,100,50),new Rectangle(50,50,100,50),new Rectangle(100,100,50,50), //hitboxes
                75f, 75f, //rotational centre
                125f, 125f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-IF.png")),
                Piece.L_IF,  
                new Rectangle(0,50,150,50),new Rectangle(50,0,50,150),null, //hitboxes
                75f, 75f, //rotational centre
                75f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-CS.png")),
                Piece.L_CS,  
                new Rectangle(0,0,100,50),new Rectangle(0,0,50,150),new Rectangle(0,100,100,50), //hitboxes
                25f, 75f, //rotational centre
                75f, 125f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-AC.png")),
                Piece.L_AC,  
                new Rectangle(50,0,100,50),new Rectangle(0,50,100,50),new Rectangle(50,0,50,150), //hitboxes
                75f, 75f, //rotational centre
                75f, 125f  //reference point for game rules
        );
        stage.addActor(tmpPiece);

        //Dark Pieces
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-TA.png")),
                Piece.D_TA1,  
                new Rectangle(0,0,50,50),null,null, //hitboxes
                25f, 25f, //rotational centre
                25f, 25f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-TA.png")),
                Piece.D_TA2,  
                new Rectangle(0,0,50,50),null,null, //hitboxes
                25f, 25f, //rotational centre
                25f, 25f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-ST.png")),
                Piece.D_ST1,  
                new Rectangle(0,0,50,100),null,null, //hitboxes
                25f, 50f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-ST.png")),
                Piece.D_ST2,  
                new Rectangle(0,0,50,100),null,null, //hitboxes
                25f, 50f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-IN.png")),
                Piece.D_IN1,  
                new Rectangle(0,0,100,50),new Rectangle(0,0,50,100),null, //hitboxes
                25f, 25f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-IN.png")),
                Piece.D_IN2,  
                new Rectangle(0,0,100,50),new Rectangle(0,0,50,100),null, //hitboxes
                25f, 25f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-BR.png")),
                Piece.D_BR,  
                new Rectangle(0,0,50,150),null,null, //hitboxes
                25f, 75f, //rotational centre
                25f, 125f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-SQ.png")),
                Piece.D_SQ,  
                new Rectangle(0,0,100,100),null,null, //hitboxes
                50f, 50f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-AB.png")),
                Piece.D_AB,  
                new Rectangle(0,0,50,100),new Rectangle(50,50,50,100),null, //hitboxes
                50f, 75f, //rotational centre
                75f, 125f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-MA.png")),
                Piece.D_MA,  
                new Rectangle(0,0,150,50),new Rectangle(50,0,50,100),null, //hitboxes
                75f, 25f, //rotational centre
                75f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-TO.png")),
                Piece.D_TO,  
                new Rectangle(0,0,100,50),new Rectangle(50,50,100,50),new Rectangle(100,100,50,50), //hitboxes
                75f, 75f, //rotational centre
                125f, 125f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-IF.png")),
                Piece.D_IF,  
                new Rectangle(0,50,150,50),new Rectangle(50,0,50,150),null, //hitboxes
                75f, 75f, //rotational centre
                75f, 75f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-CS.png")),
                Piece.D_CS,  
                new Rectangle(0,0,100,50),new Rectangle(0,0,50,150),new Rectangle(0,100,100,50), //hitboxes
                25f, 75f, //rotational centre
                75f, 125f  //reference point for game rules
        );
        stage.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-AC.png")),
                Piece.D_AC,  
                new Rectangle(0,0,100,50),new Rectangle(50,50,100,50),new Rectangle(50,0,50,150), //hitboxes
                75f, 75f, //rotational centre
                75f, 125f  //reference point for game rules
        );
        stage.addActor(tmpPiece);

        //Cathedral Piece
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/CA.png")),
                Piece.CA,  
                new Rectangle(0,100,150,50),new Rectangle(50,0,50,200),null, //hitboxes
                75f, 125f, //rotational centre
                75f, 175f  //reference point for game rules
        );
        stage.addActor(tmpPiece);


    }

    private void initClaimActors() {
        int i,j;
        ClaimActor tmpClaim;
        claimGroup = new Group();
        claimGroup.setName("claimGroup");

        //set ClaimActor statics
        TextureRegionDrawable lightTex, darkTex;
        lightTex = new TextureRegionDrawable(new TextureRegion( new Texture(Gdx.files.internal("lightClaimOverlay.png"))));
        darkTex  = new TextureRegionDrawable(new TextureRegion( new Texture(Gdx.files.internal( "darkClaimOverlay.png"))));
        ClaimActor.setDrawables(lightTex, darkTex);
        ClaimActor.setGameState(gameState);

        for(i=0; i < 10; i++){
            for(j=0; j < 10; j++){
                tmpClaim = new ClaimActor(i,j);
                claimGroup.addActor(tmpClaim);
            }
        }

        stage.addActor(claimGroup);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        viewport.apply();
        }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f); // Sets a Color to Fill the Screen with (RGB = 0, 0, 0), Opacity of 1 (100%)
        //used to fill with .7, .7, .7 but that's confusing as tyhe game world doens't extend in that way.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Fills the screen with the selected color

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

