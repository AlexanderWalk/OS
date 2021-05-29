package scheduler.task.executable;

import scheduler.task.Task;

public class ForceBreakPoint extends Task {
    @Override
    public void execute() {
        MAGIC.inline(0xCC);
        this.terminateTask();
    }
}
