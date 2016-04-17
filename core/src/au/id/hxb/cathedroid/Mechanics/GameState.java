package au.id.hxb.cathedroid.Mechanics;


import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Created by hxb on 4/04/2016.
 */
public class GameState {
    private SquareState[][] board;
    private int [][] coordinates, tmp;
    private boolean [][] checkedsquares;
    private Move moveList;
    private Square captureCoordList;
    private int numMoves = 0;
    private Player nextPlayer;
    private EnumMap<Piece, Boolean> pieceAvailable;
    private ArrayList<Piece> capturedPieces;

    private final int BOARD_WIDTH = 10, BOARD_HEIGHT = 10;


    public GameState(){

        board = new SquareState[BOARD_WIDTH][BOARD_HEIGHT];
        checkedsquares = new boolean[BOARD_WIDTH][BOARD_HEIGHT];

        coordinates = new int[][] {{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}};
        tmp = new int[][] {{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}};

        pieceAvailable = new EnumMap<Piece, Boolean>(Piece.class);
        capturedPieces = new ArrayList<Piece>();

    }

    // set up a new game with an empty board, no moves made and the requested starting player
    public void newGame(Player startingPlayer){

        //(re)fill board array with empties
        int i,j;

        for (i = 0; i < BOARD_WIDTH; i++) {
            for (j = 0; j < BOARD_HEIGHT; j++) {
                board[i][j] = SquareState.EMPTY;
            }
        }

        //empty the list of moves
        moveList = null;
        numMoves = 0;

        // reset piece usage list
        for( Piece piece : Piece.values())
        {
            pieceAvailable.put(piece, true);
        }

        //set starting player
        nextPlayer = startingPlayer;

    }

    // called after a move has been processed for the UI to learn which pieces have been captured.
    // each call gives a new piece, as multiple pieces can be captured on a move.
    // returns null if no pieces left to process (or  none captured to begin with)
    public Piece getCaptureRef() {
        if (capturedPieces.isEmpty())
            return null;
        else {
            return capturedPieces.remove(0);
        }
    }

    //go through the list of captured piece coordinates and mark those pieces available
    // collect piece refs in a list for the UI to query
    private void processCaptures(){

        Piece capturedPiece = null;
        Move index;

        while (captureCoordList != null) {

            index = moveList;

            //find the most recent move that matches this coordinate
            while (index != null) {
                if (captureCoordList.x == index.x && captureCoordList.y == index.y)
                    capturedPiece = index.piece;
                index = index.nextMove;
            }

            // mark the piece as available in gameState's own list
            if (capturedPiece != null)
                pieceAvailable.put(capturedPiece, true);

            //add it to the captured set for reference by the UI
            capturedPieces.add(capturedPiece);

            captureCoordList = captureCoordList.nextSquare;
        }

    }

    public Player whoseTurn(){ return nextPlayer; }

    // the board is a 10x10 array of these. piece origins are used for capture and claim checks
    enum SquareState {
        EMPTY,
        DARKPIECE,
        DARKPIECE_ORIGIN,
        LIGHTPIECE,
        LIGHTPIECE_ORIGIN,
        CATHEDRALPIECE,
        CATHEDRALPIECE_ORIGIN,
        DARKCLAIM,
        LIGHTCLAIM;
    }

    // this is the main interface to the gamestate. Attempt to make a move with the given details. 
    // returns true if move was accepted and processes updates to gamestate.
    public boolean attemptMove(Piece piece, Orientation orientation, int pieceX, int pieceY, Player player){
        int i;
        SquareState state;
        SquareState originState;
        int x, y;
        int numSquares;
        Move move;

        //correct turn? fail if not
        if (player != nextPlayer)
            return false;

        //first move Cathedral? fail if not
        if (numMoves == 0 && piece != Piece.CA)
            return false;


        // load translated and rotated piece coverage in to the coordinates array
        // save count for iteration
        numSquares = getSquares(piece, orientation, pieceX, pieceY, coordinates);

        //check each coordinate
        if( !checkSquares(numSquares, coordinates,player))
            return false;

        // note: numSquares and coordinates are used again later if successful

        //still here? then the piece fits

        //mark piece as used in gamestate's list
        pieceAvailable.put(piece, false);

        //record piece in board

        // which colour piece is it?
        if (piece == Piece.CA) {
            state = SquareState.CATHEDRALPIECE;
            originState = SquareState.CATHEDRALPIECE_ORIGIN;
        }
        else if (player == Player.LIGHT) {
            state = SquareState.LIGHTPIECE;
            originState = SquareState.LIGHTPIECE_ORIGIN;
        }
        else {//dark
            state = SquareState.DARKPIECE;
            originState = SquareState.DARKPIECE_ORIGIN;
        }

        //copy it in using coordinates generated earlier for checking fit
        // the first coordinate is marked as a piece origin for capture checks (should match reference point for placement)
        x = coordinates[0][0];
        y = coordinates[0][1];
        board[x][y] = originState;
        // the remaining coordinates are just marked as player piece
        for (i = 1; i < numSquares; i++) {
            x = coordinates[i][0];
            y = coordinates[i][1];
            board[x][y] = state;
        }

        //store the new move in the list of moves
        move = new Move(piece, orientation, pieceX, pieceY, player);
        addMove(move);
        numMoves++;

        //only check claims after cathedral and 1 piece each are placed
        if (numMoves > 3) {
            checkPieceClaims(player, numSquares, coordinates);
            processCaptures();
        }

        // other player's turn
        nextPlayer = nextPlayer.getOther();

        //if the next player can't make a move, swap back
        if (movesImpossible(nextPlayer)) {
            nextPlayer = nextPlayer.getOther();
        }


        Gdx.app.log("GameState", "Next player: " + nextPlayer.toString());

        return true;
    }

    // check if the current player can make a move at all
    // brute force - might be optimisable if iterations are re-ordered and non-empty/claimed squares skipped
    private boolean movesImpossible(Player player){
        int x,y;
        int numSquares;

        // for each piece
        for (Piece testPiece : Piece.values()) {
            // if that piece belongs to current player and is available
            if (testPiece.getOwner() == player && pieceAvailable.get(testPiece)) {
                //check each orientation of that piece
                for (Orientation dir : Orientation.values()) {
                    //in every position
                    for (x = 0; x < BOARD_WIDTH; x++) {
                        for (y = 0; y < BOARD_HEIGHT; y++){
                            //check piece for fit

                            // load translated and rotated piece coverage in to the coordinates array
                            // save numSquares count for iteration
                            numSquares = getSquares(testPiece, dir, x, y, coordinates);

                            //check each coordinate, if the piece fits then moves are not impossible
                            if(checkSquares(numSquares, coordinates,player))
                                return false;
                        }
                    }
                }

            }
        }

        return true;
    }


    // check for claimed area around a new piece by checking each square around it
    // (including diagonals)
    // this needs to restart at each square so a piece that defines two claimed areas will
    // actualy be able to claim both areas.
    // a matrix is used to avoid repeat work
    private void checkPieceClaims(Player player, int numSquares, int[][] coordinates){
        int i, j;
        int pieceX, pieceY;


        //reset array of which squares have been checked
        for (i = 0; i < checkedsquares.length; i++) {
            for (j = 0; j < checkedsquares[i].length; j++) {
                checkedsquares[i][j] = false;
            }
        }

        // generate start points around placed piece
        for (i = 0; i < numSquares; i++) {
            pieceX = coordinates[i][0];
            pieceY = coordinates[i][1];


            checkSquareClaim(pieceX + 1, pieceY + 1, player);
            checkSquareClaim(pieceX + 1, pieceY,     player);
            checkSquareClaim(pieceX + 1, pieceY - 1, player);
            checkSquareClaim(pieceX,     pieceY + 1, player);
            checkSquareClaim(pieceX,     pieceY - 1, player);
            checkSquareClaim(pieceX - 1, pieceY + 1, player);
            checkSquareClaim(pieceX - 1, pieceY,     player);
            checkSquareClaim(pieceX - 1, pieceY - 1, player);

        }

    }

    // starting at the given square, flood fill (including via diagonals)
    // treat friendly pieces as the edge of the flood and count enemy pieces in there
    // if 0 or 1 enemy pieces are found, claim the area
    // capture piece if relevant
    private void checkSquareClaim(int x, int y, Player player){
        int i,j;
        int currentX, currentY;
        Square queueHead = null;
        Square queueTail = null;
        Square capturedPieceOrigin = null;
        Square includedAreaStack = null;
        int enemyPieces = 0;
        SquareState allied, alliedOrigin, alliedClaim;
        SquareState enemy, enemyOrigin;
        //Gdx.app.log("Claims begin", Integer.toString(x) + "," + Integer.toString(y));

        //bounds check
        if (x < 0 || x > 9)
            return;
        if (y < 0 || y > 9)
            return;

        // avoid repeats
        if (checkedsquares[x][y])
            return;

        queueHead = new Square(x,y);
        queueTail = queueHead;
        queueHead.markChecked();
        Gdx.app.log("Claims", "Begin Claim Check " + queueHead.toString());

        //pop top of queue by checking if it is empty/enemy
        // if so, add surrounding unchecked squares to the queue and move it to included area
        // mark this square as checked and advance queue
        while (queueHead != null)
        {
            //queueHead.markChecked();

            // allied squares are the border, just pop and continue
            if (queueHead.isAllied(player)) {
                queueHead = queueHead.nextSquare;
            }
            else{
                //if this is a piece marker, not just walls, handle that.
                if (queueHead.isEnemyOrigin(player)){
                    enemyPieces++;
                    if (enemyPieces == 2)
                        capturedPieceOrigin = null; //found 2 pieces in this region, no captures.
                    // must finish filling this region to mark all squares as checked otherwise another  partial region might find only 1 piece
                    //TODO - this might be faster if we quit here and subsequent region checks expire when they hit previous ones.
                    //this would require changing checkedSquares to an int from a bool.
                    if (enemyPieces == 1)
                        capturedPieceOrigin = queueHead; //hold on to this location for capture lookup
                    Gdx.app.log("Claims", "Hit Enemy #" + Integer.toString(enemyPieces) + " " + queueHead.toString());
                }

                currentX = queueHead.x;
                currentY = queueHead.y;

                // check surrounds and add each to queue if unchecked and on board
                for (i=-1; i<2;i++){
                    for (j=-1; j<2; j++){
                        if(currentX+i >= 0 && currentX+i <= 9  &&
                           currentY+j >= 0 && currentY+j <= 9  &&
                           !checkedsquares[currentX+i][currentY+j]){
                             queueTail.nextSquare = new Square(currentX+i, currentY+j);
                             //Gdx.app.log("Claims queue", Integer.toString(currentX+i) + "," + Integer.toString(currentY+j));
                             queueTail = queueTail.nextSquare;
                             queueTail.markChecked();
                        }
                    }
                }

                //now move queue head to included area
                Square tmp = queueHead;
                queueHead = queueHead.nextSquare;

                tmp.nextSquare = includedAreaStack;
                includedAreaStack = tmp;
                //Gdx.app.log("Included queue", Integer.toString(currentX) + "," + Integer.toString(currentY));
            }
        }

        // region has too many enemies to claim. give up now that all connected squares are marked checked
        if ( enemyPieces > 1)
        {
            Gdx.app.log("Claims", "Claim failed from " + new Square(x,y).toString());
            return;
        }

        // still here? make the claim
        // included area calculated, so set it all to claimed

        //assign allied enum
        if (player == Player.LIGHT)
            alliedClaim = SquareState.LIGHTCLAIM;
        else
            alliedClaim = SquareState.DARKCLAIM;

        int area = 0;
        while (includedAreaStack != null) {
            Gdx.app.log("Claims",player.toString() +  " claims " + includedAreaStack.toString());
            board[includedAreaStack.x][includedAreaStack.y] = alliedClaim;
            area++;
            includedAreaStack = includedAreaStack.nextSquare;
        }

        // if a capture occurred, push it on to the stack of captures.
        if(enemyPieces == 1){
            capturedPieceOrigin.nextSquare = captureCoordList;
            captureCoordList = capturedPieceOrigin;
        }

    }


    //append the move to the movelist
    private void addMove(Move move){
        //log
        Gdx.app.log("Move recorded", move.toString());

        //trivial empty list case
        if (moveList == null){
            moveList = move;
            return;
        }

        //use a temp pointer to traverse the list
        Move tmp;
        tmp = this.moveList;

        //find the end of the list
        while(tmp.nextMove != null)
            tmp = tmp.nextMove;

        // plug it in and done.
        tmp.nextMove = move;


    }

    //put the piece's coordinates in the array and return the number of squares occupied
    private int getSquares( Piece piece, Orientation orientation, int x, int y, int[][] coords){
        int numSquares, i;

        // get piece coordinates as offsets from its origin when facing north
        // first coordinate is always 0,0 so origin gets marked correctly for piece capture algorithm
        switch (piece){

            case CA: //cathedral
                numSquares = 6;
                tmp[0][0] =  0; tmp [0][1] =  0;
                tmp[1][0] = -1; tmp [1][1] =  1;
                tmp[2][0] =  0; tmp [2][1] =  1;
                tmp[3][0] =  1; tmp [3][1] =  1;
                tmp[4][0] =  0; tmp [4][1] =  2;
                tmp[5][0] =  0; tmp [5][1] =  3;
                break;
            case L_TA1:
            case L_TA2:
            case D_TA1:
            case D_TA2: //all taverns
                numSquares = 1;
                tmp[0][0] = 0; tmp [0][1] = 0;
                break;
            case L_ST1:
            case L_ST2:
            case D_ST1:
            case D_ST2:// all stables
                numSquares = 2;
                tmp[0][0] = 0; tmp [0][1] = 0;
                tmp[1][0] = 0; tmp [1][1] = 1;
                break;
            case L_IN1:
            case L_IN2:
            case D_IN1:
            case D_IN2: // all inns
                numSquares = 3;
                tmp[0][0] = 0; tmp [0][1] = 0;
                tmp[1][0] = 0; tmp [1][1] = 1;
                tmp[2][0] = 1; tmp [2][1] = 1;
                break;
            case L_BR:
            case D_BR: // both bridges
                numSquares = 3;
                tmp[0][0] = 0; tmp [0][1] = 0;
                tmp[1][0] = 0; tmp [1][1] = 1;
                tmp[2][0] = 0; tmp [2][1] = 2;
                break;
            case L_SQ:
            case D_SQ: // both squares
                numSquares = 4;
                tmp[0][0] = 0; tmp [0][1] = 0;
                tmp[1][0] = 0; tmp [1][1] = 1;
                tmp[2][0] = 1; tmp [2][1] = 1;
                tmp[3][0] = 1; tmp [3][1] = 0;
                break;
            case L_AB: //light abbey
                numSquares = 4;
                tmp[0][0] = 0; tmp [0][1] = 0;
                tmp[1][0] = 0; tmp [1][1] = 1;
                tmp[2][0] = 1; tmp [2][1] = 1;
                tmp[3][0] = 1; tmp [3][1] = 2;
                break;
            case D_AB: // dark abbey
                numSquares = 4;
                tmp[0][0] = 0; tmp [0][1] = 0;
                tmp[1][0] = 0; tmp [1][1] = 1;
                tmp[2][0] =-1; tmp [2][1] = 1;
                tmp[3][0] =-1; tmp [3][1] = 2;
                break;
            case L_MA:
            case D_MA: // both manors
                numSquares = 4;
                tmp[0][0] = 0; tmp [0][1] = 0;
                tmp[1][0] = 0; tmp [1][1] = 1;
                tmp[2][0] = 1; tmp [2][1] = 1;
                tmp[3][0] =-1; tmp [3][1] = 1;
                break;
            case L_TO:
            case D_TO: // both towers
                numSquares = 5;
                tmp[0][0] = 0; tmp [0][1] = 0;
                tmp[1][0] = 0; tmp [1][1] = 1;
                tmp[2][0] =-1; tmp [2][1] = 1;
                tmp[3][0] =-1; tmp [3][1] = 2;
                tmp[4][0] =-2; tmp [4][1] = 2;
                break;
            case L_IF:
            case D_IF: // both infirmaries
                numSquares = 5;
                tmp[0][0] = 0; tmp [0][1] = 0;
                tmp[1][0] = 0; tmp [1][1] = 1;
                tmp[2][0] = 0; tmp [2][1] =-1;
                tmp[3][0] = 1; tmp [3][1] = 0;
                tmp[4][0] =-1; tmp [4][1] = 0;
                break;
            case L_CS:
            case D_CS: // both castles
                numSquares = 5;
                tmp[0][0] = 0; tmp [0][1] = 0;
                tmp[1][0] =-1; tmp [1][1] = 0;
                tmp[2][0] =-1; tmp [2][1] = 1;
                tmp[3][0] =-1; tmp [3][1] = 2;
                tmp[4][0] = 0; tmp [4][1] = 2;
                break;
            case L_AC: // light academy
                numSquares = 5;
                tmp[0][0] = 0; tmp [0][1] = 0;
                tmp[1][0] = 0; tmp [1][1] = 1;
                tmp[2][0] =-1; tmp [2][1] = 1;
                tmp[3][0] = 0; tmp [3][1] = 2;
                tmp[4][0] = 1; tmp [4][1] = 2;
                break;
            case D_AC:
                numSquares = 5;
                tmp[0][0] = 0; tmp [0][1] = 0;
                tmp[1][0] = 0; tmp [1][1] = 1;
                tmp[2][0] = 1; tmp [2][1] = 1;
                tmp[3][0] = 0; tmp [3][1] = 2;
                tmp[4][0] =-1; tmp [4][1] = 2;
                break;
            default:
                numSquares = 0;

        }

        //convert piece north offsets to game coordinates
        switch (orientation){
            case NORTH:
                for (i = 0;i < numSquares;i++) {
                    coords[i][0] = x + tmp[i][0];
                    coords[i][1] = y + tmp[i][1];
                }
                break;
            case EAST:
                for (i = 0;i < numSquares;i++) {
                    coords[i][0] = x - tmp[i][1];
                    coords[i][1] = y + tmp[i][0];
                }
                break;
            case SOUTH:
                for (i = 0;i < numSquares;i++) {
                    coords[i][0] = x - tmp[i][0];
                    coords[i][1] = y - tmp[i][1];
                }
                break;
            case WEST:
                for (i = 0;i < numSquares;i++) {
                    coords[i][0] = x + tmp[i][1];
                    coords[i][1] = y - tmp[i][0];
                }
                break;
        }

        // return the number of squares to process in coords
        return numSquares;
    }

    // check a single square for room for a player's piece - claimed area and empty are both good
    private boolean checkSquare(int x, int y, Player player){

        //check board limits
        if (x < 0 || x > 9 || y < 0 || y > 9)
            return false; //out of bounds

        //check space on board
        if (player == Player.LIGHT)
            return board[x][y] == SquareState.EMPTY || board[x][y] == SquareState.LIGHTCLAIM;
        else //dark
            return board[x][y] == SquareState.EMPTY || board[x][y] == SquareState.DARKCLAIM;
    }

    private boolean checkSquares(int numSquares, int[][] coordinates, Player player) {
     int x, y;
        for (int i = 0; i < numSquares; i++) {
            x = coordinates[i][0];
            y = coordinates[i][1];
            if (!checkSquare(x, y, player))
                return false;
        }
        return true;
    }

    // move class holds the record of moves made in a linked list. Useful for save/load and piece capture
    class Move {
        public Piece piece;
        public Orientation orientation;
        public int x,y;
        public Move nextMove;
        public Player player;

        public Move ( Piece piece, Orientation orientation, int x, int y, Player player){
            this.piece = piece;
            this.orientation = orientation;
            this.x = x;
            this.y = y;
            this.player = player;
            this.nextMove = null;
        }

        @Override
        public String toString(){
            return player.toString() + ": " + piece.getCode() + orientation.toLetter() + Character.toString((char)(x + (int)'A')) + Integer.toString(y+1);
        }

        public int numMoves(){
            if (nextMove == null)
                return 1;
            else
                return nextMove.numMoves() + 1;
        }

    }
    
    // a linked list of 2-D coordinates with some team logic
    class Square {
        public int x, y;
        public Square nextSquare;
        
        public Square(int x, int y){
            this.x = x;
            this.y = y;
            this.nextSquare = null;
        }


        // get this coordinate's state from the board
        public SquareState getState(){
            return GameState.this.board[this.x][this.y];
        }

        // is this coordinate filled with a friendly piece?
        public boolean isAllied(Player player){
            SquareState state = this.getState();
            if (player == Player.LIGHT)
                return  state == SquareState.LIGHTPIECE ||
                        state == SquareState.LIGHTCLAIM ||
                        state == SquareState.LIGHTPIECE_ORIGIN;
            else //dark
                return  state == SquareState.DARKPIECE ||
                        state == SquareState.DARKCLAIM ||
                        state == SquareState.DARKPIECE_ORIGIN;
        }

        // is this coordinate the origin of an enamy piece or cathedral? used for captures
        public boolean isEnemyOrigin(Player player){
            SquareState state = this.getState();
            if (player == Player.LIGHT)
                return  state == SquareState.DARKPIECE_ORIGIN ||
                        state == SquareState.CATHEDRALPIECE_ORIGIN;
            else
                return  state == SquareState.LIGHTPIECE_ORIGIN ||
                        state == SquareState.CATHEDRALPIECE_ORIGIN;
        }

        public void markChecked(){
            GameState.this.checkedsquares[this.x][this.y] = true;
        }

        public boolean isChecked(){
            return GameState.this.checkedsquares[this.x][this.y];
        }

        @Override
        public String toString() {
            //if (nextSquare == null)
                return "(" + Character.toString((char)((int)'A' + x)) + ", " + Integer.toString(y+1) + ")";
            //else
                //return "(" + Character.toString('A' + (char)x) + ", " + Integer.toString(y+1) + "), " + nextSquare.toString();
        }
    }
}

