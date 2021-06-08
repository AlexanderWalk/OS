package paint;

import devices.keyboard.Key;
import devices.keyboard.KeyboardEvent;
import output.vesa.VESAGraphics;
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
        if(!this.isFocused()||this.buffer==null){
            return;
        }
        while(this.buffer.canRead()&&!this.hasTerminated()){
            KeyboardEvent event = this.buffer.readEvent();
            if(event.alt||event.control){
                this.handleCommand(event);
            }
        }
    }

    public void handleCommand(KeyboardEvent event){
        if(event.control) {
            switch (event.keyCode) {
                case Key.c:
                    this.terminateTask();
                    break;
            }
        }
    }
}
