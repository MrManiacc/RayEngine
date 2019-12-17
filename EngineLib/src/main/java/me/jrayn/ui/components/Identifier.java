package me.jrayn.ui.components;

import com.artemis.Component;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Represents a identifier that contains the name
 * of the component
 */
public class Identifier extends Component {

    private String id;

    public Identifier(String id) {
        this.id = id;
    }

    /**
     * No id is specified so we generated a random id
     */
    public Identifier() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
