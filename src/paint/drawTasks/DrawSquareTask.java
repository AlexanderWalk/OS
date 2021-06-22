package paint.drawTasks;

import paint.PaintTask;
import scheduler.task.Task;

public class DrawSquareTask extends Task {

    protected final int xOffset;
    protected final int yOffset;
    protected final int size;
    protected final int color;

    public DrawSquareTask(int x, int y, int size, int color){
        this.xOffset =x;
        this.yOffset =y;
        this.size=size;
        this.color=color;
    }

    @Override
    public void execute() {
        for (int j = 0; j < this.size; j++) {
            for (int i = 0; i < this.size; i++) {
                this.setPixel(i,j);
            }
        }
        this.terminateTask();
    }

    protected void setPixel(int x, int y){
        PaintTask.graphics.setPixel(x + this.xOffset, y + this.yOffset, color);
    }
}
