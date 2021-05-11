package sheduler.task;

public abstract class Task {

    private boolean terminated;

    public boolean hasInput(){
        return false;
    }

    public abstract void execute();

    protected void setTerminationStatus(boolean status){
        this.terminated=status;
    }

    public boolean hasTerminated(){
        return terminated;
    }
}
