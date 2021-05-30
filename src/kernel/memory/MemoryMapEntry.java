package kernel.memory;

public class MemoryMapEntry {
    public long baseAddress;
    public long length;
    public int type;

    public MemoryMapEntry(long baseAddr, long length, int type) {
        this.baseAddress = baseAddr;
        this.length = length;
        this.type = type;
    }
}
