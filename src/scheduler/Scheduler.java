package scheduler;

import scheduler.task.InputTask;
import scheduler.task.Task;
import scheduler.util.InputPriorityStack;

public class Scheduler {

    private static int maxTasks = 32;
    private static Task[] tasks;
    private static int taskptr = 0;
    private static InputPriorityStack inputPriority;

    static{
        tasks=new Task[maxTasks];
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
        expandArray();
        addTask(t);
        return false;
    }

    private static void expandArray(){
        int oldTaskCount=maxTasks;
        maxTasks*=2;
        Task[] temp = new Task[maxTasks];
        for(int i=0;i<oldTaskCount;i++){
            temp[i]=tasks[i];
        }
        tasks=temp;
    }

    public static void delTask(Task t){
        t.terminateTask();
    }

    public static void schedule(){
        while(true){
            for(taskptr=0;taskptr<maxTasks;taskptr++){
                Task currTask = tasks[taskptr];
                if(currTask == null || currTask.hasTerminated()){
                    continue;
                }
                currTask.execute();
            }
        }
    }

    public static InputTask getCurrInputTask(){
        while(inputPriority.peak().hasTerminated()){
            inputPriority.pop();
        }
        return inputPriority.peak();
    }
}
