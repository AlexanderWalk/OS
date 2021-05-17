package scheduler.util;

import dataStructures.StackBase;
import scheduler.task.InputTask;
import scheduler.task.Task;

public class InputPriorityStack extends StackBase {

    private InputTask[] tasks;

    public InputPriorityStack(){
        this(defaultSize);
    }

    public InputPriorityStack(int size){
        super(size);
        this.tasks = new InputTask[size];
    }

    public void push(InputTask t){
        if(this.canPush()){
            this.increaseStackPointer();
            this.tasks[stackPointer]=t;
        }
    }

    //use only if canPop
    public InputTask pop(){
        InputTask t = this.peak();
        this.decreaseStackPointer();
        return t;
    }

    //use only if canPop
    public InputTask peak(){
        Task t = this.tasks[stackPointer];
        if(t==null){
            //In case of custom delete
            //pop with Stackpointer == 0 should never happen because Terminal is always on Bottom level
            this.pop();
            t=this.peak();
        }
        return this.tasks[stackPointer];
    }

    public void customDelete(Task t){
        for(int i = 0; i<size;i++){
            if(t==tasks[i]){
                tasks[i]=null;
            }
        }
    }
}
