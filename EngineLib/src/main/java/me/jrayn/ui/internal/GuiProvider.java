package me.jrayn.ui.internal;

import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import com.google.common.collect.Maps;
import me.jrayn.core.IWindow;
import me.jrayn.ui.IGuiProvider;
import me.jrayn.ui.components.Identifier;
import me.jrayn.ui.components.Layout;
import me.jrayn.ui.components.Style;
import me.jrayn.ui.components.Text;
import me.jrayn.ui.systems.GuiRenderer;
import me.jrayn.ui.systems.IGuiRenderable;
import me.jrayn.ui.systems.TextRenderer;

import java.util.HashMap;

/**
 * A provider that will manage the gui systems
 */
public class GuiProvider implements IGuiProvider {
    private World world;
    private final HashMap<Identifier, Integer> identifiableNodes = Maps.newHashMap();

    /**
     * Create a new gui provider with the given IGuiRenderable type
     *
     * @param renderable the type of renderer to use for the gui's
     */
    public GuiProvider(IWindow window, IGuiRenderable renderable) {
        this.world = new World(new WorldConfigurationBuilder().with(0, new GuiRenderer(window, renderable), new TextRenderer(renderable)).build());
        //Set the core window, TODO: find a cleaner way todo this
        Layout.setWindow(window);
    }

    /**
     * Create a new node id reference which is automatically added to the world.
     * This will automatically add a default style and layout
     *
     * @return new node id
     */
    public int createNode() {
        int id = world.create();
        world.edit(id).add(new Style());
        world.edit(id).add(new Layout());
        Identifier identifier = new Identifier();
        identifiableNodes.put(identifier, id);
        world.edit(id).add(identifier);//empty id, randomly generated
        return id;
    }

    /**
     * Create a new node id reference which is automatically added to the world.
     * This will automatically add a default style and layout. It will also
     * create a identifier used to get the component by id
     *
     * @param identifier used to get a node by it's id
     * @return new node id
     */
    public int createNode(String identifier) {
        int id = createNode();
        world.getEntity(id).getComponent(Identifier.class).setId(identifier);
        return id;
    }

    /**
     * Create a new node id reference which is automatically added to the world.
     * This will automatically add a default style and layout
     *
     * @param text the text value
     * @return new node id
     */
    public int createTextNode(String text) {
        int id = createNode();
        world.edit(id).add(new Text());
        text(id).setText(text);
        return id;
    }

    /**
     * Create a new node id reference which is automatically added to the world.
     * This will automatically add a default style and layout
     *
     * @param identifier
     * @param text       the text value
     * @return new node id
     */
    public int createTextNode(String identifier, String text) {
        int id = createNode(identifier);
        world.edit(id).add(new Text());
        text(id).setText(text);
        return id;
    }

    /**
     * Create a new node id reference which is automatically added to the world.
     * This will automatically add a default style and layout
     *
     * @return new node id
     */
    public int createRootNode() {
        int id = world.create();
        world.edit(id).add(new Style());
        world.edit(id).add(new Layout());
        return id;
    }


    /**
     * Compute the position of the node
     */
    public void compute(int nodeID) {
        layout(nodeID).compute();
    }


    /**
     * Insert into the parent node
     *
     * @param parentNodeID the parent to insert to
     */
    public void insert(int nodeID, int parentNodeID) {
        layout(nodeID).insert(layout(parentNodeID));
    }

    /**
     * Insert into the parent node
     *
     * @param parentNodeID the parent to insert to
     */
    public void insert(int nodeID, Layout parentNodeID) {
        layout(nodeID).insert(parentNodeID);
    }

    /**
     * Should delete the given node and remove it from the system
     *
     * @param nodeID    the node nodeID to delete
     * @param recursive delete all of it's children or not
     */
    public void deleteNode(int nodeID, boolean recursive) {
        layout(nodeID).dispose(recursive);
        world.delete(nodeID);
    }

    /**
     * Gets the style for a given node
     *
     * @param nodeID the node to get the style for
     * @return style for node
     */
    public Style style(int nodeID) {
        return world.getEntity(nodeID).getComponent(Style.class);
    }

    /**
     * Gets the text for a given node
     *
     * @param nodeID the node to get the style for
     * @return text for node
     */
    public Text text(int nodeID) {
        return world.getEntity(nodeID).getComponent(Text.class);
    }


    /**
     * Gets the layout for a given node
     *
     * @param nodeID the node to get the style for
     * @return layout for node
     */
    public Layout layout(int nodeID) {
        return world.getEntity(nodeID).getComponent(Layout.class);
    }

    /**
     * Get the ecs world for processing and other things
     *
     * @return gui ecs world
     */
    public World getWorld() {
        return world;
    }

}
