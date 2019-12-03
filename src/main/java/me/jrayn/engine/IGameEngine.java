package me.jrayn.engine;

import me.jrayn.window.IWindow;

/**
 * This interface represents the core the engine,handles all game events/input/renderings
 */
public interface IGameEngine {
    /**
     * Every game engine needs some of window right?
     *
     * @return main window
     */
    IWindow getWindow();

    /**
     * Starts the engine with the given game state
     *
     * @param state the state to start with
     */
    void run(IGameState state);

    /**
     * Gets the current game state
     *
     * @return the current game state
     */
    IGameState getState();

    /**
     * Shut down the game engine, dispose of all objects etc and close the window
     */
    void dispose();

    /**
     * Updates the current game state
     *
     * @param state the new state
     */
    void changeState(IGameState state);

    /**
     * Update the game engine
     */
    boolean update();
}
