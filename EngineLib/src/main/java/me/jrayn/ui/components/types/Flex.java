package me.jrayn.ui.components.types;

import lombok.Getter;

import static org.lwjgl.util.yoga.Yoga.*;

public enum Flex {
    COLUMN(YGFlexDirectionColumn),
    COLUMN_REVERSED(YGFlexDirectionColumnReverse),
    ROW(YGFlexDirectionRow),
    ROW_REVERSED(YGFlexDirectionRowReverse);

    @Getter
    private int value;

    Flex(int value) {
        this.value = value;
    }

}
