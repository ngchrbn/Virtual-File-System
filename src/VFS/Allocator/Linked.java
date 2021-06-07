package VFS.Allocator;

import VFS.VFileManager.Directory;
import VFS.VFileManager._File;

public class Linked implements Allocator{
    public Linked(int blocks) {

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
        return 0;
    }

    @Override
    public void deAllocateFile(_File file) {

    }

    @Override
    public void displayDiskStatus() {

    }
}
