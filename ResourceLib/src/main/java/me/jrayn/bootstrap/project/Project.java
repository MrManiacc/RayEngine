package me.jrayn.bootstrap.project;

import me.jrayn.bootstrap.folder.Folder;
import me.jrayn.bootstrap.folder.IFolder;

public class Project implements IProject {
    IFolder root; //package level, so {@EngineProject} can set the root manually

    /**
     * Create a project with a given root folder
     *
     * @param rootFolder the actual root folder
     */
    public Project(IFolder rootFolder) {
        this.root = rootFolder;
        this.root.propagate();
    }

    /**
     * Create a project with a given folder path
     *
     * @param rootFolder the rootFolder path, this will be made into a new folder
     */
    public Project(String rootFolder) {
        this(new Folder(rootFolder));
    }

    /**
     * Used so you can manually manipulate the root folder in extensions
     * of this class
     */
    public Project() {
    }


    /**
     * This method will attempt to find the folder at the first level.
     * If the folder isn't found at the first level it will keep searching lower
     * level's until it's found or it'll just return null
     *
     * @param folderName the name of the folder to get
     * @return null or the found folder
     */
    public IFolder getFolder(String folderName) {
        return findFolder(root, folderName);
    }

    /**
     * This method will attempt to find the folder at the first level.
     * If the folder isn't found at the first level it will keep searching lower
     * level's until it's found or it'll just return null
     *
     * @param folderName the name of the folder to get
     * @return null or the found folder
     */
    public IFolder getFolder(IFolder parentFolder, String folderName) {
        return findFolder(parentFolder, folderName);
    }

    /**
     * This method will first search the top level for the folder,
     * if it's not found there, it will recursively search the sub folder's
     * until it's found the correct folder
     *
     * @param folder     the folder to search in
     * @param folderName the folderName to search for
     * @return the folder with the given folderName or null
     */
    private IFolder findFolder(IFolder folder, String folderName) {
        if (folder.getFolders().containsKey(folderName))
            return folder.getFolders().get(folderName);
        for (IFolder subFolder : folder.getFolders().values()) {
            return findFolder(subFolder, folderName);
        }
        return null;
    }

    public IFolder getRoot() {
        return root;
    }
}
