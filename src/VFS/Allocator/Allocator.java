package VFS.Allocator;

import VFS.VFileManager.Directory;
import VFS.VFileManager._File;

public interface Allocator {

    /**
     * Set all blocks as empty blocks
     * @param nBlocks: number of blocks to fill
     */
    void initialize(int nBlocks);
    /**
     * Load all files and directories from VFS.txt, and
     * Store them in the root directory
     * @param root: Store all files and directories(subdirectories)
     */
    void loadHardDisk(Directory root);

    /**
     * Save the directory and its files and subdirectories to VFS.txt
     * @param root: Contains all files and directories(subdirectories)
     */
    void saveHardDisk(Directory root);

    int allocateFile(_File file);

    void deAllocateFile(_File file);

    void displayDiskStatus();
}
