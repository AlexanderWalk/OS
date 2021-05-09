package dataStructures;

public abstract class StackBase {
    protected static final int defaultSize = 64;
    protected int size;
    protected int stackPointer=0;

    public StackBase(){
        this(defaultSize);
    }

    public StackBase(int size){
        this.size =size;
        //init stack-array with size
    }

    public boolean canPush(){
        return stackPointer != size -1;
    }

    public boolean canPop(){
        return stackPointer != 0;
    }

    protected void increaseStackPointer(){
        if(canPush())
            stackPointer++;
    }

    protected void decreaseStackPointer(){
        if(canPop())
            stackPointer--;
    }
}
