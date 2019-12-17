package me.jrayn.ui.components.types;

import org.lwjgl.util.yoga.Yoga;

public enum Wrap {
    NO_WRAP(Yoga.YGWrapNoWrap, "no-wrap"), WRAP(Yoga.YGWrapWrap, "wrap"), WRAP_REVERSE(Yoga.YGWrapReverse, "wrap-reverse");
    private int value;
    private String literal;

    Wrap(int value, String literal) {
        this.value = value;
        this.literal = literal;
    }

    public int getValue() {
        return value;
    }

    public static Wrap fromString(String value) {
        for(Wrap wrap : Wrap.values()){
            if(wrap.literal.equalsIgnoreCase(value))
                return wrap;
        }
        return Wrap.NO_WRAP;
    }
}
