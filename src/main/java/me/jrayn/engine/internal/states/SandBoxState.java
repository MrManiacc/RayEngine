package me.jrayn.engine.internal.states;

import com.artemis.Entity;
import me.jrayn.core.IGameEngine;
import me.jrayn.core.IGameState;
import me.jrayn.engine.ecs.components.Camera;
import me.jrayn.engine.ecs.components.CameraConfig;
import me.jrayn.engine.ecs.components.RawMesh;
import me.jrayn.engine.ecs.components.Transform;
import me.jrayn.ui.IGuiProvider;
import me.jrayn.ui.components.Layout;
import me.jrayn.ui.components.types.*;
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
        createTestGui(engine.getGui());
    }

    private void createTestGui(IGuiProvider provider) {
        int root = provider.createRootNode();
        provider.layout(root).setFlexDirection(Flex.COLUMN);
        provider.style(root).setRender(false);
        provider.layout(root).setFlex(1.0f);
        provider.insert(root, Layout.root);
        provider.layout(root).setJustify(Justify.CENTER);

        int hbContainer = provider.createNode();
        provider.layout(hbContainer).setAbsolute();
        provider.layout(hbContainer).setPosition(20, Edge.BOTTOM);
        provider.layout(hbContainer).setWidthPercent(30);
        provider.layout(hbContainer).setHeightAbsolute(80);
        provider.layout(hbContainer).setSelfAlignment(Align.CENTER);
        provider.style(hbContainer).setBackgroundColor(Color.of("gray"));
        provider.layout(hbContainer).setFlexDirection(Flex.ROW);
        provider.insert(hbContainer, root);

        int container = provider.createNode();
        provider.layout(container).setWidthPercent(30);
        provider.layout(container).setHeightPercent(95);
        provider.layout(container).setMaxWidthAbsolute(400);
        provider.style(container).setBackgroundColor(Color.of("dark-gray"));
        provider.style(container).setBorderRadius(8);
        provider.layout(container).setFlexDirection(Flex.COLUMN);
        provider.layout(container).setMargin(20, Edge.LEFT);
        provider.layout(container).setMargin(20, Edge.TOP);
        provider.insert(container, root);

        int inputContainer = provider.createNode();
        provider.layout(inputContainer).setWidthPercent(100);
        provider.layout(inputContainer).setHeightPercent(20);
        provider.layout(inputContainer).setJustify(Justify.CENTER);
        provider.layout(inputContainer).setFlexDirection(Flex.COLUMN);
        provider.style(inputContainer).setRender(false);
        provider.style(inputContainer).setBackgroundColor(Color.of("light-gray"));
        provider.insert(inputContainer, container);

        int inputOne = provider.createNode();
        provider.layout(inputOne).setWidthPercent(90);
        provider.layout(inputOne).setHeightPercent(20);
        provider.layout(inputOne).setMargin(20, Edge.TOP);
        provider.layout(inputOne).setMarginPercent(5, Edge.HORIZONTAL);
        provider.style(inputOne).setBackgroundColor(Color.of("light-gray"));
        provider.insert(inputOne, inputContainer);

        int inputTwo = provider.createNode();
        provider.layout(inputTwo).setWidthPercent(90);
        provider.layout(inputTwo).setHeightPercent(20);
        provider.layout(inputTwo).setMargin(20, Edge.TOP);
        provider.layout(inputTwo).setMarginPercent(5, Edge.HORIZONTAL);
        provider.style(inputTwo).setBackgroundColor(Color.of("light-gray"));
        provider.insert(inputTwo, inputContainer);


        int pageContainer = provider.createNode();
        provider.layout(pageContainer).setFlex(1.0f);
        provider.layout(pageContainer).setWidthPercent(100);
        provider.style(pageContainer).setBackgroundColor(Color.of("light-gray"));
        provider.layout(pageContainer).setFlexDirection(Flex.ROW);
        provider.layout(pageContainer).setMargin(20, Edge.BOTTOM);
        provider.insert(pageContainer, container);
        provider.layout(pageContainer).setItemsAlignment(Align.SPACE_BETWEEN);
        provider.layout(pageContainer).setContentAlignment(Align.SPACE_BETWEEN);
        provider.style(pageContainer).setRender(false);

        int pageOne = provider.createTextNode("page one");
        provider.layout(pageOne).setHeightAbsolute(25);
        provider.layout(pageOne).setFlex(1);
        provider.layout(pageOne).setMargin(2.5f, Edge.RIGHT);
        provider.style(pageOne).setBackgroundColor(Color.of("light-gray"));
        provider.insert(pageOne, pageContainer);

        int pageTwo = provider.createTextNode("page two");
        provider.layout(pageTwo).setHeightAbsolute(25);
        provider.layout(pageTwo).setFlex(1);
        provider.layout(pageTwo).setMargin(2.5f, Edge.LEFT);
        provider.style(pageTwo).setBackgroundColor(Color.of("gray"));
        provider.insert(pageTwo, pageContainer);



        provider.compute(root);
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
