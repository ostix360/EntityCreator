package fr.entityCreator.frame;

import java.io.File;

public class FileInList {

    private File file;
    private String name;

    public FileInList(File file) {
        this.file = file;
        this.name = file.getName().split("\\.")[0];
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
