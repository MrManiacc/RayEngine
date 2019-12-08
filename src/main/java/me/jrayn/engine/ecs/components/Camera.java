package me.jrayn.engine.ecs.components;

import com.artemis.Component;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Simply stores the matrices needed for a camera class
 */
public class Camera extends Component {
    public Matrix4f projectionMatrix = new Matrix4f();
    public Matrix4f viewMatrix = new Matrix4f();

    public Vector3f forward = new Vector3f(), right = new Vector3f(), up = new Vector3f();
}
