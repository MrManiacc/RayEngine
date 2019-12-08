package me.jrayn.engine.internal.states;

import com.artemis.Entity;
import me.jrayn.engine.IGameEngine;
import me.jrayn.engine.IGameState;
import me.jrayn.engine.ecs.components.Camera;
import me.jrayn.engine.ecs.components.CameraConfig;
import me.jrayn.engine.ecs.components.RawMesh;
import me.jrayn.engine.ecs.components.Transform;
import org.joml.Vector3f;

/**
 * This state will load all the assets including textures, shaders, models etc
 */
public class SandBoxState implements IGameState {

    public void init(IGameEngine engine) {
        createTestModel(engine, new Vector3f(0, 0, -10));
        createTestModel(engine, new Vector3f(5, 0, -10));
        createTestModel(engine, new Vector3f(-5, 0, -10));
        createCamera(engine);
    }
    
    private void createTestModel(IGameEngine engine, Vector3f position) {
        int testModel = engine.getWorld().createEntity();
        RawMesh model = engine.getWorld().create(testModel, RawMesh.class);
        Transform transform = engine.getWorld().create(testModel, Transform.class);
        transform.position = position;

        model.vertices = new float[]{
                // front
                -1.0f, -1.0f, 1.0f,
                1.0f, -1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                -1.0f, 1.0f, 1.0f,
                // back
                -1.0f, -1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,
                1.0f, 1.0f, -1.0f,
                -1.0f, 1.0f, -1.0f
        };
        model.indices = new int[]{
                0, 1, 2,
                2, 3, 0,
                // right
                1, 5, 6,
                6, 2, 1,
                // back
                7, 6, 5,
                5, 4, 7,
                // left
                4, 0, 3,
                3, 7, 4,
                // bottom
                4, 5, 1,
                1, 0, 4,
                // top
                3, 2, 6,
                6, 7, 3
        };


    }


    private void createCamera(IGameEngine engine) {
        int playerEntity = engine.getWorld().createEntity();
        Entity player = engine.getWorld().getEntity(playerEntity);
        player.edit().create(Camera.class);
        player.edit().create(Transform.class);
        player.edit().create(CameraConfig.class);
        engine.getWorld().registerTagEntity("CAMERA", player);
    }


}
