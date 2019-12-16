package me.jrayn;

import me.jrayn.bootstrap.project.EngineProject;
import me.jrayn.bootstrap.project.IProject;
import me.jrayn.core.IGameEngine;
import me.jrayn.engine.internal.CoreEngine;
import me.jrayn.engine.internal.states.SandBoxState;
import me.jrayn.engine.window.internal.GlfwWindow;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static IGameEngine ENGINE;

    public static void main(String[] args) throws IOException {
        String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j2.xml";
        ConfigurationSource source = new ConfigurationSource(new FileInputStream(log4jConfigFile));
        Configurator.initialize(null, source);
        IProject project = new EngineProject(".memez");
        System.out.println(project.getRoot());
        ENGINE = new CoreEngine(new GlfwWindow("GameEngine", 1080, 720, false));
        ENGINE.run(new SandBoxState());
    }
}
