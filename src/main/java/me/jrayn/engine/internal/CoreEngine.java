package me.jrayn.engine.internal;

import me.jrayn.engine.IGameEngine;
import me.jrayn.engine.IGameState;
import me.jrayn.render.model.Model;
import me.jrayn.render.shader.Shader;
import me.jrayn.window.IWindow;


public class CoreEngine implements IGameEngine {
    private IWindow window;
    private IGameState state;
    private IGameState nextState;
    private long lastTime = System.currentTimeMillis();

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
    public void run(IGameState state) {
        this.state = state;
        window.createWindow();
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
        lastTime = current;
        state.render(); //render the current
        state.update(delta);
        window.update();
        return true;
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
