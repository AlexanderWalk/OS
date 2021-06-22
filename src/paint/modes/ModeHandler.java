package paint.modes;

public class ModeHandler extends ModeObserver {

    private ModeBase currMode;

    @Override
    public void update(ModeBase mode, boolean isActive) {
        if(isActive){
            this.currMode=mode;
        }
    }

    public ModeBase getCurrMode(){
        return this.currMode;
    }
}
