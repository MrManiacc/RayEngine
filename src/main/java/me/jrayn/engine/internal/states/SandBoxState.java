package me.jrayn.engine.internal.states;

import me.jrayn.engine.IGameEngine;
import me.jrayn.engine.IGameState;
import me.jrayn.render.model.Model;
import me.jrayn.render.shader.Shader;

import static org.lwjgl.opengl.GL11.*;

/**
 * This state will load all the assets including textures, shaders, models etc
 */
public class SandBoxState implements IGameState {
    private Model model;
    private Shader shader;

    public void init(IGameEngine engine) {
        createQuad();
        createShader();
    }

    /**
     * Create a test quad
     */
    private void createQuad() {
        model = Model.create();
        model.bind(0, 1);
        model.createAttribute(0, new float[]{-1, -1, 0, -1, 1, 0, 1, 1, 0, 1, -1, 0}, 3);
        model.createAttribute(1, new float[]{-1, 1, -1, -1, 1, 1, 1, -1}, 2);
        model.createIndexBuffer(new int[]{0, 1, 2, 0, 2, 3});
        model.unbind(0, 1);
    }

    private void createShader() {
        shader = new Shader("main") {
            @Override
            protected void doBinds() {
                super.bind(0, "vertex");
                super.bind(1, "texCoords");
            }

            @Override
            protected void doUniformBinds() {
            }
        };
    }

    public void render() {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        shader.start();
        model.bind(0, 1);
        glDrawElements(GL_TRIANGLES, model.getIndexCount(), GL_UNSIGNED_INT, 0);
        model.unbind(0, 1);
        shader.stop();
    }
}
