package VFS.VFileManager;

import VFS.Allocator.Allocator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class VirtualFileSystem {
    private Directory root;
    private final Allocator allocator;

    public VirtualFileSystem(Allocator allocator) throws IOException {
        String vfsPathName = "VFS.txt";
        File vfsFile = new File(vfsPathName);
        if (vfsFile.createNewFile())
            System.out.println("\nInitializing...");
        setRoot(new Directory());
        getRoot().setDirectoryPath("root");
        this.allocator = allocator;
    }

    private Directory getRoot() {
        return this.root;
    }

    private void setRoot(Directory directory) {
        this.root = directory;
    }

    /**
     * Execute the given command after validating the path(if given).
     * @param cmd command to execute
     * @param args arguments of the command if needed
     * @param nArgs number of arguments for the command
     */
    public void execute(String cmd, String[] args, Integer nArgs) {

        switch (cmd)
        {
            case "CreateFile" -> {
                String newFilePath = args[0];

                String[] rootPath = newFilePath.split("/");
                StringBuilder filePath = new StringBuilder();
                for (int i=0; i<rootPath.length-1; ++i) {
                    filePath.append(rootPath[i]).append("/");
                }
                Directory dir = GetDirectory(filePath.toString(), getRoot());

                if (validateDirectory(newFilePath, dir)) {
                    _File file = new _File(newFilePath, Integer.parseInt(args[1]));

                    if (allocator.allocateFile(file) != -1)
                    {
                        dir.addFile(file);
                        System.out.println("FIle Added Successfully");
                    }
                    else
                    {
                        System.out.println("Failed to add file");
                    }
                }
                else
                    System.out.println("Invalid path");
            }
            case "CreateFolder" -> {
                String newFolderPath = args[0];

                String[] rootPath = newFolderPath.split("/");
                StringBuilder folderPath = new StringBuilder();
                for (int i=0; i<rootPath.length-1; ++i) {
                    folderPath.append(rootPath[i]).append("/");
                }
                Directory directory = GetDirectory(folderPath.toString(), getRoot());

                if (validateDirectoryPath(newFolderPath, directory)) {
                    Directory dir = new Directory();

                    dir.setDirectoryPath(newFolderPath);
                    directory.addDirectory(dir);

                    System.out.println("Directory added successfully");
                }
                else
                    System.out.println("Directory already exists");
            }
        }
    }

    private boolean validateDirectory(String filePath, Directory dir) {
        if (dir == null)
            return false;

        ArrayList<_File> files = dir.getFiles();

        for (_File file: files)
        {
            if (file.getFilePath().equals(filePath))
                return false;
        }
        return true;
    }
    private boolean validateDirectoryPath(String newFolderPath, Directory dir) {
        if (dir == null)
            return false;
        ArrayList<Directory> subDirectories = dir.getSubDirectories();
        for (Directory directory: subDirectories)
        {
            if (directory.getDirectoryPath().equals(newFolderPath))
                return false;
        }
        return true;
    }

    private Directory GetDirectory(String path, Directory root) {
        if ((root.getDirectoryPath() + "/").equals(path))
            return root;
        ArrayList<Directory> directories = root.getSubDirectories();
        Directory directory = null;

        for (Directory value : directories) {
            if (directory != null)
                break;
            if ((value.getDirectoryPath() + "/").equals(path)) {
                directory = value;
                return directory;
            } else
                directory = GetDirectory(path, value);
        }
        return directory;
    }

    private boolean validatePath(String arg) {
        String[] path = arg.split("/");
        return false;
    }
}
