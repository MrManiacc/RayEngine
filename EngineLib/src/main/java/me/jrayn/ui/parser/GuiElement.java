package me.jrayn.ui.parser;

import com.google.common.collect.Lists;
import me.jrayn.core.IGuiProvider;
import me.jrayn.ui.components.Layout;
import me.jrayn.ui.components.Style;
import me.jrayn.ui.components.Text;

import java.util.List;

public class GuiElement {
    private String id, type;
    private String[] classes;
    private Layout layout;
    private Text text;
    private Style style;
    private List<GuiElement> children = Lists.newArrayList();
    private GuiElement parentElement;
    private int nodeId;

    public GuiElement(String type, String id, String... classes) {
        this.id = id;
        this.type = type;
        this.classes = classes;
    }

    public GuiElement(String type, String id) {
        this.type = type;
        this.id = id;
    }

    public GuiElement(String type, String... classes) {
        this.type = type;
        this.classes = classes;
    }

    public GuiElement(String type) {
        this.type = type;
    }

    /**
     * Creates the layout based upon the provided id, type and classes
     *
     * @return the generated layout
     */
    public void parseElement(Stylesheet[] stylesheets) {
        layout = new Layout();
        style = new Style();
        //First level will be the actual selector; i.e div, or h1
        for (Stylesheet stylesheet : stylesheets)
            stylesheet.cascadeElement(type, layout, style);
        //Second level will be applying all of the classes to the element
        if (classes != null)
            for (Stylesheet stylesheet : stylesheets)
                for (String clazz : classes)
                    stylesheet.cascadeElement("." + clazz.trim(), layout, style);
        //Third and final level will be applying the id types
        if (id != null && !id.isEmpty()) {
            for (Stylesheet stylesheet : stylesheets)
                stylesheet.cascadeElement("#" + id, layout, style);
        }
    }


    /**
     * Add a new child to this element
     *
     * @param guiElement the child element
     */
    public void addChild(GuiElement guiElement) {
        children.add(guiElement);
    }

    /**
     * Insert into parent
     */
    public void insert(IGuiProvider provider) {
        layout.insert(parentElement == null ? Layout.root : parentElement.layout);
        for (GuiElement child : children)
            child.insert(provider);
        this.nodeId = provider.addNode(this);
    }

    /**
     * Compute the layout
     */
    public void compute() {
        layout.compute();
    }

    /**
     * Used for inserting into parent
     *
     * @param parentElement the parent of this element
     */
    public void setParentElement(GuiElement parentElement) {
        this.parentElement = parentElement;
    }

    public Layout getLayout() {
        return layout;
    }

    public Style getStyle() {
        return style;
    }

    public void setTextValue(String textValue) {
        if (this.type.equalsIgnoreCase("h1") || this.type.equalsIgnoreCase("h2") || this.type.equalsIgnoreCase("h3")){
            this.text = new Text(textValue);
        }
    }

    public Text getText() {
        return text;
    }
}
