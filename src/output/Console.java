package output;

import output.colors.*;

public class Console {
    public final ConsoleColor currentColor= new ConsoleColor();

    public void setColor(int font, int back){
        this.currentColor.setColor(font,back);
    }

    public void setCursor(int x, int y){
        VideoMemory.setCursor(x,y);
    }

    public void clearConsole(){
        VideoMemory.clearMemory();
    }

    public static void clearConsoleDebug(){
        VideoMemory.clearMemory();
    }

    public static void directDebugPrint(String string) {
        if(string!=null)
            for (int i=0; i<string.length(); i++)
                directDebugPrint(string.charAt(i));
    }

    public static void directDebugPrint(char c) {
        VideoMemory.writeChar(c,StaticColors.back_black|StaticColors.font_grey);
    }

    public void println(){
        VideoMemory.newLine();
    }

    public void print(char c) {
        VideoMemory.writeChar(c,this.currentColor.getColor());
    }

    public void println(char c){
        this.print(c);
        this.println();
    }

    public void print(String string) {
        if(string!=null)
        for (int i=0; i<string.length(); i++)
            this.print(string.charAt(i));
    }

    public void println(String string){
        this.print(string);
        this.println();
    }

    public void print(int value){
        this.print((long)value);
    }

    public void println(int value){
        this.print(value);
        this.println();
    }

    public void print(long value){
        this.printRecursiveInt(this.handleNegativevalue(value));
    }

    public void println(long value){
        this.print(value);
        this.println();
    }

    //TODO: Rekursiven Aufruf durch Array ersetzen?
    private void printRecursiveInt(long value){
        //Höchste Stelle als erstes ausgeben
        int charOffset = 48;
        char currChar = (char)(value%10+charOffset);
        value/=10;
        if(value>0)
            this.printRecursiveInt(value);
        this.print(currChar);
    }

    private long handleNegativevalue(long value){
        if(value<0){
            this.print('-');
            value*=-1;
        }
        return value;
    }

    public void printHex(byte b){
        int byteCount=1;
        this.printHexvalue((int)b,byteCount);
    }

    public void printlnHex(byte b){
        this.printHex(b);
        this.println();
    }

    public void printHex(short value){
        int byteCount=2;
        this.printHexvalue((int)value,byteCount);
    }

    public void printlnHex(short value){
        this.printHex(value);
        this.println();
    }

    public void printHex(int value){
        int byteCount=4;
        this.printHexvalue(value,byteCount);
    }

    public void printlnHex(int value){
        this.printHex(value);
        this.println();
    }

    //TODO: Überprüfen, da nur 32 Bit..
    public void printHex(long value){
        int byteCount=8;
        int lowerInt = (int)value;
        value = value>>32;
        int higherInt = (int)value;
        this.printHexvalue((int)value,byteCount);
    }

    public void printlnHex(long value){
        this.printHex(value);
        this.println();
    }

    //TODO: Hex überarbeiten, lesbarer machen, ggf eigene Klasse -> HexHandler
    //Uses printByteAsHex for every Byte from heighest one to lowest one
    private void printHexvalue(int value, int byteCount){
        int byteOffset=8;
        int currentByte;
        int currentOffset=byteOffset*(byteCount-1);
        this.printHexPrefix();
        while(currentOffset>=0){
            currentByte=value>>currentOffset;
            printByteAsHex((byte)currentByte);
            currentOffset-=byteOffset;
        }
    }

    //prints a single byte as Hex
    private void printByteAsHex(byte b){
        byte hexMask = 0x0F;
        byte HexOffset = 4;
        int firstHex = b & hexMask;
        b= (byte) (b>>(byte)HexOffset);
        int secondHex = b & hexMask;
        this.print(getHexChar(secondHex));
        this.print(getHexChar(firstHex));
    }

    private void printHexPrefix(){
        this.print("0x");
    }

    //Converts int value to Hex value.
    private char getHexChar(int value){
        int charOffset = 48;
        int hexCharOffset = 7;
        if(value>=10)
            charOffset+= hexCharOffset;
        return (char)(value+charOffset);
    }
}
