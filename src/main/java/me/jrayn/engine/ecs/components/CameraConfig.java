package me.jrayn.engine.ecs.components;

import com.artemis.Component;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Used to specify how the camera will be moved
 */
public class CameraConfig extends Component {
    public float SPEED = 0.01f, SENSITIVITY = 3;//Speed is speed of camera, sensitivity is the speed the camera will rotate
    public float FOV = 90, NEAR = 0.1f, FAR = 1000f; //matrix data

    //the keys for the camera
    public int FORWARD_KEY = GLFW_KEY_W, BACKWARD_KEY = GLFW_KEY_S,
            LEFT_KEY = GLFW_KEY_A, RIGHT_KEY = GLFW_KEY_D,
            UP_KEY = GLFW_KEY_SPACE, DOWN_KEY = GLFW_KEY_LEFT_CONTROL;
}
