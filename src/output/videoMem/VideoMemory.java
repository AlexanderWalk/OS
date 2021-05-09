package output.videoMem;

public class VideoMemory {
    //rel Address
    private static final int rowCount = 25;
    private static final int entriesPerRow =80;
    private static final int bytesPerEntry = 2;
    private static final int firstRow = 0;
    private static final int firstEntry = 0;
    private static final int lastRow = rowCount-1;
    private static final int lastEntry = entriesPerRow -1;
    private static int currRow = 0;
    private static int currEntryPosition = 0;

    //phys Address
    private static final int startAddress =0xB8000;
    private static final int memoryByteCount = entriesPerRow * bytesPerEntry * rowCount;
    private static final int endAddress = startAddress + memoryByteCount;

    //const for clearing
    private static final char space = ' ';

    public static void writeChar(char c, int color){
        setCharAtPosition(c,color,currRow,currEntryPosition);
        increaseCurrPosition(color);
    }

    public static void delEntry(int color){
        decreaseCurrPosition(color);
        setCharAtPosition(space,color,currRow,currEntryPosition);
        updateCursor();
    }

    public static void newLine(int color){
        nextRow(color);
    }

    public static void clearMemory(int color){
        resetCurrPosition();
        for(int i = firstRow; i<rowCount;i++){
            clearRow(i,color);
        }
    }

    public static void setCursor(int positionInRow, int row) {
        updateCursor(getCursorPos(positionInRow,row));
    }

    public static void updateCursor(){
        updateCursor(getCursorPos(currEntryPosition,currRow));
    }

    private static int getCursorPos(int entryPos, int row){
        return row*entriesPerRow+entryPos;
    }

    private static void updateCursor(int cursorPos){
        MAGIC.wIOs8(0x3D4, (byte)0x0F);
        MAGIC.wIOs8(0x3D5, (byte)(cursorPos&0xFF));
        MAGIC.wIOs8(0x3D4, (byte)0x0E);
        MAGIC.wIOs8(0x3D5, (byte)((cursorPos>>8)&0xFF));
    }

    //Returns real Address from relative X and Y
    private static int getVidMemPosition(int row, int entryPosition){
        return startAddress + (row * entriesPerRow + entryPosition)*bytesPerEntry;
    }

    private static void setCharAtPosition(char c, int color, int row, int entryPosition){
        int pos = getVidMemPosition(row, entryPosition);
        MAGIC.wMem8(pos, (byte)c);
        MAGIC.wMem8(pos+1, (byte)color);
    }

    private static void increaseCurrPosition(int color) {
        if (currEntryPosition == lastEntry) {
            nextRow(color);
        } else {
            currEntryPosition++;
        }
    }

    private static void decreaseCurrPosition(int color){
        if(currEntryPosition == firstEntry){
            prevRow(color);
        }else{
            currEntryPosition--;
        }
    }

    private static void resetCurrPosition(){
        currRow=firstRow;
        currEntryPosition=firstEntry;
        updateCursor();
    }

    private static void nextRow(int color){
        currEntryPosition = firstEntry;
        if(currRow == lastRow)
            currRow=firstRow;
        else
            currRow++;
        clearRow(currRow, color);
    }

    private static void prevRow(int color){
        currEntryPosition = lastEntry;
        if(currRow == firstRow)
            currRow = lastRow;
        else
            currRow--;
    }

    private static void clearRow(int row, int color){
        for(int i = firstEntry; i<entriesPerRow;i++){
            setCharAtPosition(space, color, row, i);
        }
    }
}
