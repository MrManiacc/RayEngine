package me.jrayn.ui.components.types;

import lombok.Getter;

import static org.lwjgl.util.yoga.Yoga.*;

public enum Align {
    AUTO(YGAlignAuto),
    START(YGAlignFlexStart),
    END(YGAlignFlexEnd),
    CENTER(YGAlignCenter),
    SPACE_BETWEEN(YGAlignSpaceBetween),
    SPACE_AROUND(YGAlignSpaceAround),
    BASE_LINE(YGAlignBaseline),
    STRETCH(YGAlignStretch);

    @Getter
    private int value;

    Align(int value) {
        this.value = value;
    }
}
