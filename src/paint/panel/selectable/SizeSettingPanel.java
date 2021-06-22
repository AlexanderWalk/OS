package paint.panel.selectable;

import binimp.ByteData;
import paint.drawTasks.DrawSquareTask;
import paint.bitmap.Bitmap;
import paint.panel.BitmapPanel;
import paint.panel.PanelBase;
import paint.settings.SizeViewObserver;
import scheduler.Scheduler;

public class SizeSettingPanel extends SettingPanel{
    private SizeViewObserver observer;
    private final int cursorSize;
    //TODO: only 24Bit Colormode
    private final int color = 0xFFFFFF;

    public SizeSettingPanel(PanelBase parent, int xOffset, int yOffset, int cursorSize) {
        super(parent, xOffset+(20-cursorSize)/2, yOffset+(20-cursorSize)/2);
        int off = (20-cursorSize)/2;
        this.cursorSize=cursorSize;
        this.iconBorderActive = new BitmapPanel(this,-off,-off,new Bitmap(ByteData.activeSettingBorder));
        this.iconBorderInactive = new BitmapPanel(this,-off,-off,new Bitmap(ByteData.settingBorder));
        this.currIconBorder = iconBorderInactive;
    }

    public void register(SizeViewObserver observer){
        this.observer=observer;
    }

    protected void updateObserver() {
        this.observer.update(this.cursorSize);
    }

    @Override
    public void enterSetting(){
        this.updateObserver();
    }

    @Override
    public void draw() {
        this.currIconBorder.draw();
        Scheduler.addTask(new DrawSquareTask(this.getAbsoluteX(),this.getAbsoluteY(),this.cursorSize,this.color));
    }
}
