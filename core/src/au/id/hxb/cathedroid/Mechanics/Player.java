package au.id.hxb.cathedroid.mechanics;

/**
 * Created by hxb on 4/04/2016.
 */
public enum Player {
    LIGHT,
    DARK;

    public Player getOther(){
        return (this == LIGHT ? DARK : LIGHT);
    }
}
