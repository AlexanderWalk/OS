package sheduler.task;

import devices.keyboard.KeyboardReadonlyEventbuffer;

public abstract class InputTask extends Task{

    protected KeyboardReadonlyEventbuffer buffer;

    @Override
    public boolean hasInput(){
        return true;
    }

    public void setBuffer(KeyboardReadonlyEventbuffer buffer){
        this.buffer=buffer;
    }
}
