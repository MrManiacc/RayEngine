package me.jrayn.bootstrap.folder;

import me.jrayn.bootstrap.record.IRecord;
import me.jrayn.bootstrap.record.Record;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Used to represent a game folder
 */
public class Folder implements IFolder {
    private final Map<String, IFolder> children = new HashMap<>();
    private final List<IRecord> records = new ArrayList<>();
    private final String rawPath;
    private final File file;
    private final Path path;
    private Folder parent;

    public Folder(String path) {
        this.rawPath = path;
        this.file = new File(rawPath);
        this.path = Paths.get(file.toURI());
        if (file.getParent() != null)
            if (!file.getParent().isEmpty())
                this.parent = new Folder(file.getParent());
    }

    /**
     * Get a sub folder based on the parent
     *
     * @param parent the parent directory
     * @param subDir the sub directory
     */
    public Folder(IFolder parent, String subDir) {
        this(parent.getAbsolutePath() + File.separator + subDir);
    }

    /**
     * Get a directory with specified subDirs, the subDirs are all
     * appended together using the File.Separator.
     *
     * @param parent  the parent directory
     * @param subDirs the array of child directories
     */
    public Folder(IFolder parent, String... subDirs) {
        StringBuilder subDir = new StringBuilder();
        for (String dir : subDirs)
            subDir.append(File.separator).append(dir);
        this.rawPath = parent.getAbsolutePath() + subDir.toString();
        this.file = new File(rawPath);
        this.path = Paths.get(file.toURI());
        if (!file.getParent().isEmpty())
            this.parent = new Folder(file.getParent());
    }


    /**
     * Get the children of the directory.
     * This is computed recursively
     *
     * @return children dirs
     */
    public Map<String, IFolder> getFolders() {
        return children;
    }

    /**
     * Rebuilds the children folders and records
     * This will recursively fill the children list
     */
    public IFolder propagate() {
        String[] directories = file.list((current, name) -> new File(current, name).isDirectory());
        assert directories != null;
        for (String dirName : directories)
            children.put(dirName, new Folder(this, dirName));

        String[] files = file.list((current, name) -> new File(current, name).isFile());
        assert files != null;
        for (String fileName : files)
            records.add(new Record(this, fileName));


        for (IFolder folder : children.values())
            folder.propagate();

        return this;
    }

    /**
     * Whether or not the folder is created
     *
     * @return folder status
     */
    public boolean isPresent() {
        return file.exists() && file.isDirectory();
    }

    /**
     * The real path to the folder
     *
     * @return folder path
     */
    public String getAbsolutePath() {
        return rawPath;
    }

    /**
     * Create's the folder, should auto check isPresent() first
     *
     * @return returns true if created, false if it's already created
     */
    public boolean createFolder() {
        if (!isPresent())
            return file.mkdirs();
        return false;
    }

    /**
     * Get the collection of files inside this directory
     *
     * @return collection of records in directory
     */
    public Collection<IRecord> getRecords() {
        return records;
    }

    /**
     * Get the collection of files inside this directory
     * and filters them based on their extension
     *
     * @return collection of records in directory
     */
    public Collection<IRecord> getRecords(String extension) {
        List<IRecord> sortedRecords = new ArrayList<>();
        for (IRecord record : records) {
            if (record.getExtension().equalsIgnoreCase(extension)) {
                sortedRecords.add(record);
            }
        }
        return sortedRecords;
    }

    public String toString() {
        return "[" + getName() + "] " + rawPath;
    }

    public Path getPath() {
        return path;
    }

    public File getFile() {
        return file;
    }

    public IFolder getParent() {
        return parent;
    }
}
