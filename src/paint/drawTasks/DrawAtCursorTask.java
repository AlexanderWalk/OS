package paint.drawTasks;

import paint.PaintTask;
import paint.bitmap.BitmapData;

public class DrawAtCursorTask extends DrawSquareTask{

    private final BitmapData colorData;

    public DrawAtCursorTask(int x, int y, int size, int color, BitmapData data){
        super(x,y,size,color);
        this.colorData=data;
    }

    @Override
    protected void setPixel(int x, int y){
        this.colorData.getData()[x+this.xOffset][y+this.yOffset]=color;
        PaintTask.graphics.setPixel(x+this.xOffset, y+this.yOffset, color);
    }
}
