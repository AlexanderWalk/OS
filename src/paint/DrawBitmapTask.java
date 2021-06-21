package paint;

import output.vesa.VESAModeSelector;
import paint.bitmap.Bitmap;
import scheduler.task.Task;

public class DrawBitmapTask extends Task {

    private final Bitmap bitmap;
    private final int xOffset;
    private final int yOffset;

    public DrawBitmapTask(Bitmap bitmap, int xOffset, int yOffset){
        this.bitmap = bitmap;
        this.xOffset=xOffset;
        this.yOffset=yOffset;
    }

    @Override
    public void execute() {
        if(PaintTask.graphics.curMode==null){
            VESAModeSelector.standardModeSelector(bitmap.getInfoHeader());
        }
        this.drawBitmap();
        this.terminateTask();
    }

    private void drawBitmap(){
        for(int j=0;j<bitmap.getInfoHeader().getBitmapHeight();j++){
            for(int i=0;i<bitmap.getInfoHeader().getBitmapWidth();i++){
                PaintTask.graphics.setPixel(i+xOffset,j+yOffset,bitmap.getBitmapData().getData()[i][j]);
            }
        }
    }
}
