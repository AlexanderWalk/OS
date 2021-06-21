package scheduler.task;

import devices.keyboard.KeyboardReadonlyEventbuffer;
import scheduler.Scheduler;

public abstract class InputTask extends Task{

    protected KeyboardReadonlyEventbuffer buffer;
    protected boolean outOfFocus = false;

    @Override
    public void terminateTask(){
        this.terminated=true;
        this.buffer.clearBuffer();
    }

    @Override
    public boolean hasInput(){
        return true;
    }

    public void setBuffer(KeyboardReadonlyEventbuffer buffer){
        this.buffer=buffer;
    }

    protected boolean canExecute(){
            return this.isFocused()&&this.buffer!=null;
    }

    protected boolean isFocused(){
        boolean focus = this==Scheduler.getCurrInputTask();
        if(focus==false){
            this.outOfFocus=true;
        }
        return focus;
    }

    protected boolean wasOutOfFcous(){
        if(this.outOfFocus){
            this.outOfFocus = false;
            return true;
        }
        return false;
    }
}
