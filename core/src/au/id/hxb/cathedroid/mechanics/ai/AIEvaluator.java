package au.id.hxb.cathedroid.mechanics.ai;

import java.util.EnumMap;

import au.id.hxb.cathedroid.mechanics.Piece;
import au.id.hxb.cathedroid.mechanics.Player;
import au.id.hxb.cathedroid.mechanics.SquareState;

/**
 * Created by Hayden on 3/05/2016.
 */
public interface AIEvaluator {

    float evaluate(SquareState[][] board, EnumMap<Piece, Boolean> pieceAvailable, Player aiPlayer, boolean isGameOver);

}