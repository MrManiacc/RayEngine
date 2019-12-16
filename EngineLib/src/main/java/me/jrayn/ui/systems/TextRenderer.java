package me.jrayn.ui.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import me.jrayn.ui.components.Layout;
import me.jrayn.ui.components.Style;
import me.jrayn.ui.components.Text;


import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Calculates the text bounds of the text
 */
@All({Text.class, Layout.class, Style.class})
public class TextRenderer extends IteratingSystem {
    protected ComponentMapper<Text> textComponents;
    protected ComponentMapper<Layout> layoutComponents;
    protected ComponentMapper<Style> styleComponents;
    private IGuiRenderable guiRenderable;
    private float[] bounds = new float[4];

    public TextRenderer(IGuiRenderable guiRenderable) {
        this.guiRenderable = guiRenderable;
    }


    /**
     * Calculate the text bounding box size
     *
     * @param entityId the node with text
     */
    protected void process(int entityId) {
        Text text = textComponents.get(entityId);
        Style style = styleComponents.get(entityId);
        Layout layout = layoutComponents.get(entityId);
        if (style.isComputeSize()) {
            long vg = guiRenderable.getVg();
            nvgSave(vg);
            nvgFontFace(vg, style.getTextFamily());
            nvgFontSize(vg, style.getTextSize());
            nvgTextBounds(vg, 0, 0, text.getText(), bounds);
            nvgRestore(vg);
            float width = bounds[2];
            layout.setWidthAbsolute(width);
        }
        guiRenderable.drawText(layout, style, text);
    }

    /**
     * Called after  the rendering ends, used to close frames etc
     */
    protected void end() {
        guiRenderable.endDraw();
    }
}
