package paint.modes;

import devices.keyboard.Key;
import devices.keyboard.KeyboardEvent;
import paint.Cursor;
import paint.ShowCursorTask;
import paint.bitmap.BitmapData;
import paint.panel.selectable.InputModePanel;
import scheduler.Scheduler;

public class CursorMode extends ModeBase{

    private int lastCursorX=0;
    private int lastCursorY=0;
    private int lastCursorSize=0;

    public CursorMode(InputModePanel panelToObserve, Cursor cursor, BitmapData data) {
        super(panelToObserve, cursor, data);
    }

    @Override
    public void handleInput(KeyboardEvent event) {
        switch(event.keyCode){
            case Key.UP_ARROW:
                this.cursor.moveUp();
                this.showCursor();
                break;
            case Key.DOWN_ARROW:
                this.cursor.moveDown();
                this.showCursor();
                break;
            case Key.LEFT_ARROW:
                this.cursor.moveLeft();
                this.showCursor();
                break;
            case Key.RIGHT_ARROW:
                this.cursor.moveRight();
                this.showCursor();
                break;
            case Key.ENTER:
                this.showCursor();
                break;
        }
    }

    @Override
    public void activate() {
        this.lastCursorSize=0;
        this.lastCursorX=0;
        this.lastCursorY=0;
        this.showCursor();
    }

    private void showCursor(){
        Scheduler.addTask(new ShowCursorTask(lastCursorX,lastCursorY,lastCursorSize,this.colorData));
        this.lastCursorSize=this.cursor.getSquareSize();
        this.lastCursorX=this.cursor.getX();
        this.lastCursorY=this.cursor.getY();
        Scheduler.addTask(new ShowCursorTask(lastCursorX,lastCursorY,lastCursorSize,this.colorData));
    }

}
