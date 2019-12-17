package me.jrayn.bootstrap.project;

import me.jrayn.bootstrap.folder.IFolder;
import me.jrayn.bootstrap.record.IRecord;

/**
 * Represents a project which has a list of folders
 * and a list of project files
 */
public interface IProject {
    /**
     * The root project folder
     *
     * @return the root
     */
    IFolder getRoot();

    /**
     * Get a folder based upon the directory name
     *
     * @param folderName the name of the folder to get
     * @return folder with given name
     */
    IFolder getFolder(String folderName);

    /**
     * Get a folder based upon the directory name
     *
     * @param parentFolder the folder's parent to get from
     * @param folderName   the name of the folder to get
     * @return folder with given name
     */
    IFolder getFolder(IFolder parentFolder, String folderName);

    /**
     * Get's a record from the specified folder
     *
     * @param folderName the folder to look for the record in
     * @param recordName the record to look for in the specified folder
     * @return the record if found or null
     */
    default IRecord getRecord(String folderName, String recordName) {
        IFolder folder = getFolder(folderName);
        return getRecord(folder, recordName);
    }

    /**
     * Get's a record from the root folder
     *
     * @param recordName the record to look for in the root folder
     * @return a record in the root folder or null
     */
    default IRecord getRecord(String recordName) {
        return getRecord(getRoot(), recordName);
    }

    /**
     * Get's a record from the specified folder
     *
     * @param folder     the folder to look for the record in
     * @param recordName the record to look for in the specified folder
     * @return the record if found or null
     */
    default IRecord getRecord(IFolder folder, String recordName) {
        for (IRecord record : folder.getRecords())
            if (record.getName().equalsIgnoreCase(recordName))
                return record;
        return null;
    }
}
