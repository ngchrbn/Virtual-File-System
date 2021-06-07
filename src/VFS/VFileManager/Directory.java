package VFS.VFileManager;

import java.util.ArrayList;

public class Directory {

    private String directoryPath;

    private ArrayList<_File> files = new ArrayList<>();

    private ArrayList<Directory> subDirectories = new ArrayList<>();

    private boolean deleted = false;

    public Directory() {}
    public Directory(String name) {
        directoryPath = name;
    }
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

    public void printDirectoryStructure(int level) {
        System.out.println(directoryPath);
        printDirectory(level+1, this);
    }

    private void printDirectory(int level, Directory directory) {
        printFiles(directory.files, level);
        ArrayList<Directory> directories = directory.getSubDirectories();
        for (Directory dir: directories) {
            for (int j=0; j<level*2; ++j)
                System.out.print("   ");
            System.out.println(dir.getDirectoryPath());
            level++;
            printDirectory(level, dir);
            level--;
        }
    }

    private void printFiles(ArrayList<_File> files, int level) {
        for (_File file : files) {
            for (int j = 0; j < level * 2; ++j) {
                System.out.print("   ");
            }
            System.out.println(file.getFilePath());
        }
    }
}
