package me.jrayn.engine;

public interface IGameState {

    /**
     * Initialize method
     */
    default void init(IGameEngine engine) {
    }

    /**
     * Render the current game state
     */
    default void render() {
    }

    /**
     * Update the current game state
     *
     * @param delta the time since the last frame
     */
    default void update(float delta) {
    }

    /**
     * Clean up anything needed to be cleaned from the current state
     */
    default void dispose() {
    }
}
