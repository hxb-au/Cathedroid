package au.id.hxb.cathedroid.mechanics;

/**
 * Created by Hayden on 3/05/2016.
 */ // the board is a 10x10 array of these. piece origins are used for capture and claim checks
    // each piece has 1 origin and the rest of its volume is xxxPIECE
public enum SquareState {
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
