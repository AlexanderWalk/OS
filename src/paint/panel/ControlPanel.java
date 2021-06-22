package paint.panel;

import binimp.ByteData;
import paint.bitmap.Bitmap;
import paint.modes.ModeCreator;

public class ControlPanel extends PanelBase{

    public static final int panelHeight=300;
    public static final int panelWidth=100;
    private int activeMode=-1;
    private int currModeCount;
    private int maxModeCount=2;
    private InputModePanel[] modes;
    private ModeCreator modeCreator;

    public ControlPanel(ModeCreator factory){
        this.height=ControlPanel.panelHeight;
        this.width=ControlPanel.panelWidth;
        this.modeCreator=factory;
    }

    @Override
    public void draw() {
        this.createControlPanel();
        for(int i=0;i<this.currChildCount;i++){
            this.children[i].draw();
        }
    }

    public int getHeight(){
        return this.height;
    }

    public int getWidth(){
        return this.width;
    }

    public void setStartPos(int topLeftX, int topLeftY){
        this.topLeftX=topLeftX;
        this.topLeftY=topLeftY;
    }

    public void updateMode(){
        if(activeMode==currModeCount-1){
            this.modes[activeMode].deactivate();
            this.modes[0].activate();
            activeMode=0;
        }else{
            this.modes[activeMode].deactivate();
            activeMode++;
            this.modes[activeMode].activate();
        }
    }

    protected void addMode(InputModePanel panel){
        if(this.modes == null){
            this.currModeCount = 0;
            modes = new InputModePanel[maxModeCount];
        }
        if(this.maxModeCount == this.currModeCount){
            this.maxModeCount*=2;
            InputModePanel[] temp = new InputModePanel[maxModeCount];
            for(int i=0;i<currModeCount;i++){
                temp[i]=modes[i];
            }
            this.modes=temp;
        }
        this.modes[currModeCount] = panel;
        this.currModeCount++;
    }

    private void createControlPanel(){
        this.addBorder(3);
        this.createModeSection();
    }

    private void createModeSection(){
        PanelBase spaceButton = new BitmapPanel(new Bitmap(ByteData.spaceButton),this,10,10);
        this.addChild(spaceButton);
        InputModePanel mouseIcon = new InputModePanel(new Bitmap(ByteData.mouseIcon),this,10,50);
        this.modeCreator.createCursorMode(mouseIcon);
        this.addMode(mouseIcon);
        this.addChild(mouseIcon);
        InputModePanel pencilIcon = new InputModePanel(new Bitmap(ByteData.pencilIcon),this,60,50);
        this.modeCreator.createDrawMode(pencilIcon);
        this.addMode(pencilIcon);
        this.addChild(pencilIcon);
        this.setInitialMode();
    }

    private void setInitialMode(){
        this.modes[0].activate();
        activeMode=0;
    }
}
