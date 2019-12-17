package me.jrayn.ui.components.types;

public enum TextAlign {
    LEFT("left"),
    RIGHT("right"),
    CENTER("center");

    private String literal;

    TextAlign(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }

    public static TextAlign fromString(String value) {
        for (TextAlign align : TextAlign.values()) {
            if (align.getLiteral().toLowerCase().equalsIgnoreCase(value))
                return align;
        }
        return TextAlign.CENTER;
    }
}
