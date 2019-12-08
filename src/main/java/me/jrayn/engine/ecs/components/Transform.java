package me.jrayn.engine.ecs.components;

import com.artemis.Component;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Used to manipulate a 3d model's position rotation and scale
 */
public class Transform extends Component {
    public Vector3f position = new Vector3f();
    public Vector3f rotation = new Vector3f();
    public Vector3f scale = new Vector3f(1);
    private Matrix4f matrix = new Matrix4f();

    /**
     * Update the matrix with the current transform data
     *
     * @return
     */
    public Matrix4f matrix() {
        return matrix.identity().translate(position).rotateX(rotation.x).rotateY(rotation.y).rotateZ(rotation.z).scale(scale);
    }
}
