package scheduler;

import devices.StaticV24;
import output.console.DebugConsole;
import rte.DynamicRuntime;
import rte.SClassDesc;
import scheduler.task.InputTask;
import scheduler.task.Task;
import scheduler.util.InputPriorityStack;
import scheduler.util.TaskBuffer;

public class Scheduler {

    private static int maxTasks = 32;
    //Doch Array statt Ringbuffer - weil Ringbuffer ohne overflow verfehlt ein wenig das eigentliche Ziel
    //TODO private
    //public static TaskBuffer tasks;
    private static Task[] tasks;
    private static int taskptr = 0;
    private static InputPriorityStack inputPriority;

    static{
        tasks=new Task[maxTasks];
        //tasks = new TaskBuffer(maxTasks);
        inputPriority = new InputPriorityStack(maxTasks);
    }

    public static boolean addTask(Task t){
        for(int i=0; i<maxTasks;i++){
            if(tasks[i]==null||tasks[i].hasTerminated()){
                tasks[i]=t;
                if(t.hasInput()){
                    inputPriority.push((InputTask)t);
                }
                return true;
            }
        }
        return false;
    }

    public static void delTask(Task t){
        t.terminateTask();
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
            Task currTask = tasks[taskptr];
            if(taskptr==maxTasks-1){
                taskptr=0;
            }
            else
                taskptr++;
            if(currTask == null || currTask.hasTerminated()){
                continue;
            }
            currTask.execute();
        }
    }

    public static InputTask getCurrInputTask(){
        return inputPriority.peak();
    }
}
