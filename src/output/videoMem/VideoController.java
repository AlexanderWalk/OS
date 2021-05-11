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
        increaseCurrPosition();
    }

    public static void deleteChar(int color){
        decreaseCurrPosition();
        Video.setCharAtPosition(space,color,currRow,currEntryPosition);
    }

    public static void tab(){
        int newPosOffset = 4 - currEntryPosition%4;
        for(int i = 0; i<newPosOffset; i++){
            increaseCurrPosition();
        }
    }

    @SJC.Inline
    public static void newLine(int color){
        nextRow();
        Video.clearRow(currRow, color);
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
        updateCursor();
    }

    public static void increaseCurrPosition() {
        if (currEntryPosition == lastEntry) {
            nextRow();
        } else {
            currEntryPosition++;
        }
        updateCursor();
    }

    public static void decreaseCurrPosition(){
        if(currEntryPosition == firstEntry){
            prevRow();
        }else{
            currEntryPosition--;
        }
        updateCursor();
    }

    private static void resetCurrPosition(){
        currRow=firstRow;
        currEntryPosition=firstEntry;
        updateCursor();
    }

    private static void nextRow(){
        currEntryPosition = firstEntry;
        if(currRow == lastRow)
            currRow=firstRow;
        else
            currRow++;
    }

    private static void prevRow(){
        currEntryPosition = lastEntry;
        if(currRow == firstRow)
            currRow = lastRow;
        else
            currRow--;
    }
}
