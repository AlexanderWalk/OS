package scheduler.util;

import dataStructures.RingBufferBase;
import scheduler.task.Task;

public class TaskBuffer extends RingBufferBase {
    private Task[] buffer;

    public TaskBuffer(){this(defaultSize);}

    public TaskBuffer(int size){
        super(size);
        this.buffer = new Task[size];
    }

    public boolean canAddTask(){
        return(!((this.writePtr+1)==this.readPtr || ((this.writePtr==this.bufferSize-1) && this.readPtr==0)));
    }

    //Use only if canAdd is true
    public void addTask(Task t){
        this.buffer[this.writePtr]=t;
        this.increaseWritePointer();
    }

    //Use only if canRead is true
    public Task getTask(){
        Task t = this.buffer[this.readPtr];
        this.increaseReadPointer();
        return t;
    }

    public void delTask(Task t){
        int currReadPointer = this.readPtr;
        while(this.canRead()){
            Task currTask = this.buffer[this.readPtr];
            if(t == currTask){
                this.buffer[this.readPtr]=null;
            }
            this.increaseReadPointer();
        }
        this.readPtr=currReadPointer;
    }
}


