package output.Console;

import output.VideoMem.VideoMemory;
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

    public void println(){
        VideoMemory.newLine();
    }

    //Actual printing
    public void print(char c) {
        VideoMemory.writeChar(c,this.currentColor.getColor());
    }

    public void println(char c){
        this.print(c);
        this.println();
    }

    //Strings
    public void print(String string) {
        if(string!=null)
        for (int i=0; i<string.length(); i++)
            this.print(string.charAt(i));
    }

    public void println(String string){
        this.print(string);
        this.println();
    }
    //Strings end

    //Decimal values
    public void print(long value){
        this.printRecursiveInt(this.handleNegativevalue(value));
    }

    public void println(long value){
        this.print(value);
        this.println();
    }

    public void print(int value){
        this.print((long)value);
    }

    public void println(int value){
        this.print(value);
        this.println();
    }

    public void print(short value){
        this.print((long)value);
    }

    public void println(short value){
        this.print(value);
        this.println();
    }

    //Handling Decimals
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
    //Decimal values end

    //Hex values
    public void printHex(byte b){
        this.printHexvalue(b,1);
    }

    public void printlnHex(byte b){
        this.printHex(b);
        this.println();
    }

    public void printHex(short value){
        this.printHexvalue(value,2);
    }

    public void printlnHex(short value){
        this.printHex(value);
        this.println();
    }

    public void printHex(int value){
        this.printHexvalue(value,4);
    }

    public void printlnHex(int value){
        this.printHex(value);
        this.println();
    }

    public void printHex(long value){
        this.printHexvalue(value, 8);
    }

    public void printlnHex(long value){
        this.printHex(value);
        this.println();
    }

    //Handling Hex
    private void printHexvalue(long value, int byteCount){
        byte[] b = new byte[byteCount];
        int bitmask = 0xFF, arrayIndex=byteCount-1, i;
        //highest Byte at lowest index
        for(i = 0; i < byteCount; i++){
            b[arrayIndex]=(byte)(value&bitmask);
            arrayIndex--;
            value = value>>8;
        }
        this.printHexPrefix();
        for(int j = 0; j<byteCount; j++){
            printByteAsHex(b[j]);
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
