package me.jrayn.core;

import me.jrayn.ui.components.Layout;
import me.jrayn.ui.components.Style;
import me.jrayn.ui.components.Text;

import javax.annotation.Nullable;

public interface IGuiNode {
    Layout getLayout();

    Style getStyle();

    @Nullable
    Text getText();
}
