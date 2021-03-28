package Output;

import Colors.ConsoleColor;
import Colors.FontColor;

class VideoMemory {
    //80x25 Auflösung -> 2000 Zeichen, 2 Byte/Zeichen -> Ascii + Farbe
    private static final int rowCount =80;
    private static final int bytesPerRow = rowCount *2;
    private static final int columnCount = 25;
    private static final int memoryLength = bytesPerRow * columnCount;
    private static final int startAddress =0xB8000;
    private static final int endAddress = startAddress + memoryLength;
    public static final int clearColor = 0x00;
    private static final int spaceKey = 0x20;
    private static int vidMemCurr = startAddress;

    static void clearVideoMemory(){
        vidMemCurr = startAddress;
        while(vidMemCurr < endAddress){
            MAGIC.wMem8(vidMemCurr++,(byte)spaceKey);
            MAGIC.wMem8(vidMemCurr++,(byte)clearColor);
        }
        VideoMemory.vidMemCurr = VideoMemory.startAddress;
    }

    static void outputChar(char c, int color){
        checkVideoMemoryOverflow(2);
        MAGIC.wMem8(vidMemCurr++, (byte)c);
        MAGIC.wMem8(vidMemCurr++, (byte)color);
    }

    private static void checkVideoMemoryOverflow(int byteCount){
        if(vidMemCurr+byteCount>=endAddress)
            vidMemCurr=startAddress;
    }

    static void nextRow(){
        checkVideoMemoryOverflow(bytesPerRow);
        //vidMemCurr=(vidMemCurr+bytesPerRow-2)&~rowCount;
        //relativen Platz in der Zeile holen.
        int temp = (vidMemCurr-startAddress)%bytesPerRow;
        //restlichen Bytes zum auffüllen der Zeile bestimmen.
        int temp2 = bytesPerRow-temp;
        vidMemCurr += temp2;
    }

}
