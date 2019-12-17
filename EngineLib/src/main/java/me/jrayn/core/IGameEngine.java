package me.jrayn.core;

import me.jrayn.bootstrap.project.IProject;

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
    void run(IProject project, IGameState state);

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

    /**
     * get the ecs world used for registering systems, component management entities etc
     *
     * @return the ecs world
     */
    IWorldProvider getWorld();

    /**
     * Get the ecs gui world used for managing gui systems
     *
     * @return the gui world
     */
    IGuiProvider getGui();
}
