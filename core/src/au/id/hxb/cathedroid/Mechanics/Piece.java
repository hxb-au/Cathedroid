package au.id.hxb.cathedroid.Mechanics;

/**
 * Created by Hayden on 4/04/2016.
 */
public enum Piece {
    CA ("Cathedral", "CA"),
    L_TA1 ("Light Tavern 1", "TA"),
    L_TA2 ("Light Tavern 2", "TA"),
    L_ST1 ("Light Stable 1", "ST"),
    L_ST2 ("Light Stable 2", "ST"),
    L_IN1 ("Light Inn 1", "IN"),
    L_IN2 ("Light Inn 2", "IN"),
    L_BR ("Light Bridge", "BR"),
    L_SQ ("Light Square", "SQ"),
    L_AB  ("Light Abbey", "AB"),
    L_MA ("Light Manor", "MA"),
    L_TO ("Light Tower", "TO"),
    L_IF ("Light Infirmary", "IF"),
    L_CS ("Light Castle", "CS"),
    L_AC ("Light Academy", "AC"),
    D_TA1 ("Dark Tavern 1", "TA"),
    D_TA2 ("Dark Tavern 2", "TA"),
    D_ST1 ("Dark Stable 1", "ST"),
    D_ST2 ("Dark Stable 2", "ST"),
    D_IN1 ("Dark Inn 1", "IN"),
    D_IN2 ("Dark Inn 2", "IN"),
    D_BR ("Dark Bridge", "BR"),
    D_SQ ("Dark Square", "SQ"),
    D_AB  ("Dark Abbey", "AB"),
    D_MA ("Dark Manor", "MA"),
    D_TO ("Dark Tower", "TO"),
    D_IF ("Dark Infirmary", "IF"),
    D_CS ("Dark Castle", "CS"),
    D_AC ("Dark Academy", "AC");
    
    private final String name;
    private final String code;
    
    private Piece(String name, String code){
        this.name = name;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
