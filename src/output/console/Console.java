package output.console;

import output.videoMem.VideoController;
import output.colors.*;

public class Console {
    public final ConsoleColor currentColor= new ConsoleColor();

    public void setColor(int font, int back){
        this.currentColor.setColor(font,back);
    }

    public void setCursor(int x, int y){
        VideoController.setCursorAtPos(x,y);
    }

    public void clearConsole(){
        VideoController.clearMemory(this.currentColor.getColor());
    }

    public void println(){
        VideoController.newLine(this.currentColor.getColor());
    }

    public void delchar(){
        VideoController.deleteChar(this.currentColor.getColor());
    }

    //Actual printing
    private void printToVidmem(char c) {
        VideoController.writeChar(c,this.currentColor.getColor());
    }

    public void print(char c){
        this.printToVidmem(c);
        VideoController.updateCursor();
    }


    public void println(char c){
        this.printToVidmem(c);
        this.println();
        VideoController.updateCursor();
    }

    //Strings
    public void print(String string) {
        if(string!=null)
        for (int i=0; i<string.length(); i++)
            this.printToVidmem(string.charAt(i));
        VideoController.updateCursor();
    }

    public void println(String string){
        this.print(string);
        this.println();
        VideoController.updateCursor();
    }
    //Strings end

    //Decimal values
    public void print(long value){
        this.printRecursiveInt(this.handleNegativevalue(value));
        VideoController.updateCursor();
    }

    public void println(long value){
        this.print(value);
        this.println();
        VideoController.updateCursor();
    }

    public void print(int value){
        this.print((long)value);
        VideoController.updateCursor();
    }

    public void println(int value){
        this.print(value);
        this.println();
        VideoController.updateCursor();
    }

    public void print(short value){
        this.print((long)value);
        VideoController.updateCursor();
    }

    public void println(short value){
        this.print(value);
        this.println();
        VideoController.updateCursor();
    }

    //Handling Decimals
    private void printRecursiveInt(long value){
        //Höchste Stelle als erstes ausgeben
        int charOffset = 48;
        char currChar = (char)(value%10+charOffset);
        value/=10;
        if(value>0)
            this.printRecursiveInt(value);
        this.printToVidmem(currChar);
    }

    private long handleNegativevalue(long value){
        if(value<0){
            this.printToVidmem('-');
            value*=-1;
        }
        return value;
    }
    //Decimal values end

    //Hex values
    public void printHex(byte b){
        this.printHexvalue(b,1);
        VideoController.updateCursor();
    }

    public void printlnHex(byte b){
        this.printHex(b);
        this.println();
        VideoController.updateCursor();
    }

    public void printHex(short value){
        this.printHexvalue(value,2);
        VideoController.updateCursor();
    }

    public void printlnHex(short value){
        this.printHex(value);
        this.println();
        VideoController.updateCursor();
    }

    public void printHex(int value){
        this.printHexvalue(value,4);
        VideoController.updateCursor();
    }

    public void printlnHex(int value){
        this.printHex(value);
        this.println();
        VideoController.updateCursor();
    }

    public void printHex(long value){
        this.printHexvalue(value, 8);
        VideoController.updateCursor();
    }

    public void printlnHex(long value){
        this.printHex(value);
        this.println();
        VideoController.updateCursor();
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
        this.printToVidmem(getHexChar(secondHex));
        this.printToVidmem(getHexChar(firstHex));
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
