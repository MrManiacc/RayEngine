package me.jrayn.ui.parser;

import com.google.common.collect.Maps;
import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.parser.SACParserCSS3;
import me.jrayn.bootstrap.record.IRecord;
import me.jrayn.bootstrap.record.Record;
import me.jrayn.ui.components.Layout;
import me.jrayn.ui.components.Style;
import me.jrayn.ui.components.types.*;
import org.apache.commons.io.FileUtils;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Stylesheet extends Record {
    private Map<String, Layout> layouts = Maps.newHashMap();
    private Map<String, Style> styles = Maps.newHashMap();
    private Map<String, CSSStyleDeclaration> declarationMap = Maps.newHashMap();

    public Stylesheet(IRecord record) {
        super(record.getPath().toString());
    }

    /**
     * Parses the css file
     */
    public void parse() {
        try {
            String style = FileUtils.readFileToString(getFile());
            InputSource source = new InputSource(new StringReader(style));
            CSSOMParser parser = new CSSOMParser(new SACParserCSS3());
            CSSStyleSheet sheet = parser.parseStyleSheet(source, null, null);
            parseElement(sheet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parse the css element
     *
     * @param stylesheet the css declared variables
     */
    private void parseElement(CSSStyleSheet stylesheet) {
        CSSRuleList ruleList = stylesheet.getCssRules();
        for (int i = 0; i < ruleList.getLength(); i++) {
            CSSRule rule = ruleList.item(i);
            if (rule instanceof CSSStyleRule) {
                CSSStyleRule styleRule = (CSSStyleRule) rule;
                CSSStyleDeclaration styleDeclaration = styleRule.getStyle();
                declarationMap.put(styleRule.getSelectorText(), styleDeclaration);
            }
        }
    }

    /**
     * This will attempt to process the styles of the given
     * selector, whether it be a class or id, or even a generic selector
     *
     * @param selector the selector of the element
     */
    public boolean cascadeElement(String selector, Layout layout, Style style) {
        if (declarationMap.containsKey(selector)) {
            CSSStyleDeclaration styleDeclaration = declarationMap.get(selector);
            for (int j = 0; j < styleDeclaration.getLength(); j++) {
                String property = styleDeclaration.item(j);
                String value = styleDeclaration.getPropertyCSSValue(property).getCssText();
                switch (property) {
                    case "position":
                        if (value.equalsIgnoreCase("rel")
                                || value.equalsIgnoreCase("relative"))
                            layout.setRelative();
                        else if (value.equalsIgnoreCase("abs")
                                || value.equalsIgnoreCase("absolute"))
                            layout.setAbsolute();
                        break;
                    case "top":
                    case "bottom":
                    case "right":
                    case "left":
                        if (isPercent(value))
                            layout.setPositionPercent(stripValue(value), Edge.fromString(property));
                        else
                            layout.setPosition(stripValue(value), Edge.fromString(property));
                        break;
                    case "width":
                        if (value.equalsIgnoreCase("auto")) {
                            layout.setWidthAuto();
                        } else {
                            if (isPercent(value))
                                layout.setWidthPercent(stripValue(value));
                            else
                                layout.setWidthAbsolute(stripValue(value));
                        }
                        break;
                    case "height":
                        if (value.equalsIgnoreCase("auto")) {
                            layout.setHeightAuto();
                        } else {
                            if (isPercent(value))
                                layout.setHeightPercent(stripValue(value));
                            else
                                layout.setHeightAbsolute(stripValue(value));
                        }
                        break;
                    case "overflow":
                        if(value.equalsIgnoreCase("hidden"))
                            style.setHideOverflow(true);
                        else
                            style.setHideOverflow(false);
                        break;
                    case "text-align":
                        style.setTextAlign(TextAlign.fromString(value));
                        break;
                    case "min-width":
                        if (isPercent(value))
                            layout.setMinWidthPercent(stripValue(value));
                        else
                            layout.setMinWidthAbsolute(stripValue(value));
                        break;
                    case "min-height":
                        if (isPercent(value))
                            layout.setMinHeightPercent(stripValue(value));
                        else
                            layout.setMinHeightAbsolute(stripValue(value));
                        break;
                    case "max-width":
                        if (isPercent(value))
                            layout.setMaxWidthPercent(stripValue(value));
                        else
                            layout.setMaxWidthAbsolute(stripValue(value));
                        break;
                    case "max-height":
                        if (isPercent(value))
                            layout.setMaxHeightPercent(stripValue(value));
                        else
                            layout.setMaxHeightAbsolute(stripValue(value));
                        break;
                    case "padding":
                        parsePadding(layout, value);
                        break;
                    case "border":
                        parseBorder(layout, value);
                        break;
                    case "margin":
                        parseMargin(layout, value);
                        break;
                    case "align-self":
                        Align selfAlign = Align.fromString(value);
                        layout.setSelfAlignment(selfAlign);
                        break;
                    case "align-content":
                        Align contentAlign = Align.fromString(value);
                        layout.setContentAlignment(contentAlign);
                        break;
                    case "align-item":
                        Align itemAlign = Align.fromString(value);
                        layout.setItemsAlignment(itemAlign);
                        break;
                    case "flex":
                        layout.setFlex(stripValue(value));
                        break;
                    case "flex-direction":
                        Flex direction = Flex.fromString(value);
                        layout.setFlexDirection(direction);
                        break;
                    case "flex-basis":
                        if (isPercent(value))
                            layout.setFlexBasisPercent(stripValue(value));
                        else
                            layout.setFlexBasis(stripValue(value));
                        break;
                    case "flex-grow":
                        layout.setFlexGrow(stripValue(value));
                        break;
                    case "flex-shrink":
                        layout.setFlexShrink(stripValue(value));
                        break;
                    case "background-color":
                        style.setBackgroundColor(parseColor(value));
                        break;
                    case "text-color":
                        style.setTextColor(parseColor(value));
                        break;
                    case "border-color":
                        style.setBorderColor(parseColor(value));
                        break;
                    case "text-family":
                        style.setTextFamily(value);
                        break;
                    case "flex-wrap":
                        layout.setWrap(Wrap.fromString(value));
                        break;
                    case "text-size":
                        style.setTextSize(stripValue(value));
                        break;
                    case "border-radius":
                        parseBorderRadius(style, value);
                        break;
                    case "display":
                        if (value.equalsIgnoreCase("none"))
                            style.setRender(false);
                        else
                            style.setRender(true);
                        break;
                    case "box-shadow":
                        parseDropShadow(style, value);
                        break;
                    case "justify":
                        layout.setJustify(Justify.fromString(value));
                        break;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Get the layout for the given selector
     *
     * @param selector the selector of the layout
     * @return the layout for the given selector
     */
    public Layout getLayout(String selector) {
        return layouts.get(selector);
    }

    /**
     * Get the style for the given selector
     *
     * @param selector the selector of the style
     * @return the style for the given selector
     */
    public Style getStyle(String selector) {
        return styles.get(selector);
    }

    /**
     * Parse the drop shadow for the element
     *
     * @param style the style to apply the drop shadow to
     * @param value the drop shadow value
     */
    private void parseDropShadow(Style style, String value) {
        String[] split = value.split(" ");
        if (split.length == 1) {
            //Default drop shadow with rgba value of 30
            float offsetValue = stripValue(split[0]);
            style.setDropShadow(true);
            style.setDropShadow(offsetValue, offsetValue, new Color(0, 0, 0, 30));
        } else if (split.length == 2) {
            float offsetValueX = stripValue(split[0]);
            float offsetValueY = stripValue(split[1]);
            style.setDropShadow(true);
            style.setDropShadow(offsetValueX, offsetValueY, new Color(0, 0, 0, 30));
        } else if (split.length == 3) {
            float offsetValueX = stripValue(split[0]);
            float offsetValueY = stripValue(split[1]);
            Color color = parseColor(split[2]);
            style.setDropShadow(true);
            style.setDropShadow(offsetValueX, offsetValueY, color);
        }
    }


    /**
     * This will get the correct color based on the inputted value
     *
     * @param value the color raw value
     * @return the formatted color
     */
    private Color parseColor(String value) {
        Color color = Color.of(value.toLowerCase());
        Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(value);
        if (color != null)
            return color;
        if (value.contains("#"))
            return new Color(value);
        if (value.contains("rgba")) {
            while (m.find()) {
                String rgbValue = m.group(1).trim();
                String[] rgb = rgbValue.split(",");
                int r = Integer.parseInt(rgb[0].trim());
                int g = Integer.parseInt(rgb[1].trim());
                int b = Integer.parseInt(rgb[2].trim());
                int a = Integer.parseInt(rgb[3].trim());
                return new Color(r, g, b, a);
            }
        }
        if (value.contains("rgb")) {
            while (m.find()) {
                String rgbValue = m.group(1).trim();
                String[] rgb = rgbValue.split(",");
                int r = Integer.parseInt(rgb[0].trim());
                int g = Integer.parseInt(rgb[1].trim());
                int b = Integer.parseInt(rgb[2].trim());
                return new Color(r, g, b);
            }
        }
        System.err.println("Unable to parse color: " + value);
        return Color.of("transparent");
    }


    /**
     * Parse the padding for the element
     *
     * @param layout the layout to set the padding for
     * @param value  the value of the padding
     */
    private void parsePadding(Layout layout, String value) {
        String[] split = value.trim().split(" ");
        if (split.length == 1) {
            if (isPercent(value))
                layout.setPaddingPercent(stripValue(value));
            else
                layout.setPadding(stripValue(value));
        } else if (split.length == 2 || split.length == 3) { // 3 is a weird number
            if (isPercent(split[0]))
                layout.setPaddingPercent(stripValue(split[0]), Edge.HORIZONTAL);
            else
                layout.setPadding(stripValue(split[0]), Edge.HORIZONTAL);
            if (isPercent(split[1]))
                layout.setPaddingPercent(stripValue(split[1]), Edge.VERTICAL);
            else
                layout.setPadding(stripValue(split[1]), Edge.VERTICAL);
        } else if (split.length == 4) {
            if (isPercent(split[0]))
                layout.setPaddingPercent(stripValue(split[0]), Edge.LEFT);
            else
                layout.setPadding(stripValue(split[0]), Edge.LEFT);
            if (isPercent(split[1]))
                layout.setPaddingPercent(stripValue(split[1]), Edge.TOP);
            else
                layout.setPadding(stripValue(split[1]), Edge.TOP);
            if (isPercent(split[2]))
                layout.setPaddingPercent(stripValue(split[2]), Edge.RIGHT);
            else
                layout.setPadding(stripValue(split[2]), Edge.RIGHT);
            if (isPercent(split[3]))
                layout.setPaddingPercent(stripValue(split[3]), Edge.BOTTOM);
            else
                layout.setPadding(stripValue(split[3]), Edge.BOTTOM);
        }
    }


    /**
     * Parse the border for the element
     *
     * @param layout the layout to set the border for
     * @param value  the value of the border
     */
    private void parseBorder(Layout layout, String value) {
        String[] split = value.trim().split(" ");
        if (split.length == 1) {
            layout.setBorder(stripValue(value));
        } else if (split.length == 2 || split.length == 3) { // 3 is a weird number
            layout.setBorder(stripValue(split[0]), Edge.HORIZONTAL);
            layout.setBorder(stripValue(split[1]), Edge.VERTICAL);
        } else if (split.length == 4) {
            layout.setBorder(stripValue(split[0]), Edge.LEFT);
            layout.setBorder(stripValue(split[1]), Edge.TOP);
            layout.setBorder(stripValue(split[2]), Edge.RIGHT);
            layout.setBorder(stripValue(split[3]), Edge.BOTTOM);
        }
    }

    /**
     * Parse the border radius for the element
     *
     * @param style the layout to set the border for
     * @param value the value of the border
     */
    private void parseBorderRadius(Style style, String value) {
        String[] split = value.trim().split(" ");
        if (split.length == 4) {
            style.setBorderRadius(Corner.TOP_LEFT, stripValue(split[0]));
            style.setBorderRadius(Corner.TOP_RIGHT, stripValue(split[1]));
            style.setBorderRadius(Corner.BOTTOM_LEFT, stripValue(split[2]));
            style.setBorderRadius(Corner.BOTTOM_RIGHT, stripValue(split[3]));
        } else {
            style.setBorderRadius(stripValue(split[0]));
        }
    }

    /**
     * Parse the margin for the element
     *
     * @param layout the layout to set the margin for
     * @param value  the value of the margin
     */
    private void parseMargin(Layout layout, String value) {
        String[] split = value.trim().split(" ");
        if (split.length == 1) {
            if (isPercent(value))
                layout.setMarginPercent(stripValue(value));
            else
                layout.setMargin(stripValue(value));
        } else if (split.length == 2 || split.length == 3) { // 3 is a weird number
            if (isPercent(split[0]))
                layout.setMarginPercent(stripValue(split[0]), Edge.HORIZONTAL);
            else
                layout.setMargin(stripValue(split[0]), Edge.HORIZONTAL);
            if (isPercent(split[1]))
                layout.setMarginPercent(stripValue(split[1]), Edge.VERTICAL);
            else
                layout.setMargin(stripValue(split[1]), Edge.VERTICAL);
        } else if (split.length == 4) {
            if (isPercent(split[0]))
                layout.setMarginPercent(stripValue(split[0]), Edge.LEFT);
            else
                layout.setMargin(stripValue(split[0]), Edge.LEFT);
            if (isPercent(split[1]))
                layout.setMarginPercent(stripValue(split[1]), Edge.TOP);
            else
                layout.setMargin(stripValue(split[1]), Edge.TOP);
            if (isPercent(split[2]))
                layout.setMarginPercent(stripValue(split[2]), Edge.RIGHT);
            else
                layout.setMargin(stripValue(split[2]), Edge.RIGHT);
            if (isPercent(split[3]))
                layout.setMarginPercent(stripValue(split[3]), Edge.BOTTOM);
            else
                layout.setMargin(stripValue(split[3]), Edge.BOTTOM);
        }
    }

    /**
     * Simple method to check if a value is pixel or relative
     *
     * @param value the string to check against
     * @return returns true if type is pixel, false if relative
     */
    private boolean isPercent(String value) {
        return value.contains("%");
    }

    /**
     * This method will strip a string of it's % or px values
     * and parse it
     *
     * @return the stripped and parsed float value of the string
     */
    private float stripValue(String value) {
        if (isPercent(value))
            return Float.parseFloat(value.replace("%", "").trim());
        return Float.parseFloat(value.replace("px", "").trim());
    }

    /**
     * Will create a new style if one doesn't exist
     * or will return the style
     *
     * @param selector the id or class of the selector
     */
    private Style getOrMakeStyle(String selector) {
        if (styles.containsKey(selector))
            return styles.get(selector);
        Style style = new Style();
        styles.put(selector, style);
        return style;
    }

    /**
     * Will create a new layout if one doesn't exist
     * or will return the layout
     *
     * @param selector the id or class of the selector
     */
    private Layout getOrMakeLayout(String selector) {
        if (layouts.containsKey(selector))
            return layouts.get(selector);
        Layout layout = new Layout();
        layouts.put(selector, layout);
        return layout;
    }
}
