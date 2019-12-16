package me.jrayn.engine.ecs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import me.jrayn.core.IGameEngine;
import me.jrayn.engine.ecs.components.Camera;
import me.jrayn.engine.ecs.components.RenderModel;
import me.jrayn.engine.ecs.components.Transform;
import me.jrayn.render.shader.Shader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.lwjgl.opengl.GL11.*;


/**
 * This system will render all entities that have a
 */
@All({RenderModel.class, Transform.class})
public class ModelRenderer extends IteratingSystem {
    protected ComponentMapper<RenderModel> renderModels;
    protected ComponentMapper<Transform> transforms;
    private final IGameEngine engine;
    private final Logger logger = LogManager.getLogger();
    private Shader shader;
    private Camera camera;

    public ModelRenderer(IGameEngine gameEngine) {
        this.engine = gameEngine;
    }

    /**
     * Initialize the shader
     */
    protected void initialize() {
        shader = new Shader("model") {
            protected void doBinds() {
                super.bind(0, "vertex");
                super.bind(1, "normal");
                super.bind(2, "texCoords");
                //TODO: create a shader that can use tangents and biTangents
            }

            protected void doUniformBinds() {
                super.bindUniform("projectMatrix");
                super.bindUniform("viewMatrix");
                super.bindUniform("modelMatrix");
            }
        };
        //get the camera component

    }

    /**
     * Begin the render process
     */
    protected void begin() {
        setupCamera();
        glClearColor(0.301f, 0.702f, 0.666f, 1.0f);
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        shader.start();
        shader.loadMat4("projectMatrix", camera.projectionMatrix);
        shader.loadMat4("viewMatrix", camera.viewMatrix);
    }

    /**
     * Attempts to get the camera component
     */
    private void setupCamera() {
        if (this.camera == null) {
            this.camera = engine.getWorld().getTagEntity("CAMERA").getComponent(Camera.class);
        }
    }

    /**
     * Render the iterated entity
     *
     * @param entityId the entity to render
     */
    protected void process(int entityId) {
        RenderModel model = renderModels.get(entityId);
        Transform transform = transforms.get(entityId);
        shader.loadMat4("modelMatrix", transform.matrix());
        model.render();
    }

    /**
     * End the render process
     */
    protected void end() {
        shader.stop();
    }
}
