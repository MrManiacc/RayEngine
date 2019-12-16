package me.jrayn.ui.components.types;

import lombok.Getter;
import org.lwjgl.util.yoga.Yoga;

public enum Wrap {
    NO_WRAP(Yoga.YGWrapNoWrap), WRAP(Yoga.YGWrapWrap), WRAP_REVERSE(Yoga.YGWrapReverse);
    @Getter
    private int value;

    Wrap(int value) {
        this.value = value;
    }
}
