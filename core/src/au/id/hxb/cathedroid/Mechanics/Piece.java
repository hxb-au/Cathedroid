package au.id.hxb.cathedroid.mechanics;

/**
 * Created by Hayden on 4/04/2016.
 */
public enum Piece {
    CA ("Cathedral", "CA", null),
    L_TA1 ("Light Tavern 1", "TA", Player.LIGHT),
    L_TA2 ("Light Tavern 2", "TA", Player.LIGHT),
    L_ST1 ("Light Stable 1", "ST", Player.LIGHT),
    L_ST2 ("Light Stable 2", "ST", Player.LIGHT),
    L_IN1 ("Light Inn 1", "IN", Player.LIGHT),
    L_IN2 ("Light Inn 2", "IN", Player.LIGHT),
    L_BR ("Light Bridge", "BR", Player.LIGHT),
    L_SQ ("Light Square", "SQ", Player.LIGHT),
    L_AB  ("Light Abbey", "AB", Player.LIGHT),
    L_MA ("Light Manor", "MA", Player.LIGHT),
    L_TO ("Light Tower", "TO", Player.LIGHT),
    L_IF ("Light Infirmary", "IF", Player.LIGHT),
    L_CS ("Light Castle", "CS", Player.LIGHT),
    L_AC ("Light Academy", "AC", Player.LIGHT),
    D_TA1 ("Dark Tavern 1", "TA", Player.DARK),
    D_TA2 ("Dark Tavern 2", "TA", Player.DARK),
    D_ST1 ("Dark Stable 1", "ST", Player.DARK),
    D_ST2 ("Dark Stable 2", "ST", Player.DARK),
    D_IN1 ("Dark Inn 1", "IN", Player.DARK),
    D_IN2 ("Dark Inn 2", "IN", Player.DARK),
    D_BR ("Dark Bridge", "BR", Player.DARK),
    D_SQ ("Dark Square", "SQ", Player.DARK),
    D_AB  ("Dark Abbey", "AB", Player.DARK),
    D_MA ("Dark Manor", "MA", Player.DARK),
    D_TO ("Dark Tower", "TO", Player.DARK),
    D_IF ("Dark Infirmary", "IF", Player.DARK),
    D_CS ("Dark Castle", "CS", Player.DARK),
    D_AC ("Dark Academy", "AC", Player.DARK);
    
    private final String name;
    private final String code;
    private final Player owner;
    
    private Piece(String name, String code, Player owner){
        this.name = name;
        this.code = code;
        this.owner = owner;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Player getOwner() { return owner; }
}
