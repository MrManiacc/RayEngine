package me.jrayn.ui.components.types;

import org.lwjgl.util.yoga.Yoga;

public enum Dimension {
    WIDTH(Yoga.YGDimensionWidth, "width"), HEIGHT(Yoga.YGDimensionHeight, "height");
    private int value;
    private String literal;

    Dimension(int value, String literal) {
        this.value = value;
        this.literal = literal;
    }

    public int getValue() {
        return value;
    }

    public String getLiteral() {
        return literal;
    }

    public static Dimension fromString(String value){
        for (Dimension dimension : Dimension.values()) {
            if (dimension.getLiteral().toLowerCase().equalsIgnoreCase(value))
                return dimension;
        }
        return Dimension.WIDTH;
    }
}
