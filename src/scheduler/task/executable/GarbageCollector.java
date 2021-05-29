package scheduler.task.executable;

import rte.DynamicRuntime;
import scheduler.task.Task;

public class GarbageCollector extends Task {

    private int i = 0;
    @Override
    public void execute() {
        if(i==10000){
            i=0;
            DynamicRuntime.garbageCollection();
        }
        i++;
    }
}
