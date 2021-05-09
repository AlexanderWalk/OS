package sheduler;

import output.console.DebugConsole;
import sheduler.task.InputTask;
import sheduler.task.Task;
import sheduler.util.InputPriorityStack;
import sheduler.util.TaskBuffer;

public class Scheduler {

    private static int maxTasks = 32;
    //Doch Array statt Ringbuffer - weil Ringbuffer ohne overflow verfehlt ein wenig das eigentliche Ziel
    private static TaskBuffer tasks;
    private static InputPriorityStack inputPriority;

    static{
        tasks = new TaskBuffer(maxTasks);
        inputPriority = new InputPriorityStack(maxTasks);
    }

    public static boolean addTask(Task t){
        if(tasks.canAddTask()){
            tasks.addTask(t);
            if(t.hasInput()){
                inputPriority.push((InputTask)t);
            }
            return true;
        }
        return false;
    }

    public static void delTask(Task t){
        tasks.delTask(t);
        if(t.hasInput()){
            if(t == inputPriority.peak()){
                inputPriority.pop();
            }else{
                inputPriority.customDelete(t);
            }
        }
    }

    public static void schedule(){
        while(true){

            //Can never be false
            if(tasks.canRead()){
                Task currTask = tasks.getTask();
                if(currTask == null){
                    continue;
                }
                currTask.execute();
                if(!currTask.hasTerminated()){
                    tasks.addTask(currTask);
                }else{
                    delTask(currTask);
                }
            }
        }
    }

    public static InputTask getCurrInputTask(){
        return inputPriority.peak();
    }
}
