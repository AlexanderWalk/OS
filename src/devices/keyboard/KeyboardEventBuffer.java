package devices.keyboard;

public class KeyboardEventBuffer extends KeyboardReadonlyEventbuffer{

    public KeyboardEventBuffer(){
        super();
    }

    public KeyboardEventBuffer(int size){
        super(size);
    }

    public void writeEvent(KeyboardEvent e){
        this.buffer[this.writePtr]= e;
        this.increaseWritePointer();
    }
}