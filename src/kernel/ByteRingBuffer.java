package kernel;

import output.Console;

public class ByteRingBuffer {
    private static final int defaultSize = 64;
    private int bufferSize;
    private byte[] buffer;
    private int readPtr=0;
    private int writePtr=0;
    private Console c = new Console();

    public ByteRingBuffer(){
        this(defaultSize);
    }

    public ByteRingBuffer(int size){
        this.buffer= new byte[size];
        this.bufferSize=size;
    }

    public void writeByte(byte b){
        this.buffer[this.writePtr]= b;
        //debug
        //c.printlnHex(this.buffer[this.writePtr]);
        //endofdebug
        this.increaseWritePointer();
    }

    private void increaseWritePointer(){
        if(this.writePtr<this.bufferSize)
            this.writePtr++;
        else
            this.writePtr=0;
    }

    //Use only if canReadOrPeek is true
    public byte peekByte(){
        return this.buffer[this.readPtr];
    }

    public boolean canReadOrPeekByte(){
        return this.readPtr!=this.writePtr;
    }

    //Use only if canReadOrPeek is true
    public byte readByte(){
        byte b = this.peekByte();
        this.increaseReadPointer();
        return b;
    }

    private void increaseReadPointer(){
        if(readPtr!=writePtr){
            if(this.readPtr<this.bufferSize)
                this.readPtr++;
            else
                this.readPtr=0;
        }
    }

    public void clearBuffer(){
        this.writePtr=0;
        this.readPtr=0;
    }
}
