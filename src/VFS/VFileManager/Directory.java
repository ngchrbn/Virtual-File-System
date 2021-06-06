package VFS.VFileManager;

import java.util.ArrayList;

public class Directory {

    private String directoryPath;

    private ArrayList<_File> files = new ArrayList<>();

    private ArrayList<Directory> subDirectories = new ArrayList<>();

    private boolean deleted = false;
    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public ArrayList<_File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<_File> files) {
        this.files = files;
    }

    public ArrayList<Directory> getSubDirectories() {
        return subDirectories;
    }

    public void setSubDirectories(ArrayList<Directory> subDirectories) {
        this.subDirectories = subDirectories;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void addFile(_File file) {
        files.add(file);
    }

    public void addDirectory(Directory directory) {
        subDirectories.add(directory);
    }

    public Directory getSubDirectory(String directoryName) {
        for (Directory subDirectory : subDirectories) {
            if (subDirectory.directoryPath.equals(directoryName))
                return subDirectory;
        }
        System.out.println("No such Directory");
        return null;
    }

    @Override
    public String toString() {
        return directoryPath;
    }
}
