package VFS.Allocator;

import VFS.VFileManager._File;

public interface Allocator {
    int allocateFile(_File file);
}
