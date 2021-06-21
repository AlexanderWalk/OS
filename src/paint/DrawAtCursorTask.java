package paint;

import paint.bitmap.BitmapData;
import scheduler.task.Task;

public class DrawAtCursorTask extends Task{

    private final int x;
    private final int y;
    private final int size;
    private final int color;
    private final BitmapData colorData;

    public DrawAtCursorTask(int x, int y, int size, int color, BitmapData data){
        this.x=x;
        this.y=y;
        this.size=size;
        this.color=color;
        this.colorData=data;
    }

    @Override
    public void execute() {
        for (int j = 0; j < this.size; j++) {
            for (int i = 0; i < this.size; i++) {
                this.colorData.getData()[i+x][j+y]=color;
                PaintTask.graphics.setPixel(i + this.x, j + this.y, color);
            }
        }
        this.terminateTask();
    }
}
