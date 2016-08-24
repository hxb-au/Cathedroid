package au.id.hxb.cathedroid.mechanics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hayden on 2016/07/06.
 */

public enum Symmetry {
    NONE(new Orientation[] {Orientation.NORTH, Orientation.EAST, Orientation.SOUTH, Orientation.WEST}),
    SYM_180(new Orientation[] {Orientation.NORTH, Orientation.EAST}),
    SYM_90(new Orientation[] {Orientation.NORTH});

    private Orientation[] uniqueOrientations;

    Symmetry(Orientation[] uniqueOrientations){
        this.uniqueOrientations = uniqueOrientations;
    }

    public Orientation[] getUniqueOrientations(){
        return uniqueOrientations;
    }
}
