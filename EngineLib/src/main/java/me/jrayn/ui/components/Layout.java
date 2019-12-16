package me.jrayn.ui.components;

import com.artemis.Component;
import lombok.Getter;
import me.jrayn.core.IWindow;
import me.jrayn.ui.components.types.*;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.util.yoga.YGLayout;
import org.lwjgl.util.yoga.YGNode;
import org.lwjgl.util.yoga.Yoga;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.util.yoga.Yoga.*;

/**
 * Represents a
 */
public class Layout extends Component {
    //The yoga id instance
    @Getter
    private long nodeID;
    private long parentNodeID;
    @Getter
    private Layout parent;


    private YGLayout layout;
    //Buffers so we aren't creating new vector's ever single time
    private final Vector2f sizeBuffer = new Vector2f();
    private final Vector4f positionBuffer = new Vector4f();
    private final Vector4f borderBuffer = new Vector4f();
    private final Vector4f marginBuffer = new Vector4f();
    private final Vector4f paddingBuffer = new Vector4f();
    private final Vector2f offsetBuffer = new Vector2f();
    private int nextChild = 0;
    public static final Layout root = new Layout(); //the root layout
    private static IWindow _window;
    private Set<Layout> children = new HashSet<>();

    /**
     * Create a new gui element component with no parent
     */
    public Layout() {
        nodeID = YGNodeNew();
        layout = YGNode.create(nodeID).layout();
        parentNodeID = -1;
    }


    /**
     * Sets the window, used for root layouts
     *
     * @param window the main window
     */
    public static void setWindow(IWindow window) {
        _window = window;
    }


    /**
     * Calculate the given layout, will get the parent width
     * or the window width, if the node is a root
     */
    public void compute() {
        YGNodeCalculateLayout(nodeID, _window.getWidth(), _window.getHeight(), YGDirectionLTR);
    }

    /**
     * Gets the total offset of recursively
     *
     * @return the offset
     */
    public Vector2f getOffset() {
        return getOffset(parent, 0, 0);
    }

    /**
     * Recursively gets the offset
     *
     * @return
     */
    private Vector2f getOffset(Layout parent, float currentX, float currentY) {
        float x = currentX;
        float y = currentY;
        if (parent != null) {
            x += parent.getPosition().x;
            y += parent.getPosition().y;
            return getOffset(parent.getParent(), x, y);
        } else {
            offsetBuffer.x = x;
            offsetBuffer.y = y;
            return offsetBuffer;
        }
    }

    /**
     * Insert this layout into the given layout
     *
     * @param layout the layout to insert into
     * @return whether inserted or not
     */
    public boolean insert(Layout layout) {
        parentNodeID = layout.nodeID;
        parent = layout;
        if (layout.children.contains(this))
            return false;
        YGNodeInsertChild(parentNodeID, nodeID, layout.nextChild++);
        layout.children.add(this);
        return true;
    }

    /**
     * Sets the position of a node in absolute coordinates space
     *
     * @param position the position to set
     * @param edge     the edge to set for
     */
    public void setPosition(float position, Edge edge) {
        YGNodeStyleSetPosition(nodeID, edge.getValue(), position);
    }

    /**
     * Delete the yg node instance
     */
    public void dispose(boolean recursive) {
        if (!recursive)
            YGNodeFree(nodeID);
        else
            YGNodeFreeRecursive(nodeID);
    }

    /**
     * Sets the flex grow value
     *
     * @param grow the grow amount of the flex
     */
    public void setFlexGrow(float grow) {
        YGNodeStyleSetFlexGrow(nodeID, grow);
    }

    /**
     * Sets the flex shrink value
     *
     * @param grow the grow amount of the flex
     */
    public void setFlexShrink(float grow) {
        YGNodeStyleSetFlexShrink(nodeID, grow);
    }

    /**
     * Sets the flex basis value
     *
     * @param basis the basis amount of the flex
     */
    public void setFlexBasis(float basis) {
        YGNodeStyleSetFlexBasis(nodeID, basis);
    }

    /**
     * Sets the flex basis value to auto
     */
    public void setFlexBasisAuto() {
        YGNodeStyleSetFlexBasisAuto(nodeID);
    }

    /**
     * Sets the flex basis value to auto
     *
     * @param percent the basis amount of the flex
     */
    public void setFlexBasisPercent(float percent) {
        YGNodeStyleSetFlexBasisPercent(nodeID, percent);
    }

    /**
     * The flex direction for the element
     */
    public void setFlexDirection(Flex flex) {
        YGNodeStyleSetFlexDirection(nodeID, flex.getValue());
    }

    /**
     * Set the width absolute pixel based
     *
     * @param width element width
     */
    public void setWidthAbsolute(float width) {
        YGNodeStyleSetWidth(nodeID, width);
    }

    /**
     * Set the minimum width absolute pixel based
     *
     * @param minWidth element width
     */
    public void setMinWidthAbsolute(float minWidth) {
        YGNodeStyleSetMinWidth(nodeID, minWidth);
    }


    /**
     * Set the maximum width absolute pixel based
     *
     * @param maxWidth element width
     */
    public void setMaxWidthAbsolute(float maxWidth) {
        YGNodeStyleSetMaxWidth(nodeID, maxWidth);
    }

    /**
     * Set the minimum width relative based
     *
     * @param minWidth element width
     */
    public void setMinWidthPercent(float minWidth) {
        YGNodeStyleSetMinWidthPercent(nodeID, minWidth);
    }


    /**
     * Set the maximum width relative based
     *
     * @param maxWidth element width
     */
    public void setMaxWidthPercent(float maxWidth) {
        YGNodeStyleSetMaxWidthPercent(nodeID, maxWidth);
    }

    /**
     * Set the minimum height absolute pixel based
     *
     * @param minHeight element height
     */
    public void setMinHeightAbsolute(float minHeight) {
        YGNodeStyleSetMinHeight(nodeID, minHeight);

    }


    /**
     * Set the maximum height absolute pixel based
     *
     * @param maxHeight element height
     */
    public void setMaxHeightAbsolute(float maxHeight) {
        YGNodeStyleSetMaxHeight(nodeID, maxHeight);

    }

    /**
     * Set the minimum height relative based
     *
     * @param minHeight element height
     */
    public void setMinHeightPercent(float minHeight) {
        YGNodeStyleSetMinHeightPercent(nodeID, minHeight);
    }


    /**
     * Set the maximum height relative based
     *
     * @param maxHeight element height
     */
    public void setMaxHeightPercent(float maxHeight) {
        YGNodeStyleSetMaxHeightPercent(nodeID, maxHeight);
    }

    /**
     * Set the width based on percent of parent
     *
     * @param width element width
     */
    public void setWidthPercent(float width) {
        YGNodeStyleSetWidthPercent(nodeID, width);
    }

    /**
     * Automatically calculate the width based
     * on margin and padding size
     */
    public void setWidthAuto() {
        YGNodeStyleSetWidthAuto(nodeID);
    }

    /**
     * Set the height absolute pixel based
     *
     * @param height element height
     */
    public void setHeightAbsolute(float height) {
        YGNodeStyleSetHeight(nodeID, height);
    }

    /**
     * Set the height based on percent of parent
     *
     * @param height element height
     */
    public void setHeightPercent(float height) {
        YGNodeStyleSetHeightPercent(nodeID, height);
    }

    /**
     * Automatically calculate the width based
     * on margin and padding size
     */
    public void setHeightAuto() {
        YGNodeStyleSetHeightAuto(nodeID);
    }

    /**
     * Sets the flex wrapping style
     *
     * @param wrap the type of wrap
     */
    public void setWrap(Wrap wrap) {
        YGNodeStyleSetFlexWrap(nodeID, wrap.getValue());
    }


    /**
     * Sets the padding for all sides with pixels
     *
     * @param padding pixel padding
     */
    public void setPadding(float padding) {
        YGNodeStyleSetPadding(nodeID, Yoga.YGEdgeAll, padding);
    }

    /**
     * Sets the padding for specified side with pixels
     *
     * @param padding pixel padding
     * @param edge    the side to add padding to
     */
    public void setPadding(float padding, Edge edge) {
        YGNodeStyleSetPadding(nodeID, edge.getValue(), padding);
    }

    /**
     * Sets the padding for all sides with pixels
     *
     * @param padding pixel padding
     */
    public void setPaddingPercent(float padding) {
        YGNodeStyleSetPaddingPercent(nodeID, Yoga.YGEdgeAll, padding);
    }

    /**
     * Sets the padding for specified side with pixels
     *
     * @param padding pixel padding
     * @param edge    the side to add padding to
     */
    public void setPaddingPercent(float padding, Edge edge) {
        YGNodeStyleSetPaddingPercent(nodeID, edge.getValue(), padding);
    }

    /**
     * Sets the margin for all sides with pixels
     *
     * @param margin pixel margin
     */
    public void setMargin(float margin) {
        YGNodeStyleSetMargin(nodeID, Yoga.YGEdgeAll, margin);
    }


    /**
     * Sets the margin for specified side with pixels
     *
     * @param margin pixel margin
     * @param edge   the side to add margin to
     */
    public void setMargin(float margin, Edge edge) {
        YGNodeStyleSetMargin(nodeID, edge.getValue(), margin);
    }

    /**
     * Sets the margin for all sides with pixels
     *
     * @param margin pixel margin
     */
    public void setMarginPercent(float margin) {
        YGNodeStyleSetMarginPercent(nodeID, Yoga.YGEdgeAll, margin);
    }

    /**
     * Sets the margin for specified side with pixels
     *
     * @param margin pixel margin
     * @param edge   the side to add margin to
     */
    public void setMarginPercent(float margin, Edge edge) {
        YGNodeStyleSetMarginPercent(nodeID, edge.getValue(), margin);

    }

    /**
     * The flex wrap property is set on containers and controls
     * what happens when children overflow the size of the container
     * along the main axis. By default children are forced into
     * a single line (which can shrink elements).
     * If wrapping is allowed items are wrapped into multiple
     * lines along the main axis if needed. wrap reverse behaves
     * the same, but the order of the lines is reversed.
     *
     * @param flex the flex value
     */
    public void setFlex(float flex) {
        YGNodeStyleSetFlex(nodeID, flex);
    }

    /**
     * Justify content describes how to align children within the main axis of their container.
     * For example, you can use this property to center a child horizontally
     * within a container with flex direction set to row
     * or vertically within a container with flex direction set to column.
     *
     * @param justification the justification of the property
     */
    public void setJustify(Justify justification) {
        YGNodeStyleSetJustifyContent(nodeID, justification.getValue());
    }

    /**
     * AspectRatio is a property introduced by Yoga and is not present
     * as a settable property in the css flexbox specification.
     * Flexbox does has the notion of aspect ratio though for things with
     * intrinsic aspect ratio such as images.
     *
     * @param aspectRatio the new aspect ratio
     */
    public void setAspectRatio(float aspectRatio) {
        YGNodeStyleSetAspectRatio(nodeID, aspectRatio);
    }

    /**
     * Align content defines the distribution of lines along the cross-axis.
     * This only has effect when items are wrapped to multiple lines using flex wrap.
     *
     * @param alignment the alignment to set for the content
     */
    public void setContentAlignment(Align alignment) {
        YGNodeStyleSetAlignContent(nodeID, alignment.getValue());
    }

    /**
     * Align content defines the distribution of lines along the cross-axis.
     * This only has effect when items are wrapped to multiple lines using flex wrap.
     *
     * @param alignment the alignment to set for the content
     */
    public void setSelfAlignment(Align alignment) {
        YGNodeStyleSetAlignSelf(nodeID, alignment.getValue());
    }


    /**
     * Align content defines the distribution of lines along the cross-axis.
     * This only has effect when items are wrapped to multiple lines using flex wrap.
     *
     * @param alignment the alignment to set for the content
     */
    public void setItemsAlignment(Align alignment) {
        YGNodeStyleSetAlignItems(nodeID, alignment.getValue());
    }

    /**
     * BORDER in Yoga acts exactly like padding and only exists as a separate
     * property so that higher level frameworks get a hint as to
     * how thick to draw a border. Yoga however does not do any drawing
     * so just uses this information during layout where border acts exactly like padding.
     *
     * @param border the value of the border
     */
    public void setBorder(float border) {
        YGNodeStyleSetBorder(nodeID, YGEdgeAll, border);
    }

    /**
     * BORDER in Yoga acts exactly like padding and only exists as a separate
     * property so that higher level frameworks get a hint as to
     * how thick to draw a border. Yoga however does not do any drawing
     * so just uses this information during layout where border acts exactly like padding.
     *
     * @param border the value of the border
     * @param edge   the side to set the border for
     */
    public void setBorder(float border, Edge edge) {
        YGNodeStyleSetBorder(nodeID, edge.getValue(), border);
    }

    /**
     * Should get the computed width based on the element id
     *
     * @param dimension the dimension to get the size for
     * @return computed element width
     */
    public float getSize(Dimension dimension) {
        return layout.dimensions(dimension.getValue());
    }

    /**
     * Get the size as a vector.
     * X represents the width.
     * Y represents the height.
     *
     * @return the size buffer vector
     */
    public Vector2f getSize() {
        sizeBuffer.x = getSize(Dimension.WIDTH);
        sizeBuffer.y = getSize(Dimension.HEIGHT);
        return sizeBuffer;
    }

    /**
     * Should get the computed left position of the element
     *
     * @param edge the side to get the position for
     * @return computed position
     */
    public float getPosition(Edge edge) {
        return layout.positions(edge.getValue());
    }

    /**
     * Get the position as a vector.
     * X represents the left.
     * Y represents the top.
     * Z represents the right.
     * W represents the bottom.
     *
     * @return the position buffer vector
     */
    public Vector4f getPosition() {
        positionBuffer.x = getPosition(Edge.LEFT);
        positionBuffer.y = getPosition(Edge.TOP);
        positionBuffer.z = getPosition(Edge.RIGHT);
        positionBuffer.w = getPosition(Edge.BOTTOM);
        return positionBuffer;
    }


    /**
     * Should get the border at the specified edge
     *
     * @param edge the side to get the border for
     * @return the border at the edge
     */
    public float getBorder(Edge edge) {
        return layout.border(edge.getValue());
    }

    /**
     * Checks to see if the border is present
     *
     * @return border status
     */
    public boolean hasBorder() {
        Vector4f border = getBorder();
        return border.x > 0 || border.y > 0 || border.z > 0 || border.w > 0;
    }

    public void setRelative() {
        YGNodeStyleSetPositionType(nodeID, YGPositionTypeRelative);
    }

    public void setAbsolute() {
        YGNodeStyleSetPositionType(nodeID, YGPositionTypeAbsolute);
    }

    /**
     * Get the border as a vector.
     * X represents the left.
     * Y represents the top.
     * Z represents the right.
     * W represents the bottom.
     *
     * @return the border buffer vector
     */
    public Vector4f getBorder() {
        borderBuffer.x = getBorder(Edge.LEFT);
        borderBuffer.y = getBorder(Edge.TOP);
        borderBuffer.z = getBorder(Edge.RIGHT);
        borderBuffer.w = getBorder(Edge.BOTTOM);
        return borderBuffer;
    }

    /**
     * Should get the margin at the specified edge
     *
     * @param edge the side to get the margin for
     * @return the margin at the edge
     */
    public float getMargin(Edge edge) {
        return layout.margin(edge.getValue());
    }

    /**
     * Get the margin as a vector.
     * X represents the left.
     * Y represents the top.
     * Z represents the right.
     * W represents the bottom.
     *
     * @return the margin buffer vector
     */
    public Vector4f getMargin() {
        marginBuffer.x = getMargin(Edge.LEFT);
        marginBuffer.y = getMargin(Edge.TOP);
        marginBuffer.z = getMargin(Edge.RIGHT);
        marginBuffer.w = getMargin(Edge.BOTTOM);
        return marginBuffer;
    }

    /**
     * Should get the padding at the specified edge
     *
     * @param edge the side to get the padding for
     * @return the padding at the edge
     */
    public float getPadding(Edge edge) {
        return layout.padding(edge.getValue());
    }

    /**
     * Get the padding as a vector.
     * X represents the left.
     * Y represents the top.
     * Z represents the right.
     * W represents the bottom.
     *
     * @return the padding buffer vector
     */
    public Vector4f getPadding() {
        paddingBuffer.x = getPadding(Edge.LEFT);
        paddingBuffer.y = getPadding(Edge.TOP);
        paddingBuffer.z = getPadding(Edge.RIGHT);
        paddingBuffer.w = getPadding(Edge.BOTTOM);
        return paddingBuffer;
    }

    /**
     * Simple check to see if the nodes are equal
     *
     * @param obj the other layout
     * @return equality of the layouts=
     */
    public boolean equals(Object obj) {
        if (obj instanceof Layout) {
            Layout layout = (Layout) obj;
            return layout.nodeID == this.nodeID;
        }
        return false;
    }
}
