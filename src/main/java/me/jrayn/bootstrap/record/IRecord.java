package me.jrayn.bootstrap.record;


import me.jrayn.bootstrap.folder.IFolder;

import java.io.File;
import java.nio.file.Path;

/**
 * Represents a record
 */
public interface IRecord {
    /**
     * The normalized directory name
     *
     * @return normalized name
     */
    default String getName() {
        return getPath().getFileName().toString();
    }

    /**
     * The path of the directory
     *
     * @return path representation
     */
    Path getPath();

    /**
     * Get the file of the record
     *
     * @return file
     */
    File getFile();

    /**
     * Get the parent directory
     *
     * @return parent directory
     */
    IFolder getParent();

    /**
     * Parse the record, default because not every file type
     * is parsable
     */
    default void parse() {
    }
}
