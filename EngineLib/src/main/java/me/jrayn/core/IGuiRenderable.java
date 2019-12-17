package me.jrayn.core;

import me.jrayn.ui.components.Layout;
import me.jrayn.ui.components.Style;
import me.jrayn.ui.components.Text;


/**
 * Represents an interface from drawing 2d
 * graphics
 */
public interface IGuiRenderable {
    /**
     * Called once when the renderer is created
     */
    default void init() {
    }

    /**
     * Called before drawing begins
     */
    default void beginDraw() {
    }

    /**
     * Called once for each element
     *
     * @param layout the layout for the element
     * @param style  the style for the element
     */
    void drawQuad(Layout layout, Style style);


    /**
     * Called after the drawing ends
     */
    default void endDraw() {
    }

    /**
     * Get the vg instance
     *
     * @return the vg instance
     */
    long getVg();

    /**
     * Draws a text component
     *
     * @param layout the layout for the element
     * @param style  the style for the element
     * @param text   the text component
     */
    void drawText(Layout layout, Style style, Text text);
}
