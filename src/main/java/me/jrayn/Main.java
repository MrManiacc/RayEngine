package me.jrayn;

import me.jrayn.bootstrap.project.EngineProject;
import me.jrayn.bootstrap.project.IProject;
import me.jrayn.core.IGameEngine;
import me.jrayn.engine.internal.CoreEngine;
import me.jrayn.engine.internal.states.SandBoxState;
import me.jrayn.engine.window.GlfwWindow;

import java.io.IOException;

public class Main {
    public static IGameEngine ENGINE;

    public static void main(String[] args) throws IOException {
        IProject project = new EngineProject(".memez");
        System.out.println(project.getRoot());
        ENGINE = new CoreEngine(new GlfwWindow("GameEngine", 1080, 720, false));
        ENGINE.run(new SandBoxState());
    }
}
