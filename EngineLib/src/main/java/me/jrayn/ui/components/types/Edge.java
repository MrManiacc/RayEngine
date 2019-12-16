package me.jrayn.ui.components.types;

import lombok.Getter;

import static org.lwjgl.util.yoga.Yoga.*;

public enum Edge {
    LEFT(YGEdgeLeft), TOP(YGEdgeTop), RIGHT(YGEdgeRight), BOTTOM(YGEdgeBottom), HORIZONTAL(YGEdgeHorizontal), VERTICAL(YGEdgeVertical);
    @Getter
    private int value;

    Edge(int value) {
        this.value = value;
    }
}
