package me.jrayn.ui.components;

import com.artemis.Component;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a text component, can be added
 * to a ui node to display text
 */
public class Text extends Component {

    private String text = "";
    //represents the width of the text

    public Text(String text) {
        this.text = text;
    }

    public Text() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
