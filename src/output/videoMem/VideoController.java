package output.videoMem;

public class VideoController {

    //boundaries
    private static final int firstRow = 0;
    private static final int firstEntry = 0;
    private static final int lastRow = Video.rowCount-1;
    private static final int lastEntry = Video.entriesPerRow -1;
    //Position in rel Address
    private static int currRow = 0;
    private static int currEntryPosition = 0;
    //Space for clearing
    private static final char space = ' ';

    public static void writeChar(char c, int color){
        Video.setCharAtPosition(c,color,currRow,currEntryPosition);
        increaseCurrPosition(color);
    }

    public static void deleteChar(int color){
        decreaseCurrPosition();
        Video.setCharAtPosition(space,color,currRow,currEntryPosition);
        updateCursor();
    }

    @SJC.Inline
    public static void newLine(int color){
        nextRow(color);
    }

    public static void clearMemory(int color){
        resetCurrPosition();
        Video.clearMemory(color);
    }

    public static void updateCursor(){
        Video.updateCursor(currRow,currEntryPosition);
    }

    public static void setCursorAtPos(int positionInRow, int row) {
        currEntryPosition = positionInRow;
        currRow = row;
        Video.updateCursor(row,positionInRow);
    }

    private static void increaseCurrPosition(int color) {
        if (currEntryPosition == lastEntry) {
            nextRow(color);
        } else {
            currEntryPosition++;
        }
    }

    private static void decreaseCurrPosition(){
        if(currEntryPosition == firstEntry){
            prevRow();
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
        Video.clearRow(currRow, color);
        updateCursor();
    }

    private static void prevRow(){
        currEntryPosition = lastEntry;
        if(currRow == firstRow)
            currRow = lastRow;
        else
            currRow--;
    }
}
