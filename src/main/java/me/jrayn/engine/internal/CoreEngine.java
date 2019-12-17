package me.jrayn.engine.internal;

import com.artemis.managers.TagManager;
import me.jrayn.bootstrap.project.EngineProject;
import me.jrayn.bootstrap.project.IProject;
import me.jrayn.core.IGameEngine;
import me.jrayn.core.IGameState;
import me.jrayn.core.IWorldProvider;
import me.jrayn.engine.ecs.internal.WorldProvider;
import me.jrayn.engine.ecs.systems.CameraMover;
import me.jrayn.engine.ecs.systems.ModelLoader;
import me.jrayn.engine.ecs.systems.ModelRenderer;
import me.jrayn.core.IGuiProvider;
import me.jrayn.ui.internal.GuiProvider;
import me.jrayn.ui.systems.NvgRenderer;
import me.jrayn.render.model.Model;
import me.jrayn.render.shader.Shader;
import me.jrayn.core.IWindow;

/**
 * The core of the engine, used for testing features among other things
 */
public class CoreEngine implements IGameEngine {
    private IWindow window;
    private IGameState state;
    private IGameState nextState;
    private long lastTime = System.currentTimeMillis();
    private IWorldProvider worldProvider;
    private IGuiProvider guiProvider;

    public CoreEngine(IWindow window) {
        this.window = window;
    }

    /**
     * Every game engine needs some of window right?
     *
     * @return main window
     */
    public IWindow getWindow() {
        return window;
    }

    /**
     * Starts the engine with the given game state
     *
     * @param state the state to start with
     */
    public void run(IProject project, IGameState state) {
        this.state = state;
        this.window.createWindow();
        this.worldProvider = createWorldProvider();
        this.guiProvider = createGuiProvider();
        guiProvider.parseGuiElements(project);
        state.init(this);
        while (update()) {
        }
        dispose();
    }

    /**
     * Update the game engine
     */
    public boolean update() {
        if (window.shouldClose())
            return false;
        processNextState();
        if (state == null) {
            dispose();
            return false;
        }
        long current = System.currentTimeMillis();
        float delta = (current - lastTime);
        worldProvider.getWorld().setDelta(delta);
        worldProvider.process();
        guiProvider.getWorld().setDelta(delta);
        guiProvider.getWorld().process();
        lastTime = current;
        state.render(); //render the current
        state.update(delta);
        window.update();
        return true;
    }

    /**
     * Gets the world provider. Only called getWorld for simplicity sake
     *
     * @return the world provider
     */
    public IWorldProvider getWorld() {
        return worldProvider;
    }

    /**
     * Get the ecs gui world used for managing gui systems
     *
     * @return the gui world
     */
    public IGuiProvider getGui() {
        return guiProvider;
    }

    /**
     * Create the world provider with the given systems
     *
     * @return the new world provider
     */
    private IWorldProvider createWorldProvider() {
        return new WorldProvider(
                new TagManager(),
                new CameraMover(this),
                new ModelRenderer(this),
                new ModelLoader(this)
        );
    }

    private IGuiProvider createGuiProvider() {
        return new GuiProvider(window, new NvgRenderer(this));
    }


    /**
     * Gets the current game state
     *
     * @return the current game state
     */
    public IGameState getState() {
        return state;
    }

    /**
     * Shut down the game engine, dispose of all objects etc and close the window
     */
    public void dispose() {
        Shader.disposeAll();
        Model.disposeAll();
        window.disposeWindow();
    }

    /**
     * Updates the current game state
     *
     * @param state the new state
     */
    public void changeState(IGameState state) {
        if (this.state != null)
            nextState = state;
        else
            switchState(state);
    }

    /**
     * @param newState the new state
     */
    private void switchState(IGameState newState) {
        if (state != null) {
            state.dispose();
        }
        state = newState;
        newState.init(this);
    }

    /**
     * Process the next state at the end of the update loop
     */
    private void processNextState() {
        if (nextState != null) {
            switchState(nextState);
            nextState = null;
        }
    }

}
