package paint.panel;

import paint.panel.selectable.InputModePanel;

public class ControlPanelModes {
    private int activeMode;
    private int currModeCount;
    private int maxModeCount=2;
    private InputModePanel[] modes;

    public void updateMode(){
        if(activeMode==currModeCount-1){
            this.modes[activeMode].deactivateBorder();
            this.modes[0].activateBorder();
            activeMode=0;
        }else{
            this.modes[activeMode].deactivateBorder();
            activeMode++;
            this.modes[activeMode].activateBorder();
        }
    }

    void addMode(InputModePanel panel){
        if(this.modes == null){
            this.currModeCount = 0;
            modes = new InputModePanel[maxModeCount];
        }
        if(this.maxModeCount == this.currModeCount){
            this.expandArray();
        }
        this.modes[currModeCount] = panel;
        this.currModeCount++;
    }

    private void expandArray(){
        this.maxModeCount*=2;
        InputModePanel[] temp = new InputModePanel[maxModeCount];
        for(int i=0;i<currModeCount;i++){
            temp[i]=modes[i];
        }
        this.modes=temp;
    }

    void init(){
        this.modes[0].activateBorder();
        activeMode=0;
    }
}
