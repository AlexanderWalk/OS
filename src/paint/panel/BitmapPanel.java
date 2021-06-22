package paint.panel;

import paint.DrawBitmapTask;
import paint.bitmap.Bitmap;
import scheduler.Scheduler;

public class BitmapPanel extends PanelBase{
    protected Bitmap bitmap;

    public BitmapPanel(PanelBase parent, int xOffset, int yOffset, Bitmap bitmap){
        super(parent,xOffset,yOffset);
        this.bitmap=bitmap;
        this.height = this.bitmap.getInfoHeader().getBitmapHeight();
        this.width = this.bitmap.getInfoHeader().getBitmapWidth();
    }

    @Override
    public void draw() {
        this.drawChildren();
        Scheduler.addTask(new DrawBitmapTask(this.bitmap,this.getAbsoluteX(),this.getAbsoluteY()));
    }
}
