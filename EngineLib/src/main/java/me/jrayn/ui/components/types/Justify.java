package me.jrayn.ui.components.types;

import lombok.Getter;

import static org.lwjgl.util.yoga.Yoga.*;

public enum Justify {
    START(YGJustifyFlexStart),
    END(YGJustifyFlexEnd),
    CENTER(YGJustifyCenter),
    SPACE_BETWEEN(YGJustifySpaceBetween),
    SPACE_AROUND(YGJustifySpaceAround),
    SPACE_EVENLY(YGJustifySpaceEvenly);
    @Getter
    private int value;

    Justify(int value) {
        this.value = value;
    }
}
