package devices.keyboard;

import dataStructures.RingBufferBase;

public class KeyboardEventBuffer extends RingBufferBase{

    private KeyboardEvent[] buffer;

    public KeyboardEventBuffer(){
        this(defaultSize);
    }

    public KeyboardEventBuffer(int size){
        super(size);
        this.buffer= new KeyboardEvent[size];
    }

    public void writeEvent(KeyboardEvent e){
        this.buffer[this.writePtr]= e;
        this.increaseWritePointer();
    }

    //Use only if canReadOrPeek is true
    public KeyboardEvent peekEvent(){
        return this.buffer[this.readPtr];
    }

    //Use only if canReadOrPeek is true
    public KeyboardEvent readEvent(){
        KeyboardEvent e = this.peekEvent();
        this.increaseReadPointer();
        return e;
    }
}
