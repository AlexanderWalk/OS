package paint.drawTasks;

import paint.PaintTask;
import paint.bitmap.BitmapData;

public class ShowCursorTask extends DrawSquareTask {

    private final BitmapData colorData;

    public ShowCursorTask(int x, int y, int size, BitmapData colorData){
        super(x,y,size,0);
        this.colorData=colorData;
    }

    @Override
    protected void setPixel(int x, int y){
        this.colorData.getData()[x+this.xOffset][y+this.yOffset] =
                this.colorData.getData()[x+this.xOffset][y+this.yOffset] ^ 0xFFFFFF;
        PaintTask.graphics.setPixel(x+this.xOffset, y+this.yOffset,
                this.colorData.getData()[x+this.xOffset][y+this.yOffset]);
    }
}
