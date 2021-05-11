package output.videoMem;

public class Video {

    //phys Address
    private static final int startAddress =0xB8000;
    private static final VideoMemory videoMemory = (VideoMemory) MAGIC.cast2Struct(startAddress);
    //rel Address
    static final int entriesPerRow =80;
    static final int rowCount = 25;
    //Space for clearing
    private static final char space = ' ';


    static void updateCursor(int row, int entryPosition){
        int cursorPos = row * entriesPerRow + entryPosition;
        MAGIC.wIOs8(0x3D4, (byte)0x0F);
        MAGIC.wIOs8(0x3D5, (byte)(cursorPos&0xFF));
        MAGIC.wIOs8(0x3D4, (byte)0x0E);
        MAGIC.wIOs8(0x3D5, (byte)((cursorPos>>8)&0xFF));
    }

    static void setCharAtPosition(char c, int color, int row, int entryPosition){
        int pos = getVidMemPosition(row, entryPosition);
        assert videoMemory != null;
        videoMemory.entry[pos].ascii = (byte) c;
        videoMemory.entry[pos].color = (byte)color;
    }

    static void clearRow(int row, int color){
        for(int i = 0; i<Video.entriesPerRow;i++){
            Video.setCharAtPosition(space, color, row, i);
        }
    }

    static void clearMemory(int color){
        for(int i = 0; i<Video.rowCount;i++){
            clearRow(i,color);
        }
    }

    private static int getVidMemPosition(int row, int entryPosition){
        return (row * entriesPerRow) + entryPosition;
    }


}
