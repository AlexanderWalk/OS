package paint.panel.selectable;

import binimp.ByteData;
import paint.drawTasks.DrawBitmapTask;
import paint.bitmap.Bitmap;
import paint.modes.ModeViewObserver;
import paint.panel.BitmapPanel;
import paint.panel.PanelBase;
import scheduler.Scheduler;

public class InputModePanel extends SelectionPanelWithBorder {
    private final Bitmap bitmap;
    private ModeViewObserver observer;
    protected static int innerOffset = 3;

    public InputModePanel(Bitmap bitmap, PanelBase parent, int xOffset, int yOffset) {
        super(parent, xOffset, yOffset);
        this.iconBorderActive = new BitmapPanel(this,-innerOffset,-innerOffset,new Bitmap(ByteData.iconBorderActive));
        this.iconBorderInactive = new BitmapPanel(this,-innerOffset,-innerOffset,new Bitmap(ByteData.iconBorder));
        this.currIconBorder = iconBorderInactive;
        this.bitmap=bitmap;
    }

    @Override
    public void activateBorder(){
        super.activateBorder();
        this.updateObserver();
    }

    @Override
    public void deactivateBorder(){
        super.deactivateBorder();
        this.updateObserver();
    }

    @Override
    public void draw() {
        this.currIconBorder.draw();
        this.drawChildren();
        Scheduler.addTask(new DrawBitmapTask(this.bitmap,this.getAbsoluteX(),this.getAbsoluteY()));
    }

    public void register(ModeViewObserver observer){
        this.observer=observer;
    }

    protected void updateObserver(){
        this.observer.update();
    }
}
