package me.jrayn.ui.components;

import com.artemis.Component;
import me.jrayn.ui.components.types.Color;
import me.jrayn.ui.components.types.Corner;
import me.jrayn.ui.components.types.TextAlign;
import org.joml.Vector2f;
import org.joml.Vector4f;

/**
 * Contains everything related to the color
 * and image of the element
 */
public class Style extends Component {
    private Color backgroundColor, hoverColor, activeColor, borderColor, dropShadowColor, textColor;
    private float textSize;
    private String textFamily;
    private Vector4f borderRadius;
    private Vector2f dropShadowOffset;
    private boolean render;
    private boolean rounded;
    private boolean dropShadow;
    private boolean computeSize;
    private boolean hideOverflow;
    private TextAlign textAlign;
    private float textOffsetX, textOffsetY;

    public Style() {
        backgroundColor = Color.of("white");//default background color of black
        hoverColor = Color.of("white");//default background color of black
        activeColor = Color.of("white");//default background color of black
        borderColor = Color.of("black");//default border color of transparent
        dropShadowColor = new Color(0, 0, 0, 100);
        textColor = Color.of("black");
        textFamily = "light";
        textSize = 22;//default text size of 22 pixels
        borderRadius = new Vector4f(0, 0, 0, 0); //No border radius by default
        render = true;//render by default
        rounded = false; //No rounding by default
        dropShadow = false; //No drop shadow by default
        dropShadowOffset = new Vector2f(20, 20);
        computeSize = true;
        hideOverflow = false;
        textAlign = TextAlign.LEFT;
        textOffsetX = 0;
        textOffsetY = 0;
    }

    /**
     * Creates a copy of the style given
     *
     * @param copy the parent style to copy
     */
    public Style(Style copy) {
        backgroundColor = copy.backgroundColor;//default background color of black
        hoverColor = copy.hoverColor;//default background color of black
        activeColor = copy.activeColor;//default background color of black
        borderColor = copy.borderColor;//default border color of transparent
        dropShadowColor = copy.dropShadowColor;
        textColor = copy.textColor;
        textFamily = copy.textFamily;
        textSize = copy.textSize;//default text size of 22 pixels
        borderRadius = copy.borderRadius; //No border radius by default
        render = copy.render;//render by default
        rounded = copy.rounded; //No rounding by default
        dropShadow = copy.dropShadow; //No drop shadow by default
        dropShadowOffset = copy.dropShadowOffset;
        computeSize = copy.computeSize;
    }


    /**
     * Set the border radius for the given corner
     *
     * @param corner the corner to set the border radius for
     * @param radius the radius of the corner
     */
    public void setBorderRadius(Corner corner, float radius) {
        switch (corner) {
            case TOP_LEFT:
                borderRadius.x = radius;
                break;
            case TOP_RIGHT:
                borderRadius.y = radius;
                break;
            case BOTTOM_LEFT:
                borderRadius.z = radius;
                break;
            case BOTTOM_RIGHT:
                borderRadius.w = radius;
                break;
        }
        rounded = true;
    }

    /**
     * Sets all of the border radius' to the given radius
     *
     * @param radius the radius for all of the corners
     */
    public void setBorderRadius(float radius) {
        setBorderRadius(Corner.TOP_LEFT, radius);
        setBorderRadius(Corner.TOP_RIGHT, radius);
        setBorderRadius(Corner.BOTTOM_LEFT, radius);
        setBorderRadius(Corner.BOTTOM_RIGHT, radius);
    }

    /**
     * Get the border radius at the given corner
     *
     * @param corner the corner to get the border radius for
     * @return border radius at given corner
     */
    public float getBorderRadius(Corner corner) {
        switch (corner) {
            case TOP_LEFT:
                return borderRadius.x;
            case TOP_RIGHT:
                return borderRadius.y;
            case BOTTOM_LEFT:
                return borderRadius.z;
            case BOTTOM_RIGHT:
                return borderRadius.w;
        }
        return 0;
    }

    /**
     * Apply a drop shadow to the style
     *
     * @param xOffset the offset of the x
     * @param yOffset the offset of the y
     * @param color   the color dropShadow
     */
    public void setDropShadow(float xOffset, float yOffset, Color color) {
        this.dropShadowOffset.x = xOffset;
        this.dropShadowOffset.y = yOffset;
        this.dropShadowColor = color;
        this.dropShadow = true;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getHoverColor() {
        return hoverColor;
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public Color getActiveColor() {
        return activeColor;
    }

    public void setActiveColor(Color activeColor) {
        this.activeColor = activeColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public Color getDropShadowColor() {
        return dropShadowColor;
    }

    public void setDropShadowColor(Color dropShadowColor) {
        this.dropShadowColor = dropShadowColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public String getTextFamily() {
        return textFamily;
    }

    public void setTextFamily(String textFamily) {
        this.textFamily = textFamily;
    }

    public Vector4f getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(Vector4f borderRadius) {
        this.borderRadius = borderRadius;
    }

    public Vector2f getDropShadowOffset() {
        return dropShadowOffset;
    }

    public void setDropShadowOffset(Vector2f dropShadowOffset) {
        this.dropShadowOffset = dropShadowOffset;
    }

    public boolean isRender() {
        return render;
    }

    public void setRender(boolean render) {
        this.render = render;
    }

    public boolean isRounded() {
        return rounded;
    }

    public void setRounded(boolean rounded) {
        this.rounded = rounded;
    }

    public boolean isDropShadow() {
        return dropShadow;
    }

    public void setDropShadow(boolean dropShadow) {
        this.dropShadow = dropShadow;
    }

    public boolean isComputeSize() {
        return computeSize;
    }

    public void setComputeSize(boolean computeSize) {
        this.computeSize = computeSize;
    }

    public boolean isHideOverflow() {
        return hideOverflow;
    }

    public void setHideOverflow(boolean hideOverflow) {
        this.hideOverflow = hideOverflow;
    }

    public TextAlign getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(TextAlign textAlign) {
        this.textAlign = textAlign;
    }

    public void setTextOffset(float offsetX, float offsetY) {
        this.textOffsetX = offsetX;
        this.textOffsetY = offsetY;
    }

    public float getTextOffsetX() {
        return textOffsetX;
    }

    public float getTextOffsetY() {
        return textOffsetY;
    }
}
