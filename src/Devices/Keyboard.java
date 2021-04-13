package Devices;

import kernel.ByteRingBuffer;
import output.Console;

public class Keyboard {
    private static ByteRingBuffer buffer;

    static{
        buffer = new ByteRingBuffer();
    }

    //store Byte from Keyboard Interrupt
    public static void storeByte(){
        byte b = MAGIC.rIOs8(0x60);
        //ignore if code >>
        if(b>0xE2)
            return;
        buffer.writeByte(b);
        //debug
        Console c = new Console();
        c.printlnHex(b);
    }
}
