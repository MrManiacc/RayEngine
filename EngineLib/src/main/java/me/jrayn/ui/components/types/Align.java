package me.jrayn.ui.components.types;

import static org.lwjgl.util.yoga.Yoga.*;

public enum Align {
    AUTO(YGAlignAuto, "auto"),
    START(YGAlignFlexStart, "start"),
    END(YGAlignFlexEnd, "end"),
    CENTER(YGAlignCenter, "center"),
    SPACE_BETWEEN(YGAlignSpaceBetween, "space-between"),
    SPACE_AROUND(YGAlignSpaceAround, "space-around"),
    BASE_LINE(YGAlignBaseline, "base-line"),
    STRETCH(YGAlignStretch, "stretch");

    private int value;
    private String literal;

    Align(int value, String literal) {
        this.value = value;
        this.literal = literal;
    }

    public int getValue() {
        return value;
    }

    public String getLiteral() {
        return literal;
    }

    public static Align fromString(String value) {
        for (Align align : Align.values()) {
            if (align.getLiteral().toLowerCase().equalsIgnoreCase(value))
                return align;
        }
        return Align.AUTO;
    }
}
