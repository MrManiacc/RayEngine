package me.jrayn.ui.components;

import com.artemis.Component;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a text component, can be added
 * to a ui node to display text
 */
public class Text extends Component {
    @Getter
    @Setter
    private String text = "";
    //represents the width of the text
    @Getter
    @Setter
    private float width = 0;

}
