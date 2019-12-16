package me.jrayn.ui.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Exclude;
import com.artemis.systems.IteratingSystem;
import me.jrayn.core.IWindow;
import me.jrayn.ui.components.Layout;
import me.jrayn.ui.components.Style;
import me.jrayn.ui.components.Text;

@All({Style.class, Layout.class})
@Exclude(Text.class)
public class GuiRenderer extends IteratingSystem {
    protected ComponentMapper<Style> styles;
    protected ComponentMapper<Layout> layouts;
    private IGuiRenderable guiRenderable;
    private IWindow window;

    public GuiRenderer(IWindow window, IGuiRenderable guiRenderable) {
        this.window = window;
        this.guiRenderable = guiRenderable;
    }

    /**
     * called once the gui renderer is ready to be created
     */
    protected void initialize() {
        guiRenderable.init();
    }

    /**
     * Called before the rendering starts, used to create frames etc
     */
    protected void begin() {
        Layout.root.compute();
        guiRenderable.beginDraw();
    }

    /**
     * render the gui component
     *
     * @param entityId
     */
    protected void process(int entityId) {
        Layout layout = layouts.get(entityId);
        Style style = styles.get(entityId);
        guiRenderable.drawQuad(layout, style);
    }

}
