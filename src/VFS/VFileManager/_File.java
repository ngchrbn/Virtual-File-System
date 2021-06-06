package VFS.VFileManager;

import java.util.ArrayList;

public class _File {
    private String filePath;

    private ArrayList<Integer> allocatedBlocks = new ArrayList<>();

    private boolean deleted = false;

    private int size;

    public _File(int size) {
        setSize(size);
    }

    public _File(String filePath, int size) {
        setFilePath(filePath);
        setSize(size);
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setAllocatedBlocks(ArrayList<Integer> allocatedBlocks) {
        this.allocatedBlocks = allocatedBlocks;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getSize() {
        return size;
    }

    public ArrayList<Integer> getAllocatedBlocks() {
        return allocatedBlocks;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return filePath;
    }
}
