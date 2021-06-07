package VFS.Allocator;

import VFS.VFileManager.Directory;
import VFS.VFileManager._File;

import java.util.ArrayList;

public class Contiguous implements Allocator{

    private ArrayList<Integer> nBlocks = new ArrayList<>();

    private final String SystemPath = "VFS.txt";
    private int loadIndex = 1;
    private String[] vfsContentList;

    public Contiguous(int blocks) {
        for (int i=0; i<blocks; ++i)
            nBlocks.add(-1);
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

    }

    /**
     * Save the directory and its files and subdirectories to VFS.txt
     *
     * @param root : Contains all files and directories(subdirectories)
     */
    @Override
    public void saveHardDisk(Directory root) {

    }

    @Override
    public int allocateFile(_File file) {
        if (file.getStartIndex() != -1)
        {
            int start = file.getStartIndex();
            int size = file.getSize();
            boolean validBlocks = true;
            for (int i=0; i<size; ++i) {
                if (nBlocks.get(i+start) != -1) {
                    validBlocks = false;
                    break;
                }
            }
            if (validBlocks) {
                for (int i = 0; i < size; i++) {
                    nBlocks.set(i+start, 1);
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
                nBlocks.set(startIndex+i, 1);
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

    }

    @Override
    public void displayDiskStatus() {

    }
}
