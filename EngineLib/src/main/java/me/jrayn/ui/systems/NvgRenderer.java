package me.jrayn.ui.systems;

import me.jrayn.core.IGameEngine;
import me.jrayn.core.IWindow;
import me.jrayn.ui.components.Layout;
import me.jrayn.ui.components.Style;
import me.jrayn.ui.components.Text;
import me.jrayn.ui.components.types.Color;
import me.jrayn.ui.components.types.Dimension;
import me.jrayn.ui.components.types.Edge;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.nanovg.NanoVGGL2;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.lwjgl.BufferUtils.createByteBuffer;
import static org.lwjgl.nanovg.NanoVG.*;

/**
 * Renders using nanovg
 */
public class NvgRenderer implements IGuiRenderable {
    private long vg = 0;
    private IWindow window;

    public NvgRenderer(IGameEngine gameEngine) {
        this.window = gameEngine.getWindow();
    }

    /**
     * Creates the nanovg context
     */
    public void init() {
        boolean modernOpenGL = (GL11.glGetInteger(GL30.GL_MAJOR_VERSION) > 3) || (GL11.glGetInteger(GL30.GL_MAJOR_VERSION) == 3 && GL11.glGetInteger(GL30.GL_MINOR_VERSION) >= 2);
        if (modernOpenGL) {
            int flags = NanoVGGL3.NVG_STENCIL_STROKES | NanoVGGL3.NVG_ANTIALIAS;
            vg = NanoVGGL3.nvgCreate(flags);
        } else {
            int flags = NanoVGGL2.NVG_STENCIL_STROKES | NanoVGGL2.NVG_ANTIALIAS;
            vg = NanoVGGL2.nvgCreate(flags);
        }
        try {
            loadFont("light");
            loadFont("regular");
            loadFont("bold");
            loadFont("semibold");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Load the font family
     */
    private void loadFont(String font) throws IOException {
        ByteBuffer buffer;

        Path path = Paths.get("src/main/resources/fonts/" + font + ".ttf");
        if (Files.isReadable(path)) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = createByteBuffer((int) fc.size() + 1);
                while (fc.read(buffer) != -1) {
                    ;
                }
            }
        } else {
            try (InputStream source = Window.class.getClassLoader().getResourceAsStream(font); ReadableByteChannel rbc = Channels.newChannel(source)) {
                buffer = createByteBuffer(512 * 1024);

                while (true) {
                    int bytes = rbc.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    if (buffer.remaining() == 0) {
                        ByteBuffer newBuffer = BufferUtils.createByteBuffer(buffer.capacity() * 3 / 2);
                        buffer.flip();
                        newBuffer.put(buffer);
                        buffer = newBuffer;
                    }
                }
            }
        }
        buffer.flip();
        nvgCreateFontMem(vg, font, buffer, 0);
    }


    /**
     * Begins the frame for nanovg
     */
    public void beginDraw() {
        nvgBeginFrame(vg, window.getWidth(), window.getHeight(), 1);
        nvgSave(vg);
    }

    /**
     * renders each element using nanovg
     *
     * @param layout the layout for the element
     * @param style  the style for the element
     */
    public void drawQuad(Layout layout, Style style) {
        if (style.isRender()) {
            nvgSave(vg);
            if (layout.getParent() != null) {
                Vector2f offset = layout.getOffset();
                nvgTranslate(vg, offset.x, offset.y);
            }
            if (!style.isRounded()) {
                if (style.isDropShadow())
                    drawOffsetRect(layout, style.getDropShadowOffset(), style.getDropShadowColor());
                drawRect(layout, style.getBackgroundColor());
                if (layout.hasBorder()) {
                    drawBorder(layout, style.getBorderColor());
                }
            } else {
                if (style.isDropShadow()) {
                    drawRoundedOffsetRect(layout, style.getDropShadowOffset(), style.getDropShadowColor(), style.getBorderRadius());
                }
                drawRoundedRect(layout, style.getBackgroundColor(), style.getBorderRadius());
                if (layout.hasBorder())
                    drawRoundedBorder(layout, style.getBorderColor(), style.getBorderRadius());
            }
            nvgRestore(vg);
        }
    }

    /**
     * Draw a normal rectangle
     *
     * @param layout layout of the rectangle
     * @param color  color of the rectangle
     */
    private void drawRect(Layout layout, Color color) {
        nvgBeginPath(vg);
        nvgRect(vg, layout.getPosition(Edge.LEFT), layout.getPosition(Edge.TOP), layout.getSize(Dimension.WIDTH), layout.getSize(Dimension.HEIGHT));
        nvgFillColor(vg, color.getNanoColor());
        nvgFill(vg);
    }

    /**
     * Draw a normal rectangle
     *
     * @param layout layout of the rectangle
     * @param color  color of the rectangle
     */
    private void drawBorder(Layout layout, Color color) {
        float left = layout.getBorder(Edge.LEFT);
        if (left > 0) {
            nvgBeginPath(vg);
            nvgRect(vg, layout.getPosition(Edge.LEFT), layout.getPosition(Edge.TOP), left, layout.getSize(Dimension.HEIGHT));
            nvgFillColor(vg, color.getNanoColor());
            nvgFill(vg);
        }

        float right = layout.getBorder(Edge.RIGHT);
        if (right > 0) {
            nvgBeginPath(vg);
            nvgRect(vg, layout.getPosition(Edge.LEFT) + layout.getSize(Dimension.WIDTH), layout.getPosition(Edge.TOP), right, layout.getSize(Dimension.HEIGHT));
            nvgFillColor(vg, color.getNanoColor());
            nvgFill(vg);
        }

        float top = layout.getBorder(Edge.TOP);
        if (top > 0) {
            nvgBeginPath(vg);
            nvgRect(vg, layout.getPosition(Edge.LEFT), layout.getPosition(Edge.TOP), layout.getSize(Dimension.WIDTH) - top, top);
            nvgFillColor(vg, color.getNanoColor());
            nvgFill(vg);
        }
        float bottom = layout.getBorder(Edge.BOTTOM);
        if (bottom > 0) {
            nvgBeginPath(vg);
            nvgRect(vg, layout.getPosition(Edge.LEFT), (layout.getPosition(Edge.TOP) + layout.getSize(Dimension.HEIGHT)), layout.getSize(Dimension.WIDTH), bottom);
            nvgFillColor(vg, color.getNanoColor());
            nvgFill(vg);
        }
    }


    /**
     * Draw a normal rectangle
     *
     * @param layout layout of the rectangle
     * @param color  color of the rectangle
     */
    private void drawRoundedBorder(Layout layout, Color color, Vector4f radius) {
        nvgBeginPath(vg);
        nvgRoundedRectVarying(vg, layout.getPosition(Edge.LEFT), layout.getPosition(Edge.TOP),
                layout.getSize(Dimension.WIDTH), layout.getSize(Dimension.HEIGHT),
                radius.x, radius.y,
                radius.z, radius.w);
        nvgStrokeColor(vg, color.getNanoColor());
        nvgStroke(vg);
    }

    /**
     * Draw a normal rectangle
     *
     * @param layout layout of the rectangle
     * @param color  color of the rectangle
     */
    private void drawOffsetRect(Layout layout, Vector2f offset, Color color) {
        nvgBeginPath(vg);
        nvgRect(vg, layout.getPosition(Edge.LEFT) + offset.x, layout.getPosition(Edge.TOP) + offset.y, layout.getSize(Dimension.WIDTH) + offset.x, layout.getSize(Dimension.HEIGHT) + offset.y);
        nvgFillColor(vg, color.getNanoColor());
        nvgFill(vg);
    }

    /**
     * Draw a normal rectangle
     *
     * @param layout layout of the rectangle
     * @param color  color of the rectangle
     */
    private void drawRoundedOffsetRect(Layout layout, Vector2f offset, Color color, Vector4f radius) {
        nvgBeginPath(vg);
        nvgRoundedRectVarying(vg, layout.getPosition(Edge.LEFT) + offset.x, layout.getPosition(Edge.TOP) + offset.y, layout.getSize(Dimension.WIDTH) + offset.x, layout.getSize(Dimension.HEIGHT) + offset.y, radius.x, radius.y, radius.z, radius.w);
        nvgFillColor(vg, color.getNanoColor());
        nvgFill(vg);
    }

    /**
     * Draw a normal a rounded rectangle
     *
     * @param layout layout of the rectangle
     * @param color  color of the rectangle
     * @param radius the radius of the rectangle
     */
    private void drawRoundedRect(Layout layout, Color color, Vector4f radius) {
        nvgBeginPath(vg);
        nvgRoundedRectVarying(vg, layout.getPosition(Edge.LEFT), layout.getPosition(Edge.TOP),
                layout.getSize(Dimension.WIDTH), layout.getSize(Dimension.HEIGHT),
                radius.x, radius.y,
                radius.z, radius.w);
        nvgFillColor(vg, color.getNanoColor());
        nvgFill(vg);
    }

    /**
     * Ends the frame for nanovg
     */
    public void endDraw() {
        nvgRestore(vg);
        nvgEndFrame(vg);
    }

    /**
     * Used to get the vg instance in other places
     *
     * @return gets the vg context
     */
    public long getVg() {
        return vg;
    }

    /**
     * Draws a text component
     *
     * @param layout the layout for the element
     * @param style  the style for the element
     * @param text   the text component
     */
    public void drawText(Layout layout, Style style, Text text) {
        drawQuad(layout, style);
        Vector2f offset = layout.getOffset();
        nvgSave(vg);
        nvgFontFace(vg, style.getTextFamily());
        nvgFontSize(vg, style.getTextSize());
        nvgTextAlign(vg, NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE);
        nvgTranslate(vg, offset.x, offset.y + layout.getSize(Dimension.HEIGHT));
        nvgFillColor(vg, style.getTextColor().getNanoColor());
        nvgText(vg, layout.getPosition(Edge.LEFT) + (layout.getSize(Dimension.WIDTH) / 2), (layout.getPosition(Edge.TOP) - (layout.getSize(Dimension.HEIGHT) / 2)) - 1, text.getText());
        nvgRestore(vg);
    }
}
