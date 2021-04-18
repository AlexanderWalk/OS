package DataStructures;

public abstract class RingBufferBase {
    protected static final int defaultSize = 64;
    protected int bufferSize;
    protected int readPtr=0;
    protected int writePtr=0;


    public RingBufferBase(){
        this(defaultSize);
    }

    public RingBufferBase(int size){
        this.bufferSize=size;
        //init buffer-array with size
    }

    public boolean canRead(){
        return this.readPtr!=this.writePtr;
    }

    protected void increaseWritePointer(){
        if(this.writePtr<this.bufferSize)
            this.writePtr++;
        else
            this.writePtr=0;
    }

    protected void increaseReadPointer(){
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
