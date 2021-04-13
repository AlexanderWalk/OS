package Devices;

import DataStructures.ByteRingBuffer;
import output.Console;

//TODO: Dynamic Device
public class Keyboard {
    private static ByteRingBuffer buffer;
    private static boolean shift, control, alt;
    private static boolean capslock, numlock, scrolllock;

    static{
        buffer = new ByteRingBuffer();
    }

    //store Byte from Keyboard Interrupt
    public static void storeByte(){
        byte b = MAGIC.rIOs8(0x60);
        //ignore if code >>
        if((b&0xFF)>=0xE2)
            return;
        buffer.writeByte(b);
        //debug
        Console c = new Console();
        c.printlnHex(b);
    }

    public static void processInputBuffer(){
        while(buffer.canReadOrPeekByte()){
            byte b = buffer.readByte();
            if((b&0xFF)==0xE0){

            } else if((b&0xFF)==0xE1){

            } else{

            }
        }
    }

    public static boolean eventAvailable(){
        return false;
        //if(buffer.canReadOrPeekByte())
    }

}
