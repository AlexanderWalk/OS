package paint.modes;

import paint.Cursor;
import paint.bitmap.BitmapData;
import paint.panel.selectable.InputModePanel;

public class ModeCreator {

    private Cursor cursor;
    private BitmapData colorData;
    private ModeHandler handler;

    public ModeCreator(Cursor cursor, BitmapData data, ModeHandler handler){
        this.cursor=cursor;
        this.colorData=data;
        this.handler=handler;
    }

    public void observeCursorMode(InputModePanel panel){
        ModeBase mode = new CursorMode(panel, this.cursor, this.colorData);
        this.handleRegistration(panel,mode);
    }

    public void observeDrawMode(InputModePanel panel){
        DrawMode mode = new DrawMode(panel,this.cursor,this.colorData);
        this.handleRegistration(panel,mode);
    }

    private void handleRegistration(InputModePanel panel, ModeBase mode){
        panel.register(mode);
        mode.register(this.handler);
    }
}
