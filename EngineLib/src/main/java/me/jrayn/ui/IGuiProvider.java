package me.jrayn.ui;

import com.artemis.World;
import me.jrayn.ui.components.Layout;
import me.jrayn.ui.components.Style;
import me.jrayn.ui.components.Text;


public interface IGuiProvider {
    /**
     * Create a new node id reference which is automatically added to the world
     *
     * @return new node id
     */
    int createNode();

    /**
     * Create a new node id reference which is automatically added to the world.
     * This will automatically add a default style and layout
     *
     * @return new node id
     */
    int createRootNode();

    /**
     * Create a new node id reference which is automatically added to the world.
     * This will automatically add a default style and layout. It will also
     * create a
     *
     * @param identifier the id of the node
     * @return new node id
     */
    int createNode(String identifier);

    /**
     * Compute the position of the node
     */
    void compute(int nodeID);

    /**
     * Insert into the parent node
     *
     * @param parentNodeID the parent to insert to
     */
    void insert(int nodeID, int parentNodeID);

    /**
     * Insert into the parent node
     *
     * @param parentNodeID the parent to insert to
     */
    void insert(int nodeID, Layout parentNodeID);

    /**
     * Should delete the given node and remove it from the system
     *
     * @param id        the node id to delete
     * @param recursive delete all of it's children or not
     */
    void deleteNode(int id, boolean recursive);

    /**
     * Gets the style for a given node
     *
     * @param nodeID the node to get the style for
     * @return style for node
     */
    Style style(int nodeID);

    /**
     * Gets the layout for a given node
     *
     * @param nodeID the node to get the style for
     * @return layout for node
     */
    Layout layout(int nodeID);

    /**
     * Gets the text for a given node
     *
     * @param nodeID the node to get the style for
     * @return text for node
     */
    Text text(int nodeID);

    /**
     * Get the ecs world for processing and other things
     *
     * @return gui ecs world
     */
    World getWorld();

    /**
     * Create a new node id reference which is automatically added to the world.
     * This will automatically add a default style and layout
     *
     * @param identifier
     * @param text       the text value
     * @return new node id
     */
    int createTextNode(String identifier, String text);

    /**
     * Create a new node id reference which is automatically added to the world.
     * This will automatically add a default style and layout
     *
     * @param text the text value
     * @return new node id
     */
    int createTextNode(String text);

}
