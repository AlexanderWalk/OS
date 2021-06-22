package paint.modes;

import devices.keyboard.Key;
import devices.keyboard.KeyboardEvent;
import paint.Cursor;
import paint.DrawAtCursorTask;
import paint.bitmap.BitmapData;
import paint.panel.InputModePanel;
import scheduler.Scheduler;

public class DrawMode extends ModeBase{

    public DrawMode(InputModePanel panelToObserve, Cursor cursor, BitmapData data) {
        super(panelToObserve, cursor, data);
    }

    @Override
    public void handleInput(KeyboardEvent event) {
        switch(event.keyCode){
            case Key.UP_ARROW:
                this.cursor.moveUp();
                this.drawAtCursor();
                break;
            case Key.DOWN_ARROW:
                this.cursor.moveDown();
                this.drawAtCursor();
                break;
            case Key.LEFT_ARROW:
                this.cursor.moveLeft();
                this.drawAtCursor();
                break;
            case Key.RIGHT_ARROW:
                this.cursor.moveRight();
                this.drawAtCursor();
                break;
        }
    }

    @Override
    protected void activate() {
        this.drawAtCursor();
    }

    private void drawAtCursor(){
        Scheduler.addTask(new DrawAtCursorTask(this.cursor.getX(),this.cursor.getY(),
                this.cursor.getSquareSize(),0x00FF00, this.colorData));
    }
}
