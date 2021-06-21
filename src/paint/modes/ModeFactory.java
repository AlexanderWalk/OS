package paint.modes;

import paint.Cursor;
import paint.bitmap.BitmapData;

public class ModeFactory {

    private Cursor cursor;
    private BitmapData colorData;

    public ModeFactory(Cursor cursor, BitmapData data){
        this.cursor=cursor;
        this.colorData=data;
    }

    public ModeBase getCursorMode(){
        return new CursorMode(this.cursor,this.colorData);
    }

    public ModeBase getDrawMode(){
        return new DrawMode(this.cursor,this.colorData);
    }

}
