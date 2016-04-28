package au.id.hxb.cathedroid.mechanics;

/**
 * Created by hxb on 4/04/2016.
 */
public enum Orientation {
    NORTH ("n"),
    EAST ("e"),
    SOUTH ("s"),
    WEST ("w");

    private final String letter;

    private Orientation(String letter){
        this.letter = letter;
    }

    public String toLetter(){
        return this.letter;
    }

}
