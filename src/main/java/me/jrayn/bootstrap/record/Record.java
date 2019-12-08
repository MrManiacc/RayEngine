package me.jrayn.bootstrap.record;

import lombok.Getter;
import me.jrayn.bootstrap.folder.Folder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents a file in the game engine
 */
public class Record implements IRecord {
    @Getter
    private Path path;
    @Getter
    private File file;
    @Getter
    private Folder parent;//the container

    /**
     * Create a record using an absolute file path
     *
     * @param filePath the absolute file path
     */
    public Record(String filePath) {
        this.file = new File(filePath);
        this.path = Paths.get(file.toURI());
        this.parent = new Folder(file.getParent());
    }

    /**
     * Get a record with a folder parent and a fileName offset
     *
     * @param parent   the container folder
     * @param fileName the sub-file name
     */
    public Record(Folder parent, String fileName) {
        this.file = new File(parent.getFile(), fileName);
        this.path = Paths.get(file.toURI());
        this.parent = new Folder(file.getParent());
    }

    public String toString() {
        return "[" + getName() + "] " + getPath().toString();
    }
}
