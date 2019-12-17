package me.jrayn.ui.components.types;

import static org.lwjgl.util.yoga.Yoga.*;

public enum Edge {
    LEFT(YGEdgeLeft, "left"), TOP(YGEdgeTop, "top"), RIGHT(YGEdgeRight, "right"),
    BOTTOM(YGEdgeBottom, "bottom"), HORIZONTAL(YGEdgeHorizontal, "horizontal"),
    VERTICAL(YGEdgeVertical, "vertical");

    private int value;
    private String literal;

    Edge(int value, String literal) {
        this.value = value;
        this.literal = literal;
    }

    public int getValue() {
        return value;
    }

    public String getLiteral() {
        return literal;
    }

    public static Edge fromString(String value) {
        for (Edge edge : Edge.values()) {
            if (edge.getLiteral().toLowerCase().equalsIgnoreCase(value))
                return edge;
        }
        return Edge.LEFT;
    }

}
