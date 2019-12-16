package me.jrayn.engine.ecs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import me.jrayn.core.IGameEngine;
import me.jrayn.engine.ecs.components.Camera;
import me.jrayn.engine.ecs.components.CameraConfig;
import me.jrayn.engine.ecs.components.Transform;
import me.jrayn.util.Input;
import org.joml.Matrix4f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

@All({Camera.class, Transform.class, CameraConfig.class})
public class CameraMover extends IteratingSystem {
    private IGameEngine gameEngine;
    protected ComponentMapper<Camera> cameras;
    protected ComponentMapper<Transform> transforms;
    protected ComponentMapper<CameraConfig> cameraConfigs;

    public CameraMover(IGameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    /**
     * Process the camera
     *
     * @param entityId the camera id
     */
    protected void process(int entityId) {
        Camera camera = cameras.get(entityId);
        Transform transform = transforms.get(entityId);
        CameraConfig config = cameraConfigs.get(entityId);
        float delta = getWorld().getDelta();
        updateProjection(camera, config);
        if (Input.isCursorDisabled()) {
            updateDirection(delta, camera, config);
            updatePosition(camera, config, transform);
            updateRotation(transform, config, delta);
            if (Input.keyPressed(GLFW_KEY_ESCAPE))
                Input.setCursorDisabled(false);
        } else {
            if (Input.mousePressed(GLFW_MOUSE_BUTTON_LEFT))
                Input.setCursorDisabled(true);
        }
        updateView(camera, transform);
    }

    /**
     * Checks to see if the projection matrix needs to be updated or not
     */
    private void updateProjection(Camera camera, CameraConfig config) {
        if (gameEngine.getWindow().hasResized() || camera.projectionMatrix == null) {
            camera.projectionMatrix = new Matrix4f().identity();
            camera.projectionMatrix.setPerspective((float) Math.toRadians(config.FOV), (float) gameEngine.getWindow().getWidth() / (float) gameEngine.getWindow().getHeight(), config.NEAR, config.FAR);
        }
    }

    /**
     * Update the direction the camera is facing
     */
    private void updateDirection(float delta, Camera camera, CameraConfig config) {
        camera.viewMatrix.positiveZ(camera.forward).negate().mul(config.SPEED * delta);
        camera.viewMatrix.positiveY(camera.up).mul(config.SPEED * delta);
        camera.viewMatrix.positiveX(camera.right).mul(config.SPEED * delta);
    }


    /**
     * Update the camera position based on the key input specified in the config
     */
    private void updatePosition(Camera camera, CameraConfig config, Transform transform) {
        if (Input.keyDown(config.FORWARD_KEY)) transform.position.add(camera.forward);
        if (Input.keyDown(config.BACKWARD_KEY)) transform.position.sub(camera.forward);
        if (Input.keyDown(config.LEFT_KEY)) transform.position.sub(camera.right);
        if (Input.keyDown(config.RIGHT_KEY)) transform.position.add(camera.right);
        if (Input.keyDown(config.UP_KEY)) transform.position.add(camera.up);
        if (Input.keyDown(config.DOWN_KEY)) transform.position.sub(camera.up);
    }

    /**
     * Update the camera rotation based on the mouse position on screen
     *
     * @param transform the camera transform=
     * @param config    the camera config
     */
    private void updateRotation(Transform transform, CameraConfig config, float delta) {
        transform.rotation.x = (float) (Input.mousePosition.y * config.SENSITIVITY);
        transform.rotation.y = (float) (Input.mousePosition.x * config.SENSITIVITY);
    }

    /**
     * Update the view matrix
     */
    private void updateView(Camera camera, Transform transform) {
        if (camera.viewMatrix == null)
            camera.viewMatrix = new Matrix4f();
        camera.viewMatrix.identity();
        camera.viewMatrix.rotateX(transform.rotation.x).rotateY(transform.rotation.y).translate(-transform.position.x, -transform.position.y, -transform.position.z);
    }
}
