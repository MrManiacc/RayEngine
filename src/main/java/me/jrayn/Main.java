package me.jrayn;

import me.jrayn.engine.IGameEngine;
import me.jrayn.engine.internal.CoreEngine;
import me.jrayn.window.internal.GlfwWindow;
import me.jrayn.engine.internal.states.SandBoxState;

public class Main {
    public static IGameEngine ENGINE;

    public static void main(String[] args) {
        ENGINE = new CoreEngine(new GlfwWindow("GameEngine", 1080, 720, false));
        ENGINE.run(new SandBoxState());
    }
}
