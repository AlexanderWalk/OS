package scheduler.task;

public abstract class Task {
    protected boolean terminated;

    public boolean hasTerminated(){
        return terminated;
    }
    public void terminateTask(){ this.terminated=true;}

    public abstract void execute();

    public boolean hasInput(){
        return false;
    }

}
