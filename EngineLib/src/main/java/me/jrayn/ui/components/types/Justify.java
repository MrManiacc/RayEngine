package me.jrayn.ui.components.types;

import static org.lwjgl.util.yoga.Yoga.*;

public enum Justify {
    START(YGJustifyFlexStart, "start"),
    END(YGJustifyFlexEnd, "end"),
    CENTER(YGJustifyCenter, "center"),
    SPACE_BETWEEN(YGJustifySpaceBetween, "space-between"),
    SPACE_AROUND(YGJustifySpaceAround, "space-around"),
    SPACE_EVENLY(YGJustifySpaceEvenly, "space-evenly");
    private int value;
    private String literal;

    Justify(int value, String literal) {
        this.value = value;
        this.literal = literal;
    }

    public int getValue() {
        return value;
    }

    public String getLiteral() {
        return literal;
    }

    public static Justify fromString(String value) {
        for (Justify align : Justify.values()) {
            if (align.getLiteral().toLowerCase().equalsIgnoreCase(value))
                return align;
        }
        return Justify.START;
    }

}
