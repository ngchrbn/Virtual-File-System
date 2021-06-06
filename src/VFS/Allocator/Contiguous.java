package VFS.Allocator;

import VFS.VFileManager._File;

public class Contiguous implements Allocator{
    public Contiguous(int blocks) {

    }

    @Override
    public int allocateFile(_File file) {
        return 0;
    }
}
