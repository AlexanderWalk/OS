package paint.panel.selectable;

import binimp.ByteData;
import paint.bitmap.Bitmap;
import paint.drawTasks.DrawSquareTask;
import paint.panel.BitmapPanel;
import paint.panel.PanelBase;
import paint.settings.ColorViewObserver;
import scheduler.Scheduler;

public class ColorSettingPanel extends SettingPanel{
    private ColorViewObserver observer;
    private final int color;
    private final int colorSampleSize=14;
    protected static int innerOffset = 3;
    //TODO: only 24Bit Colormode

    public ColorSettingPanel(PanelBase parent, int xOffset, int yOffset, int color) {
        super(parent, xOffset+innerOffset, yOffset+innerOffset);
        this.color=color;
        this.iconBorderActive = new BitmapPanel(this,-innerOffset,-innerOffset,new Bitmap(ByteData.activeSettingBorder));
        this.iconBorderInactive = new BitmapPanel(this,-innerOffset,-innerOffset,new Bitmap(ByteData.settingBorder));
        this.currIconBorder = iconBorderInactive;
    }

    public void register(ColorViewObserver observer){
        this.observer=observer;
    }

    protected void updateObserver() {
        this.observer.update(this.color);
    }

    @Override
    public void enterSetting(){
        this.updateObserver();
    }

    @Override
    public void draw() {
        this.currIconBorder.draw();
        Scheduler.addTask(new DrawSquareTask(this.getAbsoluteX(),this.getAbsoluteY(),this.colorSampleSize,this.color));
    }
}
