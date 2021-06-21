package paint;

import paint.bitmap.BitmapData;
import scheduler.task.Task;

public class ShowCursorTask extends Task {

    private final int x;
    private final int y;
    private final int size;
    private final BitmapData colorData;

    public ShowCursorTask(int x, int y, int size, BitmapData colorData){
        this.x=x;
        this.y=y;
        this.size=size;
        this.colorData=colorData;
    }

    @Override
    public void execute() {
        for(int j=0;j<this.size;j++) {
            for (int i = 0; i < this.size; i++) {
                this.colorData.getData()[i+x][j+y] = this.colorData.getData()[i+x][j+y] ^ 0xFFFFFF;
                PaintTask.graphics.setPixel(i + this.x, j + this.y, (this.colorData.getData()[i+x][j+y]));
            }
        }
        this.terminateTask();
    }
}
