package me.jrayn.ui.components.types;

import static org.lwjgl.util.yoga.Yoga.*;

public enum Flex {
    COLUMN(YGFlexDirectionColumn, "column"),
    COLUMN_REVERSED(YGFlexDirectionColumnReverse, "column-reversed"),
    ROW(YGFlexDirectionRow, "row"),
    ROW_REVERSED(YGFlexDirectionRowReverse, "row-reversed");

    private int value;
    private String literal;

    Flex(int value, String literal) {
        this.value = value;
        this.literal = literal;
    }

    public int getValue() {
        return value;
    }

    public static Flex fromString(String value) {
        for (Flex flex : Flex.values()) {
            if (flex.literal.equalsIgnoreCase(value)) {
                return flex;
            }
        }
        return Flex.COLUMN;
    }
}
