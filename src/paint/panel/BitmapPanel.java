package paint.panel;

import paint.BitmapTask;
import paint.bitmap.Bitmap;
import scheduler.Scheduler;

public class BitmapPanel extends PanelBase{
    protected Bitmap bitmap;

    public BitmapPanel(Bitmap bitmap, PanelBase parent, int xOffset, int yOffset){
        this.bitmap=bitmap;
        this.parent=parent;
        this.topLeftX = xOffset;
        this.topLeftY = yOffset;
        this.height = this.bitmap.getInfoHeader().getBitmapHeight();
        this.width = this.bitmap.getInfoHeader().getBitmapWidth();
    }

    @Override
    public void draw() {
        for(int i=0;i<this.currChildCount;i++){
            this.children[i].draw();
        }
        Scheduler.addTask(new BitmapTask(this.bitmap,this.getAbsoluteX(),this.getAbsoluteY()));
    }
}
