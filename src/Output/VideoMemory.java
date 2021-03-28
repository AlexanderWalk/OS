package Output;

//TODO: Aufsplitten in zwei Klassen - relative VideoAdresse aus column*rowPosition erstellen zur leichten Bestimmung
// von Zeilen/Bildschirmüberlauf
class VideoMemory {
    private static final int rowCount =80;
    private static final int bytesPerRow = rowCount *2;
    private static final int columnCount = 25;
    private static final int memoryLength = bytesPerRow * columnCount;
    private static final int startAddress =0xB8000;
    private static final int endAddress = startAddress + memoryLength;
    private static final int clearColor = 0x00;
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
        //TODO:Mit &~ zu einem Einzeiler?
        //relativen Platz in der Zeile holen.
        int temp = (vidMemCurr-startAddress)%bytesPerRow;
        //restlichen Bytes zum auffüllen der Zeile bestimmen.
        int temp2 = bytesPerRow-temp;
        vidMemCurr += temp2;
    }

    //TODO Cursor Update nach ausgabe
    static void setCursor(int x, int y) {
        if(x<0||x>rowCount||y<0||y>columnCount){
            //TODO
            return;
        }
        int position = y*bytesPerRow+x;
        MAGIC.wIOs8(0x3D4, (byte)0x0F);
        MAGIC.wIOs8(0x3D5, (byte)(position&0xFF));
        MAGIC.wIOs8(0x3D4, (byte)0x0E);
        MAGIC.wIOs8(0x3D5, (byte)((position>>8)&0xFF));
    }
}
