package sheduler.task;

import devices.keyboard.KeyboardReadonlyEventbuffer;

public abstract class InputTask extends Task{

    protected KeyboardReadonlyEventbuffer buffer;
    protected boolean outOfFocus = false;

    @Override
    public boolean hasInput(){
        return true;
    }

    public void setBuffer(KeyboardReadonlyEventbuffer buffer){
        this.buffer=buffer;
    }
}
