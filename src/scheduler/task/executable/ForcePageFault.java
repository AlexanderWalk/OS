package scheduler.task.executable;

import scheduler.task.Task;

public class ForcePageFault extends Task {

    private final int firstPageAddr = 0x0;
    private final int lastPageAddr = 0xFFFFFFFF;
    private int addr=firstPageAddr;

    @Override
    public void execute() {
        Object obj = MAGIC.cast2Obj(addr);
        obj = obj._r_next;
        this.terminateTask();
    }

    public void testFirstPage(){
        this.addr=this.firstPageAddr;
    }

    public void testLastPage(){
        this.addr=this.lastPageAddr;
    }
}
