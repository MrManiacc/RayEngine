package me.jrayn.ui.components.types;

import lombok.Getter;
import org.lwjgl.util.yoga.Yoga;

public enum Dimension {
    WIDTH(Yoga.YGDimensionWidth), HEIGHT(Yoga.YGDimensionHeight);
    @Getter
    private int value;

    Dimension(int value) {
        this.value = value;
    }
}
