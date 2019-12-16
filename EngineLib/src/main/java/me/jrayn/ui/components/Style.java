package me.jrayn.ui.components;

import com.artemis.Component;
import lombok.Getter;
import lombok.Setter;
import me.jrayn.ui.components.types.Color;
import me.jrayn.ui.components.types.Corner;
import org.joml.Vector2f;
import org.joml.Vector4f;

/**
 * Contains everything related to the color
 * and image of the element
 */
public class Style extends Component {
    @Setter
    @Getter
    private Color backgroundColor, hoverColor, activeColor, borderColor, dropShadowColor, textColor;
    @Getter
    @Setter
    private float textSize;
    @Getter
    @Setter
    private String textFamily;
    @Getter
    private Vector4f borderRadius;
    @Getter
    private Vector2f dropShadowOffset;
    @Getter
    @Setter
    private boolean render;
    @Getter
    @Setter
    private boolean rounded;
    @Getter
    @Setter
    private boolean dropShadow;
    @Getter
    @Setter
    private boolean computeSize;

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

}
