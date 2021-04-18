package DataStructures;

public class ByteRingBuffer extends RingBufferBase{

    private byte[] buffer;

    public ByteRingBuffer(){
        this(defaultSize);
    }

    public ByteRingBuffer(int size){
        super(size);
        this.buffer= new byte[size];
    }

    public void writeByte(byte b){
        this.buffer[this.writePtr]= b;
        this.increaseWritePointer();
    }

    //Use only if canReadOrPeek is true
    public byte peekByte(){
        return this.buffer[this.readPtr];
    }

    //Use only if canReadOrPeek is true
    public byte readByte(){
        byte b = this.peekByte();
        this.increaseReadPointer();
        return b;
    }
}
