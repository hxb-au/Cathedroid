package au.id.hxb.cathedroid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
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
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntFloatMap;
import com.badlogic.gdx.utils.IntIntMap;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import au.id.hxb.cathedroid.CathedroidGame;
import au.id.hxb.cathedroid.mechanics.GameState;
import au.id.hxb.cathedroid.mechanics.Move;
import au.id.hxb.cathedroid.mechanics.Orientation;
import au.id.hxb.cathedroid.mechanics.Piece;
import au.id.hxb.cathedroid.mechanics.Player;
import au.id.hxb.cathedroid.mechanics.ai.AIEngine;

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
    private AIEngine ai;
    private Group claimGroup;
    private boolean aiRunning = false;
    private InputProcessor inputMux;
    private Group lightPieces, darkPieces;
    private PieceActor cathedralPiece;
    private Skin skin;


    public GameScreen(CathedroidGame game) {
        Gdx.app.log("GameScreen", "Attached");
        this.game = game;
        gameState = new GameState();
        ai = new AIEngine();
        int nativeWidth = 1280;
        int nativeHeight = 720;

        cam = new OrthographicCamera(nativeWidth, nativeHeight);
        cam.setToOrtho(false, nativeWidth, nativeHeight);
        viewport = new FitViewport(nativeWidth, nativeHeight, cam);
        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);


        BackProcessor backProcessor = new BackProcessor();

        inputMux = new InputMultiplexer(stage, backProcessor);

        //load dialog textures
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        Image bgImg = new Image(new Texture(Gdx.files.internal("board_on_grey.png")));
        bgImg.setName("bg");
        stage.addActor(bgImg);
        initClaimActors();
        initPieceActors();
    }

    public void startNewGame(Player startingPlayer) {

        Gdx.app.log("GameScreen", "new game " + startingPlayer.toString());

        //reset game logic
        gameState.newGame(startingPlayer);

        // give cathedral piece to starting player
        cathedralPiece.setPlayer(startingPlayer);

        //reset pieces
        Array<Actor> pieceActors = stage.getActors();
        for (Actor pieceActor : pieceActors) {
            //TODO this casting feels ugly - is there a better way?
            if (pieceActor instanceof PieceActor)
                ((PieceActor) pieceActor).reset();
        }

        //update the claim markers to match now empty board
        updateClaimActors();

        //put cathedral on top
        cathedralPiece.toFront();

        //get AI to play first if required
        //if (game.isAI(gameState.whoseTurn())) {
        //    makeAIMove();
        //}

        //highlight appropriate pieces
        highlightNext();
    }

    public void updateClaimActors() {
        Array<Actor> claimActors = claimGroup.getChildren();
        for (Actor claimActor : claimActors) {
            //TODO this casting feels ugly - is there a better way?
            if (claimActor instanceof ClaimActor)
                ((ClaimActor) claimActor).updateState();
        }
    }

    private void initPieceActors() {
        PieceActor.setGameState(gameState);
        PieceActor.setGameScreen(this);
        PieceActor tmpPiece;

        lightPieces = new Group();
        darkPieces = new Group();

        stage.addActor(lightPieces);
        stage.addActor(darkPieces);

        //Light Pieces
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-TA.png")),
                Piece.L_TA1,
                new Rectangle(0, 0, 50, 50), null, null, //hitboxes
                25f, 25f, //rotational centre
                25f, 25f  //reference point for game rules
        );
        lightPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-TA.png")),
                Piece.L_TA2,
                new Rectangle(0, 0, 50, 50), null, null, //hitboxes
                25f, 25f, //rotational centre
                25f, 25f  //reference point for game rules
        );
        lightPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-ST.png")),
                Piece.L_ST1,
                new Rectangle(0, 0, 50, 100), null, null, //hitboxes
                25f, 50f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        lightPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-ST.png")),
                Piece.L_ST2,
                new Rectangle(0, 0, 50, 100), null, null, //hitboxes
                25f, 50f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        lightPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-IN.png")),
                Piece.L_IN1,
                new Rectangle(0, 0, 100, 50), new Rectangle(0, 0, 50, 100), null, //hitboxes
                25f, 25f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        lightPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-IN.png")),
                Piece.L_IN2,
                new Rectangle(0, 0, 100, 50), new Rectangle(0, 0, 50, 100), null, //hitboxes
                25f, 25f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        lightPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-BR.png")),
                Piece.L_BR,
                new Rectangle(0, 0, 50, 150), null, null, //hitboxes
                25f, 75f, //rotational centre
                25f, 125f  //reference point for game rules
        );
        lightPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-SQ.png")),
                Piece.L_SQ,
                new Rectangle(0, 0, 100, 100), null, null, //hitboxes
                50f, 50f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        lightPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-AB.png")),
                Piece.L_AB,
                new Rectangle(0, 50, 50, 100), new Rectangle(50, 0, 50, 100), null, //hitboxes
                50f, 75f, //rotational centre
                25f, 125f  //reference point for game rules
        );
        lightPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-MA.png")),
                Piece.L_MA,
                new Rectangle(0, 0, 150, 50), new Rectangle(50, 0, 50, 100), null, //hitboxes
                75f, 25f, //rotational centre
                75f, 75f  //reference point for game rules
        );
        lightPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-TO.png")),
                Piece.L_TO,
                new Rectangle(0, 0, 100, 50), new Rectangle(50, 50, 100, 50), new Rectangle(100, 100, 50, 50), //hitboxes
                75f, 75f, //rotational centre
                125f, 125f  //reference point for game rules
        );
        lightPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-IF.png")),
                Piece.L_IF,
                new Rectangle(0, 50, 150, 50), new Rectangle(50, 0, 50, 150), null, //hitboxes
                75f, 75f, //rotational centre
                75f, 75f  //reference point for game rules
        );
        lightPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-CS.png")),
                Piece.L_CS,
                new Rectangle(0, 0, 100, 50), new Rectangle(0, 0, 50, 150), new Rectangle(0, 100, 100, 50), //hitboxes
                25f, 75f, //rotational centre
                75f, 125f  //reference point for game rules
        );
        lightPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/L-AC.png")),
                Piece.L_AC,
                new Rectangle(50, 0, 100, 50), new Rectangle(0, 50, 100, 50), new Rectangle(50, 0, 50, 150), //hitboxes
                75f, 75f, //rotational centre
                75f, 125f  //reference point for game rules
        );
        lightPieces.addActor(tmpPiece);

        //Dark Pieces
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-TA.png")),
                Piece.D_TA1,
                new Rectangle(0, 0, 50, 50), null, null, //hitboxes
                25f, 25f, //rotational centre
                25f, 25f  //reference point for game rules
        );
        darkPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-TA.png")),
                Piece.D_TA2,
                new Rectangle(0, 0, 50, 50), null, null, //hitboxes
                25f, 25f, //rotational centre
                25f, 25f  //reference point for game rules
        );
        darkPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-ST.png")),
                Piece.D_ST1,
                new Rectangle(0, 0, 50, 100), null, null, //hitboxes
                25f, 50f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        darkPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-ST.png")),
                Piece.D_ST2,
                new Rectangle(0, 0, 50, 100), null, null, //hitboxes
                25f, 50f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        darkPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-IN.png")),
                Piece.D_IN1,
                new Rectangle(0, 0, 100, 50), new Rectangle(0, 0, 50, 100), null, //hitboxes
                25f, 25f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        darkPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-IN.png")),
                Piece.D_IN2,
                new Rectangle(0, 0, 100, 50), new Rectangle(0, 0, 50, 100), null, //hitboxes
                25f, 25f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        darkPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-BR.png")),
                Piece.D_BR,
                new Rectangle(0, 0, 50, 150), null, null, //hitboxes
                25f, 75f, //rotational centre
                25f, 125f  //reference point for game rules
        );
        darkPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-SQ.png")),
                Piece.D_SQ,
                new Rectangle(0, 0, 100, 100), null, null, //hitboxes
                50f, 50f, //rotational centre
                25f, 75f  //reference point for game rules
        );
        darkPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-AB.png")),
                Piece.D_AB,
                new Rectangle(0, 0, 50, 100), new Rectangle(50, 50, 50, 100), null, //hitboxes
                50f, 75f, //rotational centre
                75f, 125f  //reference point for game rules
        );
        darkPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-MA.png")),
                Piece.D_MA,
                new Rectangle(0, 0, 150, 50), new Rectangle(50, 0, 50, 100), null, //hitboxes
                75f, 25f, //rotational centre
                75f, 75f  //reference point for game rules
        );
        darkPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-TO.png")),
                Piece.D_TO,
                new Rectangle(0, 0, 100, 50), new Rectangle(50, 50, 100, 50), new Rectangle(100, 100, 50, 50), //hitboxes
                75f, 75f, //rotational centre
                125f, 125f  //reference point for game rules
        );
        darkPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-IF.png")),
                Piece.D_IF,
                new Rectangle(0, 50, 150, 50), new Rectangle(50, 0, 50, 150), null, //hitboxes
                75f, 75f, //rotational centre
                75f, 75f  //reference point for game rules
        );
        darkPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-CS.png")),
                Piece.D_CS,
                new Rectangle(0, 0, 100, 50), new Rectangle(0, 0, 50, 150), new Rectangle(0, 100, 100, 50), //hitboxes
                25f, 75f, //rotational centre
                75f, 125f  //reference point for game rules
        );
        darkPieces.addActor(tmpPiece);
        tmpPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/D-AC.png")),
                Piece.D_AC,
                new Rectangle(0, 0, 100, 50), new Rectangle(50, 50, 100, 50), new Rectangle(50, 0, 50, 150), //hitboxes
                75f, 75f, //rotational centre
                75f, 125f  //reference point for game rules
        );
        darkPieces.addActor(tmpPiece);

        //Cathedral Piece
        cathedralPiece = new PieceActor(new Texture(Gdx.files.internal("pieces/CA.png")),
                Piece.CA,
                new Rectangle(0, 100, 150, 50), new Rectangle(50, 0, 50, 200), null, //hitboxes
                75f, 125f, //rotational centre
                75f, 175f  //reference point for game rules
        );
        stage.addActor(cathedralPiece);


    }

    private void initClaimActors() {
        int i, j;
        ClaimActor tmpClaim;
        claimGroup = new Group();
        claimGroup.setName("claimGroup");

        //set ClaimActor statics
        TextureRegionDrawable lightTex, darkTex;
        lightTex = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("lightClaimOverlay.png"))));
        darkTex = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("darkClaimOverlay.png"))));
        ClaimActor.setDrawables(lightTex, darkTex);
        ClaimActor.setGameState(gameState);

        for (i = 0; i < 10; i++) {
            for (j = 0; j < 10; j++) {
                tmpClaim = new ClaimActor(i, j);
                claimGroup.addActor(tmpClaim);
            }
        }

        stage.addActor(claimGroup);
    }

    public void highlightDarks() {
        highlightNone();
        for (Actor pieceActor : darkPieces.getChildren())
        {
            ((PieceActor)pieceActor).setHighlight(true);
        }
    }

    public void highlightLights() {
        highlightNone();
        for (Actor pieceActor : lightPieces.getChildren())
        {
            ((PieceActor)pieceActor).setHighlight(true);
        }
    }

    public void highlightCathedral() {
        highlightNone();
        cathedralPiece.setHighlight(true);
    }

    public void highlightPiece(PieceActor pieceActor){
        highlightNone();

        pieceActor.setHighlight(true);
    }

    public void highlightNext() {
        if (gameState.cathedralMoveReqd()) {
            highlightCathedral();
            return;
        }
        if (gameState.isGameOver()){
            highlightNone();
            return;
        }

        switch (gameState.whoseTurn()) {
            case LIGHT:
                highlightLights();
                return;
            case DARK:
                highlightDarks();
                return;
        }
    }

    public void highlightNone() {
        for (Actor pieceActor : lightPieces.getChildren())
        {
            ((PieceActor)pieceActor).setHighlight(false);
        }
        for (Actor pieceActor : darkPieces.getChildren())
        {
            ((PieceActor)pieceActor).setHighlight(false);
        }
        cathedralPiece.setHighlight(false);
    }

    public boolean attemptMove(PieceActor pieceActor, Piece piece, Orientation dir, int boardX, int boardY, Player player) {
        //TODO pass as Move
        boolean successfulMove = gameState.attemptMove(piece, dir, boardX, boardY, player);

        if (successfulMove) {
            Move move = new Move(piece, dir, boardX, boardY, player);

            applyMove(move, pieceActor);

            //check for endgame
            if (gameState.isGameOver())
                Gdx.app.log("GameScreen", "Gamestate indicates game over");


        }

        return successfulMove;
    }

    private void makeAIMove() {
        Move aiMove = ai.makeMove(gameState);
        PieceActor aiPiece = stage.getRoot().findActor(aiMove.piece.getName());

        applyMove(aiMove, aiPiece);
    }

    private void applyMove(Move move, PieceActor pieceActor) {
        Gdx.app.log("GameScreen", "Move being applied " + move.toString());
        // put the piece in place and lock it
        pieceActor.placePiece(move.orientation, move.x, move.y);

        //check for captures and handle them
        handleCaptures();

        // board state will have changed. check claims
        updateClaimActors();

        //highlight pieces for next turn
        highlightNext();

        //check for gameover
        if (gameState.isGameOver()){
            endGameDialog();
        }

    }

    private void endGameDialog(){

        String winnerStr, lightStr, darkStr;
        int lightScore = gameState.getScore(Player.LIGHT);
        int darkScore  = gameState.getScore(Player.DARK);
        lightStr = Integer.toString(lightScore);
        darkStr  = Integer.toString(darkScore );


        Dialog diag = new Dialog("Game Over", skin);

        if (lightScore == darkScore)
            winnerStr = "Draw:";
        else if (lightScore < darkScore)
            winnerStr = "Light Player Wins: ";
        else //if (lightScore > darkScore)
            winnerStr = "Dark Player Wins: ";

        diag.text(winnerStr + lightStr + "-" + darkStr);
        diag.button("OK");

        diag.show(stage);

    }


    private void handleCaptures() {
        Piece capturedPiece = gameState.getCaptureRef();
        while (capturedPiece != null) {
            //find the actor and tell it it's been captured.
            PieceActor capturedActor = stage.getRoot().findActor(capturedPiece.getName());
            if (capturedActor != null)
                capturedActor.capture();

            //get next capture if it exists, or null
            capturedPiece = gameState.getCaptureRef();
        }
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMux);
        viewport.apply();
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f); // Sets a Color to Fill the Screen with (RGB = 0, 0, 0), Opacity of 1 (100%)
        //used to fill with .7, .7, .7 but that's confusing as tyhe game world doens't extend in that way.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Fills the screen with the selected color

        stage.act(delta);
        stage.draw();

        //if it's an AI turn, make the move.
        //only run AI if game isn't over
        //TODO put in a different thread with a lock.
        if (!aiRunning && !gameState.isGameOver() && game.isAI(gameState.whoseTurn())) {
            aiRunning = true;
            makeAIMove();
            aiRunning = false;
        }

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
        Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void dispose() {

    }


    class BackProcessor extends InputAdapter {
        @Override
        public boolean keyDown(int keycode) {
            if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
                GameScreen.this.game.setMenuScreen();
                return true;
            }

            return false;
        }
    }

}