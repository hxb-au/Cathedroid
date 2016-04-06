package au.id.hxb.cathedroid.Mechanics;


import com.badlogic.gdx.Gdx;

import java.util.Arrays;

/**
 * Created by hxb on 4/04/2016.
 */
public class GameState {
    private SquareState[][] board;
    private int [][] coordinates, tmp;

    public GameState(){
        int i,j;
        board = new SquareState[10][10];
        //Arrays.fill(board, SquareState.EMPTY);
        coordinates = new int[][] {{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}};
        tmp = new int[][] {{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}};
        //Arrays.fill(coordinates, 0);
        //Arrays.fill(tmp , 0);

        //fill board array
        for (i = 0; i < board.length; i++) {
            for (j = 0; j < board[i].length; j++) {
                board[i][j] = SquareState.EMPTY;
            }
        }

        Gdx.app.log("GameState Created:", Arrays.deepToString(tmp));
    }

    public Player whoseTurn(){ return Player.LIGHT; }

    enum SquareState {
        EMPTY,
        DARKPIECE,
        LIGHTPIECE,
        CATHEDRALPIECE,
        DARKCLAIM,
        LIGHTCLAIM
    }

    public boolean attemptMove(Piece piece, Orientation orientation, int pieceX, int pieceY, Player player){
        int i;
        SquareState state;
        int x, y;
        int numSquares;

        //load translated and rotated piece coverage in to coordinates array
        numSquares = getSquares(piece, orientation, pieceX, pieceY, coordinates);

        //check each coordinate
        for (i = 0; i < numSquares; i++) {
            x = coordinates[i][0];
            y = coordinates[i][1];
            if (!checkSquare(x, y, player))
                return false;
        }

        //still here? then the piece fits
        //record piece in board

        // which colour piece is it?
        if (piece == Piece.CA)
            state = SquareState.CATHEDRALPIECE;
        else if (player == Player.LIGHT)
            state = SquareState.LIGHTPIECE;
        else //dark
           state = SquareState.DARKPIECE;

        //copy it in using coordinates generated for checking fit
        for (i = 0; i < numSquares; i++) {
            x = coordinates[i][0];
            y = coordinates[i][1];
            board[x][y] = state;
        }

        // TODO record Move in list

        // TODO regenerate claims (and capture pieces)

        return true;
    }

    //put the piece's coordinates in the array and return the number of squares occupied
    private int getSquares( Piece piece, Orientation orientation, int x, int y, int[][] coords){
        int numSquares, i;

        switch (piece){

            case CA: //cathedral
                numSquares = 6;
                this.tmp[0][0] =  0; tmp [0][1] =  0;
                tmp[1][0] = -1; tmp [1][1] =  0;
                tmp[2][0] =  0; tmp [2][1] = -1;
                tmp[3][0] =  1; tmp [3][1] =  0;
                tmp[4][0] =  0; tmp [4][1] =  1;
                tmp[5][0] =  0; tmp [5][1] =  2;
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


    class Move {
        public Piece piece;
        public Orientation orientation;
        public int x,y;
        public Move nextMove;
        public Player player;

        public int numMoves(){
            if (nextMove == null)
                return 1;
            else
                return nextMove.numMoves() + 1;
        }

    }
}

