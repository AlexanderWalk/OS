package paint;

import devices.StaticV24;
import devices.keyboard.Key;
import devices.keyboard.KeyboardEvent;
import output.console.Console;
import output.vesa.VESAGraphics;
import output.vesa.VESAMode;
import scheduler.Scheduler;
import scheduler.task.ConsoleTask;
import scheduler.task.InputTask;


public class PaintTask extends InputTask {

    public VESAGraphics graphic;
    //public VESAMode modes;

    public PaintTask(){
        graphic = VESAGraphics.detectDevice();
    }

    @Override
    public void execute() {
        //byte[] test = ByteData.gandalf;
        if(!this.isFocused()){
            return;
        }
        while(buffer!=null && buffer.canRead()&&!this.hasTerminated()){
            KeyboardEvent event = buffer.readEvent();
            if(event.alt||event.control){
                this.handleCommand(event);
            } else {
            }
        }
    }

    public void handleCommand(KeyboardEvent event){
        if(event.control) {
            switch (event.keyCode) {
                case Key.c:
                    this.terminateTask();
                    Scheduler.paintTaskIndex=-1;
                    break;
            }
        }
    }
}
