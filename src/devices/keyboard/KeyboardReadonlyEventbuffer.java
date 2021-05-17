package devices.keyboard;

import dataStructures.RingBufferBase;
import output.console.DebugConsole;

public class KeyboardReadonlyEventbuffer extends RingBufferBase {

    protected KeyboardEvent[] buffer;

    public KeyboardReadonlyEventbuffer(){
        this(defaultSize);
    }

    public KeyboardReadonlyEventbuffer(int size){
        super(size);
        this.buffer= new KeyboardEvent[size];
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
