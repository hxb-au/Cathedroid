package au.id.hxb.cathedroid.mechanics;

/**
 * Created by Hayden on 4/04/2016.
 */
public enum Piece {
    CA ("Cathedral", "CA", null,6, Symmetry.NONE),
    L_TO ("Light Tower", "TO", Player.LIGHT,5, Symmetry.NONE),
    L_IF ("Light Infirmary", "IF", Player.LIGHT,5, Symmetry.SYM_90),
    L_CS ("Light Castle", "CS", Player.LIGHT,5, Symmetry.NONE),
    L_AC ("Light Academy", "AC", Player.LIGHT,5, Symmetry.SYM_90),
    L_SQ ("Light Square", "SQ", Player.LIGHT,4, Symmetry.SYM_90),
    L_AB  ("Light Abbey", "AB", Player.LIGHT,4, Symmetry.SYM_180),
    L_MA ("Light Manor", "MA", Player.LIGHT,4, Symmetry.NONE),
    L_IN1 ("Light Inn 1", "IN", Player.LIGHT,3, Symmetry.NONE),
    L_IN2 ("Light Inn 2", "IN", Player.LIGHT,3, Symmetry.NONE),
    L_BR ("Light Bridge", "BR", Player.LIGHT,3, Symmetry.SYM_180),
    L_ST1 ("Light Stable 1", "ST", Player.LIGHT,2, Symmetry.SYM_180),
    L_ST2 ("Light Stable 2", "ST", Player.LIGHT,2, Symmetry.SYM_180),
    L_TA1 ("Light Tavern 1", "TA", Player.LIGHT,1, Symmetry.SYM_90),
    L_TA2 ("Light Tavern 2", "TA", Player.LIGHT,1, Symmetry.SYM_90),
    D_TO ("Dark Tower", "TO", Player.DARK,5, Symmetry.NONE),
    D_IF ("Dark Infirmary", "IF", Player.DARK,5, Symmetry.SYM_90),
    D_CS ("Dark Castle", "CS", Player.DARK,5, Symmetry.NONE),
    D_AC ("Dark Academy", "AC", Player.DARK,5, Symmetry.NONE),
    D_SQ ("Dark Square", "SQ", Player.DARK,4, Symmetry.SYM_90),
    D_AB  ("Dark Abbey", "AB", Player.DARK,4, Symmetry.SYM_180),
    D_MA ("Dark Manor", "MA", Player.DARK,4, Symmetry.NONE),
    D_IN1 ("Dark Inn 1", "IN", Player.DARK,3, Symmetry.NONE),
    D_IN2 ("Dark Inn 2", "IN", Player.DARK,3, Symmetry.NONE),
    D_BR ("Dark Bridge", "BR", Player.DARK,3, Symmetry.SYM_180),
    D_ST1 ("Dark Stable 1", "ST", Player.DARK,2, Symmetry.SYM_180),
    D_ST2 ("Dark Stable 2", "ST", Player.DARK,2, Symmetry.SYM_180),
    D_TA1 ("Dark Tavern 1", "TA", Player.DARK,1, Symmetry.SYM_90),
    D_TA2 ("Dark Tavern 2", "TA", Player.DARK,1, Symmetry.SYM_90);


    
    private final String name;
    private final String code;
    private final Player owner;
    private final int size;
    private final Symmetry symmetry;
    
    private Piece(String name, String code, Player owner, int size, Symmetry symmetry){
        this.name = name;
        this.code = code;
        this.owner = owner;
        this.size = size;
        this.symmetry = symmetry;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Player getOwner() {
        return owner;
    }

    public int getSize() {
        return size;
    }

    public Orientation[] getUniqueOrientations() {
        return symmetry.getUniqueOrientations();
    }

}
