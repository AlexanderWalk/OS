package paint.panel;

import binimp.ByteData;
import paint.bitmap.Bitmap;
import paint.modes.CursorMode;
import paint.modes.DrawMode;
import paint.modes.ModeBase;

public class ControlPanel extends PanelBase{

    private int activeMode=-1;
    private int currModeCount;
    private int maxModeCount=2;
    private IconPanel[] modes;

    public ControlPanel(){
        this.height=300;
        this.width=100;
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

    public ModeBase getMode(){
        return this.modes[activeMode].getMode();
    }

    protected void addMode(IconPanel panel){
        if(this.modes == null){
            this.currModeCount = 0;
            modes = new IconPanel[2];
        }
        if(this.maxModeCount == this.currModeCount){
            this.maxModeCount*=2;
            IconPanel[] temp = new IconPanel[maxModeCount];
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
        PanelBase spaceButton = new BitmapPanel(new Bitmap(ByteData.spaceButton),this,10,10);
        this.addChild(spaceButton);
        IconPanel mouseIcon = new IconPanel(new Bitmap(ByteData.mouseIcon),this,10,50,new CursorMode());
        this.addMode(mouseIcon);
        this.addChild(mouseIcon);
        IconPanel pencilIcon = new IconPanel(new Bitmap(ByteData.pencilIcon),this,60,50, new DrawMode());
        this.addMode(pencilIcon);
        this.addChild(pencilIcon);
        this.setupMode();
    }

    private void setupMode(){
        if(this.activeMode==-1 && this.modes!=null){
            this.modes[0].activate();
            this.activeMode=0;
        }
    }
}