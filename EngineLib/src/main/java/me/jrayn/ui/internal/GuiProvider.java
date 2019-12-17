package me.jrayn.ui.internal;

import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import com.google.common.collect.Maps;
import me.jrayn.bootstrap.folder.IFolder;
import me.jrayn.bootstrap.project.IProject;
import me.jrayn.bootstrap.record.IRecord;
import me.jrayn.core.IGuiProvider;
import me.jrayn.core.IGuiRenderable;
import me.jrayn.core.IWindow;
import me.jrayn.ui.components.Identifier;
import me.jrayn.ui.components.Layout;
import me.jrayn.ui.components.Style;
import me.jrayn.ui.components.Text;
import me.jrayn.ui.parser.GuiElement;
import me.jrayn.ui.parser.GuiNode;
import me.jrayn.ui.parser.Stylesheet;
import me.jrayn.ui.systems.GuiRenderer;
import me.jrayn.ui.systems.TextRenderer;
import org.apache.commons.io.FilenameUtils;

import java.util.Map;

/**
 * A provider that will manage the gui systems
 */
public class GuiProvider implements IGuiProvider {
    private World world;
    private final Map<Identifier, Integer> identifiableNodes = Maps.newHashMap();
    private final Map<String, Stylesheet> stylesheets = Maps.newHashMap();

    /**
     * Create a new gui provider with the given IGuiRenderable type
     *
     * @param renderable the type of renderer to use for the gui's
     */
    public GuiProvider(IWindow window, IGuiRenderable renderable) {
        this.world = new World(new WorldConfigurationBuilder().with(0, new GuiRenderer(window, renderable), new TextRenderer(renderable)).build());
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
     * This will automatically add a default style and layout
     *
     * @return new node id
     */
    public int createNode(Layout parentLayout) {
        int id = world.create();
        world.edit(id).add(new Style());
        world.edit(id).add(new Layout(parentLayout));
        Identifier identifier = new Identifier();
        identifiableNodes.put(identifier, id);
        world.edit(id).add(identifier);//empty id, randomly generated
        return id;
    }

    /**
     * Create a new node id reference which is automatically added to the world.
     * This will automatically add a default style and layout
     *
     * @return new node id
     */
    public int createNode(Layout parentLayout, Style parentStyle) {
        int id = world.create();
        world.edit(id).add(new Style(parentStyle));
        world.edit(id).add(new Layout(parentLayout));
        Identifier identifier = new Identifier();
        identifiableNodes.put(identifier, id);
        world.edit(id).add(identifier);//empty id, randomly generated
        return id;
    }

    /**
     * Gets a stylesheet based on the stylesheet name
     *
     * @param stylesheet the stylesheet to grab
     * @return the stylesheet
     */
    public Stylesheet stylesheet(String stylesheet) {
        return stylesheets.get(stylesheet);
    }

    /**
     * Adds a node to the system with the given guiElement
     *
     * @param element the element to add to the system
     * @return the new id for the node
     */
    public int addNode(GuiElement element) {
        int id = world.create();
        world.edit(id).add(element.getLayout());
        world.edit(id).add(element.getStyle());
        if (element.getText() != null) {
            world.edit(id).add(element.getText());
        }
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
        world.edit(id).add(new Text(text));
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
        world.edit(id).add(new Text(text));
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
     * Gets the text for a given node
     *
     * @param nodeID the node to get the style for
     * @return text for node
     */
    public Text text(String nodeID) {
        return text(identifiableNodes.get(nodeID));
    }

    /**
     * Gets the style for a given node
     *
     * @param nodeID the node to get the style for
     * @return style for node
     */
    public Style style(String nodeID) {
        return style(identifiableNodes.get(nodeID));
    }

    /**
     * Gets the layout for a given node
     *
     * @param nodeID the node to get the style for
     * @return layout for node
     */
    public Layout layout(String nodeID) {
        return layout(identifiableNodes.get(nodeID));
    }

    /**
     * Parses the css and xml gui files from the project
     *
     * @param project the project to parse from
     */
    public void parseGuiElements(IProject project) {
        IFolder elements = project.getFolder("guis");
        IFolder styles = project.getFolder(elements, "styles");
        for (IRecord cssRecord : styles.getRecords("css")) {
            Stylesheet node = new Stylesheet(cssRecord);
            node.parse();
            stylesheets.put(FilenameUtils.removeExtension(node.getName()), node);
        }
        for (IRecord cssRecord : elements.getRecords("xml")) {
            GuiNode node = new GuiNode(cssRecord);
            node.parse(this);
        }
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
