package paint;

import paint.bitmap.Bitmap;
import paint.bitmap.BitmapData;
import paint.drawTasks.DrawBitmapTask;
import scheduler.Scheduler;

public class Screen {
    private final int height;
    private final int width;
    private final Cursor cursor;
    private final BitmapData colorData;

    public Screen(Bitmap bitmap, Cursor cursor){
        Scheduler.addTask(new DrawBitmapTask(bitmap,0,0));
        this.height = bitmap.getInfoHeader().getBitmapHeight();
        this.width = bitmap.getInfoHeader().getBitmapWidth();
        this.colorData = bitmap.getBitmapData();
        this.cursor = cursor;
    }
}

