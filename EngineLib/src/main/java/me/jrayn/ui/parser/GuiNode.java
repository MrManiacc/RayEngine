package me.jrayn.ui.parser;

import me.jrayn.bootstrap.record.IRecord;
import me.jrayn.bootstrap.record.Record;
import me.jrayn.core.IGuiProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Represents a gui element, which is in the form of xml
 */
public class GuiNode extends Record {
    public GuiNode(IRecord parent) {
        super(parent.getFile().getPath());
    }

    /**
     * Parse the gui node from file
     *
     * @param provider used to create the actual node it's self
     */
    public void parse(IGuiProvider provider) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(getFile());
            Element root = document.getDocumentElement();
            parseDocument(provider, root);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * parses the document using the given root
     *
     * @param rootNode
     */
    private void parseDocument(IGuiProvider provider, Element rootNode) {
        Stylesheet[] stylesheets = getStylesheets(provider, rootNode.hasAttribute("using") ? rootNode.getAttribute("using") : null);
        GuiElement root = parseElements(stylesheets, rootNode, null, null);
        root.insert(provider);
        root.compute();
    }

    /**
     * Recursively parses the the gui elements, and will return the root element
     *
     * @param guiNode     the guiNode list to parse
     * @param stylesheets the styles to use for identifiers
     */
    private GuiElement parseElements(Stylesheet[] stylesheets, Node guiNode, GuiElement root, GuiElement parent) {
        for (int i = 0; i < guiNode.getChildNodes().getLength(); i++) {
            Node node = guiNode.getChildNodes().item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String type = node.getNodeName();
                String[] classes = null;
                String id = "";
                NamedNodeMap attributes = node.getAttributes();
                for (int j = 0; j < attributes.getLength(); j++) {
                    Node attribute = attributes.item(j);
                    if (attribute.getNodeName().equalsIgnoreCase("class"))
                        classes = attribute.getNodeValue().split(",");
                    else if (attribute.getNodeName().equalsIgnoreCase("id"))
                        id = attribute.getNodeValue();
                }
                GuiElement element;
                if (id.isEmpty() && classes == null)
                    element = new GuiElement(type);
                else if (!id.isEmpty() && classes == null)
                    element = new GuiElement(type, id);
                else if (id.isEmpty())
                    element = new GuiElement(type, classes);
                else
                    element = new GuiElement(type, id, classes);
                element.parseElement(stylesheets);
                if (root == null)
                    root = element;
                else
                    root.addChild(element);
                element.setParentElement(parent);
                if (!node.getTextContent().isEmpty()) {
                    element.setTextValue(node.getTextContent());
                }
                if (node.hasChildNodes())
                    parseElements(stylesheets, node, root, element);

            }
        }
        return root;
    }

    /**
     * Gets a list of the stylesheets given the using string
     *
     * @param provider the gui provider to get the stylesheets
     * @param using    the literal string to get the stylesheets
     * @return the list of stylesheets by the given name
     */
    private Stylesheet[] getStylesheets(IGuiProvider provider, String using) {
        if (using == null)
            return new Stylesheet[]{provider.stylesheet("core")};
        String[] sheets = using.split(",");
        Stylesheet[] stylesheets = new Stylesheet[sheets.length + 1];
        stylesheets[0] = provider.stylesheet("core");
        for (int i = 1; i < sheets.length + 1; i++)
            stylesheets[i] = provider.stylesheet(sheets[i]);
        return stylesheets;
    }


}
