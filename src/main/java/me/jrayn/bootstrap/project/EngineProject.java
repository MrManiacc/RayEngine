package me.jrayn.bootstrap.project;

import me.jrayn.bootstrap.folder.Folder;
import me.jrayn.bootstrap.folder.IFolder;
import me.jrayn.bootstrap.sys.OS;

import java.io.File;

/**
 * This class represents a engine project,
 * which will create the folders needed
 * based on the operating system
 */
public class EngineProject extends Project {
    //The folder containing the game models
    private IFolder modelsFolder;
    //The folder containing the game textures
    private IFolder texturesFolder;
    //The folder containing the game libraries
    private IFolder libsFolder;
    //The folder containing the game logs
    private IFolder logsFolder;
    //The folder containing the game config's
    private IFolder configFolder;

    /**
     * Create an engine project directory, which is relative to
     * the current operating system
     *
     * @param projectName the projectName
     */
    public EngineProject(String projectName) {
        this.root = new Folder(OS.getOSDir() + File.separator + projectName);
        if (!root.createFolder())
            root.propagate();
        //create all of the sub folders
        makeFolders();
    }

    /**
     * This method will create the sub folders
     * that will store the data needed, for example it will
     * create a folder for libs, and will download the required libraries
     */
    private void makeFolders() {
        //create the models folder
        this.modelsFolder = new Folder(root, "models");
        if (!modelsFolder.createFolder())
            modelsFolder.propagate();

        //create the textures folder
        this.texturesFolder = new Folder(root, "textures");
        if (!texturesFolder.createFolder())
            texturesFolder.propagate();

        //create the libraries folder
        this.libsFolder = new Folder(root, "libraries");
        if (!libsFolder.createFolder())
            libsFolder.propagate();

        //create the logs folder
        this.logsFolder = new Folder(root, "logs");
        if (!logsFolder.createFolder())
            logsFolder.propagate();

        //create the config folder
        this.configFolder = new Folder(root, "config");
        if (!configFolder.createFolder())
            configFolder.propagate();

    }

}
