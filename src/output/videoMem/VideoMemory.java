package output.videoMem;

public class VideoMemory extends STRUCT {
    @SJC(count = 2000)
    public VideoEntry[] entry;
}
