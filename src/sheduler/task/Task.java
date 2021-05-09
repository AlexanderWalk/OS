package sheduler.task;

public abstract class Task {

    protected boolean terminated;

    public boolean hasInput(){
        return false;
    }

    public abstract void execute();

    public boolean hasTerminated(){
        return terminated;
    }
}
