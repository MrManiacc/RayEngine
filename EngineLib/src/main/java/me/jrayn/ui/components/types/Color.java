package me.jrayn.ui.components.types;

import lombok.Getter;
import org.lwjgl.nanovg.NVGColor;

/**
 * Represents a color, can be created with
 * rgb, rgba, or a hex (with alpha or no alpha,
 * as well as with a # at the start or not)
 */
public class Color {
    private int r, g, b, a;
    private NVGColor nanoColor;

    /**
     * Creates a color with the given
     * rgb value, alpha is automatically
     * fully visible.
     *
     * @param r red component
     * @param g green component
     * @param b blue component
     */
    public Color(float r, float g, float b) {
        this(r, g, b, 255.0f);
    }

    /**
     * Creates a color with given rgba value,
     * can be in range of 0-1, will be auto-converted
     * to the range of 0-255 if so.
     *
     * @param r red component
     * @param g green component
     * @param b blue component
     * @param a alpha component
     */
    public Color(float r, float g, float b, float a) {
        if (r <= 1.0f)
            this.r = (int) (r * 255f);
        else
            this.r = (int) r;
        if (g <= 1.0f)
            this.g = (int) (g * 255.0f);
        else
            this.g = (int) g;
        if (b <= 1.0f)
            this.b = (int) (b * 255.0f);
        else
            this.b = (int) b;
        if (a <= 1.0f)
            this.a = (int) (a * 255.0f);
        else
            this.a = (int) a;

        this.nanoColor = NVGColor.create();
        this.nanoColor.r(r / 255.0f);
        this.nanoColor.g(g / 255.0f);
        this.nanoColor.b(b / 255.0f);
        this.nanoColor.a(a / 255.0f);
    }

    /**
     * Creates a color based on a hex value
     * the hex can start with # or not, and
     * can contain an alpha value or not.
     *
     * @param colorStr the hex value of the color
     */
    public Color(String colorStr) {
        if (colorStr.startsWith("#")) {
            this.r = Integer.valueOf(colorStr.substring(1, 3), 16);
            this.g = Integer.valueOf(colorStr.substring(3, 5), 16);
            this.b = Integer.valueOf(colorStr.substring(5, 7), 16);
            if (colorStr.length() == 9)
                this.a = Integer.valueOf(colorStr.substring(7, 9), 16);
            else
                this.a = 255;
        } else {
            this.r = Integer.valueOf(colorStr.substring(0, 2), 16);
            this.g = Integer.valueOf(colorStr.substring(2, 4), 16);
            this.b = Integer.valueOf(colorStr.substring(4, 6), 16);
            if (colorStr.length() == 8)
                this.a = Integer.valueOf(colorStr.substring(6, 8), 16);
            else
                this.a = 255;
        }
        this.nanoColor = NVGColor.create();
        this.nanoColor.r(r / 255.0f);
        this.nanoColor.g(g / 255.0f);
        this.nanoColor.b(b / 255.0f);
        this.nanoColor.a(a / 255.0f);
    }

    /**
     * Gets a color by it's name from the registered colors
     *
     * @param name the name of the color
     * @return the given color
     */
    public static Color of(String name) {
        return Colors.get(name);
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public int getA() {
        return a;
    }

    public NVGColor getNanoColor() {
        return nanoColor;
    }

    @Override
    public String toString() {
        return "Color{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                ", a=" + a +
                '}';
    }
}
