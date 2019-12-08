package me.jrayn.bootstrap.folder;

import me.jrayn.bootstrap.record.IRecord;

import java.util.Collection;
import java.util.Map;

/**
 * Represents a game engine directory
 */
public interface IFolder extends IRecord {

    /**
     * Get the children of the directory
     *
     * @return children dirs
     */
    Map<String, IFolder> getFolders();

    /**
     * Rebuilds the children folders and records
     */
    IFolder propagate();

    /**
     * Whether or not the folder is created
     *
     * @return folder status
     */
    boolean isPresent();

    /**
     * The real path to the folder
     *
     * @return folder path
     */
    String getAbsolutePath();

    /**
     * Create's the folder, should auto check isPresent() first
     *
     * @return returns true if created, false if it's already created
     */
    boolean createFolder();

    /**
     * Get the collection of files inside this directory
     *
     * @return collection of records in directory
     */
    Collection<IRecord> getRecords();
}
