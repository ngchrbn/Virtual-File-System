package VFS.Allocator;

import VFS.VFileManager.Directory;
import VFS.VFileManager._File;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Contiguous implements Allocator{

    private final ArrayList<Integer> blocks = new ArrayList<>();

    private final String systemPath = "VFS.txt";
    private String[] vfsContentList;

    public Contiguous(int blocks) {
        for (int i=0; i<blocks; ++i)
            this.blocks.add(-1);
    }

    /**
     * Set all blocks as empty blocks
     *
     * @param nBlocks : number of blocks to fill
     */
    @Override
    public void initialize(int nBlocks) {

    }

    /**
     * Load all files and directories from VFS.txt, and
     * Store them in the root directory
     *
     * @param root : Store all files and directories(subdirectories)
     */
    @Override
    public void loadHardDisk(Directory root) {
        int loadIndex = 1;

        String vfsContent = readFile(systemPath);
        vfsContentList = vfsContent.split("\n");
        String[] rootParams = vfsContentList[0].split("-");

        String rootPath = rootParams[0];
        int nFiles = Integer.parseInt(rootParams[1]);
        int nDirectory = Integer.parseInt(rootParams[2]);

        root.setDirectoryPath(rootPath);
        loadFiles(root.getFiles(), nFiles, loadIndex);

        loadIndex += nFiles;

        loadDirectories(root.getSubDirectories(), nDirectory, loadIndex);
        loadIndex += nDirectory;
    }

    private void loadDirectories(ArrayList<Directory> subDirectories, int nDirectory, int startIndex) {
        for (int i=0; i<nDirectory; ++i) {
            String[] directoryParams = vfsContentList[startIndex+i].split("-");
            String directoryPath = directoryParams[0];
            int nFiles = Integer.parseInt(directoryParams[1]);
            int nSubDirectory = Integer.parseInt(directoryParams[2]);

            Directory subDirectory = new Directory();
            subDirectory.setDirectoryPath(directoryPath);
            loadFiles(subDirectory.getFiles(), nFiles, startIndex);

            startIndex += nFiles;

            loadDirectories(subDirectory.getSubDirectories(), nSubDirectory, startIndex);
            startIndex += nSubDirectory;
            subDirectories.add(subDirectory);
        }
    }

    private void loadFiles(ArrayList<_File> files, int nFiles, int startIndex) {
        for (int i=0; i<nFiles; ++i) {
            String[] fileParams = vfsContentList[startIndex+i].split("-");
            String filePath = fileParams[0];
            int startBlock = Integer.parseInt(fileParams[1]);
            int nBlocks = Integer.parseInt(fileParams[2]);

            _File file = new _File(nBlocks);
            file.setStartIndex(startBlock);
            file.setFilePath(filePath);
            allocateFile(file);

            files.add(file);
        }
    }

    private String readFile(String path) {
        BufferedReader bufferedReader;
        String lines = "";

        try {
            if (new File(path).isFile()) {
                bufferedReader = new BufferedReader(new FileReader(path));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    lines += line + '\n';
                }
            }
            else {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return lines;
    }

    /**
     * Save the directory and its files and subdirectories to VFS.txt
     *
     * @param root : Contains all files and directories(subdirectories)
     */
    @Override
    public void saveHardDisk(Directory root) {
        deleteFile(systemPath);
        saveDisk(root);
    }

    private void saveDisk(Directory root) {
        int nFiles = root.getFiles().size();
        int nFolder = root.getSubDirectories().size();

        String line = root.getDirectoryPath() + "-" + nFiles + "-" + nFolder +
                "\r\n";
        appendOnFile(systemPath, line);

        if (nFiles > 0)
            writeFiles(root.getFiles());
        if (nFolder > 0)
            writeFolder(root.getSubDirectories());
    }

    private void writeFolder(ArrayList<Directory> directories) {
        for (Directory directory :
                directories) {
            saveDisk(directory);
        }
    }

    private void writeFiles(ArrayList<_File> files) {
        for (_File file : files) {
            String lines = file.getFilePath() + "-" + file.getStartIndex() + "-" +
                    file.getSize() + "\r\n";
            appendOnFile(systemPath, lines);
        }
    }

    private void appendOnFile(String path, String lines) {
        try {
            Files.write(Paths.get(path), lines.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteFile(String path) {
        try (FileChannel outChannel = new FileOutputStream(new File(path), true).getChannel()) {
            outChannel.truncate(0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int allocateFile(_File file) {
        if (file.getStartIndex() != -1)
        {
            int start = file.getStartIndex();
            int size = file.getSize();
            boolean validBlocks = true;
            for (int i=0; i<size; ++i) {
                if (blocks.get(i+start) != -1) {
                    validBlocks = false;
                    break;
                }
            }
            if (validBlocks) {
                for (int i = 0; i < size; i++) {
                    blocks.set(i+start, 1);
                }
                return start;
            }
            else {
                System.out.println("Invalid index for the block");
                return -1;
            }
        }

        ArrayList<Integer> startIndexes = new ArrayList<>();
        ArrayList<Integer> blocksSize = new ArrayList<>();

        getFreeBlocks(startIndexes, blocksSize);

        int startIndex = getStartIndex(startIndexes, blocksSize, file.getSize()) ;

        if (startIndex != -1) {
            file.setStartIndex(startIndex);
            for (int i=0; i<file.getSize(); ++i) {
                blocks.set(startIndex+i, 1);
                file.getAllocatedBlocks().add(startIndex+i);
            }
        }

        return startIndex;
    }

    private int getStartIndex(ArrayList<Integer> startIndexes, ArrayList<Integer> blocksSize, int size) {
        return 0;
    }

    private void getFreeBlocks(ArrayList<Integer> startIndexes, ArrayList<Integer> blocksSize) {

    }

    @Override
    public void deAllocateFile(_File file) {
        int start = file.getStartIndex();

        for(int i=0; i<file.getSize(); ++i) {
            blocks.set(i+start, -1);
        }
    }

    @Override
    public void displayDiskStatus() {
        int nAllocated = 0, nFree = 0;
        for (int i = 0; i< blocks.size(); ++i) {
            if (i%10 == 0) {
                System.out.println();
                System.out.print(blocks.get(i) + " ");
            }
            if (blocks.get(i) != -1) {
                nAllocated++;
            }
            else
                nFree++;
        }
        System.out.println("\n\nNumber of allocated blocks: " + nAllocated);
        System.out.println("\n\nNumber of free blocks: " + nFree);
        System.out.println("\n\nTotal Number of blocks: " + blocks.size());
    }

}
