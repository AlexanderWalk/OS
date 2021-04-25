package output.Console;

import output.VideoMem.VideoMemory;
import output.colors.StaticColors;

public class DebugConsole {
    private static final int defaultBack=StaticColors.back_blue;
    private static final int defaultFont=StaticColors.font_white;

    @SJC.Inline
    public static void clearConsoleDebug(){
        VideoMemory.clearMemory(defaultBack|defaultFont);
    }

    @SJC.Inline
    public static void debugPrint(String s) {
        if(s!=null)
            for (int i=0; i<s.length(); i++)
                debugPrint(s.charAt(i));
    }

    @SJC.Inline
    public static void debugPrint(char c) {
        VideoMemory.writeChar(c, defaultBack|defaultFont);
    }

    @SJC.Inline
    public static void debugPrintln(){
        VideoMemory.newLine(defaultBack|defaultFont);
    }

    @SJC.Inline
    public static void debugPrintln(char c){
        debugPrint(c);
        debugPrintln();
    }

    @SJC.Inline
    public static void debugPrintln(String s){
        debugPrint(s);
        debugPrintln();
    }

    public static void debugPrint(int value){
        printRecursiveInt(value);
        VideoMemory.updateCursor();
    }

    public static void debugPrintln(int value){
        debugPrint(value);
        debugPrintln();
    }

    //Handling Decimals
    private static void printRecursiveInt(long value){
        //Höchste Stelle als erstes ausgeben
        int charOffset = 48;
        char currChar = (char)(value%10+charOffset);
        value/=10;
        if(value>0)
            printRecursiveInt(value);
        debugPrint(currChar);
    }


    @SJC.Inline
    public static void debugPrintHex(int value){
        printHexvalue(value,4);
        VideoMemory.updateCursor();
    }

    @SJC.Inline
    public static void debugPrintlnHex(int value){
        debugPrintHex(value);
        debugPrintln();
    }

    @SJC.Inline
    private static void printHexvalue(long value, int byteCount){
        byte[] b = new byte[byteCount];
        int bitmask = 0xFF, arrayIndex=byteCount-1, i;
        //highest Byte at lowest index
        for(i = 0; i < byteCount; i++){
            b[arrayIndex]=(byte)(value&bitmask);
            arrayIndex--;
            value = value>>8;
        }
        printHexPrefix();
        for(int j = 0; j<byteCount; j++){
            printByteAsHex(b[j]);
        }
    }


    @SJC.Inline
    //prints a single byte as Hex
    private static void printByteAsHex(byte b){
        byte hexMask = 0x0F;
        byte HexOffset = 4;
        int firstHex = b & hexMask;
        b= (byte) (b>>(byte)HexOffset);
        int secondHex = b & hexMask;
        debugPrint(getHexChar(secondHex));
        debugPrint(getHexChar(firstHex));
    }

    @SJC.Inline
    private static void printHexPrefix(){
        debugPrint("0x");
    }

    @SJC.Inline
    //Converts int value to Hex value.
    private static char getHexChar(int value){
        int charOffset = 48;
        int hexCharOffset = 7;
        if(value>=10)
            charOffset+= hexCharOffset;
        return (char)(value+charOffset);
    }
}

