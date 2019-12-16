package me.jrayn.ui.components.types;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains a lot of default colors
 */
public class Colors {
    private static Map<String, Color> colorMap = new HashMap<>();

    static {
        add("transparent", new Color(0, 0, 0, 0));
        add("indian-red", new Color("#B0171F"));
        add("crimson", new Color("#DC143C"));
        add("light-pink", new Color("#FFB6C1"));
        add("pink", new Color("#FFC0CB"));
        add("hot-pink", new Color("#FF69B4"));
        add("violet-red", new Color("#D02090"));
        add("orchid", new Color("#DA70D6"));
        add("violet", new Color("#EE82EE"));
        add("purple", new Color("#800080"));
        add("dark-violet", new Color("#9400D3"));
        add("indigo", new Color("#4B0082"));
        add("blue-violet", new Color("#8A2BE2"));
        add("slate-blue", new Color("#6A5ACD"));
        add("ghost-white", new Color("#F8F8FF"));
        add("blue", new Color("#0000FF"));
        add("dark-blue", new Color("#00008B"));
        add("navy", new Color("#000080"));
        add("steel-blue", new Color("#4682B4"));
        add("slate-gray", new Color("#708090"));
        add("turquoise", new Color("#00F5FF"));
        add("cyan", new Color("#00FFFF"));
        add("emerald-green", new Color("#00C957"));
        add("mint", new Color("#BDFCC9"));
        add("lime-green", new Color("#32CD32"));
        add("green", new Color("#00FF00"));
        add("dark-green", new Color("#006400"));
        add("yellow", new Color("#FFFF00"));
        add("gold", new Color("#FFD700"));
        add("orange", new Color("#FF8000"));
        add("red", new Color("#FF0000"));
        add("maroon", new Color("#800000"));
        add("black", new Color("#000000"));
        add("white", new Color("#FFFFFF"));
        add("dark-gray", new Color("#555555"));
        add("light-gray", new Color("#AAAAAA"));
        add("gray", new Color("#B7B7B7"));
    }

    /**
     * registers a color with the given name
     *
     * @param name  the color name
     * @param color the color it's self
     */
    private static void add(String name, Color color) {
        colorMap.put(name.toLowerCase(), color);
    }

    /**
     * Gets a color with the specified name
     *
     * @param name the color name
     * @return specified color
     */
    static Color get(String name) {
        return colorMap.get(name.toLowerCase());
    }


}
