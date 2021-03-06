package VFS.VFileManager;

import VFS.Allocator.Allocator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
     */
    public void execute(String cmd, String[] args) {

        switch (cmd)
        {
            case "CreateFile" -> {
                String newFilePath = args[0];
                StringBuilder filePath = extractPrePath(newFilePath);

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
                StringBuilder folderPath = extractPrePath(newFolderPath);

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
            case "DeleteFile" -> {
                StringBuilder filePath = extractPrePath(args[0]);
                Directory dir = GetDirectory(filePath.toString(), getRoot());

                deleteFile(args[0], dir);
            }
            case "DeleteFolder" -> {
                StringBuilder folderPath = extractPrePath(args[0]);
                Directory dir = GetDirectory(folderPath.toString(), getRoot());
                System.out.println(args[0]);
                deleteDirectory(args[0], dir);
            }
            case "DisplayDiskStatus" -> {
                allocator.displayDiskStatus();
            }
            case "DisplayDiskStructure" -> {
                getRoot().printDirectoryStructure(0);
            }
        }
    }

    private void deleteFile(String filePath, Directory dir) {
        ArrayList<_File> files = dir.getFiles();

        for (_File file: files) {
            if (file.getFilePath().equals(filePath)) {
                allocator.deAllocateFile(file);
                dir.getFiles().remove(file);
                System.out.println("File deleted successfully");
            }
        }
        System.out.println("File does not exist!");
    }

    private void deleteDirectory(String path, Directory dir) {
        ArrayList<Directory> directories = dir.getSubDirectories();

        for (Directory directory: directories) {
            if (directory.getDirectoryPath().equals(path)) {
                dir.getSubDirectories().remove(directory);
                System.out.println("Folder deleted successfully");
                return;
            }
        }
        System.out.println("Directory does not exist!");
    }

    private StringBuilder extractPrePath(String Path) {
        String[] rootPath = Path.split("/");

        StringBuilder existingPath = new StringBuilder();

        for (int i=0; i< rootPath.length-1; ++i) {
            existingPath.append(rootPath[i]).append("/");
        }

        return existingPath;
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


}
