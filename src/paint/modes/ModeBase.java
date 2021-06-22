package paint.modes;

import devices.keyboard.KeyboardEvent;
import paint.Cursor;
import paint.bitmap.BitmapData;
import paint.panel.selectable.InputModePanel;

public abstract class ModeBase extends ModeViewObserver{
    private ModeObserver observer[];
    private int curObserverCount;
    private int maxObserverCount = 5;
    private boolean isActive=false;
    private InputModePanel observedPanel;
    protected Cursor cursor;
    protected BitmapData colorData;

    public ModeBase(InputModePanel panelToObserve, Cursor cursor, BitmapData data){
        this.observedPanel = panelToObserve;
        this.cursor=cursor;
        this.colorData=data;
    }

    public abstract void handleInput(KeyboardEvent event);
    protected abstract void activate();

    public void register(ModeObserver observer){
        if(this.observer==null){
            this.observer = new ModeObserver[this.maxObserverCount];
            this.curObserverCount=0;
        }
        if(this.curObserverCount==this.maxObserverCount){
            this.extendObserverArray();
        }
        this.observer[this.curObserverCount]=observer;
        this.curObserverCount++;
    }

    private void extendObserverArray(){
        this.maxObserverCount*=2;
        ModeObserver[] temp = new ModeObserver[this.maxObserverCount];
        for(int i=0; i<curObserverCount;i++){
            temp[i]=this.observer[i];
        }
        this.observer=temp;
    }

    protected void updateObserver(){
        for(int i=0;i<curObserverCount;i++){
            this.observer[i].update(this,this.isActive);
        }
    }

    public void update(){
        this.isActive = this.observedPanel.isActive();
        if(this.isActive){
            this.activate();
        }
        this.updateObserver();
    }
}
