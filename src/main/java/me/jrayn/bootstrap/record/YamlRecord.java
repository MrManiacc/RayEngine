package me.jrayn.bootstrap.record;


import me.jrayn.bootstrap.folder.Folder;

/**
 * Represents a file in the game engine that is yaml
 */
public class YamlRecord extends Record {

    public YamlRecord(String filePath) {
        super(filePath);
    }

    public YamlRecord(Folder parent, String fileName) {
        super(parent, fileName);
    }

    /**
     * Implement a parse method for the file
     */
    public void parse() {

    }
}
