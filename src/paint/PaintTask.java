package paint;

import binimp.ByteData;
import devices.StaticV24;
import devices.keyboard.Key;
import devices.keyboard.KeyboardEvent;
import output.vesa.VESAGraphics;
import paint.bitmap.Bitmap;
import paint.bitmap.BitmapBuilder;
import scheduler.task.InputTask;


public class PaintTask extends InputTask {

    private final VESAGraphics graphic;
    private BitmapBuilder bitmapBuilder;

    public PaintTask(){
        this.graphic = VESAGraphics.detectDevice();
        this.buildBitmap();
    }

    private void buildBitmap(){
        Bitmap bitmap = new Bitmap(ByteData.gandalf);
        if(!bitmap.IsValid()){
            StaticV24.println("PaintTask: Bitmap is not valid");
        }else {
            this.bitmapBuilder = new BitmapBuilder(this.graphic, bitmap);
        }
    }

    @Override
    public void execute() {
        if(!this.isFocused()||this.buffer==null){
            return;
        }
        this.handleInput();
    }

    private void handleInput(){
        while(this.buffer.canRead()&&!this.hasTerminated()){
            KeyboardEvent event = this.buffer.readEvent();
            if(event.alt||event.control){
                this.handleCommand(event);
            }
        }
    }

    private void handleCommand(KeyboardEvent event){
        if(event.control) {
            switch (event.keyCode) {
                case Key.c:
                    this.terminateTask();
                    this.graphic.setTextMode();
                    break;
                case Key.s:
                    this.bitmapBuilder.drawBitmap();
                    break;
                case Key.m:
                    this.graphic.printModi();
                    break;
            }
        }
    }
}
