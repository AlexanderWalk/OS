package output;

class VideoMemory {
    private static final int startAddress =0xB8000;
    private static final int rowCount = 25;
    private static final int firstRow = 0;
    private static final int lastRow = rowCount-1;
    private static final int entriesPerRow =80;
    private static final int firstEntry = 0;
    private static final int lastEntry = entriesPerRow -1;
    private static final int bytesPerEntry = 2;
    private static final int memoryByteCount = entriesPerRow * bytesPerEntry * rowCount;
    private static final int endAddress = startAddress + memoryByteCount;
    private static int currRow = 0;
    private static int currEntryPosition = 0;
    private static final int clearColor = 0x00;
    private static final char space = ' ';

    static void writeChar(char c, int color){
        setCharAtPosition(c,color,currRow,currEntryPosition);
        increaseCurrPosition();
    }

    static void newLine(){
        nextRow();
    }

    //Returns real Address from relative X and Y
    private static int getVidMemPosition(int row, int entryPosition){
        if(entryPosition<firstEntry||entryPosition>entriesPerRow||row<firstRow||row>rowCount){
            //TODO
        }
        return startAddress + (row * entriesPerRow + entryPosition)*bytesPerEntry;
    }

    private static void setCharAtPosition(char c, int color, int row, int entryPosition){
        int pos = getVidMemPosition(row, entryPosition);
        MAGIC.wMem8(pos, (byte)c);
        MAGIC.wMem8(pos+1, (byte)color);
    }

    private static void increaseCurrPosition() {
        if (currEntryPosition == lastEntry) {
            nextRow();
        } else {
            currEntryPosition++;
        }
    }

    private static void nextRow(){
        currEntryPosition = firstEntry;
        if(currRow == lastRow)
            currRow=firstRow;
        else
            currRow++;
        clearRow(currRow);
    }

    private static void clearRow(int row){
        for(int i = firstEntry; i<entriesPerRow;i++){
            setCharAtPosition(space, clearColor, row, i);
        }
    }

    static void clearMemory(){
        //setCursor(firstEntry,firstRow);
        for(int i = firstRow; i<rowCount;i++){
            clearRow(i);
        }
    }

    static void setCursor(int positionInRow, int row) {
        int position = getVidMemPosition(positionInRow,row);
        MAGIC.wIOs8(0x3D4, (byte)0x0F);
        MAGIC.wIOs8(0x3D5, (byte)(position&0xFF));
        MAGIC.wIOs8(0x3D4, (byte)0x0E);
        MAGIC.wIOs8(0x3D5, (byte)((position>>8)&0xFF));
    }
}
